<?php

namespace App\Providers;

use App\Repositories\CoffeeRepository;
use App\Repositories\OrderRepository;
use App\Repositories\RepositoryInterface;
use App\Services\BaristaService;
use Illuminate\Support\ServiceProvider;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     *
     * @return void
     */
    public function register(): void
    {
        $this->app->bind(RepositoryInterface::class, CoffeeRepository::class);
        $this->app->bind(RepositoryInterface::class, OrderRepository::class);
        $this->app->singleton(BaristaService::class, function () {
            return new BaristaService();
        });
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
