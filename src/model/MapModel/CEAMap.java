/*=========================================================================== 
 | Assignment: FINAL PROJECT: [CEAMap] 
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
 | Description: This class extends Map and is used to make a CEAMap, this Map
 | used in the CEAGame (CATCH EM ALL!!!!). The createMap() method is automatically
 | called in the super() constructor.
 *===========================================================================*/
package model.MapModel;

import java.awt.Point;

public class CEAMap extends Map {

	private static final long serialVersionUID = 1L;

	// entire CEAMap is 3 by 3 Maps combined
	public static int WIDTH = (Map.WIDTH * 3), HEIGHT = (Map.HEIGHT * 3);

	/*---------------------------------------------------------------------
	 |  Method name:    [CEAMap]
	 |  Purpose:  	    [Constructs a CEAMap]
	 *---------------------------------------------------------------------*/
	public CEAMap() {
		super();
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [createMap]
	 |  Purpose:  	    [To fill the Ground and Obstacle arrays with the necessary
	 | 					Tiles that are unique to the CEAMap format]
	 *---------------------------------------------------------------------*/
	@Override
	public void createMap() {

		// these values are needed in order for the move
		// methods to work (refer to Map.java)
		w = CEAMap.WIDTH;
		h = CEAMap.HEIGHT;

		groundTiles = new Ground[h][w];
		obstacleTiles = new Obstacle[h][w];

		for (int i = 0; i < groundTiles.length; i++) {
			for (int j = 0; j < groundTiles[0].length; j++)
				groundTiles[i][j] = Ground.CAVE_1;

		}
		// TODO get CEA Tiles and Obstacles

		// grassy land = bush
		// cave = rocks
		// ice = ice blocks
		// desert = cactus
		// poop = toilets
		// rocky enclosure with entrance
		// zig-zag rocky paths?

		// first area is all teleport land YO
		// obstacles[0][0] to height - 1, width -1

		moveDown();
		// plain land
		// 4, 14 is top 7,14 is bot of entrance/exit
		// north, west, south all wall
		// 3 by 3 area of trees
		for (int i = Map.HEIGHT; i < 2 * Map.HEIGHT; i++) {
			for (int j = 0; j < Map.WIDTH; j++) {
				groundTiles[i][j] = Ground.GRASS_2;

				// border of rocks
				if (i == Map.HEIGHT || j == 0 || i == Map.HEIGHT * 2 - 1 || j == Map.WIDTH - 1)
					obstacleTiles[i][j] = Obstacle.TREE_LIGHT;

			}
		}

		for (int i = Map.HEIGHT + Map.HEIGHT / 2 - 1; i < Map.HEIGHT + Map.HEIGHT / 2 + 2; i++)
			obstacleTiles[i][Map.WIDTH - 1] = null;
		

		for (int i = Map.WIDTH - 6; i < Map.WIDTH - 3; i++) 
			for (int j = 2 * Map.HEIGHT - 5; j < 2 * Map.HEIGHT - 2; j++) {
				obstacleTiles[j][i] = Obstacle.TREE_1;
			}

		// grassy land
		// cave entrance w/ cave-y tiles and obstacles of cave-y cave, etc.
		// circly enclosure 
		// obstacleTiles[Map.HEIGHT][Map.WIDTH]

		// desert land
		// randomly placed cactus :D

		// ice land
		// look at pic

		// teleport land
		// alternating 0 1 tiles

		setTrainerPoint(new Point(Map.HEIGHT + Map.HEIGHT / 2, 2));

	}

}
