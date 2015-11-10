/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Map] 
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
 | Description: This abstract class extends JPanel and creates the visual game map.
 | Calling drawMap(Graphics) draws all the necessary parts of the Map to this JPanel:
 | ground, obstacles, and trainer.
 *===========================================================================*/

package model.MapModel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public abstract class Map extends JPanel {

	private static final long serialVersionUID = 1L;

	// visible Map is WIDTH by HEIGHT tiles
	public static final int WIDTH = 15, HEIGHT = 11;

	int h = WIDTH, w = HEIGHT;

	Ground[][] groundTiles;
	Obstacle[][] obstacleTiles;

	Image groundTileSet, obstacleTileSet;

	Point trainerPoint;

	public static enum Direction {
		UP, RIGHT, UP_1, LEFT, RIGHT_1, DOWN_1, LEFT_1, RIGHT_2, DOWN, LEFT_2, UP_2, DOWN_2
	}

	Direction dir = Direction.RIGHT;

	Image testTrainerSheet;

	// the X and Y positions in the Obstacle/Ground arrays
	// to start drawing from (for switching area trainer is in)
	private int startX = 0, startY = 0;

	public Map() {

		// get test trainer sprite sheet
		try {
			testTrainerSheet = ImageIO.read(new File(
					"./images/SucKeRS_TrainerSpriteSheet_Test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get tileset images
		try {
			groundTileSet = ImageIO.read(new File(
					"./images/SucKeRS_PokemonTileSet.png"));
			obstacleTileSet = ImageIO.read(new File(
					"./images/SucKeRS_PokemonObstacleTileSet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		createMap();

		// set the preferred size of the Map JPanel
		// based on the WIDTH and HEIGHT (num of tiles)
		// multiplied by the pixel size we want the Tiles
		int prefw = WIDTH * Tile.SIZE;
		int prefh = HEIGHT * Tile.SIZE;

		this.setPreferredSize(new Dimension(prefw, prefh));

	}

	public void paintComponent(Graphics g) {

		drawMap(g);

	}

	public abstract void createMap();

	public Ground[][] getGroundTiles() {
		return groundTiles;
	}

	public Obstacle[][] getObstacleTiles() {
		return obstacleTiles;
	}

	public void drawMap(Graphics g) {

		int x = 0, y = 0;

		// draw ground tiles
		for (int i = startX; i < startX + HEIGHT; i++) {
			y = (i - startX) * Tile.SIZE;
			for (int j = startY; j < startY + WIDTH; j++) {
				x = (j - startY) * Tile.SIZE;
				TileManager.drawTile(g, groundTiles[i][j], groundTileSet, x, y);
			}

		}

		// draw obstacle tiles
		for (int i = startX; i < startX + HEIGHT; i++) {
			y = (i - startX) * Tile.SIZE;
			for (int j = startY; j < startY + WIDTH; j++) {
				x = (j - startY) * Tile.SIZE;
				if (obstacleTiles[i][j] != null)
					TileManager.drawTile(g, obstacleTiles[i][j],
							obstacleTileSet, x, y);
			}

		}

		// draw trainer sprite
		TileManager.drawTile(g, dir, testTrainerSheet, (trainerPoint.y % WIDTH)
				* Tile.SIZE, (trainerPoint.x % HEIGHT) * Tile.SIZE);

	}

	public void moveDown() {

		if (startX + HEIGHT < h) {
			System.out.println("moving down");
			startX += HEIGHT;
		}

	}

	public void moveUp() {

		if (startX - HEIGHT >= 0) {
			System.out.println("moving up");
			startX -= HEIGHT;
		}

	}

	public void moveRight() {

		if (startY + WIDTH < w) {
			System.out.println("Moving right");
			startY += WIDTH;
		}

	}

	public void moveLeft() {

		if (startY - WIDTH >= 0) {
			System.out.println("Moving left");
			startY -= WIDTH;
		}

	}

	public void setTrainerDir(Direction d) {
		dir = d;
	}

	public Point getTrainerPoint() {
		return trainerPoint;
	}

	public void setTrainerPoint(Point pt) {
		trainerPoint = new Point(pt);
	}

}
