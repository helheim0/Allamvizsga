<?php

namespace App\Http\Middleware;

use Closure;
use Response; 
use Illuminate\Contracts\Routing\Middleware;

class API
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        $response = $next($request);
        $response->headers->set('Authorization', 'Bearer '.$request->bearerToken());
        return $response;
    }
}
