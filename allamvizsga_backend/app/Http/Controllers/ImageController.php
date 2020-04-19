<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class ImageController extends Controller
{
  public function createImagem(Request $request){

	$destinationPath = url('/images');
	$image = $request->file('part');
	$name = $request->input('name');
	$image->move($destinationPath, $name);

	$dbPath = $destinationPath. '/'.$name;
	$image = new Image();
	$image->animal_id = $request->input('animal_id');
	$image->img_url = $dbPath;
	$image->ativo = $request->input('ativo');   
	$->save();

	return response()->json($image);
	}
}
