<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateGroupingsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('groupings', function (Blueprint $table) {
            $table->unsignedBigInteger('teams_id');
            $table->unsignedBigInteger('events_id');
            $table->foreign('teams_id')->references('id')->on('teams')->onDelete('cascade');
            $table->foreign('events_id')->references('id')->on('events')->onDelete('cascade');
            $table->primary(['teams_id', 'events_id']);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('groupings');
    }
}
