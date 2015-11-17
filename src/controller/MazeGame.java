/*=========================================================================== 
 | Assignment: FINAL PROJECT: [MazeGame] 
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
 | Description: This class is used to create a MazeGame. The MazeGame contains
 | a MazeMap which is a randomly generated maze that the player must get the trainer
 | through in order to win.
 *===========================================================================*/
package controller;

import java.awt.Point;
import java.util.Random;

import view.*;
import model.ItemModel.BasicStepPotion;
import model.ItemModel.Teleporter;
import model.MapModel.Obstacle;

public class MazeGame extends GameMode {

	private static final long serialVersionUID = 1L;

	/*---------------------------------------------------------------------
	 |  Method name:    [MazeGame]
	 |  Purpose:  	    [Constructs a MazeGame]
	 |  Parameters:     [Random: to later generate random encounters/items]
	 *---------------------------------------------------------------------*/
	public MazeGame(Random rand) {
		super(rand);
		bgPath = "./sounds/Ruby_Sapphire_Mt.Pyre.mp3";
		loadImages();
		
		// for testing
		trainer.addItem(new BasicStepPotion());
		trainer.addItem(new Teleporter());
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameWon]
	 |  Purpose:  	    [To check if the user has won the game]
	 |  Returns:  	    [boolean: true is user won, false is not]
	 *---------------------------------------------------------------------*/
	public boolean isGameWon() {

		// get the obstacles of current map
		Obstacle[][] obstacle = map.getObstacleTiles();

		for (int i = 0; i < MazeMap.HEIGHT; i++) {
			if (obstacle[i][MazeMap.WIDTH - 1] == null) {
				if (trainer.getPoint().equals(new Point(i, MazeMap.WIDTH - 1)))
					return true;
			}
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

		if (trainer.getSteps() == 0)
			return true;
		
		return false;
	}
	

	/*---------------------------------------------------------------------
	 |  Method name:    [createMap]
	 |  Purpose:  	    [assigns a MazeMap to the Map instance variable]
	 *---------------------------------------------------------------------*/
	@Override
	public void createMap() {
		map = new MazeMap();
	}
	
	@Override
	public void startNewBGMusic() {
		bgPlayer.loopSound(bgPath);		
	}

	@Override
	public void trainerCaughtPokemon() {
		trainer.increaseSteps(10);
	}
}
