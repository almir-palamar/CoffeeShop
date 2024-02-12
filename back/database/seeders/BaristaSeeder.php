<?php

namespace Database\Seeders;

use App\Models\Barista;
use App\Models\EspressoMachine;
use Illuminate\Database\Seeder;

class BaristaSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run(): void
    {
        EspressoMachine::all()->each(function ($espresso_machine) {
            Barista::factory()->create(['espresso_machine_id' => $espresso_machine->id]);
        });
    }
}
