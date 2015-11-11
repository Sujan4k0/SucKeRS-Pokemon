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
package model.MapModel;

public enum Obstacle implements Tile{
	
	ROCK_1(TerrainType.GENERIC), ROCK_2(TerrainType.FOREST), ROCK_3(TerrainType.CAVE), 
	ROCK_4(TerrainType.GENERIC);
	
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
	 |  Method name:    [interactWithTrainer]
	 |  Purpose:  	    [To do specific things when the Trainer tries to walk
	 |					on this Obstacle]
	 *---------------------------------------------------------------------*/
	@Override
	public void interactWithTrainer() {
		// TODO may or may not have interaction		
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
