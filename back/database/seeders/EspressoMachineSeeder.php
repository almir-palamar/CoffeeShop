<?php

namespace Database\Seeders;

use App\Models\EspressoMachine;
use Illuminate\Database\Seeder;

class EspressoMachineSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run(): void
    {
        EspressoMachine::factory()->count(3)->create();
    }
}
