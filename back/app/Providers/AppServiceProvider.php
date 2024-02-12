<?php

namespace App\Providers;

use App\Repository\CoffeeRepository;
use App\Repository\OrderRepository;
use Illuminate\Support\ServiceProvider;
use RepositoryInterface;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     *
     * @return void
     */
    public function register()
    {
        $this->app->bind(RepositoryInterface::class, CoffeeRepository::class);
        $this->app->bind(RepositoryInterface::class, OrderRepository::class);
    }

    /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
        //
    }
}
