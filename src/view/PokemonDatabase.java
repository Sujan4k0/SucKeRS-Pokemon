/*=========================================================================== 
| Assignment: FINAL PROJECT: [PokemonDatabase] 
| 
| Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
|             [Keith Smith  (browningsmith@email.arizona.edu)]
|             [Ryan Kaye    (rkaye@email.arizona.edu)]
|             [Sarina White (sarinarw@email.arizona.edu)]
| 
| Course: 335 
| Instructor: Mercer
| Project Manager/Section Leader: Jeremy Mowery 
| Due Date: [12.7.15] 
| 
| Description: All Pokemon we have available for this game.
 *==========================================================================*/
package view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import model.MapModel.TerrainType;
import model.PokemonModel.Common;
import model.PokemonModel.Legendary;
import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonType;
import model.PokemonModel.Uncommon;
import model.TrainerModel.Trainer;

public class PokemonDatabase implements Serializable{

	// uncommon
	private Common magikarp;
	private Common cyndaquil;
	private Common rhydon;
	private Common diglett;
	private Common simisage;
	private Common luvdisc;

	// common
	private Uncommon pikachu;
	private Uncommon steelix;
	private Uncommon exeggutor;
	private Uncommon gyrados;

	// legendary
	private Legendary mew;

	// all the Common Pokemon and Uncommon
	private ArrayList<Pokemon> allCommon, allUncommon;

	/*---------------------------------------------------------------------
	|  Method name:    [PokemonDatabase]
	|  Purpose:        [Constructor - makes all Pokemon we've chosen]
	|  Parameters:     []
	|  Returns:        []
	 *---------------------------------------------------------------------*/
	public PokemonDatabase() {

		// common
		magikarp = new Common(new Random(), "Magikarp", getImage("Magikarp"), PokemonType.WATER);
		cyndaquil = new Common(new Random(), "Cyndaquil", getImage("Cyndaquil"), PokemonType.FIRE);
		rhydon = new Common(new Random(), "Rhydon", getImage("Rhydon"), PokemonType.ROCK);
		diglett = new Common(new Random(), "Diglett", getImage("Diglett"), PokemonType.GROUND);
		simisage = new Common(new Random(), "Simisage", getImage("Simisage"), PokemonType.GRASS);
		luvdisc = new Common(new Random(), "Luvdisc", getImage("Luvdisc"), PokemonType.WATER);

		allCommon = new ArrayList<Pokemon>();
		allCommon.add(magikarp);
		allCommon.add(cyndaquil);
		allCommon.add(rhydon);
		allCommon.add(diglett);
		allCommon.add(simisage);
		allCommon.add(luvdisc);

		// uncommon 
		pikachu = new Uncommon(new Random(), "Pikachu", getImage("Pikachu"), PokemonType.ELECTRIC);
		steelix = new Uncommon(new Random(), "Steelix", getImage("Steelix"), PokemonType.GROUND);
		exeggutor =
				new Uncommon(new Random(), "Exeggutor", getImage("Exeggutor"), PokemonType.GRASS);
		gyrados = new Uncommon(new Random(), "Gyrados", getImage("Gyrados"), PokemonType.WATER);

		allUncommon = new ArrayList<Pokemon>();
		allUncommon.add(pikachu);
		allUncommon.add(steelix);
		allUncommon.add(exeggutor);
		allUncommon.add(gyrados);

		// legendary
		mew = new Legendary(new Random(), "Mew", getImage("Mew"), PokemonType.PSYCHIC);
	}

