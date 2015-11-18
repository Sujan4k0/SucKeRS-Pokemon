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

import java.util.Random;

import model.ItemModel.Harmonica;
import model.ItemModel.PokeBall;
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
		
		// useItemOnPokemon(new Harmonica(), database.getMew().getName());
		
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameWon]
	 |  Purpose:  	    [To check if the user has won the game]
	 |  Returns:  	    [boolean: true is user won, false is not]
	 *---------------------------------------------------------------------*/
	@Override
	public boolean isGameWon() {
		
		return database.caughtEmAll(trainer);
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
		map = new CEAMap();
	}

	@Override
	public void trainerCaughtPokemon() {
		// nothing hurr
	}

}