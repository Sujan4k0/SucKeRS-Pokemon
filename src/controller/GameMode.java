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

	public Map getMap() {

		return map;

	}

	public void newMap(MapType mapType) {
		map = new Map(mapType);
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

	private class OurKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			Point prev = trainer.getPoint();
			
			Obstacle[][] obsts = map.getObstacleTiles();

			// System.out.println("Key pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (obsts[prev.x - 1][prev.y] == null) {
					trainer.getPoint().translate(-1, 0);
					map.setTrainerDir(Map.Direction.UP);
					if (trainer.getPoint().x % Map.HEIGHT == Map.HEIGHT - 1)
						map.moveUp();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (obsts[prev.x + 1][prev.y] == null) {
					trainer.getPoint().translate(1, 0);
					map.setTrainerDir(Map.Direction.DOWN_1);
					if (trainer.getPoint().x % Map.HEIGHT == 0)
						map.moveDown();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (obsts[prev.x][prev.y + 1] == null) {
					trainer.getPoint().translate(0, 1);
					map.setTrainerDir(Map.Direction.RIGHT);
					if (trainer.getPoint().y/ Map.WIDTH != 0
							&& trainer.getPoint().y % Map.WIDTH == 0)
						map.moveRight();
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (obsts[prev.x][prev.y - 1] == null) {
					trainer.getPoint().translate(0, -1);
					map.setTrainerDir(Map.Direction.LEFT);
					if (trainer.getPoint().y % Map.WIDTH == Map.WIDTH - 1)
						map.moveLeft();
				}
			}

			System.out.println("Trainer point: " + trainer.getPoint());
			map.setTrainerStart(trainer.getPoint());
			System.out.println("On Map point: " + map.getTrainerStart());
			map.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

}
