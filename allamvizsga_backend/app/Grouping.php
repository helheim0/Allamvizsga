<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Grouping extends Model
{
	public $timestamps = false;

     protected $fillable = [
    	'team_id', 'event_id'
    ];
}
