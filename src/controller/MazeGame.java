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
package controller;

import java.awt.Point;

import model.MapModel.Map;
import model.MapModel.MazeMap;
import model.MapModel.Obstacle;
import model.TrainerModel.Trainer;

public class MazeGame extends GameMode {

	// entire catch 'em all Map is the same as maze (for now)
	public static int WIDTH = Map.WIDTH * 3, HEIGHT = Map.HEIGHT * 3;

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	public MazeGame() {
		super();
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	public boolean isGameWon() {

		// get the obstacles of current map
		Obstacle[][] obstacle = map.getObstacleTiles();

		for (int i = 0; i < MazeMap.HEIGHT; i++) {
			if (obstacle[i][MazeMap.WIDTH - 1] == null) {
				if (trainer.getPoint().equals(new Point(i, MazeMap.WIDTH - 1)))
					return true;
			}
		}

		return false;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	@Override
	public boolean isGameLost() {
		// TODO Add losing condition
		return false;
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	@Override
	public void createMap() {
		map = new MazeMap();
	}
}
