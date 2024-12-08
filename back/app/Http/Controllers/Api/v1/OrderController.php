<?php

namespace App\Http\Controllers\Api\v1;

use App\Http\Controllers\Controller;
use App\Http\Requests\StoreOrderRequest;
use App\Http\Response\HttpResponse;
use App\Services\OrderService;
use Illuminate\Http\JsonResponse;

class OrderController extends Controller
{
    protected OrderService $orderService;

    public function __construct(OrderService $orderService)
    {
        $this->orderService = $orderService;
    }

    public function store(StoreOrderRequest $request): JsonResponse
    {
        try {
            $order = $this->orderService->storeOrderToGo($request->validated());
            return HttpResponse::success('Created', $order, 201);
        } catch (\Exception $e) {
            return HttpResponse::error('Error', $e->getMessage());
        }
    }
}
