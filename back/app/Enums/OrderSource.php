<?php

namespace App\Enums;

enum OrderSource: string
{
    case WebUIOrder = 'WebUIOrder';
    case OrderToGo = 'OrderToGo';
}
