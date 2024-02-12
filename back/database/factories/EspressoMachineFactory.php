<?php

namespace Database\Factories;

use App\Models\EspressoMachine;
use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends Factory<EspressoMachine>
 */
class EspressoMachineFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'name' => 'Breville Barista Express',
            'grinder' => 300
        ];
    }
}
