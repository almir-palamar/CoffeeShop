<?php

namespace App\DTO;

class CoffeeDTO
{
    public readonly string $type;
    public readonly float $price;
    public readonly string $image;
    public readonly int $brew_time;
    public readonly int $coffee_amount;

    public function __construct($type, $price, $image, $brew_time, $coffee_amount)
    {
        $this->type = $type;
        $this->price = $price;
        $this->image = $image;
        $this->brew_time = $brew_time;
        $this->coffee_amount = $coffee_amount;
    }
}
