package model.ItemModel;

import model.PokemonModel.Pokemon;

public class Harmonica extends Item{
	
	public Harmonica(){
		super.setName("Harmonica");
		super.setForPokemon(true);
		super.setForTrainer(false);
	}
	
	public String getSongFilePath(Pokemon poke){
		String str = "./sounds/pokemonSongs/"+poke.getName().toLowerCase()+"_song.wav";
		return str;
		
	}

}
