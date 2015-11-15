/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Ground] 
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
 | Description: This enum holds the different type of Ground Tiles that can be
 | placed onto the ground in the game for aesthetic, and later auditory, purposes.
 *===========================================================================*/
package model.MapModel;


public enum Ground implements Tile {
	
	ICE_1(TerrainType.ICE), ICE_2(TerrainType.ICE), ICE_3(TerrainType.ICE),
	ICE_4(TerrainType.ICE), ICE_5(TerrainType.ICE),
	CAVE_1(TerrainType.CAVE), CAVE_2(TerrainType.CAVE), CAVE_3(TerrainType.CAVE),
	CAVE_4(TerrainType.CAVE), CAVE_5(TerrainType.CAVE),
	WEIRD_1(TerrainType.MYSTERY), BINARY_1(TerrainType.MYSTERY), BINARY_2(TerrainType.MYSTERY),
	BINARY_3(TerrainType.MYSTERY), T5(TerrainType.CAVE),
	GRASS_1(TerrainType.GENERIC), GRASS_2(TerrainType.FOREST), GRASS_3(TerrainType.FOREST),
	GRASS_4(TerrainType.FOREST), GRASS_5(TerrainType.CAVE),
	SAND_1(TerrainType.DESERT), SAND_2(TerrainType.DESERT), SAND_3(TerrainType.DESERT),
	SAND_4(TerrainType.DESERT), SAND_5(TerrainType.DESERT),
	GRASS_1_2_HOR(TerrainType.GENERIC), GRASS_2_1_HOR(TerrainType.GENERIC), x3(TerrainType.CAVE),
	x4(TerrainType.CAVE), x5(TerrainType.CAVE);
	
	private TerrainType terrainType;
	
	/*---------------------------------------------------------------------
	 |  Method name:    [Ground]
	 |  Purpose:  	    [Set instance variable values based on parameter]
	 |  Parameters:     [TerrainType: the type of terrain to associate this Ground with]
	 *---------------------------------------------------------------------*/
	Ground(TerrainType tt) {
		terrainType = tt;
	}


	/*---------------------------------------------------------------------
	 |  Method name:    [interactWithTrainer]
	 |  Purpose:  	    [To later play a specific sound when Trainer walks on this Ground]
	 *---------------------------------------------------------------------*/
	@Override
	public String interactWithTrainer() {
		// TODO Sound effect when trainer walking on
		
		// play getTerrainType().getSound();
		
		return "successfully called";
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
