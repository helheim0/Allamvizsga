<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Laravel\Passport\HasApiTokens;
use App\User;

class Role extends Model
{
    public functions users(){
    	return $this->belongsToMany('App\User');
    }

    public functions userroles(){
    	return $this->hasMany('App\Userrole');
    }
}
