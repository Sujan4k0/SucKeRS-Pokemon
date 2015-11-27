package test;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import model.GameModel.*;
import model.ItemModel.BasicStepPotion;
import model.ItemModel.Harmonica;
import model.ItemModel.Item;
import model.ItemModel.PokeBall;
import model.ItemModel.Teleporter;
import model.PokemonModel.Common;
import model.PokemonModel.Legendary;
import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonResponse;
import model.PokemonModel.PokemonType;
import model.PokemonModel.Rarity;
import model.TrainerModel.TrainerAction;

import org.junit.Test;

import com.sun.glass.events.KeyEvent;

import view.CEAMap;
import view.GraphicsManager;
import view.Map;
import view.MazeMap;
import view.PokemonDatabase;

public class GameModelTests {

	private static Pokemon impossiblePM = new Legendary(new Random() {
		@Override
		public int nextInt(int i) {
			return Integer.MAX_VALUE / 2;
		}
	}, "testMon1", new Image[0], PokemonType.WATER);

	private static Pokemon impossiblePM2 = new Legendary(new Random() {
		@Override
		public int nextInt(int i) {
			return Integer.MAX_VALUE / 2;
		}
	}, "testMon3", new Image[0], PokemonType.WATER);

	private static Pokemon alwaysPM = new Common(new Random() {
		@Override
		public int nextInt(int i) {
			return -100;
		}
	}, "testMon2", new Image[0], PokemonType.WATER);

	@Test
	public void testMazeGenerator() {
		Obstacle[][] obstacles = new Obstacle[11][11];
		for (int i = 0; i < 11; i++)
			for (int j = 0; j < 11; j++)
				obstacles[i][j] = Obstacle.ROCK_1;

		new MazeGenerator(new Random(), new Point(5, 1), obstacles).generateMaze();

		// maze obstacles should only have 1 null on the left most 
		// and also right most sides of the map
		int rightCount = 0;
		int leftCount = 0;
		for (int i = 0; i < obstacles.length; i++) {
			if (obstacles[i][0] == null)
				leftCount++;
			if (obstacles[i][obstacles[0].length - 1] == null)
				rightCount++;
		}
		assertEquals(leftCount, 1);
		assertEquals(rightCount, 1);

	}

	@Test
	public void testGround() {

		// .getTerrainType() tests
		assertEquals(Ground.BINARY_1.getTerrainType(), TerrainType.MYSTERY);
		assertEquals(Ground.CAVE_1.getTerrainType(), TerrainType.CAVE);
		assertEquals(Ground.GRASS_2.getTerrainType(), TerrainType.GRASS);
		assertEquals(Ground.FOREST_1.getTerrainType(), TerrainType.FOREST);
		assertEquals(Ground.ICE_1.getTerrainType(), TerrainType.ICE);
		assertEquals(Ground.ICE_2.getTerrainType(), TerrainType.ICE);

		// values(), valueOf(), getSoundFilePath() tests
		for (Ground g : Ground.values()) {
			assertEquals(g, Ground.valueOf(g.name()));

			// could be either string
			assertTrue((g.getSoundFilePath().equals(TerrainType.BASEDIR + "Grass_Step_1.wav") || g
					.getSoundFilePath().equals(TerrainType.BASEDIR + "Grass_Step_1.wav")));
			assertTrue((g.getSoundFilePath().equals(TerrainType.BASEDIR + "Grass_Step_2.wav") || g
					.getSoundFilePath().equals(TerrainType.BASEDIR + "Grass_Step_2.wav")));
		}
	}

	@Test
	public void testObstacles() {

		// some getTerrainType() tests
		assertEquals(Obstacle.ROCK_1.getTerrainType(), TerrainType.CAVE);
		assertEquals(Obstacle.TREE_SNOWY.getTerrainType(), TerrainType.ICE);
		assertEquals(Obstacle.TREE_LIGHT.getTerrainType(), TerrainType.GENERIC);
		assertEquals(Obstacle.TREE_DARK.getTerrainType(), TerrainType.GENERIC);
		assertEquals(Obstacle.CACTUS_1.getTerrainType(), TerrainType.DESERT);
		assertEquals(Obstacle.CACTUS_2.getTerrainType(), TerrainType.DESERT);
		assertEquals(Obstacle.CACTUS_3.getTerrainType(), TerrainType.DESERT);

		// values(), valueOf() tests
		for (Obstacle o : Obstacle.values()) {
			assertEquals(o, Obstacle.valueOf(o.name()));
		}

	}

