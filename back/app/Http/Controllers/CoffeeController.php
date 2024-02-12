<?php

namespace App\Http\Controllers;

use App\Http\Requests\StoreCoffeeRequest;
use App\Http\Requests\UpdateCoffeeRequest;
use App\Http\Response\HttpResponse;
use App\Models\Coffee;
use App\Repository\CoffeeRepository;
use Illuminate\Http\JsonResponse;

class CoffeeController extends Controller
{
    protected CoffeeRepository $repository;

    public function __construct(CoffeeRepository $repository)
    {
        $this->middleware('auth', ['except' => ['index']]);
        $this->repository = $repository;
    }

    /**
     * Display a listing of the resource.
     *
     * @return JsonResponse
     */
    public function index(): JsonResponse
    {
        $coffees = Coffee::all();
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
            $coffee = $this->repository->store($request->all());
            return HttpResponse::success('Created', $coffee, 201);
        } catch (\Exception $e) {
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
            $coffee = $this->repository->update($request->all(), $coffee);
            return HttpResponse::success('Created', $coffee, 201);
        } catch (\Exception $e) {
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
            $this->repository->delete($coffee);
            return HttpResponse::success('Deleted', $coffee, 200);
        } catch (\Exception $e) {
            return HttpResponse::error($e->getMessage(), $e);
        }
    }
}
