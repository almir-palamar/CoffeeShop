<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run(): void
    {
        $this->call(UserSeeder::class);
        $this->call(CoffeeSeeder::class);
        $this->call(EspressoMachineSeeder::class);
        $this->call(BaristaSeeder::class);
    }
}
