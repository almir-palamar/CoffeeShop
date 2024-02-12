<?php

use App\Http\Middleware\CheckPendingOrders;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\CoffeeController;
use App\Http\Controllers\OrderController;

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

Route::apiResources([
    '/coffees' => CoffeeController::class,
    '/orders' => OrderController::class
]);

// coffee to go is accepted by bartender = checkPendingOrders
Route::middleware(CheckPendingOrders::class)->group(function () {
    Route::post('/order-to-go', [OrderController::class, 'orderToGo']);
});

require __DIR__ . '/auth.php';
