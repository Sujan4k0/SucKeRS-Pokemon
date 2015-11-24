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

package model.GameModel;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.Random;

import javax.swing.Timer;

import view.*;
import model.ItemModel.*;
import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonResponse;
import model.TrainerModel.Trainer;
import model.TrainerModel.TrainerAction;

public abstract class GameMode implements Serializable {

	private static final long serialVersionUID = 1L;

	Trainer trainer;
	Map map; // the visual map of this game
	EncounterPanel encounter;
	int moveCount = 0; // moves during encounter
	Random r; // used for random encounters/items

	// messages to show on end game and during battle and random game message
	String endMessage = "", battleMessage = "You've encountered a Pokemon!";
	String alertMessage = "";

	boolean forfeited = false;
	boolean inBattle = false;
	Pokemon encounteredPokemon;
	Map.TrainerDirection dir = Map.TrainerDirection.RIGHT;

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
	 |  Parameters:     [int: the direction the user is trying to move the trainer from KeyEvent]
	 |  Returns:  	    [boolean: true if Trainer can move, false if Trainer can't]
	 *---------------------------------------------------------------------*/
	public boolean trainerCanMove(int keyEventNum) {

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
		if (keyEventNum == KeyEvent.VK_UP && prev.x - 1 >= 0 && obsts[prev.x - 1][prev.y] == null)
			return true;

		else if (keyEventNum == KeyEvent.VK_DOWN && prev.x + 1 < obsts.length
				&& obsts[prev.x + 1][prev.y] == null)
			return true;

		else if (keyEventNum == KeyEvent.VK_LEFT && prev.y - 1 >= 0
				&& obsts[prev.x][prev.y - 1] == null)
			return true;

		else if (keyEventNum == KeyEvent.VK_RIGHT && prev.y + 1 < obsts[0].length
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
	 |  Parameters:  	[int: direction in which trainer is trying to move from KeyEvent]
	 *---------------------------------------------------------------------*/
	public void moveTrainer(int kc) {
		if (!map.isAnimating()) {

			int dx = 0, dy = 0;

			// Depending on the KeyEvent passed in, sets the change in the x
			// or y direction accordingly. If the trainer is able to move and
			// the trainer is moving to the next part of the Map, the Map
			// 'moves'
			// so that the new area is shown
			switch (kc) {
			case KeyEvent.VK_UP:
				dx = -1;
				dir = Map.TrainerDirection.UP;
				map.setStartOffsets(-50, 0);
				/*
				 * if (trainerCanMove(e) && (trainer.getPoint().x + dx) %
				 * Map.HEIGHT == Map.HEIGHT - 1) map.moveUp();
				 */
				break;
			case KeyEvent.VK_DOWN:
				dx = 1;
				dir = Map.TrainerDirection.DOWN;
				map.setStartOffsets(50, 0);
				/*
				 * if (trainerCanMove(e) && (trainer.getPoint().x + dx) %
				 * Map.HEIGHT == 0) map.moveDown();
				 */
				break;
			case KeyEvent.VK_RIGHT:
				dy = 1;
				dir = Map.TrainerDirection.RIGHT;
				map.setStartOffsets(0, 50);
				/*
				 * if (trainerCanMove(e) && (trainer.getPoint().y + dy) %
				 * Map.WIDTH == 0) map.moveRight();
				 */
				break;
			case KeyEvent.VK_LEFT:
				dy = -1;
				dir = Map.TrainerDirection.LEFT;
				map.setStartOffsets(0, -50);
				/*
				 * if (trainerCanMove(e) && (trainer.getPoint().y + dy) %
				 * Map.WIDTH == Map.WIDTH - 1) map.moveLeft();
				 */
				break;
			default:
				break;
			}

			// set the visual direction of the trainer sprite on the Map
			map.setTrainerDir(dir);

			// if the trainer can move, then move the trainer and decrease steps
			// and check for random encounter and stuff
			if (trainerCanMove(kc)) {

				// change trainer object point
				trainer.getPoint().translate(dx, dy);

				// set trainer's sprite location in map to trainer's current loc
				map.setTrainerPoint(trainer.getPoint());

				// animate the movement
				map.startTrainerMovement();

				// play walking sound
				map.playWalkingSound();

				setEncounterBG(map.getGroundTiles()[trainer.getPoint().x][trainer.getPoint().y]
						.getTerrainType());

				// decrease steps
				trainer.decreaseSteps();

				// first try to pick up an item
				if (map.trainerSteppingOnItem()) {
					Item pickedUp = map.getItemAtCurrentLocation();
					trainer.addItem(pickedUp);
					alertMessage =
							"You picked up a *fudging* " + pickedUp.getName() + "!!!!!!! YESSSSSS";

				}
				else if (trainer.getPoint().equals(new Point(1, 7)) 
						&& this.getClass().equals(CEAGame.class)) {
					((CEAGame) this).startLegEncounter();
				}
				// else try to start an encounter
				else if (r.nextInt(15) == 14)
					startEncounter();

				// encounters/items
			} else
				map.setStartOffsets(0, 0);

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
		} else if (forfeited)
			return false;

		return true;
	}

	public boolean gameAlert() {
		if (alertMessage.length() > 0)
			return true;
		return false;
	}

	public String getNotification() {

		String x = alertMessage;
		alertMessage = "";
		return x;
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
		map.stopBGMusic();
	}

	/*---------------------------------------------------------------------
	|  Method name:    [startEncounter]
	|  Purpose:        [This start an encounter]
	 *---------------------------------------------------------------------*/
	public void startEncounter() {

		int rand = r.nextInt(10);
		System.out.println("Map class = " + map.getClass().getName());
		if (rand == 9)
			encounteredPokemon = database.getMew();
		else {
			rand = r.nextInt(5);
			if (rand == 4)
				encounteredPokemon = database.getRandomUncommon(map.getCurrentTerrain());
			else
				encounteredPokemon = database.getRandomCommon(map.getCurrentTerrain());
		}

		battleMessage = "You've encountered a " + encounteredPokemon.getName() + "!";

		inBattle = true;
		encounter.startEncounter(encounteredPokemon);
		map.pauseBGMusic();

	}

	public void setEncounteredPokemon(Pokemon p) {
		encounteredPokemon = p;
	}

	public Pokemon getEncounteredPokemon() {
		return encounteredPokemon;
	}

	protected void setEncounterBG(TerrainType tt) {
		encounter.setBGImages(tt);

	}

	public void doTrainerAction(TrainerAction action) {

		// get the name of the encountered Pokemon
		String pName = encounteredPokemon.getName();

		// to tell the EncounterPanel if the Trainer should be animated
		// at the end of the method
		boolean doAnimation = true;
		
		PokemonResponse pr = encounteredPokemon.respond(action);

		if (moveCount == 10) {
			battleMessage =
					"You used too many turns. F U.\n Pokemon is GONE. GOONNNEEEE. FUUUCKKKK";
			endEncounter();
		}

		// if the Pokemon ran away in response to this TrainerAction
		// update the battleMessage, then end the encounter
		else if (!encounter.isAnimating() && inBattle) {
			moveCount++; // increase moves used
			if (pr == PokemonResponse.RUN_AWAY) {
				battleMessage = "Pokemon ran away!";
				endEncounter();

			} else {

				switch (action) {

				// the Trainer threw a PokeBall
				case THROW_BALL:
					// if Trainer has no PokeBalls to use, update the battleMessage
					// and do not tell the EncounterPanel to animate the trainer
					if (trainer.getItemQuantities().get("PokeBall") == 0) {
						battleMessage = "You have no pokeballs left bitch";
						doAnimation = false;
					}
					// else the Trainer does have PokeBalls to use
					// so use the PokeBall, then check if the Pokemon was caught
					else {

						// use the PokeBall
						trainer.useItem(new PokeBall());

						// if the Trainer caught the Pokemon
						if (encounteredPokemon.getState() == PokemonResponse.GET_CAUGHT) {

							// do anything a specific GameMode has to do when the
							// Trainer
							// catches a Pokemon
							trainerCaughtPokemon(encounteredPokemon);

							// update the battleMessage
							battleMessage = "You successfully caught " + pName + "!";

							// add the Pokemon to the Trainer's List of Pokemon
							trainer.addPokemon(encounteredPokemon);

							// end the encounter
							endEncounter();
						}

						// the Pokemon was not caught
						else
							// just update battleMessage
							battleMessage = "You threw a PokeBall!! But it failed... :(";
					}
					break;

				// the Trainer ran away, update battleMessage, end the encounter
				// and the Trainer should not be animated
				case RUN_AWAY:
					battleMessage = "You ran away! U little bitch... -.-";
					doAnimation = false;
					endEncounter();
					break;
				// the Trainer threw bait, update the battleMessage
				case THROW_BAIT:
					battleMessage = "You threw bait to " + pName + "!";
					break;
				// the Trainer threw a rock, update the battleMessage
				case THROW_ROCK:
					battleMessage = "You threw a rock at " + pName + "!";
					break;
				// any other enum value was passed - do nothing
				default:
					break;

				}

				// if the Trainer should be animated,
				// tell the EncounterPanel to animate the Trainer
				if (doAnimation)
					encounter.animateTrainer(action, pr);
			}
		}

	}

	public void useItemOnPokemon(Item i, String pName) {

		if (i.getName().equals("Harmonica") && trainer.getItems().contains(new Harmonica())) {
			map.changeBGMusic(((Harmonica) i).getSongFilePath(database.getPokemonByName(pName)));
		}

		useItem(i);
	}

	public void loadImages() {
		map.load();
		database.loadAllPokemon();
		encounter.load();
		assignListeners();
	}

	private void assignListeners() {
		// add KeyListener to this Map so that the user can move the trainer
		// and other button-y things
		map.addKeyListener(new OurKeyListener());
	}

	public void useItem(Item i) {

		if (trainer.getItemQuantities().get(i.getName()) > 0 && i.getName().equals("Teleporter")) {
			trainer.useItem(i);
			map.setTrainerPoint(trainer.getPoint());
			map.repaint();
		} else
			trainer.useItem(i);

	}

	private void endEncounter() {

		moveCount = 0; // reset moveCount
		inBattle = false;
		encounter.stopEncounter();
		map.restartBGMusic();
	}

	public boolean trainerInBattle() {
		return inBattle;
	}

	public EncounterPanel getEncounterPanel() {
		return encounter;
	}

	public abstract void trainerCaughtPokemon(Pokemon p);

	// TODO THIS WILL BE MOVED TO MAP
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

			// if the game is not won or lost or forfeited, move the trainer
			if (!inBattle && !forfeited && !isGameWon() && !isGameLost()) {
				// set sprite direction and try to move trainer
				moveTrainer(e.getKeyCode());
			}

			if (!isGameActive())
				map.stopBGMusic();

			// map.update(trainer); // does anything the map needs to check ever
			// key press

			if (database.caughtAllExceptLeg(trainer) && map.getClass().equals(CEAMap.class))
				((CEAMap) map).lastPartCheck(trainer);

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}
}
