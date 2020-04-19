<?php

use Illuminate\Database\Seeder;

class EventsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
          DB::table('events')->insert([
            'name'=>'sepsi sic vs voluntari',
            'date' => '2020-12-12',
            'location' => 'Sepsi arena',
            'description' => 'mekkelnyerni',
            'admin' => '2',
        ]);
          DB::table('events')->insert([
            'name'=>'sepsi sic vs voluntari',
            'date' => '2020-12-12',
            'location' => 'Sepsi arena',
            'description' => 'mekkelnyerni',
            'admin' => '1',
        ]);
         DB::table('events')->insert([
            'name'=>'sepsi sic vs cfr',
            'date' => '2020-10-12',
            'location' => 'Sepsi arena',
            'description' => 'mekkelnyerni',
            'admin' => '2',
        ]);
    }
}
