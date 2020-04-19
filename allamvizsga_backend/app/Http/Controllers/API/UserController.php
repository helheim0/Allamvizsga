<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Http\Resources\UserResource;
use App\Http\Resources\UserCollection;
use App\User;

class UserController extends Controller
{
   /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $users = User::all();
            return new UserCollection($users);

     //   return $this->sendResponse(UserResource::collection($users), 'Users retrieved successfully.');
    }
    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
     $user = new User([
            'name' => $request->input('name'),
            'full_name'=> $request->input('full_name'),
            'email'=> $request->input('email'),
            'phone'=> $request->input('phone'),
        ]);         
         if($user->save()) {
            return new UserResource($user);
        } 
        return $this->sendResponse(new UserResource($user), 'User created successfully.');
    } 
   
    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
     public function show($id)
    {
        $user = User::find($id);
  
        if (is_null($user)) {
            return $this->sendError('User not found.');
        }
   
        return $this->sendResponse(new UserResource($user), 'User retrieved successfully.');
    }
}
