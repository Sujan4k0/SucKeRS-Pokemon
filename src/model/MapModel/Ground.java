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
	CAVE_4(TerrainType.CAVE), CAVE_5(TerrainType.CAVE);
	
	private TerrainType terrainType;
	
	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	Ground(TerrainType tt) {
		terrainType = tt;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	@Override
	public void interactWithTrainer() {
		// TODO Sound effect when trainer walking on
		
		// play terrainType.getSound();
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	@Override
	public TerrainType getTerrainType() {
		return terrainType;
	}


}
