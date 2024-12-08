<?php

namespace App\Models;

use App\Enums\OrderSource;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Order extends Model
{

    protected $fillable = [
        'type',
        'total',
        'processing_time',
        'coffee_amount'
    ];

    protected $table = 'orders';

    protected $casts = [
        'type' => OrderSource::class
    ];

    // task defined relationship as 1:1, more realistic would be m:n relationship
    public function coffees(): BelongsToMany
    {
        return $this->belongsToMany(Coffee::class, 'coffee_order', 'order_id')->withTimestamps();
    }

    public function barista(): BelongsTo
    {
        return $this->belongsTo(Barista::class, 'barista_id');
    }

}
