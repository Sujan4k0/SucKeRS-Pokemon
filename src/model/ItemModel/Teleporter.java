/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Teleporter] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |	       [Keith Smith  (browningsmith@email.arizona.edu)]
 |	       [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description:  An Item which allows a trainer to set a position on the first
 				 use of the teleporter and then return to that point on the 2nd
 				 use. 
 *==========================================================================*/
package model.ItemModel;

import java.awt.Point;

public class Teleporter extends Item{
	private boolean set=false;
	private Point teleportPoint;
	
	/*---------------------------------------------------------------------
	 |  Purpose:  	    [Constructor]
	 *---------------------------------------------------------------------*/
	public Teleporter(){
		super.setName("Teleporter");
	}
   	
	/*---------------------------------------------------------------------
	 |  Method name:    [setPoint]
	 |  Purpose:  	    [Sets the point for where the player returns to when they use the teleporter]
	 |  Parameters:     [A Point p representing the current position of the trainer]
	 *---------------------------------------------------------------------*/
	public void setPoint(Point p){
		teleportPoint= new Point((int)p.getX(),(int)p.getY());
		set=true;
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [isSet]
	 |  Purpose:  	    [returns whether or not the teleport point has already been set]
	 |  Returns:  	    [A boolean for whether or not the teleport point has already been set]
	 *---------------------------------------------------------------------*/
	public boolean isSet(){
		return set;
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getTeleportPoint]
	 |  Purpose:  	    [Returns point for where the player returns to when they use the teleporter]
	 |  Returns:  	    [A Point for where the player returns to when they use the teleporter]
	 *---------------------------------------------------------------------*/
	public Point getTeleportPoint(){
		return teleportPoint;
	}
}
