<?php

namespace App\Http\Middleware;

use App\Http\Response\HttpResponse;
use Illuminate\Auth\Middleware\Authenticate as Middleware;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class Authenticate extends Middleware
{
    /**
     * Get the path the user should be redirected to when they are not authenticated.
     *
     * @param Request $request
     * @return JsonResponse
     */
    protected function redirectTo($request): JsonResponse
    {
        return HttpResponse::error('Not authenticated', null, 401);
    }
}
