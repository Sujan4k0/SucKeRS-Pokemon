package model.MapModel;

import java.awt.Point;
import java.util.Random;

public class MazeMap extends Map {

	private static final long serialVersionUID = 1L;

	// entire maze Map is 3 by 3 visible maps combined
	public static int WIDTH = Map.WIDTH * 3, MAZE_HEIGHT = Map.HEIGHT * 3;

	public MazeMap() {
		super();
		
		// these values are needed in order for the move
		// methods to work (refer to Map.java)
		w = WIDTH;
		h = HEIGHT;
	}
	
	@Override
	public void createMap() {
		groundTiles = new Ground[HEIGHT][WIDTH];
		obstacleTiles = new Obstacle[HEIGHT][WIDTH];

		//TODO: Fill groundTiles[][] with Ground
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {

				// fills all ground with just these cave tiles
				groundTiles[i][j] = Ground.CAVE_1;
			}
		}

		//TODO: Fill obstacleTiles[][] with Obstacles
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				obstacleTiles[i][j] = Obstacle.ROCK_1;
			}
		}

		int randX = new Random().nextInt(HEIGHT - 2) + 1;

		// makes randX have to be an odd integer so that there is
		// only a single border of obstacles around the edges :D
		if (randX % 2 == 0 && randX < HEIGHT - 3)
			randX += 1;
		else if (randX % 2 == 0)
			randX -= 1;

		// generates the maze tiles
		obstacleTiles = new MazeGenerator(new Point(randX, 1), obstacleTiles)
				.generateMaze();

		for (int i = 0; i < HEIGHT; i++) {
			if (obstacleTiles[i][0] == null)
				setTrainerPoint(new Point(i, 0));
		}

		for (int i = 0; i < HEIGHT; i++) {
			if (obstacleTiles[i][WIDTH - 1] == null)
				groundTiles[i][WIDTH - 1] = Ground.ICE_1;
		}

		// System.out.println("Start Point : " + trainerPoint.toString());

		// move the Map to the are where the Trainer starts
		if (trainerPoint.x >= HEIGHT)
			moveDown();
		if (trainerPoint.x >= 2 * HEIGHT)
			moveDown();
	}

}
