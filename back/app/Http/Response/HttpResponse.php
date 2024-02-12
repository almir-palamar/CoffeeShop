<?php

namespace App\Http\Response;

use Illuminate\Http\JsonResponse;
use Illuminate\Http\Response;

class HttpResponse extends Response
{
    public static function success($message, $data = null, $status = 200): JsonResponse
    {
        return response()->json([
            'type' => 'success',
            'message' => $message,
            'data' => $data,
            'status' => $status
        ]);
    }

    public static function error($message, $data = null, $status = 500): JsonResponse
    {
        return response()->json([
            'type' => 'error',
            'message' => $message,
            'data' => $data,
            'status' => $status
        ]);
    }
}
