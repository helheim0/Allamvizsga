<?php

use Illuminate\Database\Seeder;

class TeamsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
         DB::table('teams')->insert([
            'name'=>'Geeks',
            'description' => 'jaoidfgalkfdjgldffa',
           'image' => 'https://p.bigstockphoto.com/vVu7XprxSayr867oA3KQ_bigstock-Colorful-fruit-pattern-of-fres-282127069.jpg',
        	'admin_id' => '1',
        ]);

         DB::table('teams')->insert([
            'name'=>'Focifanok',
             'description' => 'jaoidfgalkfdjgldffa',
          'admin_id' => '2',
        ]);

         DB::table('teams')->insert([
            'name'=>'Sepsi sic szurkolotabor',
           'description' => 'jaoidfgalkfdjgldffa',
            'admin_id' => '2',
        ]);
    }
}
