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

public class CEAMap extends Map {

	private static final long serialVersionUID = 1L;
	
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
		// TODO get CEA Tiles and Obstacles

		// grassy land = bush
		// cave = rocks
		// ice = ice blocks
		// desert = cactus
		// poop = toilets
		// rocky enclosure with entrance
		// zig-zag rocky paths?

		// first area is all teleport land
		// obstacles[0][0] to height - 1, width -1

		// plain land
		// 4, 14 is top 7,14 is bot of entrance/exit
		// north, west, south all wall
		// 3 by 3 area of trees
		// obstacleTiles[Map.HEIGHT][0]


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

	}

}
