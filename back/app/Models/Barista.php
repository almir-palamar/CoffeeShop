<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use Illuminate\Database\Eloquent\Relations\HasManyThrough;

class Barista extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'espresso_machine_id',
        'status'
    ];

    public function espresso_machine(): BelongsTo
    {
        return $this->belongsTo(EspressoMachine::class);
    }

    public function orders(): HasManyThrough
    {
        return $this->hasManyThrough(Order::class, 'coffee_order');
    }

    public function withEspressoMachine(): Builder
    {
        return $this->with('espresso_machine');
    }

}
