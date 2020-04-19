<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class MemberController extends Controller
{
	//Accept group invitation
    public function acceptInvitation($id)
    {
        //$header = $request->header('Authorization');
        $user_id = Auth::user()->id;
        $member = new Member([
        'team_id' => $id,
        'user_id' => $user_id,
        'role_id' => 2
      ]);   
    }
}
