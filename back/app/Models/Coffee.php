<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Database\Eloquent\SoftDeletes;

class Coffee extends Model
{
    use HasFactory;
    use SoftDeletes;

    protected $fillable = [
        'type',
        'price',
        'image',
        'brew_time',
        'coffee_amount'
    ];

    // the task defined relationship as 1:1, more realistic would be m:n relationship
    public function orders(): BelongsToMany
    {
        return $this->belongsToMany(Order::class, 'coffee_order', 'coffee_id');
    }
}
