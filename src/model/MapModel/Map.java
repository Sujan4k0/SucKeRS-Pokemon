/*=========================================================================== 
 | Assignment: FINAL PROJECT: [GameMode] 
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
 | Description: This class extends JPanel creates the visual game map. Calling
 | drawMap(Graphics) draws all the necessary components to this JPanel: ground,
 | obstacles, and trainer. Depending on the MapType passed to the constructor,
 | a specific map is generated.
 *===========================================================================*/
package model.MapModel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.TrainerModel.Trainer;

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;

	// visible Map is WIDTH by HEIGHT tiles
	public static int WIDTH = 15, HEIGHT = 11;

	// entire maze Map is 3 by 3 visible maps combined
	public static int MAZE_WIDTH = WIDTH * 3, MAZE_HEIGHT = HEIGHT * 3;

	// entire catch 'em all Map is the same as maze (for now)
	public static int CEA_WIDTH = WIDTH * 3, CEA_HEIGHT = HEIGHT * 3;

	private Ground[][] groundTiles;
	private Obstacle[][] obstacleTiles;

	private Image groundTileSet, obstacleTileSet;

	private MapType type;

	// Point to initialize then continuously draw trainer
	private Point trainerStart;
	
	public static enum Direction {
		UP, RIGHT, UP_1, LEFT, RIGHT_1, DOWN_1, LEFT_1, RIGHT_2, DOWN, LEFT_2, UP_2, DOWN_2
	}
	
	private Direction dir = Direction.RIGHT;

	Image testTrainerSheet;

	private int startX = 0, startY = 0;

	public Map(MapType type) {

		try {
			testTrainerSheet = ImageIO.read(new File(
					"./images/SucKeRS_TrainerSpriteSheet_Test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.type = type;

		// get tileset images
		try {
			groundTileSet = ImageIO.read(new File(
					"./images/SucKeRS_PokemonTileSet.png"));
			obstacleTileSet = ImageIO.read(new File(
					"./images/SucKeRS_PokemonObstacleTileSet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// maze map
		if (type == MapType.MAZE)
			createMaze();
		if (type == MapType.CEA)
			createCEA();

		int prefw = Map.WIDTH * Tile.SIZE;
		int prefh = Map.HEIGHT * Tile.SIZE;

		this.setPreferredSize(new Dimension(prefw, prefh));

	}

	public void paintComponent(Graphics g) {

		drawMap(g);

	}

	private void createCEA() {
		// TODO Auto-generated method stub

	}

	private void createMaze() {

		groundTiles = new Ground[MAZE_HEIGHT][MAZE_WIDTH];
		obstacleTiles = new Obstacle[MAZE_HEIGHT][MAZE_WIDTH];

		//TODO: Fill groundTiles[][] with Ground
		for (int i = 0; i < Map.MAZE_HEIGHT; i++) {
			for (int j = 0; j < Map.MAZE_WIDTH; j++) {

				// fills all ground with just these cave tiles
				groundTiles[i][j] = Ground.CAVE_1;
			}
		}

		//TODO: Fill obstacleTiles[][] with Obstacles
		for (int i = 0; i < Map.MAZE_HEIGHT; i++) {
			for (int j = 0; j < Map.MAZE_WIDTH; j++) {
				obstacleTiles[i][j] = Obstacle.ROCK_1;
			}
		}

		int randX = new Random().nextInt(MAZE_HEIGHT - 2) + 1;

		// makes randX have to be an odd integer so that there is
		// only a single border of obstacles around the edges :D
		if (randX % 2 == 0 && randX < MAZE_HEIGHT - 3)
			randX += 1;
		else if (randX % 2 == 0)
			randX -= 1;

		// generates the maze tiles
		obstacleTiles = new MazeGenerator(new Point(randX, 1), obstacleTiles)
				.generateMaze();

		for (int i = 0; i < MAZE_HEIGHT; i++) {
			if (obstacleTiles[i][0] == null)
				setTrainerStart(new Point(i, 0));
		}
		
		for (int i = 0; i < MAZE_HEIGHT; i++) {
			if (obstacleTiles[i][MAZE_WIDTH - 1] == null)
				groundTiles[i][MAZE_WIDTH - 1] = Ground.ICE_1;
		}


		System.out.println("Start Point : " + trainerStart.toString());

		if (trainerStart.x >= HEIGHT)
			moveDown();
		if (trainerStart.x >= 2 * HEIGHT)
			moveDown();

	}

	public Ground[][] getGroundTiles() {
		return groundTiles;
	}

	public Obstacle[][] getObstacleTiles() {
		return obstacleTiles;
	}

	public void drawMap(Graphics g) {

		int x = 0, y = 0;

		// draw ground tiles
		for (int i = startX; i < startX + Map.HEIGHT; i++) {
			y = (i - startX) * Tile.SIZE;
			for (int j = startY; j < startY + Map.WIDTH; j++) {
				x = (j - startY) * Tile.SIZE;
				TileManager.drawTile(g, groundTiles[i][j], groundTileSet, x, y);
			}

		}

		// draw obstacle tiles
		for (int i = startX; i < startX + Map.HEIGHT; i++) {
			y = (i - startX) * Tile.SIZE;
			for (int j = startY; j < startY + Map.WIDTH; j++) {
				x = (j - startY) * Tile.SIZE;
				if (obstacleTiles[i][j] != null)
					TileManager.drawTile(g, obstacleTiles[i][j],
							obstacleTileSet, x, y);
			}

		}
		
		// draw trainer sprite
		TileManager.drawTile(g, dir, testTrainerSheet,
				(trainerStart.y % Map.WIDTH) * Tile.SIZE, (trainerStart.x % Map.HEIGHT) * Tile.SIZE);

	}

	public void moveDown() {

		int restraint = 0;

		if (type == MapType.MAZE)
			restraint = Map.MAZE_HEIGHT;
		if (type == MapType.CEA)
			restraint = Map.CEA_HEIGHT;

		if (startX + Map.HEIGHT < restraint) {
			System.out.println("moving down");
			startX += Map.HEIGHT;
		}

	}

	public void moveUp() {

		if (startX - Map.HEIGHT >= 0) {
			System.out.println("moving up");
			startX -= Map.HEIGHT;
		}

	}

	public void moveRight() {

		int restraint = 0;

		if (type == MapType.MAZE)
			restraint = Map.MAZE_WIDTH;
		if (type == MapType.CEA)
			restraint = Map.CEA_WIDTH;

		if (startY + Map.WIDTH < restraint) {
			System.out.println("Moving right");
			startY += Map.WIDTH;
		}

	}

	public void moveLeft() {

		if (startY - Map.WIDTH >= 0) {
			System.out.println("Moving left");
			startY -= Map.WIDTH;
		}

	}
	
	public void setTrainerDir(Direction d) {
		dir = d;
	}

	public Point getTrainerStart() {
		return trainerStart;
	}

	public void setTrainerStart(Point trainerStart) {
		this.trainerStart = new Point(trainerStart);
	}

}

/*---------------------------------------------------------------------
|  Class name:     [MazeGenerator]
|  Purpose:        [Randomly generates a maze given a starting Point and 
|				    2D array of Obstacles]
*---------------------------------------------------------------------*/ 
class MazeGenerator {

	private Obstacle[][] obstacles;
	private ArrayList<Point> mazeList;
	private Point pt;

	public MazeGenerator(Point start, Obstacle[][] obsts) {

		mazeList = new ArrayList<Point>();
		mazeList.add(start);
		obstacles = obsts.clone();

	}

	public Obstacle[][] generateMaze() {

		boolean right = false, left = false, up = false, down = false;

		while (!mazeList.isEmpty()) {

			// get last added Point from list
			if (pt == null || !pt.equals(mazeList.get(mazeList.size() - 1))) {
				right = false;
				left = false;
				up = false;
				down = false;
				pt = mazeList.get(mazeList.size() - 1);
				System.out
						.println("Generating Maze at Point: " + pt.toString());
			}

			// remove point from list when no other directions can
			// be moved to
			if (left && right && up && down) {
				mazeList.remove(pt);
			}

			int randomDir = new Random().nextInt(4);
			int m = 2;

			switch (randomDir) {

			case 0:
				if (pt.x - 2 > 0 && obstacles[pt.x - 1][pt.y] != null
						&& obstacles[pt.x - 2][pt.y] != null) {
					generateUp(m);
				}
				up = true;
				break;
			case 1:
				if (pt.x + 2 < obstacles.length - 1
						&& obstacles[pt.x + 1][pt.y] != null
						&& obstacles[pt.x + 2][pt.y] != null)
					generateDown(m);
				down = true;
				break;
			case 2:
				if (pt.y - 2 > 0 && obstacles[pt.x][pt.y - 1] != null
						&& obstacles[pt.x][pt.y - 2] != null)
					generateLeft(m);
				left = true;
				break;
			case 3:
				if (pt.y + 2 < obstacles[0].length - 1
						&& obstacles[pt.x][pt.y + 1] != null
						&& obstacles[pt.x][pt.y + 2] != null)
					generateRight(m);
				right = true;
				break;
			default:
				break;

			}
		}

		boolean startPlaced = false, endPlaced = false;

		while (!startPlaced) {
			int rand = new Random().nextInt(Map.MAZE_HEIGHT);

			if (obstacles[rand][1] == null) {
				obstacles[rand][0] = null;
				startPlaced = true;
			}
		}

		while (!endPlaced) {
			int rand = new Random().nextInt(Map.MAZE_HEIGHT);

			if (obstacles[rand][Map.MAZE_WIDTH - 2] == null) {
				obstacles[rand][Map.MAZE_WIDTH - 1] = null;
				endPlaced = true;
			}
		}

		return obstacles;

	}

	private void generateDown(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[pt.x + i][pt.y] = null;
		}

		Point newP = new Point(pt.x + move, pt.y);

		System.out.println("New point at: " + newP.toString());

		if (!mazeList.contains(newP))
			mazeList.add(newP);

	}

	private void generateUp(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[pt.x - i][pt.y] = null;
		}

		Point newP = new Point(pt.x - move, pt.y);
		System.out.println("New point at: " + newP.toString());

		if (!mazeList.contains(newP))
			mazeList.add(newP);

	}

	private void generateLeft(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[pt.x][pt.y - i] = null;
		}

		Point newP = new Point(pt.x, pt.y - move);
		System.out.println("New point at: " + newP.toString());

		if (!mazeList.contains(newP))
			mazeList.add(newP);

	}

	private void generateRight(int move) {

		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[pt.x][pt.y + i] = null;
		}

		Point newP = new Point(pt.x, pt.y + move);
		System.out.println("New point at: " + newP.toString());

		if (!mazeList.contains(newP))
			mazeList.add(newP);

	}

}
