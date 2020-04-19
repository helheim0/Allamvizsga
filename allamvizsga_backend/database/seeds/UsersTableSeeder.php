<?php

use Illuminate\Database\Seeder;

class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('users')->insert([
            'name'=>'bali',
        	'full_name' => 'Kiss Balazs',
            'phone' => '074999999',
        	'email'=> 'kisbalazs@gmail.com',
            'password' => 'admin',
        ]);
        DB::table('users')->insert([
            'name'=>'jani',
        	'full_name' => 'Kiss Janos',
            'phone' => '074999999',
        	'email'=> 'kissjanos@gmail.com',
            'password' => 'admin',
        ]);
    }
}
