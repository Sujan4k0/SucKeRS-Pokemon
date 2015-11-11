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
package model.MapModel;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

public class MazeMap extends Map {

	private static final long serialVersionUID = 1L;

	// entire MazeMap is 3 by 3 Maps combined
	public static int WIDTH = (Map.WIDTH * 3), HEIGHT = (Map.HEIGHT * 3);

	/*---------------------------------------------------------------------
	 |  Method name:    [MazeMap]
	 |  Purpose:  	    [Construct a MazeMap]
	 *---------------------------------------------------------------------*/
	public MazeMap() {

		super();
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

		groundTiles = new Ground[h][w];
		obstacleTiles = new Obstacle[h][w];

		//TODO: Fill groundTiles[][] with Ground
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {

				// fills all ground with just these cave tiles
				groundTiles[i][j] = Ground.CAVE_1;
			}
		}

		//TODO: Fill obstacleTiles[][] with Obstacles
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				obstacleTiles[i][j] = Obstacle.ROCK_1;
			}
		}

		int randX = new Random().nextInt(h - 2) + 1;

		// makes randX have to be an odd integer so that there is
		// only a single border of obstacles around the edges :D
		if (randX % 2 == 0 && randX < h - 3)
			randX += 1;
		else if (randX % 2 == 0)
			randX -= 1;

		// generates the maze tiles
		obstacleTiles = new MazeGenerator(new Point(randX, 1), obstacleTiles).generateMaze();

		for (int i = 0; i < h; i++) {
			if (obstacleTiles[i][0] == null)
				setTrainerPoint(new Point(i, 0));
		}

		for (int i = 0; i < h; i++) {
			if (obstacleTiles[i][w - 1] == null)
				groundTiles[i][w - 1] = Ground.ICE_1;
		}

		// System.out.println("Start Point : " + trainerPoint.toString());

		// move the Map to the are where the Trainer starts
		if (trainerPoint.x >= Map.HEIGHT)
			moveDown();
		if (trainerPoint.x >= 2 * Map.HEIGHT)
			moveDown();
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getTrainerDir]
	 |  Purpose:  	    [Getter for the Map.Direction that the trainer sprite is facing]
	 |  Returns:  	    [Map.Direction: direction the trainer sprite is facing.
	 |					 There are multiple directions (DOWN and DOWN_1, etc) because
	 |					 they correspond to a section of the trainer's sprite sheet]
	 *---------------------------------------------------------------------*/
	public Map.Direction getTrainerDir() {
		// TODO Auto-generated method stub
		return dir;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getTrainerSheet]
	 |  Purpose:  	    [Getter for the trainer's sprite sheet]
	 |  Returns:  	    [Image: the trainer's sprite sheet]
	 *---------------------------------------------------------------------*/
	public Image getTrainerSheet() {
		// TODO Auto-generated method stub
		return trainerSheet;
	}

}
