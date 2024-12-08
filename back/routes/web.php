<?php

use App\Http\Controllers\Api\v1\CoffeeController;
use App\Http\Controllers\Api\v1\OrderController as OrderToGoController;
use App\Http\Controllers\App\OrderController;
use App\Http\Middleware\CheckPendingOrders;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/csrf-token', function () {
    return response()->json(['csrf_token' => csrf_token()]);
});

Route::prefix('v1')->group(function () {
    Route::apiResources([
        '/coffees' => CoffeeController::class,
        '/orders' => OrderController::class
    ]);

    // coffee to go is accepted by bartender = checkPendingOrders
    Route::middleware(CheckPendingOrders::class)->group(function () {
        Route::post('/order-to-go', [OrderToGoController::class, 'store']);
    });
});

require __DIR__ . '/auth.php';
