package model.ItemModel;

import model.PokemonModel.Pokemon;

public class Harmonica extends Item{
	
	public Harmonica(){
		super.setName("Harmonica");
		super.setForPokemon(false);
		super.setForTrainer(true);
	}
	
	public String getSongFilePath(Pokemon poke){
		return "./sounds/test_song.wav" ;
		
	}

}
