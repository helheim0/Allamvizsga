<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\Resource;
use Illuminate\Support\Facades\Auth;  
use App\User;
use Validator; 

class AuthController extends Controller
{
    public function login(Request $request){ 
      $validator = Validator::make($request->all(), [         
          'email' => 'required|email', 
          'password' => 'required'      
      ]);
      if ($validator->fails()) { 
          return response()->json([
            'error'=> true,
            'message' => "Invalid data!",                    
          ]);
      } 
      
     $credentials = request(['email', 'password']);

        if(!Auth::attempt($credentials)){
            return response()->json([
                'message' => 'Unauthorized'
            ], 401);
          }
        else{
        $user = Auth::user();
        $token = $user->createToken('esemenyszervezes')->accessToken;
        return response()->json([
            'error' => false,
            'message' => 'Successful login!',
            'access_token' => $token,
            'token_type' => 'Bearer',
            'id'=>$user->id
            /*'user' => [
              'id'=>$user->id,
              'name'=>$user->name,
              'email'=>$user->email,*/
                  
                ]);
                   // return $this->responseToken($this->guard());

        }
  }

  public function register(Request $request) 
  { 
    $validator = Validator::make($request->all(), [ 
          'name' => 'required', 
          'full_name' => 'required',
          'phone' => 'required',
          'email' => 'required|email', 
          'password' => 'required' 
    ]);
    if ($validator->fails()) { 
          return response()->json([
            'error'=> true,
            'message' => "Wrong input data!",                    
          ]);
    }
    $postArray = $request->all(); 
    $postArray['password'] = bcrypt($postArray['password']); 
    $user = User::create($postArray); 
    $token =  $user->createToken('eventtoken')->accessToken; 
    

    // Fire off the internal request.
    $token = Request::create(
        'oauth/token',
        'POST'
    );
   return $this->responseToken($this->guard());
  }

  protected function responseToken($token){
    return response()->json([
      'access_token' => $token,
      'token_type' => 'bearer',
      'user' => Auth::user()->id
    ]);
  }

  public function guard(){
    return Auth::guard();
  }

  public function getDetails() 
  { 
    $user = Auth::user(); 
    return response()->json(['success' => $user]); 
  } 

}
