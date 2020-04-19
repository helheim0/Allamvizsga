<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Laravel\Passport\HasApiTokens;

class Participant extends Model
{
   public function events(){
    	return $this->belongsToMany('App\Event');
    }
}
