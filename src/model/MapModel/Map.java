package model.MapModel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Map {

	// visible Map is WIDTH by HEIGHT tiles
	public static int WIDTH = 15, HEIGHT = 10;

	// entire maze Map is 3 by 3 visible maps combined
	public static int MAZE_WIDTH = WIDTH * 3, MAZE_HEIGHT = HEIGHT * 3;

	// entire catch 'em all Map is the same as maze (for now)
	public static int CEA_WIDTH = WIDTH * 3, CEA_HEIGHT = HEIGHT * 3;

	private Ground[][] groundTiles;
	private Obstacle[][] obstacleTiles;

	private Image groundTileSet, obstacleTileSet;

	private MapType type;

	private int startX = 0, startY = 0;

	public Map(MapType type) {

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

				/*
				 * This code generates ice tiles with a border if (i == 0 || i
				 * == Map.MAP_HEIGHT - 1 || j == Map.MAP_WIDTH - 1 || j == 0)
				 * groundTiles[i][j] = Ground.ICE_3; else groundTiles[i][j] =
				 * Ground.ICE_1;
				 */
			}
		}

		

		
		//TODO: Fill obstacleTiles[][] with Obstacles
		for (int i = 0; i < Map.MAZE_HEIGHT; i++) {
			for (int j = 0; j < Map.MAZE_WIDTH; j++) {
				obstacleTiles[i][j] = Obstacle.ROCK_1;
			}
		}
		
		int start = new Random().nextInt(MAZE_HEIGHT - 2) + 1;
		obstacleTiles[start][0] = null; //starting spot
		
		obstacleTiles = new MazeGenerator(new Point(start, 1), obstacleTiles)
				.generateMaze();

	}

	public Ground[][] getGroundTiles() {

		return groundTiles;

	}

	public Obstacle[][] getObstacleTiles() {

		return obstacleTiles;

	}

	public void drawMap(Graphics g) {

		int x = 0, y = 0;

		for (int i = startX; i < startX + Map.HEIGHT; i++) {
			y = (i - startX) * Tile.SIZE;
			for (int j = startY; j < startY + Map.WIDTH; j++) {
				x = (j - startY) * Tile.SIZE;
				TileManager.drawTile(g, groundTiles[i][j], groundTileSet, x, y);
			}

		}

		for (int i = startX; i < startX + Map.HEIGHT; i++) {
			y = (i - startX) * Tile.SIZE;
			for (int j = startY; j < startY + Map.WIDTH; j++) {
				x = (j - startY) * Tile.SIZE;
				if (obstacleTiles[i][j] != null)
					TileManager.drawTile(g, obstacleTiles[i][j],
							obstacleTileSet, x, y);
			}

		}

	}

	public void moveDown() {

		int restraint = 0;

		if (type == MapType.MAZE)
			restraint = Map.MAZE_HEIGHT;
		if (type == MapType.CEA)
			restraint = Map.CEA_HEIGHT;

		if (startX + Map.HEIGHT < restraint) {
			System.out.println("Moving up");
			startX += Map.HEIGHT;
		}

	}

	public void moveUp() {

		if (startX - Map.HEIGHT >= 0) {
			System.out.println("Moving down");
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

}

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

		int count = 0;

		while (!mazeList.isEmpty()) {

			// get last added Point from list
			if (pt == null || !pt.equals(mazeList.get(mazeList.size() - 1))) {
				pt = mazeList.get(mazeList.size() - 1);
				System.out.println("Generating Maze at Point: " + pt.toString());
			}

			count++;
			int count2 = 0;

			// see how many direction have already been moved
			// from this point
			for (int i = -1; i < 2; i++) {
				if (i == 0)
					i++;
				if (obstacles[pt.x + i][pt.y] == null)
					count2++;
				if (obstacles[pt.x][pt.y + i] == null)
					count2++;
			}

			// remove point from list when no other directions can
			// be moved to
			if ((pt.equals(new Point(1, 1)) 
					|| pt.equals(new Point(1, Map.MAZE_WIDTH - 2))
					|| pt.equals(new Point(Map.MAZE_HEIGHT - 2, 1)) 
					|| pt.equals(new Point(Map.MAZE_HEIGHT - 2, Map.MAZE_WIDTH - 2))) 
					&& count2 == 2)
				mazeList.remove(pt);
			
			else if ((pt.x == 1 || pt.y == 1
					|| pt.x == Map.MAZE_HEIGHT - 2
					|| pt.y == Map.MAZE_WIDTH - 2)
					&& count == 3)
				mazeList.remove(pt);
			else if (count == 4)  mazeList.remove(pt);

			if (count == 300)
				break;

			int randomDir = new Random().nextInt(4);

			switch (randomDir) {

			case 0:
				if (pt.x - Map.MAZE_HEIGHT/4 > 0)
					generateUp();
				break;
			case 1:
				if (pt.x + Map.MAZE_HEIGHT/4 < obstacles.length - 1)
					generateDown();
				break;
			case 2:
				if (pt.y - Map.MAZE_WIDTH/4 > 0)
					generateLeft();
				break;
			case 3:
				if (pt.y + Map.MAZE_WIDTH/4 < obstacles[0].length - 1)
					generateRight();
				break;
			default:
				break;

			}
		}
		
		for (int i = Map.MAZE_HEIGHT - 1; i > 0; i++)
			for (int j = Map.MAZE_WIDTH - 1; j > 0; j++) {
				
				if (obstacles[i][j] == null) {
					
					
				}
				
			}
				

		return obstacles;

	}

	private void generateDown() {

		int move = new Random().nextInt(Map.MAZE_HEIGHT/4 - 1) + 2;

		// keep generating a new random movement num until one works yo
		while (pt.x + move > (Map.MAZE_HEIGHT - 2)) {
			move = new Random().nextInt(Map.MAZE_HEIGHT/4 - 1) + 2;
}
		
		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[pt.x + i][pt.y] = null;
		}


		Point newP = new Point(pt.x + move, pt.y);

		System.out.println("New point at: " + newP.toString());

		if (!mazeList.contains(newP))
			mazeList.add(newP);

	}

	private void generateUp() {

		int move = new Random().nextInt(Map.MAZE_HEIGHT/4 - 1) + 2;

		// keep generating a new random movement num until one works yo
		while (pt.x - move < 1) {
			move = new Random().nextInt(Map.MAZE_HEIGHT/4 - 1) + 2;

		}
		
		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[pt.x - i][pt.y] = null;
		}

		Point newP = new Point(pt.x - move, pt.y);
		System.out.println("New point at: " + newP.toString());

		if (!mazeList.contains(newP))
			mazeList.add(newP);

	}

	private void generateLeft() {

		int move = new Random().nextInt(Map.MAZE_WIDTH/4 - 1) + 2;

		// keep generating a new random movement num until one works yo
		while (pt.y - move < 1) {
			move = new Random().nextInt(Map.MAZE_WIDTH/4 - 1) + 2;
		}
		
		// clear path
		for (int i = 0; i <= move; i++) {
			obstacles[pt.x][pt.y - i] = null;
		}

		Point newP = new Point(pt.x, pt.y - move);
		System.out.println("New point at: " + newP.toString());

		if (!mazeList.contains(newP))
			mazeList.add(newP);

	}

	private void generateRight() {

		int move = new Random().nextInt(Map.MAZE_WIDTH/4 - 1) + 2;

		// keep generating a new random movement num until one works yo
		while (pt.y + move > (Map.MAZE_WIDTH - 2)) {
			move = new Random().nextInt(Map.MAZE_WIDTH/4 - 1) + 2;
		}
		
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
