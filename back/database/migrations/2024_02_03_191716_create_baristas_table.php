<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up(): void
    {
        Schema::create('baristas', function (Blueprint $table) {
            $table->id();
            $table->string('name');
            $table->foreignId('espresso_machine_id')->references('id')->on('espresso_machines');
            $table->enum('status', ['available', 'makingCoffee', 'fillingGrinder'])->default('available');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down(): void
    {
        Schema::dropIfExists('baristas');
    }
};