	@Test
	public void testTerrainType() {

		// all TerrainTypes should have the same associated sound paths
		for (TerrainType t : TerrainType.values()) {
			assertEquals(t.getSound1(), "./sounds/walkingSFX/Grass_Step_1.wav");
			assertEquals(t.getSound2(), "./sounds/walkingSFX/Grass_Step_2.wav");

			// test using valueOf() method
			assertEquals(t, TerrainType.valueOf(t.name()));
		}

	}

	@Test
	public void cEAGameMapTest() {
		CEAGame cea = new CEAGame(new Random(), true);
		// CEAGame should have CEAMap
		assertEquals(cea.getMap().getClass(), CEAMap.class);
	}

	@Test
	public void cEAGameIsGameWonTest() {
		CEAGame cea = new CEAGame(new Random(), true);
		// CEAGame should not be won
		assertFalse(cea.isGameWon());
		// add mew to trainer
		cea.getTrainer().addPokemon(cea.getDatabase().getMew());
		// now game should be won
		assertTrue(cea.isGameWon());
	}

	@Test
	public void cEAGameIsGameLostTest1() {
		CEAGame cea = new CEAGame(new Random(), true);
		// CEAGame should not be lost
		assertFalse(cea.isGameLost());

		// remove all steps from trainer
		int s = cea.getTrainer().getSteps();
		for (int i = 0; i < s; i++)
			cea.getTrainer().decreaseSteps();

		// now game should be lost
		assertTrue(cea.isGameLost());
	}

	@Test
	public void cEAGameIsGameLostTest2() {
		CEAGame cea = new CEAGame(new Random(), true);
		// CEAGame should not be lost
		assertFalse(cea.isGameLost());

		// remove all pokeballs from trainer
		int s = cea.getTrainer().getItemQuantities().get("PokeBall");
		for (int i = 0; i < s; i++)
			cea.getTrainer().useItem(new PokeBall());

		// now game should be lost
		assertTrue(cea.isGameLost());
	}

	@Test
	public void cEAGameTrainerCaughtPokemonTest() {
		// just make sure method doesn't throw exception
		new CEAGame(new Random(), true).trainerCaughtPokemon(impossiblePM);
	}

