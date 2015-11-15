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
package controller;

import java.util.Random;

import model.MapModel.CEAMap;
import model.MapModel.Map;
import model.TrainerModel.Trainer;

public class CEAGame extends GameMode {

	/*---------------------------------------------------------------------
	 |  Method name:    [CEAGame]
	 |  Purpose:  	    [Constructs a CEAGame (Catch 'em All)]
	 |  Parameters:     [Random: for later random encounter/items/stuff]
	 *---------------------------------------------------------------------*/
	public CEAGame(Random rand) {
		super(rand);
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameWon]
	 |  Purpose:  	    [To check if the user has won the game]
	 |  Returns:  	    [boolean: true is user won, false is not]
	 *---------------------------------------------------------------------*/
	@Override
	public boolean isGameWon() {
		// TODO add winning condition
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
	public void startBGMusic() {
		bgPlayer.loopSound("./sounds/Ruby_Sapphire_SafariZone.mp3");		
	}

}
