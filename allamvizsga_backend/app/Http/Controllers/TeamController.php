<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\BaseController as BaseController;
use App\Team;
use App\User;
use App\Member;
use DB;
use Illuminate\Support\Facades\Auth;
use Validator;
use Illuminate\Support\Facades\File;
use Illuminate\Support\Facades\Storage;
use App\Http\Resources\TeamResource as TeamResource;

class TeamController extends BaseController
{
    
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Team::all();
    }

    //show teams for the current user - depending on id
     public function showTeams($id)
    {
        //$header = $request->header('Authorization');
        $teams = DB::table('teams')
            ->join('members', 'teams.id', '=', 'members.team_id')
            ->join('users', 'users.id', '=', 'members.user_id')
            ->where('users.id', '=', $id)
            ->select('teams.id','teams.name', 'teams.description')
            ->get();

        return $teams;
       
    }


    //list members of a team
     public function showMembers($id)
    {
       // $header = $request->header('Authorization');
        $teams = DB::table('teams')
            ->join('members', 'teams.id', '=', 'members.team_id')
            ->join('users', 'users.id', '=', 'members.user_id')
            ->where('members.team_id', '=', $id)
            ->select('users.name', 'users.full_name')
            ->get();

        return $teams;
    }
    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
       // $header = $request->header('Authorization');
        $request->validate([
        'name'=>'required',
        'description' => 'required',
        'admin_id' => 'required'
       // 'image' => 'image|required|mimes:jpeg,png,jpg,gif,svg',
      ]);

        $id = Auth::user()->id;
        $team = new Team([
        'name' => $request->input('name'),
        'description' => $request->input('description'),
        'admin_id' => $id
        //$request->input('admin_id')
      ]);     

      $team->save();
        return response()->json([
                'error' => false,
                'message' => "Team created successfully.",
        ]); 

    } 
   
    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $team = Team::find($id);
  
        if (is_null($team)) {
            return $this->sendError('Team not found.');
        }
   
        return $this->sendResponse(new TeamResource($team), 'Team retrieved successfully.');
    }
    
    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Team $team)
    {
        $input = $request->all();
   
        $validator = Validator::make($input, [
            'name' => 'required',
            'description' => 'required'
        ]);
   
        if($validator->fails()){
            return $this->sendError('Validation Error.', $validator->errors());       
        }
   
        $team->name = $input['name'];
        $team->description = $input['description'];
        $team->save();
   
        return $this->sendResponse(new TeamResource($team), 'Team updated successfully.');
    }
   
    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Team $team)
    {
        $team->delete();
        return $this->sendResponse([], 'Team deleted successfully.');
    }
}
