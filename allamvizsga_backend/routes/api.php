<?php

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\Resource;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

//LOGIN
Route::post('login', array('uses' => 'API\AuthController@login','middleware' => ['API']));
//Route::post('login', 'API\AuthController@login');
Route::post('register', 'API\AuthController@register'); 
Route::group(['middleware' => 'auth:api'], function(){
Route::post('details', 'API\AuthController@getDetails');
Route::get('users','API\UserController@index');
});

// USERS routes 
Route::get('users','UserController@index');
Route::get('users/{id}','UserController@show');
Route::post('users','UserController@store');
Route::put('users/{id}','UserController@update');
Route::delete('users/{id}','UserController@destroy');

// TEAMS routes 
//Route::get('teams','TeamController@index');
//teRoute::get('teams/{id}','TeamController@show');
Route::post('teams','TeamController@store');
Route::put('teams/{id}','TeamController@update');
Route::delete('teams/{id}','TeamController@destroy');
Route::post('image','ImageController@createImage');
Route::get('teams/{id}','TeamController@showTeams');
Route::get('teams/{id}/members','TeamController@showMembers');


// EVENTS routes 
Route::get('events','EventController@index');
Route::get('events/{id}','EventController@show');
Route::post('events','EventController@store');
Route::put('events/{id}','EventController@update');
Route::delete('events/{id}','EventController@destroy');
Route::get('events/{id}','EventController@showEvents');

//participants
Route::get('events/{id}/participants','EventController@goingParticipants');
//Route::get('events/{id}/participants','EventController@notGoingParticipants');
