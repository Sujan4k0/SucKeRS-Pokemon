/*=========================================================================== 
 | Assignment: FINAL PROJECT: [GameMode] 
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
 | Description: TODO Describe the class here. 
 *===========================================================================*/

package controller;

import model.*;
import model.MapModel.Map;
import model.MapModel.MapType;

public class GameMode {

	Trainer trainer;
	Map map;

	/*---------------------------------------------------------------------
	 |  Method name:    [GameMode]
	 |  Purpose:  	    [Constructs a GameMode in order to play some POKEMON!!!]
	 |  Parameters:     [MapType: determines the type of Map that this GameMode will be using]
	 *---------------------------------------------------------------------*/
	public GameMode(MapType gameType) {
		
		map = new Map(gameType);

	}

	/*--------------------------------------------------------------------
	 |  Method name:    [trainerCanMove]
	 |  Purpose:  	    [To know if the Trainer can move]
	 |  Returns:  	    [boolean: true if Trainer can move, false if Trainer can't]
	 *---------------------------------------------------------------------*/
	private boolean trainerCanMove() {

		return false;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameWon]
	 |  Purpose:  	    [To know if the user has won the game]
	 |  Returns:  	    [boolean: true if user has won, false if user has not]
	 *---------------------------------------------------------------------*/
	public boolean isGameWon() {

		return false;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameLost]
	 |  Purpose:  	    [To know if the user has lost the game]
	 |  Returns:  	    [boolean: true if the user has lost, false if the user has not]
	 *---------------------------------------------------------------------*/
	public boolean isGameLost() {

		return false;
	}

}
