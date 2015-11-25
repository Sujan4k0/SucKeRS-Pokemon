/*=========================================================================== 
 | Assignment: FINAL PROJECT: [CEAGame] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |	     	   [Keith Smith  (browningsmith@email.arizona.edu)]
 |	     	   [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: This class is used to make a CEAGame (Catch 'em All). It has a
 | CEAMap and its own specific win/lose conditions.
 *===========================================================================*/
package model.GameModel;

import java.awt.Point;
import java.util.Random;

import model.ItemModel.Harmonica;
import model.ItemModel.PokeBall;
import model.PokemonModel.Pokemon;
import view.*;

public class CEAGame extends GameMode {

	private static final long serialVersionUID = 1L;

	/*---------------------------------------------------------------------
	 |  Method name:    [CEAGame]
	 |  Purpose:  	    [Constructs a CEAGame (Catch 'em All)]
	 |  Parameters:     [Random: for later random encounter/items/stuff]
	 *---------------------------------------------------------------------*/
	public CEAGame(Random rand) {
		super(rand);
		loadImages();

		trainer.addItem(new Harmonica());
		trainer.addItem(new Harmonica());

		/*
		 * for testing teleportation for (Pokemon p : database.getAllPokemon())
		 * if (!p.getName().toUpperCase().equals("MEW")) trainer.addPokemon(p);
		 */

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameWon]
	 |  Purpose:  	    [To check if the user has won the game]
	 |  Returns:  	    [boolean: true is user won, false is not]
	 *---------------------------------------------------------------------*/
	@Override
	public boolean isGameWon() {

		for (Pokemon p : trainer.getPokemon()) {
			if (p.getName().equals("MEW"))
				return true;
		}

		return false;

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameLost]
	 |  Purpose:  	    [To know if the user has lost the game]
	 |  Returns:  	    [boolean: true if the user has lost, false if the user has not]
	 *---------------------------------------------------------------------*/
	@Override
	public boolean isGameLost() {
		// TODO add losing conditions
		if (trainer.getSteps() == 0 || !trainer.getItems().contains(new PokeBall()))
			return true;

		return false;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [createMap]
	 |  Purpose:  	    [assigns a CEAMap to the Map instance variable]
	 *---------------------------------------------------------------------*/
	@Override
	public void createMap() {
		map = new CEAMap(r);
	}

	/*---------------------------------------------------------------------
	|  Method name:    [startEncounter]
	|  Purpose:        [This start an encounter]
	 *---------------------------------------------------------------------*/
	@Override
	public void startEncounter() {

		if (encounteredPokemon == null) {
			// only uncommon and common pokemon can be encountered
			// and only not in secrety secret land
			if (trainer.getPoint().x >= Map.HEIGHT || trainer.getPoint().y >= Map.WIDTH) {
				int rand = r.nextInt(5);
				if (rand == 4) {

					Point tp = trainer.getPoint();

					// in plainy area
					if (tp.x >= Map.HEIGHT && tp.x < Map.HEIGHT * 2 && tp.y < Map.WIDTH)
						encounteredPokemon = database.getSteelix();
					// in icey area
					else if (tp.x >= Map.HEIGHT * 2 && tp.x < Map.HEIGHT * 3 && tp.y >= Map.WIDTH
							&& tp.y < 2 * Map.WIDTH)
						encounteredPokemon = database.getPikachu();
					// in deserty area
					else if (tp.x >= Map.HEIGHT && tp.x < Map.HEIGHT * 2 && tp.y >= Map.WIDTH * 2
							&& tp.y < 3 * Map.WIDTH)
						encounteredPokemon = database.getGyrados();
					// in cavey area
					else if (tp.x < Map.HEIGHT && tp.y >= Map.WIDTH && tp.y < 2 * Map.WIDTH)
						encounteredPokemon = database.getExeggutor();

				} else
					encounteredPokemon = database.getRandomCommon(map.getCurrentTerrain());
			}
		}
		battleMessage = "You've encountered a " + encounteredPokemon.getName() + "!";

		inBattle = true;
		encounter.startEncounter(encounteredPokemon);
		map.pauseBGMusic();

	}

	@Override
	public void trainerCaughtPokemon(Pokemon p) {
		// nothing hurr
	}

	public void startLegEncounter() {

		battleMessage = "You've encountered MEW!!\n AHHHHHHhhHHHhHhH.\n" + "Can you catch it?!?!";

		inBattle = true;
		encounteredPokemon = database.getMew();
		encounter.startEncounter(encounteredPokemon);
		map.pauseBGMusic();

	}

}
