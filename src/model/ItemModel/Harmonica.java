package model.ItemModel;

import model.PokemonModel.Pokemon;

public class Harmonica extends Item{
	
	/*---------------------------------------------------------------------
	 |  Purpose:  	    [Constructor]
	 *---------------------------------------------------------------------*/
	public Harmonica(){
		super.setName("Harmonica");
		super.setForPokemon(true);
		super.setForTrainer(false);
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getSongFilePath]
	 |  Purpose:  	    [returns a pokemon's song to play]
	 |  Parameters:  	[a pokemon]
	 |  Returns:		[a string representing a filepath to a song]
	 *---------------------------------------------------------------------*/
	public String getSongFilePath(Pokemon poke){
		String str = "./sounds/pokemonSongs/"+poke.getName().toLowerCase()+"_song.wav";
		return str;
		
	}

}