	/*---------------------------------------------------------------------
	|  Method name:    [getImage]
	|  Purpose:        [Get image array of all images for this Pokemon]
	|  Parameters:     [String name of pokemon]
	|  Returns:        [Array of Images]
	 *---------------------------------------------------------------------*/
	private Image[] getImage(String name) {

		Image[] image = new Image[1];
		Image pic = null;

		try {
			pic = ImageIO.read(new File("./images/pokemonImages/" + name + ".png"));
			image[0] = pic;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	// GETTERS FOR EVERY SINGLE POKEMON AVAILABLE

	public Pokemon getMagikarp() {

		return magikarp;
	}

	public Pokemon getCyndaquil() {

		return cyndaquil;
	}

	public Pokemon getRhydon() {

		return rhydon;
	}

	public Pokemon getDiglett() {

		return diglett;
	}

	public Pokemon getSimisage() {

		return simisage;
	}

	public Pokemon getLuvdisc() {

		return luvdisc;
	}

	public Pokemon getPikachu() {

		return pikachu;
	}

	public Pokemon getSteelix() {

		return steelix;
	}

	public Pokemon getExeggutor() {

		return exeggutor;
	}

	public Pokemon getGyrados() {

		return gyrados;
	}

	public Pokemon getMew() {

		return mew;
	}

	public Pokemon getRandomCommon(TerrainType tt) {

		return randomHelper(allCommon, tt);
		
	}

	public Pokemon getRandomUncommon(TerrainType tt) {

		return randomHelper(allUncommon, tt);

	}
	
	public Pokemon getPokemonByName(String name) {
		
		switch (name.toUpperCase()) {
		case "MAGIKARP":
			return magikarp;
		case "CYNDAQUIL":
			return cyndaquil;
		case "RHYDON":
			return rhydon;
		case "DIGLETT":
			return diglett;
		case "SIMISAGE":
			return simisage;
		case "LUVDISC":
			return luvdisc;
		case "PIKACHU":
			return pikachu;
		case "STEELIX":
			return steelix;
		case "EXEGGUTOR":
			return exeggutor;
		case "GYRADOS":
			return gyrados;
		case "MEW":
			return mew;
		default:
			System.out.println("Error: Invalid pokemon name. No worries, we'll give you a Diglett instead! ;)");
			return diglett;
		}
	}

	private Pokemon randomHelper(ArrayList<Pokemon> pokemonz, TerrainType tt) {
		
		int rand = new Random().nextInt(pokemonz.size());
		Pokemon currPokemon = pokemonz.get(rand);
		PokemonType type = currPokemon.getType();

		while (true) {

			switch (tt) {
			case GENERIC:
				return currPokemon;
			case DESERT:
				if (currPokemon.getType() == PokemonType.WATER)
					return currPokemon;
				break;
			/*case ICE:
				if (type == PokemonType.ROCK || type == PokemonType.GROUND)
					return currPokemon;
				break;
			case CAVE:
				if (type == PokemonType.GRASS || type == PokemonType.ELECTRIC)
					return currPokemon;
				break;
			case FOREST:
				if (type == PokemonType.FIRE || type == PokemonType.PSYCHIC)
					return currPokemon;
				break;*/
			default:
				return currPokemon;
			}

			rand = new Random().nextInt(allCommon.size());
			currPokemon = allCommon.get(rand);

		}
	}
	
	public boolean caughtEmAll(Trainer trainer) {
		
		boolean allCaught = false;	//Init all caught to false. Will change to true if a Mew was caug is missing.
		
		for (Pokemon i : trainer.getPokemon()) {	//For all the pokemon the trainer has
			
			if (i.getName().equals(getMew().getName())) {			//If that pokemon is Mew
				
				//System.out.println("You caught "+i.getName());
				allCaught = true;								//Set all caught to true and break the loop
				break;
			}
			allCaught = false;							//Set all cauught to false and continue to search
		}
		
		if (!allCaught) {			//If we all caught is not true, we missed Mew
			
			//System.out.println("You did not catch Mew"); 
			return false; //If mew was not caught, return false
		}
		
		for (Pokemon i : allCommon) {		//For all of the common pokemon
			
			for (Pokemon j : trainer.getPokemon()) { //For all the pokemon that the trainer has
				
				if (i.getName().equals(j.getName())) {		//If the trainer has that pokemon
					
					//System.out.println("You caught " + i.getName() + " " + j.getName());
					allCaught = true;							//Set all caught to true and break out of the loop
					break;
				}
				
				allCaught = false;			//Set all caught to false
			}
			
			if (!allCaught) {		//If not all the pokemon have been caught
				
				//System.out.println("You did not catch all the pokemon."); //If mew was not caught, return false
				return false;		//Return false
			}
		}
		
		for (Pokemon i : allUncommon) {		//For all of the uncommon pokemon
			
			for (Pokemon j : trainer.getPokemon()) {		//For all the pokemon the trainer has
				
				if (i.getName().equals(j.getName())) {				//If the trainer has that pokemon
					
					//System.out.println("You caught " + i.getName() + " " + j.getName());		
					allCaught = true;										//Set all caught to true and break out of the loop
					break;			
				}
				
				allCaught = false;						//Set all caught to false
			}
			
			if (!allCaught) {			//If not all the pokemon were caught
				
				//System.out.println("You did not catch all the pokemon."); //If mew was not caught, return false
				return false;
			}
		}
		
		//System.out.println("You caught all the pokemon!");
		
		return allCaught;	//Return allCaught, which is now true. You caught all the Pokemon!
	}
}
