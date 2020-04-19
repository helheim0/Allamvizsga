<?php

namespace App\Observers;

use App\Team;
use App\Member;
use App\User;
use App\Userrole;
use App\Role;
use Illuminate\Support\Facades\Auth;

class TeamObserver
{
    /**
     * Handle the team "created" event.
     *
     * @param  \App\Team  $team
     * @return void
     */
    public function created(Team $team)
    { 
        $team->admin_id = Auth::user()->id;

        $member = new Member();
        $member->team_id = $team->id;
        $member->user_id = $team->admin_id;
        $member->role_id = 1;
        $member->save();
/*
        $userrole = new Userrole();
        $userrole->user_id = $team->admin_id
        $userrole->role_id = 1;
        $userrole->team_id = $team->id;
        $userrole->save();*/

    }

    /**
     * Handle the team "updated" event.
     *
     * @param  \App\Team  $team
     * @return void
     */
    public function updated(Team $team)
    {
        //
    }

    /**
     * Handle the team "deleted" event.
     *
     * @param  \App\Team  $team
     * @return void
     */
    public function deleted(Team $team)
    {
        //
    }

    /**
     * Handle the team "restored" event.
     *
     * @param  \App\Team  $team
     * @return void
     */
    public function restored(Team $team)
    {
        //
    }

    /**
     * Handle the team "force deleted" event.
     *
     * @param  \App\Team  $team
     * @return void
     */
    public function forceDeleted(Team $team)
    {
        //
    }
}
