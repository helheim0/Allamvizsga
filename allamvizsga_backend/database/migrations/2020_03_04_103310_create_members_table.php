<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateMembersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('members', function (Blueprint $table) {
            $table->unsignedBigInteger('team_id')->nullable();
            $table->unsignedBigInteger('user_id')->nullable();
            $table->unsignedBigInteger('role_id');
            $table->foreign('team_id')->references('id')->on('teams')->onDelete('cascade');
            $table->foreign('user_id')->references('id')->on('users')->onDelete('cascade');
            $table->foreign('role_id')->references('id')->on('roles')->onDelete('cascade');

           //$table->primary(['teams_id', 'users_id']);
        });
/*
$sql = <<<SQL
DROP PROCEDURE IF EXISTS create_member_procedure;
CREATE PROCEDURE create_member_procedure(IN team_id INT(10), IN user_id INT(10))
BEGIN
    INSERT INTO members('teams_id','users_id') VALUES(team_id, user_id);
END
SQL;
        DB::unprepared($sql);*/
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
       /* $sql = "DROP PROCEDURE IF EXISTS create_member_procedure";
        DB::connection()->getPdo()->exec($sql);*/
        Schema::drop('members');
    }
}
