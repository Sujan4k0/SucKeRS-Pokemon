/*=========================================================================== 
 | Assignment: FINAL PROJECT: [MazeGenerator] 
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
 | Description: This class takes an Obstacle[][] array and randomly generates
 | a maze by removing Obstacles from it. The array must be completely full of
 | Obstacles.
 *===========================================================================*/
package model.GameModel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/*---------------------------------------------------------------------
 |  Class name:     [MazeGenerator]
 |  Purpose:        [Randomly generates a maze given a starting Point and 
 |				    2D array of Obstacles, the height/width of the array
 |					must be odd numbers.]
 *---------------------------------------------------------------------*/
public class MazeGenerator {

	private Obstacle[][] obstacles; // array totally full of Obstacles
	private ArrayList<Point> pointList; // list of Points to make maze
	private Point currentPt; // the current Point being checked
	private Random r;

	/*---------------------------------------------------------------------
	 |  Method name:    [MazeGenerator]
	 |  Purpose:  	    [Constructs a MazeGenerator]
	 |  Parameters:     [Random: random object to use to generate map
	 |					 Point: the starting point to generate the maze
	 |					 Obstacle[odd num][odd num]: the array to generate the maze in]
	 *---------------------------------------------------------------------*/
	public MazeGenerator(Random rand, Point start, Obstacle[][] obsts) {

		rand = r;
		
		// instantiate pointList
		pointList = new ArrayList<Point>();

		// add starting Point to list
		pointList.add(start);

		// set this obstacles var to the obsts parameter
		obstacles = obsts;

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [generateMaze]
	 |  Purpose:  	    [Generate the maze in the Obstacle[][] array]
	 *---------------------------------------------------------------------*/
	public void generateMaze() {

		// to check all directions for each point
		boolean right = false, left = false, up = false, down = false;

		// keep generating maze while pointList contains Points
		while (!pointList.isEmpty()) {

			// get last added Point from list if the currentPt is null or different than
			// the last Point in the list
			if (currentPt == null || !currentPt.equals(pointList.get(pointList.size() - 1))) {
				right = false;
				left = false;
				up = false;
				down = false;
				currentPt = pointList.get(pointList.size() - 1);
			}

			// remove point from list when all directions have been checked
			if (left && right && up && down) {
				pointList.remove(currentPt);
			}

			// generate a random int to represent the direction to try and move in
			int randomDir = r.nextInt(4);

			// the number of spaces that are moved for each direction generated
			int m = 2;

			// handles the randomDir int
			switch (randomDir) {

			// try to generate the maze up
			case 0:
				if (currentPt.x - 2 > 0 && obstacles[currentPt.x - 1][currentPt.y] != null && obstacles[currentPt.x - 2][currentPt.y] != null) {
					generateUp(m);
				}
				up = true;
				break;
			// try to generate the maze down
			case 1:
				if (currentPt.x + 2 < (obstacles.length - 1) && obstacles[currentPt.x + 1][currentPt.y] != null && obstacles[currentPt.x + 2][currentPt.y] != null)
					generateDown(m);
				down = true;
				break;
			// try to generate the maze left
			case 2:
				if (currentPt.y - 2 > 0 && obstacles[currentPt.x][currentPt.y - 1] != null && obstacles[currentPt.x][currentPt.y - 2] != null)
					generateLeft(m);
				left = true;
				break;
			// try to generate the maze right
			case 3:
				if (currentPt.y + 2 < (obstacles[0].length - 1) && obstacles[currentPt.x][currentPt.y + 1] != null && obstacles[currentPt.x][currentPt.y + 2] != null)
					generateRight(m);
				right = true;
				break;
			default:
				break;

			}
		} // end of while()

		// to know if the start and end of the maze have been placed
		boolean startPlaced = false, endPlaced = false;

		// find a place on the left that is connected with the maze
		// and place the start there by removing that object
		while (!startPlaced) {
			int rand = r.nextInt(obstacles.length);

			if (obstacles[rand][1] == null) {
				obstacles[rand][0] = null;
				startPlaced = true;
			}
		}

		// find a place on the right that is connected with the maze
		// and place the end there by removing that object
		while (!endPlaced) {
			int rand = r.nextInt(obstacles.length);

			if (obstacles[rand][obstacles[0].length - 2] == null) {
				obstacles[rand][obstacles[0].length - 1] = null;
				endPlaced = true;
			}
		}

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [generateDown]
	 |  Purpose:  	    [Generate the maze downward]
	 |  Parameters:     [int: the number of spaces to move down]
	 *---------------------------------------------------------------------*/
	private void generateDown(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[currentPt.x + i][currentPt.y] = null;
		}

		// make a new Point and add it to the pointList if it isn't
		// already in the list
		Point newP = new Point(currentPt.x + move, currentPt.y);
		if (!pointList.contains(newP))
			pointList.add(newP);

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [generateUp]
	 |  Purpose:  	    [Generate the maze upward]
	 |  Parameters:     [int: the number of spaces to move up]
	 *---------------------------------------------------------------------*/
	private void generateUp(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[currentPt.x - i][currentPt.y] = null;
		}

		// make a new Point and add it to the pointList if it isn't
		// already in the list
		Point newP = new Point(currentPt.x - move, currentPt.y);
		if (!pointList.contains(newP))
			pointList.add(newP);

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [generateLeft]
	 |  Purpose:  	    [Generate the maze left]
	 |  Parameters:     [int: the number of spaces to move left]
	 *---------------------------------------------------------------------*/
	private void generateLeft(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[currentPt.x][currentPt.y - i] = null;
		}

		// make a new Point and add it to the pointList if it isn't
		// already in the list
		Point newP = new Point(currentPt.x, currentPt.y - move);
		if (!pointList.contains(newP))
			pointList.add(newP);

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [generateRight]
	 |  Purpose:  	    [Generate the maze right]
	 |  Parameters:     [int: the number of spaces to move right]
	 *---------------------------------------------------------------------*/
	private void generateRight(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[currentPt.x][currentPt.y + i] = null;
		}

		// make a new Point and add it to the pointList if it isn't
		// already in the list
		Point newP = new Point(currentPt.x, currentPt.y + move);

		if (!pointList.contains(newP))
			pointList.add(newP);

	}

}
