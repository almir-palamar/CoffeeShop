<?php

namespace App\Jobs;

use App\Enums\OrderSource;
use App\Events\WebUIOrderReadyEvent;
use App\Models\Barista;
use App\Models\Order;
use App\Services\BaristaService;
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
    public function handle(BaristaService $baristaService): void
    {
        // this sould not be done by an job
        // all bussines logic move to service and simplify
        // this job just to call method from service

        $barista = Barista::where('status', 'available')->first(); // should utilize round-robin
        if (!is_null($barista)) {
            $baristaCanDeliverOrder = $baristaService->prepareOrder($barista, $this->order);
            if ($baristaCanDeliverOrder) {
                sleep($this->order->processing_time);
                $barista->status = 'available';
                $barista->save();
                $this->order->barista_id = $barista->id;
                $this->order->status = 'delivered';
                $this->order->save();
                if ($this->order->type === OrderSource::WebUIOrder) {
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
