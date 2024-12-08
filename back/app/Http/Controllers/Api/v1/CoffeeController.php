<?php

namespace App\Http\Controllers\Api\v1;

use App\Http\Controllers\Controller;
use App\Http\Requests\StoreCoffeeRequest;
use App\Http\Requests\UpdateCoffeeRequest;
use App\Http\Response\HttpResponse;
use App\Models\Coffee;
use App\Services\CoffeeService;
use Exception;
use Illuminate\Http\JsonResponse;

class CoffeeController extends Controller
{
    protected CoffeeService $coffeeService;

    public function __construct(CoffeeService $coffeeService)
    {
        $this->middleware('auth', ['except' => ['index']]);
        $this->coffeeService = $coffeeService;
    }

    /**
     * Display a listing of the resource.
     *
     * @return JsonResponse
     */
    public function index(): JsonResponse
    {
        $coffees = $this->coffeeService->index();
        return HttpResponse::success('Coffees', $coffees, 200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param StoreCoffeeRequest $request
     * @return JsonResponse
     */
    public function store(StoreCoffeeRequest $request): JsonResponse
    {
        try {
            $result = $this->coffeeService->store($request->validated());
            return HttpResponse::success('Created', $result, 201);
        } catch (Exception $e) {
            return HttpResponse::error($e->getMessage(), $e);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param UpdateCoffeeRequest $request
     * @param Coffee $coffee
     * @return JsonResponse
     */
    public function update(UpdateCoffeeRequest $request, Coffee $coffee): JsonResponse
    {
        try {
            $coffee = $this->coffeeService->update($request->validated(), $coffee);
            return HttpResponse::success('Updated', $coffee);
        } catch (Exception $e) {
            return HttpResponse::error($e->getMessage(), $e);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param Coffee $coffee
     * @return JsonResponse
     */
    public function destroy(Coffee $coffee): JsonResponse
    {
        try {
            $this->coffeeService->delete($coffee);
            return HttpResponse::success('Deleted', $coffee);
        } catch (Exception $e) {
            return HttpResponse::error($e->getMessage(), $e);
        }
    }
}
