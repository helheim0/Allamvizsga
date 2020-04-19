<?php

namespace App;

use App\User;
use App\Role;
use Illuminate\Database\Eloquent\Model;

class Userrole extends Model
{
	public $timestamps = false;

    public function users(){
    	return $this->belongsToMany('App\User');
    }

     public function roles(){
    	return $this->belongsToMany('App\Role');
    }

    public function teams(){
        return $this->belongsToMany('App\Team');
    }
      protected $fillable = [
    	'user_id', 'role_id', 'team_id'
    ];
}
