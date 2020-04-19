<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class MemberResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {

        return parent::toArray($request);
      /*  return [
            'id' => $this->id,
            'teams_id' => $this->teams_id,
            'users_id' => $this->users_id
        ];*/
    }
}
