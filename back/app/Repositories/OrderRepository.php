<?php

namespace App\Repositories;

use App\Models\Order;
use Illuminate\Support\Facades\DB;

class OrderRepository implements RepositoryInterface
{
    public function store($attributes, $model = null)
    {
        try {
            DB::beginTransaction();

            $order = Order::create([
                'type' => $model,
                'total' => data_get($attributes, 'total'),
                'processing_time' => data_get($attributes, 'processingTime'),
                'coffee_amount' => data_get($attributes, 'coffeeAmount'),
            ]);
            $order->coffees()->attach(data_get($attributes, 'items'));
            $order->save();
            DB::commit();
            return $order;
        } catch (\Exception $e) {
            DB::rollBack();
            return $e;
        }
    }

    public function update(array $attributes, $model)
    {
        // TODO: Implement update() method.
    }

    public function delete($model)
    {
        // TODO: Implement delete() method.
    }
}
