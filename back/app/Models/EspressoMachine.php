<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasOne;

class EspressoMachine extends Model
{
    use HasFactory;

    protected $fillable = [
        'name',
        'grinder'
    ];

    public function barista(): HasOne
    {
        return $this->hasOne(Barista::class);
    }
}
