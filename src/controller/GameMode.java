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
import view.*;
import model.ItemModel.*;
import model.MapModel.Obstacle;
import model.MapModel.TerrainType;
import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonResponse;
import model.TrainerModel.Trainer;
import model.TrainerModel.TrainerAction;

public abstract class GameMode implements Serializable {

	private static final long serialVersionUID = 1L;

	Trainer trainer;
	Map map; // the visual map of this game
	EncounterPanel encounter;
	Random r; // used for random encounters/items
	String endMessage = "", bgPath = "", battleMessage = ""; // the message to show on end game
	boolean forfeited = false;
	boolean inBattle = false;
	Pokemon encounteredPokemon;
	Map.TrainerDirection dir = Map.TrainerDirection.RIGHT;

	// Plays bg sounds :D
	SoundPlayer bgPlayer = new SoundPlayer();

	// Plays sfx :D
	SoundPlayer sfxPlayer = new SoundPlayer();

	// database of Pokemon
	PokemonDatabase database;

	// count number of steps Trainer takes so that encounters aren't every
	// one freaking step

	/*---------------------------------------------------------------------
	 |  Method name:    [GameMode]
	 |  Purpose:  	    [Constructs a GameMode in order to play some POKEMON!!!]
	 |  Parameters:     [MapType: determines the type of Map that this GameMode will be using]
	 *---------------------------------------------------------------------*/
	public GameMode(Random rand) {

		r = rand;

		createMap();

		encounter = new EncounterPanel();

		database = new PokemonDatabase();

		trainer = new Trainer();

		trainer.setPoint(new Point(map.getTrainerPoint()));

		// add KeyListener to this Map so that the user can move the trainer
		// and other button-y things
		map.addKeyListener(new OurKeyListener());
		encounter.addKeyListener(new BattleKeyListener());

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
		if (!map.isAnimating()) {

			int dx = 0, dy = 0;
			int kc = e.getKeyCode();

			// Depending on the KeyEvent passed in, sets the change in the x
			// or y direction accordingly. If the trainer is able to move and
			// the trainer is moving to the next part of the Map, the Map 'moves'
			// so that the new area is shown
			switch (kc) {
			case KeyEvent.VK_UP:
				dx = -1;
				dir = Map.TrainerDirection.UP;
				map.setStartOffsets(-50, 0);
				if (trainerCanMove(e) && (trainer.getPoint().x + dx) % Map.HEIGHT == Map.HEIGHT - 1)
					map.moveUp();
				break;
			case KeyEvent.VK_DOWN:
				dx = 1;
				dir = Map.TrainerDirection.DOWN;
				map.setStartOffsets(50, 0);
				if (trainerCanMove(e) && (trainer.getPoint().x + dx) % Map.HEIGHT == 0)
					map.moveDown();
				break;
			case KeyEvent.VK_RIGHT:
				dy = 1;
				dir = Map.TrainerDirection.RIGHT;
				map.setStartOffsets(0, 50);
				if (trainerCanMove(e) && (trainer.getPoint().y + dy) % Map.WIDTH == 0)
					map.moveRight();
				break;
			case KeyEvent.VK_LEFT:
				dy = -1;
				dir = Map.TrainerDirection.LEFT;
				map.setStartOffsets(0, -50);
				if (trainerCanMove(e) && (trainer.getPoint().y + dy) % Map.WIDTH == Map.WIDTH - 1)
					map.moveLeft();
				break;
			default:
				break;
			}

			// set the visual direction of the trainer sprite on the Map
			map.setTrainerDir(dir);

			// if the trainer can move, then move the trainer and decrease steps
			// and check for random encounter and stuff
			if (trainerCanMove(e)) {

				// change trainer object point
				trainer.getPoint().translate(dx, dy);

				// set trainer's sprite location in map to trainer's current loc
				map.setTrainerPoint(trainer.getPoint());

				// animate the movement
				map.startTrainerMovement();

				// interact with the new tile
				map.getGroundTiles()[trainer.getPoint().x][trainer.getPoint().y]
						.interactWithTrainer();

				setEncounterBG(map.getGroundTiles()[trainer.getPoint().x][trainer.getPoint().y]
						.getTerrainType());

				// decrease steps
				trainer.decreaseSteps();

				

				// start an encounter
				if (new Random().nextInt(10) == 9)
					startEncounter();

				//TODO encounters/items
			} else map.setStartOffsets(0, 0);
			
			// repaint the visual changes
			map.repaint();
		}
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isGameActive]
	 |  Purpose:  	    [To know if the game is active]
	 |  Returns:  	    [boolean: true if game is active, false if game is not]
	 *---------------------------------------------------------------------*/
	public boolean isGameActive() {
		if (isGameWon()) {
			endMessage = "You Won!!!!!!!!";
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

	public String getBattleMessage() {
		return battleMessage;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [forfeitGame]
	 |  Purpose:  	    [Forfeit the game]
	 *---------------------------------------------------------------------*/
	public void forfeitGame() {
		forfeited = true;
		endMessage = "Game Forfeited";
		stopBGMusic();
	}

	public void startEncounter() {

		int rand = new Random().nextInt(30);

		if (rand == 29)
			encounteredPokemon = database.getMew();
		else {
			rand = new Random().nextInt(15);
			if (rand == 9)
				encounteredPokemon = database.getRandomUncommon(map.getCurrentTerrain());
			else
				encounteredPokemon = database.getRandomCommon(map.getCurrentTerrain());
		}

		inBattle = true;
		encounter.startEncounter(encounteredPokemon);
		pauseBGMusic();

	}

	protected void setEncounterBG(TerrainType tt) {

		String path = "./images/bgImages/" + tt.name().toLowerCase() + "BattleBackground.png";

		Image i;
		try {
			i = ImageIO.read(new File(path));
			encounter.setBGImage(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doTrainerAction(TrainerAction action) {

		String pName = encounteredPokemon.getName();

		boolean doAnimation = true;

		if (encounteredPokemon.respond(action) == PokemonResponse.RUN_AWAY) {
			battleMessage = pName + " ran away T_T";
			endEncounter();
		}

		switch (action) {

		case THROW_BALL:
			if (trainer.getItemQuantities().get("PokeBall") == 0) {
				battleMessage = "You have no pokeballs left bitch";
				doAnimation = false;
			} else if (encounteredPokemon.getState() == PokemonResponse.GET_CAUGHT) {
				battleMessage = "You successfully caught " + pName + "!";
				trainer.useItem(new PokeBall());
				trainer.addPokemon(encounteredPokemon);
				endEncounter();
			} else
				battleMessage = "You threw a PokeBall!! But it failed... :(";
			break;
		case RUN_AWAY:
			battleMessage = "You ran away! U little bitch... -.-";
			endEncounter();
			break;
		case THROW_BAIT:
			battleMessage = "You threw bait to" + pName + "!";
			break;
		case THROW_ROCK:
			battleMessage = "You threw a rock at " + pName + "!";
			break;
		default:
			break;

		}

		if (action != TrainerAction.RUN_AWAY && doAnimation)
			encounter.animateTrainer();

	}

	public void useItemOnPokemon(Item i, String pName) {

		trainer.useItem(i);

		if (i.getName().equals("Harmonica")) {
			stopBGMusic();
			bgPath = ((Harmonica) i).getSongFilePath(database.getPokemonByName(pName));
			startNewBGMusic(); 
		}

	}
	
	public void loadImages() {
		map.loadImages();
		encounter.loadImages();
		startNewBGMusic();
	}

	public void useItem(Item i) {
		trainer.useItem(i);
	}

	private void endEncounter() {
		inBattle = false;
		encounter.stopEncounter();
		restartBGMusic();
	}

	public boolean trainerInBattle() {
		return inBattle;
	}

	public EncounterPanel getEncounterPanel() {
		return encounter;
	}

	public void startNewBGMusic() {
		bgPlayer.loopSound(bgPath);
	}
	
	public void restartBGMusic() {
		bgPlayer.restartSound();
	}

	public void pauseBGMusic() {
		bgPlayer.pauseSound();
	}
	
	public void stopBGMusic() {
		bgPlayer.stopSound();
	}

	public void setBGMusicPath(String s) {
		bgPath = s;
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

			// System.out.println("Our GameMode Listener");

			// if the game is not won or lost or forfeited, move the trainer
			if (!inBattle && !forfeited && !isGameWon() && !isGameLost()) {
				// set sprite direction and try to move trainer
				moveTrainer(e);
			}

			if (!isGameActive())
				stopBGMusic();

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

	/*---------------------------------------------------------------------
	 |  Class name:     [OurKeyListener]
	 |  Purpose:        [Used to move trainer around the map]
	 *---------------------------------------------------------------------*/
	private class BattleKeyListener implements KeyListener {

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

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}
}
