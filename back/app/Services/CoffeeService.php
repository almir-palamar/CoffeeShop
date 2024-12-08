<?php

namespace App\Services;

use App\Models\Coffee;
use App\Repositories\CoffeeRepository;
use Illuminate\Database\Eloquent\Collection;

class CoffeeService
{
    protected CoffeeRepository $coffeeRepository;

    public function __construct(CoffeeRepository $coffeeRepository)
    {
        $this->coffeeRepository = $coffeeRepository;
    }

    public function index(): Collection
    {
        return Coffee::all();
    }

    public function store($attributes)
    {
        return $this->coffeeRepository->store($attributes);
    }

    public function update($attributes, $coffee)
    {
        return $this->coffeeRepository->update($attributes, $coffee);
    }

    public function destroy($coffee)
    {
        return $this->coffeeRepository->delete($coffee);
    }
}
