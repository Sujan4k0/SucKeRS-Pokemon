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

package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import soundplayer.SoundPlayer;
import view.GraphicsManager;
import model.GameModel.*;
import model.ItemModel.Item;

public abstract class Map extends JPanel implements Serializable {

	private static final long serialVersionUID = 1L;

	// visible Map is WIDTH by HEIGHT tiles
	public static final int WIDTH = 15, HEIGHT = 11;

	// the height and width, for use in moveRight() and moveDown()
	int h = WIDTH, w = HEIGHT;

	// to hold the Ground and Obstacle Tiles to draw
	Ground[][] groundTiles;
	Obstacle[][] obstacleTiles;
	
	// to holds Items for the Trainer to pick up :D
	Item[][] itemTiles;

	// the Point the trainer is visually at
	Point trainerPoint;

	// for randomly generating stuff
	Random r;

	// the directions associated with the trainer's sprite sheet
	public static enum TrainerDirection {
		UP, RIGHT, UP_1, LEFT, RIGHT_1, DOWN_1, LEFT_1, RIGHT_2, DOWN, LEFT_2, UP_2, DOWN_2
	}

	// the direction the trainer sprite currently is facing
	TrainerDirection dir = TrainerDirection.RIGHT;
	// the direction the trainer sprite is currently drawn as
	TrainerDirection drawnDir = TrainerDirection.RIGHT;


	// the X and Y positions in the Obstacle/Ground arrays
	// to start drawing from (for switching area trainer is in)
	private int startX = 0, startY = 0;

	/*---------------------------------------------------------------------
	 |  Method name:    [Map]
	 |  Purpose:  	    [Constructor for Map]
	 *---------------------------------------------------------------------*/
	public Map(Random rand) {

		r = rand;

		// create the Map and fill the Tile arrays (Ground and Obstacle)
		createMap();
		
		// add the Items for the Trainer wewt
		initializeItems();

		// set the preferred size of the Map JPanel
		// based on the WIDTH and HEIGHT (num of tiles)
		// multiplied by the pixel size we want the Tiles
		int prefw = WIDTH * Tile.SIZE;
		int prefh = HEIGHT * Tile.SIZE;

		this.setPreferredSize(new Dimension(prefw, prefh));

	}

	public abstract void initializeItems();

	/*---------------------------------------------------------------------
	 |  Method name:    [getGroundTiles]
	 |  Purpose:  	    [Getter for groundTiles[][]]
	 |  Returns:        [Ground[][]]
	 *---------------------------------------------------------------------*/
	public abstract void createMap();

	public Ground[][] getGroundTiles() {
		return groundTiles;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getObstacleTiles]
	 |  Purpose:  	    [Getter for obstacleTiles[][]]
	 |  Returns:        [Obstacle[][]]
	 *---------------------------------------------------------------------*/
	public Obstacle[][] getObstacleTiles() {
		return obstacleTiles;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getItemTiles]
	 |  Purpose:  	    [Getter for itemTiles[][]]
	 |  Returns:        [Item[][]]
	 *---------------------------------------------------------------------*/
	public Item[][] getItemTiles() {
		return itemTiles;
	}
	
	public int getStartX() {
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
	

	public void checkMapPlacement() {
		if (trainerPoint.x >= startX)
			moveDown();
		if (trainerPoint.x >= startX)
			moveDown();

		if (trainerPoint.x < startX)
			moveUp();
		if (trainerPoint.x < startX)
			moveUp();

		if (trainerPoint.y >= startY)
			moveRight();
		if (trainerPoint.y >= startY)
			moveRight();

		if (trainerPoint.y < startY)
			moveLeft();
		if (trainerPoint.y < startY)
			moveLeft();
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [moveDown]
	 |  Purpose:  	    [To change the drawn area of the map when the Trainer is
	 |					 moved down and another area is there]
	 *---------------------------------------------------------------------*/
	public void moveDown() {

		if (startX + HEIGHT < h) {
			//System.out.println("moving down");
			startX += HEIGHT;
		}

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [moveUp]
	 |  Purpose:  	    [To change the drawn area of the map when the Trainer is
	 |					 moved up and another area is there]
	 *---------------------------------------------------------------------*/
	public void moveUp() {

		if (startX - HEIGHT >= 0) {
			//System.out.println("moving up");
			startX -= HEIGHT;
		}

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [moveRight]
	 |  Purpose:  	    [To change the drawn area of the map when the Trainer is
	 |					 moved right and another area is there]
	 *---------------------------------------------------------------------*/
	public void moveRight() {

		if (startY + WIDTH < w) {
			//System.out.println("Moving right");
			startY += WIDTH;
		}

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [moveLeft]
	 |  Purpose:  	    [To change the drawn area of the map when the Trainer is
	 |					 moved up and another area is there]
	 *---------------------------------------------------------------------*/
	public void moveLeft() {
		if (startY - WIDTH >= 0) {
			//System.out.println("Moving left");
			startY -= WIDTH;
		}
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [setTrainerDir]
	 |  Purpose:  	    [Setter for the Map.Direction the trainer sprite is using]
	 |  Parameters:     [Map.Direction]
	 *---------------------------------------------------------------------*/
	public void setTrainerDir(Map.TrainerDirection d) {
		dir = d;
		drawnDir = d;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getTrainerPoint]
	 |  Purpose:  	    [Getter for the Point the Trainer is visually at]
	 |  Returns:  	    [Point]
	 *---------------------------------------------------------------------*/
	public Point getTrainerPoint() {
		return trainerPoint;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [setTrainerPoint]
	 |  Purpose:  	    [Setter for the Point the Trainer is visually at]
	 |  Parameters:     [Point: the new point to assign trainerPoint to]
	 *---------------------------------------------------------------------*/
	public void setTrainerPoint(Point pt) {
		trainerPoint = new Point(pt);
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getTrainerDir]
	 |  Purpose:  	    [Getter for the Map.Direction that the trainer sprite is facing]
	 |  Returns:  	    [Map.Direction: direction the trainer sprite is facing.
	 |					 There are multiple directions (DOWN and DOWN_1, etc) because
	 |					 they correspond to a section of the trainer's sprite sheet]
	 *---------------------------------------------------------------------*/
	public Map.TrainerDirection getTrainerDir() {
		return dir;
	}

	public TerrainType getCurrentTerrain() {
		// TODO Auto-generated method stub
		return groundTiles[trainerPoint.x][trainerPoint.y].getTerrainType();
	}

	public boolean trainerSteppingOnItem() {
		if (itemTiles[trainerPoint.x][trainerPoint.y] != null)
			return true;
		else return false;
	}
	
	public Item getItemAtCurrentLocation() {
		Item i = itemTiles[trainerPoint.x][trainerPoint.y];
		itemTiles[trainerPoint.x][trainerPoint.y] = null;
		return i;
	}
}
