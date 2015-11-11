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

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.MapModel.Map;
import model.MapModel.Obstacle;
import model.TrainerModel.Trainer;

public abstract class GameMode {

	Trainer trainer;
	Map map;

	/*---------------------------------------------------------------------
	 |  Method name:    [GameMode]
	 |  Purpose:  	    [Constructs a GameMode in order to play some POKEMON!!!]
	 |  Parameters:     [MapType: determines the type of Map that this GameMode will be using]
	 *---------------------------------------------------------------------*/
	public GameMode() {
		
		createMap();
		
		trainer = new Trainer();
		
		trainer.setPoint(new Point(map.getTrainerPoint()));

		// add KeyListener to this Map so that the user can move the trainer
		// and other button-y things
		map.addKeyListener(new OurKeyListener());
		
		// set focusable so that the KeyListener works
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

	/*--------------------------------------------------------------------
	 |  Method name:    [trainerCanMove]
	 |  Purpose:  	    [To know if the Trainer can move]
	 |  Parameters:     [KeyEvent: the direction the user is trying to move the trainer]
	 |  Returns:  	    [boolean: true if Trainer can move, false if Trainer can't]
	 *---------------------------------------------------------------------*/
	public boolean trainerCanMove(KeyEvent e) {
		
		// get all obstacles from the map to check collision
		Obstacle[][] obsts = map.getObstacleTiles();
		
		// get the point the trainer is currently at
		Point prev = trainer.getPoint();
		
		// System.out.println("Keycode: " + e.getKeyCode() + " Point: " + prev);

		/* Depending on KeyEvent the trainer CAN move if:
		 * 1. there is no Obstacle in that direction
		 * and 2. moving won't make the trainer go out of bounds of the map */
		if (e.getKeyCode() == KeyEvent.VK_UP
				&& prev.x - 1 >= 0
				&& obsts[prev.x - 1][prev.y] == null)
			return true;
		
		else if (e.getKeyCode() == KeyEvent.VK_DOWN
				&& prev.x + 1 < obsts.length
				&& obsts[prev.x + 1][prev.y] == null)
			return true;
		
		else if (e.getKeyCode() == KeyEvent.VK_LEFT
				&& prev.y - 1 >= 0
				&& obsts[prev.x][prev.y - 1] == null)
			return true;
		
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT
				&& prev.y + 1 < obsts[0].length
				&& obsts[prev.x][prev.y + 1] == null)
			return true;

		// if all other if() statements were false, the trainer can't move
		return false;
	}
	
	public abstract void createMap();

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameWon]
	 |  Purpose:  	    [To know if the user has won the game]
	 |  Returns:  	    [boolean: true if user has won, false if user has not]
	 *---------------------------------------------------------------------*/
	public abstract boolean isGameWon();

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameLost]
	 |  Purpose:  	    [To know if the user has lost the game]
	 |  Returns:  	    [boolean: true if the user has lost, false if the user has not]
	 *---------------------------------------------------------------------*/
	public abstract boolean isGameLost();

	/*---------------------------------------------------------------------
	 |  Method name:    [moveTrainer]
	 |  Purpose:  	    [To change trainer sprite and then attempt to move the trainer]
	 |  Parameters:  	[KeyEvent: direction in which trainer is trying to move]
	 *---------------------------------------------------------------------*/
	public void moveTrainer(KeyEvent e) {

		int dx = 0, dy = 0;
		int kc = e.getKeyCode();
		Map.Direction dir = Map.Direction.RIGHT;

		// Depending on the KeyEvent passed in, sets the change in the x
		// or y direction accordingly. If the trainer is able to move and
		// the trainer is moving to the next part of the Map, the Map 'moves'
		// so that the new area is shown
		switch (kc) {
		case KeyEvent.VK_UP:
			dx = -1;
			dir = Map.Direction.UP;
			if (trainerCanMove(e)
					&& (trainer.getPoint().x + dx) % Map.HEIGHT == Map.HEIGHT - 1)
				map.moveUp();
			break;
		case KeyEvent.VK_DOWN:
			dx = 1;
			dir = Map.Direction.DOWN_1; // DOWN isn't working :(
			if (trainerCanMove(e)
					&& (trainer.getPoint().x  + dx) % Map.HEIGHT == 0)
				map.moveDown();
			break;
		case KeyEvent.VK_RIGHT:
			dy = 1;
			dir = Map.Direction.RIGHT;
			if (trainerCanMove(e)
					&& (trainer.getPoint().y + dy )% Map.WIDTH == 0)
				map.moveRight();
			break;
		case KeyEvent.VK_LEFT:
			dy = -1;
			dir = Map.Direction.LEFT;
			if (trainerCanMove(e)
					&& (trainer.getPoint().y + dy) % Map.WIDTH == Map.WIDTH - 1)
				map.moveLeft();
			break;
		default:
			break;
		}

		// System.out.println("Trainer can move: " + trainerCanMove(e));;
		
		// if the trainer can move, then move the trainer and decrease steps
		if (trainerCanMove(e)) {
			trainer.getPoint().translate(dx, dy);
			trainer.decreaseSteps();
		}
		
		// set the visual direction of the trainer sprite on the Map
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
			map.setTrainerPoint(trainer.getPoint());
			
			// repaint the visual changes
			map.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}
}
