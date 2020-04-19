<?php

namespace App\Providers;


use Illuminate\Support\Facades\Schema;
use Laravel\Passport\Passport;
use App\Team;
use App\Event;
use App\Observers\TeamObserver;
use App\Observers\EventObserver;
use Illuminate\Foundation\Support\Providers\AuthServiceProvider as ServiceProvider;
use Illuminate\Http\Resources\Json\Resource;

class AppServiceProvider extends ServiceProvider
{
    /**
     * The policy mappings for the application.
     *
     * @var array
     */
    protected $policies = [
        'App\Model' => 'App\Policies\ModelPolicy',
    ];

     /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
        Resource::withoutWrapping();
        Schema::defaultStringLength(191);

        //Registering the observers
        Team::observe(TeamObserver::class);
        Event::observe(EventObserver::class);
    }
    public function with($request)
    {
        return ['key' => 'value'];
    }
    /**
     * Register any application services.
     *
     * @return void
     */
    public function register()
    {
        //
    }

}
