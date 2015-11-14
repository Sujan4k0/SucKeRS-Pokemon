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
 | Description: This is an abstract class that any new game mode must extend from.
 | For example, we have a MazeGame which is a specific game mode so it extends
 | GameMode.
 *===========================================================================*/

package controller;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import soundplayer.SoundPlayer;
import view.EncounterPanel;
import model.MapModel.Map;
import model.MapModel.Obstacle;
import model.PokemonModel.Common;
import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonResponse;
import model.PokemonModel.PokemonType;
import model.TrainerModel.Trainer;
import model.TrainerModel.TrainerAction;

public abstract class GameMode implements Serializable {

	private static final long serialVersionUID = 1L;

	Trainer trainer;
	Map map; // the visual map of this game
	EncounterPanel encounter;
	Random r; // used for random encounters/items
	String endMessage = ""; // the message to show on end game
	boolean forfeited = false;
	boolean inBattle = false;
	Pokemon encounteredPokemon;

	// Plays bg sounds :D
	SoundPlayer bgPlayer = new SoundPlayer();

	// Plays sfx :D
	SoundPlayer sfxPlayer = new SoundPlayer();

	/*---------------------------------------------------------------------
	 |  Method name:    [GameMode]
	 |  Purpose:  	    [Constructs a GameMode in order to play some POKEMON!!!]
	 |  Parameters:     [MapType: determines the type of Map that this GameMode will be using]
	 *---------------------------------------------------------------------*/
	public GameMode(Random rand) {

		r = rand;

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

	public Trainer getTrainer() {

		return trainer;

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

		/*
		 * Depending on KeyEvent the trainer CAN move if: 1. there is no
		 * Obstacle in that direction and 2. moving won't make the trainer go
		 * out of bounds of the map
		 */
		if (e.getKeyCode() == KeyEvent.VK_UP && prev.x - 1 >= 0
				&& obsts[prev.x - 1][prev.y] == null)
			return true;

		else if (e.getKeyCode() == KeyEvent.VK_DOWN && prev.x + 1 < obsts.length
				&& obsts[prev.x + 1][prev.y] == null)
			return true;

		else if (e.getKeyCode() == KeyEvent.VK_LEFT && prev.y - 1 >= 0
				&& obsts[prev.x][prev.y - 1] == null)
			return true;

		else if (e.getKeyCode() == KeyEvent.VK_RIGHT && prev.y + 1 < obsts[0].length
				&& obsts[prev.x][prev.y + 1] == null)
			return true;

		// if all other if() statements were false, the trainer can't move
		return false;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [createMap]
	 |  Purpose:  	    [assigns a Map to the Map instance variable]
	 *---------------------------------------------------------------------*/
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
		Map.TrainerDirection dir = Map.TrainerDirection.RIGHT;

		// Depending on the KeyEvent passed in, sets the change in the x
		// or y direction accordingly. If the trainer is able to move and
		// the trainer is moving to the next part of the Map, the Map 'moves'
		// so that the new area is shown
		switch (kc) {
		case KeyEvent.VK_UP:
			dx = -1;
			dir = Map.TrainerDirection.UP;
			if (trainerCanMove(e) && (trainer.getPoint().x + dx) % Map.HEIGHT == Map.HEIGHT - 1)
				map.moveUp();
			break;
		case KeyEvent.VK_DOWN:
			dx = 1;
			dir = Map.TrainerDirection.DOWN_1; // DOWN isn't working :(
			if (trainerCanMove(e) && (trainer.getPoint().x + dx) % Map.HEIGHT == 0)
				map.moveDown();
			break;
		case KeyEvent.VK_RIGHT:
			dy = 1;
			dir = Map.TrainerDirection.RIGHT;
			if (trainerCanMove(e) && (trainer.getPoint().y + dy) % Map.WIDTH == 0)
				map.moveRight();
			break;
		case KeyEvent.VK_LEFT:
			dy = -1;
			dir = Map.TrainerDirection.LEFT;
			if (trainerCanMove(e) && (trainer.getPoint().y + dy) % Map.WIDTH == Map.WIDTH - 1)
				map.moveLeft();
			break;
		default:
			break;
		}

		// set the visual direction of the trainer sprite on the Map
		map.setTrainerDir(dir);

		// if the trainer can move, then move the trainer and decrease steps
		if (trainerCanMove(e)) {
			// change trainer object point
			trainer.getPoint().translate(dx, dy);

			// decrease steps
			trainer.decreaseSteps();

			// set trainer's sprite location in map to trainer's current loc
			map.setTrainerPoint(trainer.getPoint());

			// repaint the visual changes
			map.repaint();

			// start an encounter
			if (new Random().nextInt(5) == 1)
				startEncounter();

			//TODO encounters/items
		}

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameActive]
	 |  Purpose:  	    [To know if the game is active]
	 |  Returns:  	    [boolean: true if game is active, false if game is not]
	 *---------------------------------------------------------------------*/
	public boolean isGameActive() {
		if (isGameWon()) {
			endMessage = "You Won!";
			return false;
		} else if (isGameLost()) {
			endMessage = "You LOSTTTTT >:(";
			return false;
		}

		return true;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [setEndMessage]
	 |  Purpose:  	    [Setter for endMessage variable]
	 |  Parameters:     [String: the string to set the endMessage to]
	 *---------------------------------------------------------------------*/
	public void setEndMessage(String s) {
		endMessage = s;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getEndMessage]
	 |  Purpose:  	    [Getter for endMessage variable]
	 |  Returns:  	    [String: the endMessage]
	 *---------------------------------------------------------------------*/
	public String getEndMessage() {
		return endMessage;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [forfeitGame]
	 |  Purpose:  	    [Forfeit the game]
	 *---------------------------------------------------------------------*/
	public void forfeitGame() {
		forfeited = true;
		endMessage = "Game Forfeited";
	}

	public void startEncounter() {

		bgPlayer.playSound("./sounds/Pokemon_BattleMusic_1.mp3");

		Image[] imgs = new Image[1];

		try {
			Image testImg = ImageIO.read(new File("./images/Pokemon_1.png"));
			imgs[0] = testImg.getScaledInstance(300, 300, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		encounteredPokemon = new Common(new Random(), "name", imgs, PokemonType.ELECTRIC);
		inBattle = true;

		encounteredPokemon.startEncounter();

	}

	public void doTrainerAction(TrainerAction action) {

		switch (action) {

		case THROW_BALL:
			//trainer use ball
			break;
		case RUN_AWAY:
			break;
		case THROW_BAIT:
			break;
		case THROW_ROCK:
			break;
		default:
			break;

		}
		
		encounter.animateTrainer();
		
	}
	
	public boolean trainerInBattle() {
		return inBattle;
	}
	
	public EncounterPanel getEncounterPanel() {
		return encounter;
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

			// PROMPT USER TO PRESS "ENTER" AFTER BATTLE ENDS TO UPDATE THE MAP!!!! >:OOO
			if (e.getKeyCode() == KeyEvent.VK_ENTER && inBattle
					&& encounteredPokemon.getState() != PokemonResponse.STAND_GROUND) {
				inBattle = false;
				// repaint the visual changes
				map.repaint();
			}

			// else if the game is not won or lost or forfeited, move the trainer
			else if (!inBattle && !forfeited && !isGameWon() && !isGameLost()) {
				// set sprite direction and try to move trainer
				moveTrainer(e);
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}
}
