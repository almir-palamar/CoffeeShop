<?php

namespace App\Http\Controllers\App;

use App\Http\Controllers\Controller;
use App\Http\Requests\StoreOrderRequest;
use App\Http\Response\HttpResponse;
use App\Jobs\ProcessOrderJob;
use App\Services\BaristaService;
use App\Services\OrderService;
use Illuminate\Http\JsonResponse;

class OrderController extends Controller
{
    protected OrderService $orderService;
    protected BaristaService $baristaService;

    public function __construct(OrderService $orderService, BaristaService $baristaService)
    {
        $this->orderService = $orderService;
        $this->baristaService = $baristaService;
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
            // problem store-uje se u bazi kad dodje do exception-a
            $order = $this->orderService->storeOrderWebUI($request->validated());
//            ProcessOrderJob::dispatch($order, $this->baristaService);
            return HttpResponse::success('Created', $order, 201);
        } catch (\Exception $e) {
            return HttpResponse::error($e->getMessage(), $e);
        }
    }

//    public function orderToGo(StoreOrderRequest $request): JsonResponse
//    {
//        try {
//            $order = $this->repository->store($request->validated(), new OrderToGo());
//            ProcessOrderJob::dispatch($order);
//            return HttpResponse::success('Created', $order, 201);
//        } catch (\Exception $e) {
//            return HttpResponse::error($e->getMessage(), $e);
//        }
//    }
}
