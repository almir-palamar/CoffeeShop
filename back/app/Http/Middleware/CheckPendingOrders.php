<?php

namespace App\Http\Middleware;

use App\Http\Response\HttpResponse;
use App\Models\Barista;
use Closure;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\DB;

class CheckPendingOrders
{
    /**
     * Handle an incoming request.
     *
     * @param Request $request
     * @param Closure(Request): (Response|RedirectResponse) $next
     * @return JsonResponse
     */
    public function handle(Request $request, Closure $next)
    {
        if ($this->checkAvailableBaristasAndPendingOrders()) {
            return HttpResponse::error('All baristas are busy', null);
        }
        return $next($request);
    }

    private function checkAvailableBaristasAndPendingOrders(): bool
    {
        return Barista::where('status', '!=', 'available')->count() === config('constants.NUMBER_OF_BARISTAS') &&
            DB::table('jobs')->where('queue', 'database')->count() >= config('constants.MAXIMUM_NUMBER_OF_PENDING_ORDERS');
    }
}
