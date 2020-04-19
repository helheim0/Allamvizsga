<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use App\Team;
use App\Event;
use App\Observers\TeamObserver;
use App\Observers\EventObserver;

class EloquentEventServiceProvider extends ServiceProvider
{
    /**
     * Register services.
     *
     * @return void
     */
    public function register()
    {
        //
    }

    /**
     * Bootstrap services.
     *
     * @return void
     */
    public function boot()
    {
        //Registering the observers
        Team::observe(TeamObserver::class);
        Event::observe(EventObserver::class);
    }
}
