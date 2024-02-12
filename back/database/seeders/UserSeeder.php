<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run(): void
    {
        User::factory()->create([
           'username' => 'admin',
           'password' => '$2a$10$O52hr9s6QEpCtVoqnKjLh.gRcfrsyW8292noNPYnL9KrcW.Sr1qOa' // admin
        ]);
    }
}
