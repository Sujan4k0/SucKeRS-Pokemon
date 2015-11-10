package model.MapModel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


/*---------------------------------------------------------------------
|  Class name:     [MazeGenerator]
|  Purpose:        [Randomly generates a maze given a starting Point and 
|				    2D array of Obstacles]
*---------------------------------------------------------------------*/ 
public class MazeGenerator {

	private Obstacle[][] obstacles; // array totally full of Obstacles
	private ArrayList<Point> pointList; // list of Points to make maze
	private Point currentPt; // the current Point being checked

	public MazeGenerator(Point start, Obstacle[][] obsts) {

		pointList = new ArrayList<Point>();
		pointList.add(start);
		obstacles = obsts.clone();

	}

	public Obstacle[][] generateMaze() {

		boolean right = false, left = false, up = false, down = false;

		while (!pointList.isEmpty()) {

			// get last added Point from list
			if (currentPt == null || !currentPt.equals(pointList.get(pointList.size() - 1))) {
				right = false;
				left = false;
				up = false;
				down = false;
				currentPt = pointList.get(pointList.size() - 1);
				System.out
						.println("Generating Maze at Point: " + currentPt.toString());
			}

			// remove point from list when no other directions can
			// be moved to
			if (left && right && up && down) {
				pointList.remove(currentPt);
			}

			int randomDir = new Random().nextInt(4);
			int m = 2;

			switch (randomDir) {

			case 0:
				if (currentPt.x - 2 > 0 && obstacles[currentPt.x - 1][currentPt.y] != null
						&& obstacles[currentPt.x - 2][currentPt.y] != null) {
					generateUp(m);
				}
				up = true;
				break;
			case 1:
				if (currentPt.x + 2 < obstacles.length - 1
						&& obstacles[currentPt.x + 1][currentPt.y] != null
						&& obstacles[currentPt.x + 2][currentPt.y] != null)
					generateDown(m);
				down = true;
				break;
			case 2:
				if (currentPt.y - 2 > 0 && obstacles[currentPt.x][currentPt.y - 1] != null
						&& obstacles[currentPt.x][currentPt.y - 2] != null)
					generateLeft(m);
				left = true;
				break;
			case 3:
				if (currentPt.y + 2 < obstacles[0].length - 1
						&& obstacles[currentPt.x][currentPt.y + 1] != null
						&& obstacles[currentPt.x][currentPt.y + 2] != null)
					generateRight(m);
				right = true;
				break;
			default:
				break;

			}
		}

		boolean startPlaced = false, endPlaced = false;

		while (!startPlaced) {
			int rand = new Random().nextInt(obstacles.length);

			if (obstacles[rand][1] == null) {
				obstacles[rand][0] = null;
				startPlaced = true;
			}
		}

		while (!endPlaced) {
			int rand = new Random().nextInt(obstacles.length);

			if (obstacles[rand][obstacles[0].length - 2] == null) {
				obstacles[rand][obstacles[0].length - 1] = null;
				endPlaced = true;
			}
		}

		return obstacles;

	}

	private void generateDown(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[currentPt.x + i][currentPt.y] = null;
		}

		Point newP = new Point(currentPt.x + move, currentPt.y);

		System.out.println("New point at: " + newP.toString());

		if (!pointList.contains(newP))
			pointList.add(newP);

	}

	private void generateUp(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[currentPt.x - i][currentPt.y] = null;
		}

		Point newP = new Point(currentPt.x - move, currentPt.y);
		System.out.println("New point at: " + newP.toString());

		if (!pointList.contains(newP))
			pointList.add(newP);

	}

	private void generateLeft(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[currentPt.x][currentPt.y - i] = null;
		}

		Point newP = new Point(currentPt.x, currentPt.y - move);
		System.out.println("New point at: " + newP.toString());

		if (!pointList.contains(newP))
			pointList.add(newP);

	}

	private void generateRight(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[currentPt.x][currentPt.y + i] = null;
		}

		Point newP = new Point(currentPt.x, currentPt.y + move);
		System.out.println("New point at: " + newP.toString());

		if (!pointList.contains(newP))
			pointList.add(newP);

	}

}
