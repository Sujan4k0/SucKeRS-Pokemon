/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Obstacle] 
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
 | Description: The Obstacle Tiles to be used on Maps. These cannot be walked on/through
 | and may later have an interaction with the Trainer.
 *===========================================================================*/
package model.GameModel;

public enum Obstacle implements Tile {

	ROCK_1(TerrainType.CAVE), ROCK_2(TerrainType.GRASS), ROCK_3(TerrainType.CAVE),
	ROCK_4(TerrainType.GENERIC), ROCK_5(TerrainType.GRASS), ROCK_6(TerrainType.CAVE),
	ROCK_7(TerrainType.GENERIC), ROCK_8(TerrainType.GRASS), ROCK_9(TerrainType.CAVE),
	ROCK_10(TerrainType.GENERIC),

	TREE_LIGHT(TerrainType.GENERIC), TREE_DARK(TerrainType.GENERIC),
	TREE_LIGHT2(TerrainType.GENERIC), TREE_WEIRD(TerrainType.GENERIC),
	TREE_PALM(TerrainType.GENERIC), TREE_SNOWY(TerrainType.ICE), TREE_1(TerrainType.GENERIC),
	TREE_2(TerrainType.GENERIC), TREE_3(TerrainType.GENERIC), TREE_4(TerrainType.GENERIC),

	CACTUS_1(TerrainType.DESERT), CACTUS_2(TerrainType.DESERT), CACTUS_3(TerrainType.DESERT);

	private TerrainType terrainType;

	/*---------------------------------------------------------------------
	 |  Method name:    [Obstacle]
	 |  Purpose:  	    [Set instance variable values based on parameter]
	 |  Parameters:     [TerrainType: the type of terrain to associate this Obstacle with]
	 *---------------------------------------------------------------------*/
	Obstacle(TerrainType tt) {

		terrainType = tt;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getTerrainType]
	 |  Purpose:  	    [Getter for TerrainType terrainType]
	 |  Returns:  	    [TerrainType]
	 *---------------------------------------------------------------------*/
	@Override
	public TerrainType getTerrainType() {
		return terrainType;
	}

}
