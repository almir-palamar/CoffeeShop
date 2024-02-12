<?php

namespace App\Http\Controllers;

use App\Jobs\ProcessOrderJob;
use App\Models\Barista;
use App\Models\Order;
use Illuminate\Support\Facades\Log;

class BaristaController extends Controller
{

    public function prepareOrder(Barista $barista, Order $order): bool
    {
        if ($this->isThereEnoughCoffee($barista, $order->coffee_amount)) {
            $barista->update([
                'status' => 'makingCoffee'
            ]);
            $barista->espresso_machine->decrement('grinder', $order->coffee_amount);
            return true;
        } else {
            $this->refillGrinder($barista, $order);
        }
        return false;
    }

    public function refillGrinder(Barista $barista, Order $order): void
    {
        $barista->update([
            'status' => 'fillingGrinder'
        ]);
        Log::info('dodje ovde, vrijeme' . conf('constants.TIME_IN_SECONDS_TO_REFILL_GRINDER'));
        sleep(conf('constants.TIME_IN_SECONDS_TO_REFILL_GRINDER'));
        $barista->espresso_machine()->update([
            'grinder' => config('constants.GRINDER_CAPACITY')
        ]);
        $barista->update([
            'status' => 'available'
        ]);
        Log::info('zavrsio grindovanje');
        ProcessOrderJob::dispatch($order);
    }

    public function isThereEnoughCoffee(Barista $barista, $coffeeAmount): bool
    {
        return $barista->espresso_machine->grinder >= $coffeeAmount;
    }
}
