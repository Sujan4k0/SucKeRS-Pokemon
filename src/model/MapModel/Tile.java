/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Tile] 
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
 | Description: This interface is meant for use with enums that are a specific 
 | type of Tile. Currently there is Ground and Obstacle - each has their own 
 | interaction with the Trainer.
 *===========================================================================*/
package model.MapModel;

import java.io.Serializable;

public interface Tile extends Serializable {
	
	// the width and height of each Tile in pixels
	public static final int SIZE = 50;

	/*---------------------------------------------------------------------
	 |  Method name:    [interactWithTrainer]
	 |  Purpose:  	    [To do specific things when trainer steps on the Tile] 
	 |  Returns:        [String as placeholder for testing]
	 *---------------------------------------------------------------------*/
	public String interactWithTrainer();

	/*---------------------------------------------------------------------
	 |  Method name:    [getTerrainType]
	 |  Purpose:  	    [Getter for the TerrainType]
	 |  Returns:  	    [TerrainType]
	 *---------------------------------------------------------------------*/
	public TerrainType getTerrainType();

}
