<?php

namespace App\Http\Controllers;

use App\Http\Requests\StoreOrderRequest;
use App\Http\Requests\UpdateOrderRequest;
use App\Http\Response\HttpResponse;
use App\Jobs\ProcessOrderJob;
use App\Models\OrderToGo;
use App\Models\OrderWebUI;
use App\Repository\OrderRepository;
use Illuminate\Http\JsonResponse;

class OrderController extends Controller
{
    protected OrderRepository $repository;

    public function __construct(OrderRepository $repository)
    {
        $this->repository = $repository;
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param StoreOrderRequest $request
     * @return JsonResponse
     */
    public function store(StoreOrderRequest $request): JsonResponse
    {
        try {
            $order = $this->repository->store($request->all(), new OrderWebUI());
            ProcessOrderJob::dispatch($order);
            return HttpResponse::success('Created', $order, 201);
        } catch (\Exception $e) {
            return HttpResponse::error($e->getMessage(), $e);
        }
    }

    public function orderToGo(StoreOrderRequest $request): JsonResponse
    {
        try {
            $order = $this->repository->store($request->all(), new OrderToGo());
            ProcessOrderJob::dispatch($order);
            return HttpResponse::success('Created', $order, 201);
        } catch (\Exception $e) {
            return HttpResponse::error($e->getMessage(), $e);
        }
    }
}
