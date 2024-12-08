<?php

namespace App\Services;

use App\Enums\OrderSource;
use App\Jobs\ProcessOrderJob;
use App\Repositories\OrderRepository;

class OrderService
{
    protected OrderRepository $repository;

    public function __construct(OrderRepository $repository)
    {
        $this->repository = $repository;
    }

    public function storeOrderToGo($attributes)
    {
        $order = $this->repository->store($attributes, OrderSource::OrderToGo);
        ProcessOrderJob::dispatch($order);
        return $order;
    }

    public function storeOrderWebUI($attributes)
    {
        $order = $this->repository->store($attributes, OrderSource::WebUIOrder);
        ProcessOrderJob::dispatch($order);
        return $order;
    }
}
