<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Laravel\Passport\HasApiTokens;

class Team extends Model
{
	public $timestamps = false;

    public function members(){
        return $this->hasMany('App\Member');
    }

    public function events(){
        return $this->hasMany('App\Event');
    }

    /* 
     * Get the user that 'owns' the team
     */
    public function user(){
    	return $this->belongsTo('App\User');
    }

    protected $fillable = [
    	'name', 'description', 'image', 'admin_id'
    ];

}
