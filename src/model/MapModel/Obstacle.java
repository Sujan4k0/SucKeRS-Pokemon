/*=========================================================================== 
 | Assignment: FINAL PROJECT: [] 
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
 | Description: 
 *===========================================================================*/
package model.MapModel;

public enum Obstacle implements Tile{
	
	ROCK_1(TerrainType.GENERIC), ROCK_2(TerrainType.FOREST), ROCK_3(TerrainType.CAVE), 
	ROCK_4(TerrainType.GENERIC);
	
	private TerrainType terrainType;
	
	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	Obstacle(TerrainType tt) {
		
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
		// TODO may or may not have interaction		
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
