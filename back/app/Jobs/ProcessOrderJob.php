<?php

namespace App\Jobs;

use App\Events\WebUIOrderReadyEvent;
use App\Http\Controllers\BaristaController;
use App\Models\Barista;
use App\Models\Order;
use Illuminate\Bus\Queueable;
use Illuminate\Contracts\Queue\ShouldQueue;
use Illuminate\Foundation\Bus\Dispatchable;
use Illuminate\Queue\InteractsWithQueue;
use Illuminate\Queue\SerializesModels;
use Illuminate\Support\Facades\Log;

class ProcessOrderJob implements ShouldQueue
{
    use Dispatchable, InteractsWithQueue, Queueable, SerializesModels;

    public int $timeout = 6000; // 10 minutes
    public int $tries = 5;
    public int $backoff = 90;
    protected Order $order;


    /**
     * Create a new job instance.
     *
     * @return void
     */
    public function __construct(Order $order)
    {
        $this->order = $order;
    }

    /**
     * Execute the job.
     *
     * @return void
     */
    public function handle(): void
    {
        $barista = Barista::where('status', 'available')->first();
        if (!is_null($barista)) {
            $baristaController = new BaristaController();
            $baristaCanDeliverOrder = $baristaController->prepareOrder($barista, $this->order);
            if ($baristaCanDeliverOrder) {
                sleep($this->order->processing_time);
                $barista->status = 'available';
                $barista->save();
                $this->order->barista_id = $barista->id;
                $this->order->status = 'delivered';
                $this->order->save();
                if ($this->order->type === 'App\Models\OrderWebUI') {
                    event(new WebUIOrderReadyEvent($this->order));
                }
            }
        } else {
            $this->release(120);
        }
    }

    /**
     * Handle a job failure.
     *
     * @param Throwable $exception
     * @return void
     */
    public function failed(Throwable $exception): void
    {
        Log::error($exception->getMessage());
    }
}
