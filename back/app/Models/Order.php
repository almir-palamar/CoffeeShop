<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Order extends Model
{

    protected $fillable = [
        'total',
        'processing_time',
        'coffee_amount'
    ];

    protected $table = 'orders';

    public static function boot(): void
    {
        parent::boot();
        static::creating(function ($model) {
            $model->forceFill(['type' => static::class]);
        });
    }

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
