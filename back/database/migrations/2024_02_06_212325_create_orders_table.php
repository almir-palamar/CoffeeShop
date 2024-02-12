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
        Schema::create('orders', function (Blueprint $table) {
            $table->id();
            $table->string('type');
            $table->decimal('total', 10);
            $table->smallInteger('processing_time');
            $table->smallInteger('coffee_amount');
            $table->enum('status', ['delivered', 'pending'])->default('pending');
            $table->foreignId('barista_id')->nullable()->references('id')->on('baristas');
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
        Schema::dropIfExists('orders');
    }
};
