<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Laravel\Passport\HasApiTokens;


class Member extends Model
{
	public $timestamps = false;

   public function teams(){
        return $this->belongsToMany('App\Team');
    }

    public function events(){
        return $this->belongsToMany('App\Event');
    }
     protected $fillable = [
    	'team_id', 'user_id'
    ];

}
