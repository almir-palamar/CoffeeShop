<?php

namespace Database\Seeders;

use App\Models\Coffee;
use Illuminate\Database\Seeder;

class CoffeeSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run(): void
    {
        Coffee::factory()->create([
            'type' => 'Espresso',
            'price' => 1.00,
            'image' => 'espresso.jpg',
            'coffee_amount' => 7,
            'brew_time' => 35
        ]);

        Coffee::factory()->create([
            'type' => 'Espresso doppio',
            'price' => 2.00,
            'image' => 'espresso_doppio.jpg',
            'coffee_amount' => 14,
            'brew_time' => 45
        ]);

        Coffee::factory()->create([
            'type' => 'Cappuccino',
            'price' => 2.50,
            'image' => 'cappuccino.jpg',
            'coffee_amount' => 7,
            'brew_time' => 60
        ]);
    }
}
