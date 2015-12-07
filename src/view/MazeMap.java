/*=========================================================================== 
 | Assignment: FINAL PROJECT: [MazeMap] 
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
 | Description: This is the Map used in the MazeGame. It holds two arrays of 
 | Tiles (Ground and Obstacle) that visually create a randomly generated maze
 | that the player must traverse the Trainer through.
 *===========================================================================*/
package view;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import model.GameModel.Ground;
import model.GameModel.MazeGenerator;
import model.GameModel.Obstacle;
import model.ItemModel.BasicStepPotion;
import model.ItemModel.Harmonica;
import model.ItemModel.Item;
import model.ItemModel.SuperStepPotion;
import model.ItemModel.Teleporter;

public class MazeMap extends Map {

	private static final long serialVersionUID = 1L;

	// entire MazeMap is 3 by 3 Maps combined
	public static int WIDTH = (Map.WIDTH * 3), HEIGHT = (Map.HEIGHT * 3);

	/*---------------------------------------------------------------------
	 |  Method name:    [MazeMap]
	 |  Purpose:  	    [Construct a MazeMap]
	 *---------------------------------------------------------------------*/
	public MazeMap(Random rand) {

		super(rand);

		bgPath = "./sounds/Ruby_Sapphire_Mt.Pyre.mp3";
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [createMap]
	 |  Purpose:  	    [To fill the Obstacle and Ground arrays with the correct Tiles
	 |					 for visual use]
	 *---------------------------------------------------------------------*/
	@Override
	public void createMap() {

		// these values are needed in order for the move
		// methods to work (refer to Map.java)
		w = MazeMap.WIDTH;
		h = MazeMap.HEIGHT;

		// initialize arrays
		groundTiles = new Ground[h][w];
		obstacleTiles = new Obstacle[h][w];

		int randGround = r.nextInt(4);
		Ground ground, endGround;
		Obstacle obst;

		switch (randGround) {

		case 1:
			ground = Ground.SAND_2;
			obst = Obstacle.CACTUS_2;
			endGround = Ground.GRASS_2;
			break;
		case 2:
			ground = Ground.FOREST_1;
			obst = Obstacle.TREE_1;
			endGround = Ground.ICE_1;
			break;
		case 3:
			ground = Ground.ICE_2;
			obst = Obstacle.TREE_SNOWY;
			endGround = Ground.SAND_2;
			break;
		default:
			ground = Ground.CAVE_1;
			obst = Obstacle.ROCK_1;
			endGround = Ground.BINARY_1;
			break;

		}

		// Fill groundTiles[][] with Ground
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {

				// fills all ground with just these cave tiles
				groundTiles[i][j] = ground;
			}
		}

		// Fill obstacleTiles[][] with Obstacles
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				obstacleTiles[i][j] = obst;
			}
		}

		// generates a random location along the height at width = 1
		// to be used for the starting point of the maze generator
		int randX = r.nextInt(h - 1);

		if (randX == 0)
			randX++;

		// makes randX have to be an odd integer so that there is
		// only a single border of obstacles around the edges :D
		if (randX % 2 == 0 && randX < h - 3)
			randX += 1;
		else if (randX % 2 == 0)
			randX -= 1;

		// generates the maze tiles
		new MazeGenerator(r, new Point(randX, 1), obstacleTiles).generateMaze();

		// find the only null Obstacle on the left-most side
		// this is where the trainer will start
		for (int i = 0; i < h; i++) {
			if (obstacleTiles[i][0] == null)
				setTrainerPoint(new Point(i, 0));
		}

		// find the only null Obstacle on the right-most side
		// this is the exit: shown by an ice tiles
		for (int i = 0; i < h; i++) {
			if (obstacleTiles[i][w - 1] == null)
				groundTiles[i][w - 1] = endGround;
		}
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	@Override
	public void initializeItems() {
		itemTiles = new Item[h][w];

		int count = 0;

		// place 3 harmonicas
		while (count < 3) {

			// avoid entrance and exit
			int randX = r.nextInt(obstacleTiles.length - 2) + 1;
			int randY = r.nextInt(obstacleTiles[0].length - 2) + 1;

			if (obstacleTiles[randX][randY] == null) {
				itemTiles[randX][randY] = new Harmonica();
				count++;
			}
		}

		count = 0;

		// place 3 basic step potions
		while (count < 3) {

			// avoid entrance and exit
			int randX = r.nextInt(obstacleTiles.length - 2) + 1;
			int randY = r.nextInt(obstacleTiles[0].length - 2) + 1;

			if (obstacleTiles[randX][randY] == null) {
				itemTiles[randX][randY] = new BasicStepPotion();
				count++;
			}
		}

		count = 0;

		// place 1 super step potion
		while (count < 1) {

			// avoid entrance and exit
			int randX = r.nextInt(obstacleTiles.length - 2) + 1;
			int randY = r.nextInt(obstacleTiles[0].length - 2) + 1;

			if (obstacleTiles[randX][randY] == null) {
				itemTiles[randX][randY] = new SuperStepPotion();
				count++;
			}
		}

		// place 1 teleporter right at entrance
		for (int i = 0; i < obstacleTiles.length; i++) {
			if (obstacleTiles[i][0] == null) {
				itemTiles[i][1] = new Teleporter();
				break;
			}
		}

	}

}
