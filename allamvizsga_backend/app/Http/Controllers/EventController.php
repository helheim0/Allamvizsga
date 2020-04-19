<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Http\Controllers\BaseController as BaseController;
use App\Event;
use DB;
use Validator;
use App\Http\Resources\EventCollection;
use App\Http\Resources\EventResource as EventResource;

class EventController extends BaseController
{

    //show event within a specific team
    public function showEvents($id){
         //$header = $request->header('Authorization');
         $events = DB::table('events')
            ->join('groupings', 'events.id', '=', 'groupings.events_id')
            ->join('teams', 'teams.id', '=', 'groupings.teams_id')
            ->join('members', 'members.team_id', '=', 'teams.id' )
            ->join('users', 'users.id', '=', 'members.user_id')
            ->where('users.id', '=', $id)
            ->select('events.name','events.date', 'events.location', 'events.description')
            ->get();

        return $events;
    }

    //show participants who can go
    public function goingParticipants($id){
         //$header = $request->header('Authorization');
         $events = DB::table('events')
            ->join('groupings', 'events.id', '=', 'groupings.events_id')
            ->join('participants', 'participants.event_id', '=', $id )
            ->join('users', 'users.id', '=', 'participants.invited_id')
            ->where('participants.going', '=', true)
            ->select('users.name','users.full_name')
            ->get();

        return $events;
    }

    //show participants who can't go
    public function notGoingParticipants($id){
         $header = $request->header('Authorization');
         $events = DB::table('events')
           ->join('groupings', 'events.id', '=', 'groupings.events_id')
            ->join('members', 'members.user_id', '=', 'users.id')
            ->join('participants', 'participants.event_id', '=', 'events.id' )
            ->join('users', 'users.id', '=', 'members.user_id')
            ->where('participants.going', '=', false)
            ->select('users.name','users.full_name')
            ->get();

        return $events;
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Event::all();
        //return new EventCollection($events);
        //return $this->sendResponse(EventResource::collection($events), 'Events retrieved successfully.');
    }
    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
         $header = $request->header('Authorization');
            $event = new Event([
            'name' => $request->input('name'),
            'date'=> $request->input('date'),
            'description'=> $request->input('description'),
            'admin'=> $request->input('admin')
        ]);          
            
        if($event->save()) {
            return new EventResource($event);
        } 
    } 
   
    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $event = Event::find($id);
  
        if (is_null($event)) {
             return response()->json([
                'error' => true,
                'message' => "Event not found.",
            ]);
        }
         return response()->json([
                'error' => false,
                'message' => "Event retrieved successfully.",
            ]);
    }
    
    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Event $event)
    {
        $input = $request->all();
   
        $validator = Validator::make($input, [
            'name' => 'required',
            'date' => 'required',
            'location' => 'required',
            'description' => 'required',
        ]);
   
        if($validator->fails()){
            return response()->json([
                'error' => true,
                'message' => "Validation error!",
            ]);  
        }
   
        $event->name = $input['name'];
        $event->date = $input['date'];
        $event->location = $input['location'];
        $event->description = $input['description'];
        $event->save();
   
        return $this->sendResponse(new EventResource($event), 'Event updated successfully.');
    }
   
    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Event $event)
    {
        $event->delete();
   
        return $this->sendResponse([], 'Event deleted successfully.');
    }
}
