<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Laravel\Passport\HasApiTokens;


class Event extends Model
{
	public $timestamps = false;

    public function participants(){
    	return $this->hasMany('App\Participant');
    }

    public function team(){
    	return $this->belongsTo('App\Team');
    }

    protected $fillable = [
    	'name', 'date', 'location', 'description', 'admin'
    ];

   
}
