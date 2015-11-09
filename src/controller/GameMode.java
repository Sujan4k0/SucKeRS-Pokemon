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

import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.*;
import model.MapModel.Map;
import model.MapModel.MapType;
import model.MapModel.Obstacle;
import model.TrainerModel.Trainer;

public class GameMode {

	Trainer trainer;
	Map map;
	MapType gameType;

	/*---------------------------------------------------------------------
	 |  Method name:    [GameMode]
	 |  Purpose:  	    [Constructs a GameMode in order to play some POKEMON!!!]
	 |  Parameters:     [MapType: determines the type of Map that this GameMode will be using]
	 *---------------------------------------------------------------------*/
	public GameMode(MapType mapType) {

		gameType = mapType;

		map = new Map(mapType);
		trainer = new Trainer();
		trainer.setPoint(new Point(map.getTrainerStart()));

		map.addKeyListener(new OurKeyListener());
		map.setFocusable(true);

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getMap]
	 |  Purpose:  	    [Getter for Map instance variable]
	 |  Returns:        [Map]
	 *---------------------------------------------------------------------*/
	public Map getMap() {

		return map;

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [newMap]
	 |  Purpose:  	    [Resets Map to a new Map object: for getting a new map or changing type]
	 |  Parameters:     [MapType: determines the type of Map to re-instantiate]
	 *---------------------------------------------------------------------*/
	public void newMap(MapType mapType) {
		map = new Map(mapType);
	}

	/*--------------------------------------------------------------------
	 |  Method name:    [trainerCanMove]
	 |  Purpose:  	    [To know if the Trainer can move]
	 |  Returns:  	    [boolean: true if Trainer can move, false if Trainer can't]
	 *---------------------------------------------------------------------*/
	private boolean trainerCanMove(KeyEvent e) {
		// get all obstacles from the map to check collision
		Obstacle[][] obsts = map.getObstacleTiles();
		// get the point the trainer is currently at
		Point prev = trainer.getPoint();

		if (e.getKeyCode() == KeyEvent.VK_UP
				&& obsts[prev.x - 1][prev.y] == null)
			return true;

		return false;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameWon]
	 |  Purpose:  	    [To know if the user has won the game]
	 |  Returns:  	    [boolean: true if user has won, false if user has not]
	 *---------------------------------------------------------------------*/
	public boolean isGameWon() {

		Obstacle[][] obstacle = map.getObstacleTiles();

		// if this is a Maze game, then the player wins if they are standing on
		// the only null obstacle tile on the right-most side of the map
		if (gameType == MapType.MAZE) {
			for (int i = 0; i < Map.MAZE_HEIGHT; i++) {
				if (obstacle[i][Map.MAZE_WIDTH - 1] == null) {
					if (trainer.getPoint().equals(
							new Point(i, Map.MAZE_WIDTH - 1)))
						return true;
				}
			}
		}

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

	public void moveTrainer(KeyEvent e) {

		int dx = 0, dy = 0;
		int kc = e.getKeyCode();
		Map.Direction dir = Map.Direction.RIGHT;

		switch (kc) {
		case KeyEvent.VK_UP:
			dx = -1;
			dir = Map.Direction.UP;
			if ((trainer.getPoint().x + dx) % Map.HEIGHT == Map.HEIGHT - 1)
				map.moveUp();
			break;
		case KeyEvent.VK_DOWN:
			dx = 1;
			dir = Map.Direction.DOWN_1; // DOWN isn't working :(
			if ((trainer.getPoint().x  + dx) % Map.HEIGHT == 0)
				map.moveDown();
			break;
		case KeyEvent.VK_RIGHT:
			dy = 1;
			dir = Map.Direction.RIGHT;
			if ((trainer.getPoint().y + dy) / Map.WIDTH != 0
					&& trainer.getPoint().y % Map.WIDTH == 0)
				map.moveRight();
			break;
		case KeyEvent.VK_LEFT:
			dy = -1;
			dir = Map.Direction.LEFT;
			if ((trainer.getPoint().y + dy) % Map.WIDTH == Map.WIDTH - 1)
				map.moveLeft();
			break;
		default:
			break;
		}

		if (trainerCanMove(e))
			trainer.getPoint().translate(dx, dy);
			trainer.decreaseSteps();
		
		map.setTrainerDir(dir);
		
	}

	/*---------------------------------------------------------------------
	 |  Class name:     [OurKeyListener]
	 |  Purpose:        [Used to move trainer around the map]
	 *---------------------------------------------------------------------*/
	private class OurKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		/*---------------------------------------------------------------------
		 |  Method name:    [keyPressed]
		 |  Purpose:  	    [handles KeyEvents on key press]
		 |  Parameters:     [KeyEvent]
		 *---------------------------------------------------------------------*/
		@Override
		public void keyPressed(KeyEvent e) {

			// set sprite direction and try to move trainer
			moveTrainer(e);
			
			// set trainer's sprite location in map to trainer's current loc
			map.setTrainerStart(trainer.getPoint());
			map.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}
}