	@Test
	public void cEAGameStartEncounterTest() {
		CEAGame g = new CEAGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 4;
			}
		}, true);

		g.startEncounter(false); // plainy land

		assertEquals(g.getEncounteredPokemon().getName(), "STEELIX");
	}

	@Test
	public void cEAGameStartEncounterTest2() {
		CEAGame g = new CEAGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 4;
			}
		}, true);

		g.getTrainer().setPoint(new Point(Map.HEIGHT * 3 - 3, Map.WIDTH * 2 - 3));

		g.startEncounter(false); // icey land

		assertEquals(g.getEncounteredPokemon().getName(), "PIKACHU");
	}

	@Test
	public void cEAGameStartEncounterTest3() {
		CEAGame g = new CEAGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 4;
			}
		}, true);

		g.getTrainer().setPoint(new Point(Map.HEIGHT - 3, Map.WIDTH * 2 - 3));

		g.startEncounter(false); // cavey land

		assertEquals(g.getEncounteredPokemon().getName(), "EXEGGUTOR");
	}

	@Test
	public void cEAGameStartEncounterTest4() {
		CEAGame g = new CEAGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 4;
			}
		}, true);

		g.getTrainer().setPoint(new Point(Map.HEIGHT * 2 - 3, Map.WIDTH * 3 - 3));

		g.startEncounter(false); // deserty land

		assertEquals(g.getEncounteredPokemon().getName(), "GYRADOS");
	}

	@Test
	public void cEAGameStartLegEncounterTest() {
		CEAGame g = new CEAGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 4;
			}
		}, true);

		g.startLegEncounter();

		assertEquals(g.getEncounteredPokemon().getName(), "MEW");
	}

	@Test
	public void cEAGameLastPartTest() {
		CEAGame g = new CEAGame(new Random(), true);
		for (Pokemon p : g.getDatabase().getAllPokemon())
			if (!p.getName().equals("MEW"))
				g.getTrainer().addPokemon(p);

		((CEAGame) g).lastPartCheck();
		assertTrue(g.getTrainer().getItemQuantities().get("Teleporter") > 0);
		assertTrue(((CEAGame) g).inLastPart());
	}

	@Test
	public void cEAGameLegSpotTest() {
		CEAGame g = new CEAGame(new Random(), true);
		g.getTrainer().setPoint(new Point(2, 7));
		g.moveTrainer(KeyEvent.VK_UP, false);
	}

	@Test
	public void mazeGameMapTest() {
		MazeGame maze = new MazeGame(new Random(), true);
		maze.createMap();
		// MazeGame should have MazeMap
		assertEquals(maze.getMap().getClass(), MazeMap.class);
	}

	@Test
	public void mazeGameIsGameLostTest() {
		MazeGame maze = new MazeGame(new Random(), true);

		// MazeGame should not be lost
		assertFalse(maze.isGameLost());

		// remove all steps from trainer
		int s = maze.getTrainer().getSteps();
		for (int i = 0; i < s; i++)
			maze.getTrainer().decreaseSteps();

		// now game should be lost
		assertTrue(maze.isGameLost());
	}

	@Test
	public void mazeGameIsGameWonTest() {
		MazeGame maze = new MazeGame(new Random(), true);

		// MazeGame should not be won
		assertFalse(maze.isGameWon());

		// find end of Maze and move trainer to there
		int loc = 0;
		for (int i = 0; i < MazeMap.HEIGHT; i++) {
			if (maze.getMap().getObstacleTiles()[i][MazeMap.WIDTH - 1] == null) {
				loc = i;
				break;
			}
		}
		maze.getTrainer().setPoint(new Point(loc, MazeMap.WIDTH - 1));

		// game should now be won
		assertTrue(maze.isGameWon());
	}

	@Test
	public void mazeGameTrainerCaughtPokemonTest() {
		// should increase steps by 10
		MazeGame maze = new MazeGame(new Random(), true);
		int steps = maze.getTrainer().getSteps();
		PokemonDatabase db = new PokemonDatabase();
		maze.trainerCaughtPokemon(db.getCyndaquil());
		assertEquals(steps + 5, maze.getTrainer().getSteps());
		steps = maze.getTrainer().getSteps();
		maze.trainerCaughtPokemon(db.getGyrados());
		assertEquals(steps + 10, maze.getTrainer().getSteps());
		steps = maze.getTrainer().getSteps();
		maze.trainerCaughtPokemon(db.getMew());
		assertEquals(steps + 20, maze.getTrainer().getSteps());

	}

	@Test
	public void gameModeTrainerCanMoveTest() {
		GameMode g = new CEAGame(new Random(), true);

		// in CEAGame, should be able to move any direction at start
		assertTrue(g.trainerCanMove(KeyEvent.VK_LEFT));
		assertTrue(g.trainerCanMove(KeyEvent.VK_DOWN));
		assertTrue(g.trainerCanMove(KeyEvent.VK_RIGHT));
		assertTrue(g.trainerCanMove(KeyEvent.VK_UP));
	}

	@Test
	public void gameModeMoveTrainerTest() {
		Random r = new Random() {
			@Override
			public int nextInt(int i) {
				return 9; // so encounter starts
			}
		};
		GameMode g = new CEAGame(r, true);
		// in CEAGame, should be able to move any direction at start
		Point start = new Point(g.getTrainer().getPoint());
		g.moveTrainer(KeyEvent.VK_LEFT, false);
		Point end = new Point(g.getTrainer().getPoint());
		assertEquals(start, new Point(end.x, end.y + 1));
		start = new Point(g.getTrainer().getPoint());
		g.moveTrainer(KeyEvent.VK_RIGHT, false);
		end = new Point(g.getTrainer().getPoint());
		assertEquals(start, new Point(end.x, end.y - 1));
		start = new Point(g.getTrainer().getPoint());
		g.moveTrainer(KeyEvent.VK_UP, false);
		end = new Point(g.getTrainer().getPoint());
		assertEquals(start, new Point(end.x + 1, end.y));
		start = new Point(g.getTrainer().getPoint());
		g.moveTrainer(KeyEvent.VK_DOWN, false);
		end = new Point(g.getTrainer().getPoint());
		assertEquals(start, new Point(end.x - 1, end.y));

	}

	@Test
	public void mazeGametartEncounterTest() {
		GameMode g = new MazeGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 9;
			}
		}, true);

		g.startEncounter(false);

		assertEquals(g.getEncounteredPokemon().getRarity(), Rarity.LEGENDARY);

	}

	@Test
	public void mazeGameStartEncounterTest2() {
		GameMode g = new MazeGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 4;
			}
		}, true);

		g.startEncounter(false);

		assertEquals(g.getEncounteredPokemon().getRarity(), Rarity.UNCOMMON);

	}

	@Test
	public void mazeGameStartEncounterTest3() {
		GameMode g = new MazeGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 0;
			}
		}, true);

		g.startEncounter(false);

		assertEquals(g.getEncounteredPokemon().getRarity(), Rarity.COMMON);

	}

	@Test
	public void gameModeEndGameTest() {
		GameMode g = new MazeGame(new Random(), true);

		assertTrue(g.isGameActive());
		// win game
		int loc = 0;
		for (int i = 0; i < MazeMap.HEIGHT; i++) {
			if (g.getMap().getObstacleTiles()[i][MazeMap.WIDTH - 1] == null) {
				loc = i;
				break;
			}
		}

		g.getTrainer().setPoint(new Point(loc, MazeMap.WIDTH - 1));
		assertFalse(g.isGameActive());
		assertTrue(g.getEndMessage().toUpperCase().contains("WON"));

		g = new CEAGame(new Random(), true);
		// lose game
		int s = g.getTrainer().getSteps();
		for (int i = 0; i < s; i++)
			g.getTrainer().decreaseSteps();

		assertFalse(g.isGameActive());
		assertTrue(g.getEndMessage().toUpperCase().contains("LOST"));

		g = new MazeGame(new Random(), true);
		//forfeit game
		g.forfeitGame();
		assertFalse(g.isGameActive());
		assertTrue(g.getEndMessage().toUpperCase().contains("FORFEIT"));

	}

	@Test
	public void gameModeSetEndMessageTest() {
		GameMode g = new MazeGame(new Random(), true);
		g.setEndMessage("yo");

		assertEquals(g.getEndMessage(), "yo");
	}

	@Test
	public void gameModeDoTrainerActionTest() {
		GameMode g = new CEAGame(new Random() {
			@Override
			public int nextInt(int i) {
				return 0;
			}
		}, true);
		g.setEncounteredPokemon(impossiblePM2);
		g.startEncounter(false);
		assertTrue(g.trainerInBattle());
		// set the encountered pokemon to something already running away
		impossiblePM2.setState(PokemonResponse.RUN_AWAY);
		// throw bait
		g.doTrainerAction(TrainerAction.THROW_BAIT);
		// pokemon should still have run away state
		assertEquals(impossiblePM2.getState(), PokemonResponse.RUN_AWAY);
	}

	@Test
	public void gameModePokemonRunAwayTest() {
		GameMode g = new CEAGame(new Random(), true);
		Pokemon alwaysPM2 = new Common(new Random() {
			@Override
			public int nextInt(int i) {
				return -100;
			}
		}, "testMon2", new Image[0], PokemonType.WATER);
		alwaysPM2.setState(PokemonResponse.RUN_AWAY);
		g.setEncounteredPokemon(alwaysPM2);
		g.startEncounter(false);
		g.doTrainerAction(TrainerAction.THROW_ROCK);
		// battle msg should be updated
		//assertFalse(g.trainerInBattle());
		assertTrue(g.getBattleMessage().toUpperCase().contains("RAN AWAY"));
	}

	@Test
	public void gameModeDoTrainerActionThrowBallTest() {
		GameMode g = new MazeGame(new Random(), true);
		// should start off with more than 0 balls so this is fine
		int balls = g.getTrainer().getItemQuantities().get("PokeBall");
		// set the encountered pokemon to something impossible to catch
		g.setEncounteredPokemon(impossiblePM);
		g.startEncounter(false);
		// use a ball
		g.doTrainerAction(TrainerAction.THROW_BALL);
		//should have decremented balls by 1
		assertEquals(balls, g.getTrainer().getItemQuantities().get("PokeBall") + 1);

	}

	@Test
	public void gameModeDoTrainerActionThrowBallTest2() {
		GameMode g = new MazeGame(new Random(), true);
		// should start off with more than 0 balls so this is fine
		int balls = g.getTrainer().getItemQuantities().get("PokeBall");
		// set the encountered pokemon to something impossible to catch
		g.setEncounteredPokemon(impossiblePM);
		g.startEncounter(false);
		//use all balls
		for (int i = 0; i < balls; i++)
			g.useItem(new PokeBall());
		//try to throw another ball
		g.doTrainerAction(TrainerAction.THROW_BALL);
		//battle message should be updated
		assertTrue(g.getBattleMessage().toUpperCase().contains("NO POKEBALLS LEFT"));

	}

	@Test
	public void gameModeDoTrainerActionThrowBallTest3() {
		GameMode g = new MazeGame(new Random(), true);
		//catch the pokemon
		g.getTrainer().addItem(new PokeBall());
		g.setEncounteredPokemon(alwaysPM);
		g.startEncounter(false);
		g.doTrainerAction(TrainerAction.THROW_BALL);
		//battle msg should be updated
		assertTrue(g.getBattleMessage().toUpperCase()
				.contains("CAUGHT " + alwaysPM.getName().toUpperCase()));
		//1 pokemon should be in Trainer's list of mons
		assertTrue(g.getTrainer().getPokemon().size() == 1);
	}

	@Test
	public void gameModeDoTrainerActionRunAwayTest() {
		GameMode g = new MazeGame(new Random(), true);
		// set the encountered pokemon to something impossible to run
		g.setEncounteredPokemon(impossiblePM);
		g.startEncounter(false);
		// run away
		g.doTrainerAction(TrainerAction.RUN_AWAY);
		// battle msg should be updated
		assertTrue(g.getBattleMessage().toUpperCase().contains("RAN AWAY"));

	}

	@Test
	public void gameModeDoTrainerActionThrowBaitTest() {
		GameMode g = new MazeGame(new Random(), true);
		// set the encountered pokemon to something impossible to run
		g.setEncounteredPokemon(impossiblePM);
		g.startEncounter(false);
		// throw bait
		g.doTrainerAction(TrainerAction.THROW_BAIT, false);
		// battle msg should be updated
		assertTrue(g.getBattleMessage().toUpperCase().contains("BAIT"));

	}

	@Test
	public void gameModeDoTrainerActionThrowRockTest() {
		GameMode g = new MazeGame(new Random(), true);
		// set the encountered pokemon to something impossible to run
		g.setEncounteredPokemon(impossiblePM);
		g.startEncounter(false);
		// throw rock
		g.doTrainerAction(TrainerAction.THROW_ROCK, false);
		// battle msg should be updated
		assertTrue(g.getBattleMessage().toUpperCase().contains("ROCK"));
	}

	@Test
	public void gameModeUseItemTest() {
		GameMode g = new CEAGame(new Random(), true);
		//give the Trainer items
		Teleporter tele = new Teleporter();
		g.getTrainer().addItem(tele);
		g.getTrainer().addItem(new Harmonica());

		// use the harmonica
		String pName = new PokemonDatabase().getDiglett().getName();
		assertFalse(g.getMap().getBGMusicFilePath().toUpperCase().contains(pName.toUpperCase()));
		g.useItemOnPokemon(new Harmonica(), pName);
		assertTrue(g.getMap().getBGMusicFilePath().toUpperCase().contains(pName.toUpperCase()));

		// use the teleporter
		assertFalse(tele.isSet());
		g.useItem(tele);
		assertTrue(tele.isSet());
		assertTrue(g.getTrainer().getPoint().equals(tele.getTeleportPoint()));

	}

	@Test
	public void gameModeMoveCountTest() {
		GameMode g = new CEAGame(new Random(), true);
		int balls = g.getTrainer().getItemQuantities().get("PokeBall");

		g.startEncounter(false);

		for (int i = 0; i < balls; i++)
			g.getTrainer().useItem(new PokeBall());

		for (int i = 0; i < 11; i++)
			g.doTrainerAction(TrainerAction.THROW_BALL);

		assertTrue(g.getEncounteredPokemon() == null);
	}

	@Test
	public void gameModePickUpItemTest() {
		GameMode g = new CEAGame(new Random(), true);
		Item[][] items = g.getMap().getItemTiles();
		items[g.getTrainer().getPoint().x][g.getTrainer().getPoint().y + 1] = new BasicStepPotion();
		g.moveTrainer(KeyEvent.VK_RIGHT, false);
		assertTrue(g.getTrainer().getItems().contains(new BasicStepPotion()));
		assertTrue(g.gameAlert());
		assertTrue(g.getNotification().length() > 0);
		assertTrue(g.getNotification().equals(""));
	}

}
