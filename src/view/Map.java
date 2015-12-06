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

	private static int MOVEMENT_SPEED = 7;

	// visible Map is WIDTH by HEIGHT tiles
	public static final int WIDTH = 15, HEIGHT = 11;

	// the height and width, for use in moveRight() and moveDown()
	int h = WIDTH, w = HEIGHT;

	// file path of BG music
	String bgPath = "";

	// to hold the Ground and Obstacle Tiles to draw
	Ground[][] groundTiles;
	Obstacle[][] obstacleTiles;

	// to holds Items for the Trainer to pick up :D
	Item[][] itemTiles;

	// the tile sets for Ground and Obstacle
	transient Image groundTileSet, obstacleTileSet, itemImage;

	transient Image mewImage, teleporterImage;

	// the Point the trainer is visually at
	// also teleporter
	Point trainerPoint, teleporterPoint;

	// timer to animate trainer movement
	Timer movementTimer;

	// if the trainer movement is being animated
	boolean animating = false;

	boolean muted = false;

	// offsets for trainer animation
	int xOffset = 0, yOffset = 0;

	// Plays bg sounds :D
	SoundPlayer bgPlayer = new SoundPlayer();

	// Plays sfx :D
	SoundPlayer sfxPlayer = new SoundPlayer();

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

	// the Trainer's sprite sheet
	transient Image trainerSheet;

	// the X and Y positions in the Obstacle/Ground arrays
	// to start drawing from (for switching area trainer is in)
	private int startX = 0, startY = 0;

	/*---------------------------------------------------------------------
	 |  Method name:    [Map]
	 |  Purpose:  	    [Constructor for Map]
	 *---------------------------------------------------------------------*/
	public Map(Random rand) {

		r = rand;

		// get sprite sheets & tile sets
		load();

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
	 |  Method name:    [paintComponent]
	 |  Purpose:  	    [To make it so that calling Map.repaint() draws the map
	 |					directly onto this JPanel]
	 |  Parameters:     [Graphics: this object's Graphics object]
	 *---------------------------------------------------------------------*/
	public void paintComponent(Graphics g) {

		drawMap(g);
		drawTrainer(g);

	}

	private void drawTrainer(Graphics g) {
		// draw trainer sprite
		GraphicsManager.drawSprite(g, drawnDir, trainerSheet, (trainerPoint.y % WIDTH) * Tile.SIZE
				- yOffset, (trainerPoint.x % HEIGHT) * Tile.SIZE - xOffset);
	}

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

	public void setTeleporterPoint(Point p) {
		if (p == null)
			teleporterPoint = null;
		else teleporterPoint = new Point(p);
	}

	public Point getTeleporterPoint() {
		return teleporterPoint;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [drawMap]
	 |  Purpose:  	    [Draws the Ground, Obstacle, and Trainer]
	 |  Parameters:     [Graphics: the Graphics Object to use to draw]
	 *---------------------------------------------------------------------*/
	public void drawMap(Graphics g) {

		int x = 0, y = 0;

		checkMapPlacement();

		// draw ground and obstacle tiles and ITEMSSS
		for (int i = startX; i < (startX + Map.HEIGHT); i++) {
			y = (i - startX) * Tile.SIZE;
			for (int j = startY; j < (startY + Map.WIDTH); j++) {
				x = (j - startY) * Tile.SIZE;
				GraphicsManager.drawTile(g, groundTiles[i][j], groundTileSet, x, y);
				if (obstacleTiles[i][j] != null)
					GraphicsManager.drawTile(g, obstacleTiles[i][j], obstacleTileSet, x, y);
				if (itemTiles[i][j] != null)
					g.drawImage(itemImage, x + Tile.SIZE / 4, y + Tile.SIZE / 4, null);
			}

		}

		// draw teleporter loc if necessary
		if (teleporterPoint != null) {
			if (teleporterPoint.x >= startX && teleporterPoint.x < startX + Map.HEIGHT) {
				if (teleporterPoint.y >= startY && teleporterPoint.y < startY + Map.WIDTH) {
					g.drawImage(teleporterImage, (teleporterPoint.y % WIDTH) * Tile.SIZE,
							(teleporterPoint.x % HEIGHT) * Tile.SIZE, null);
				}
			}
		}

		// draw trainer sprite
		GraphicsManager.drawSprite(g, drawnDir, trainerSheet, (trainerPoint.y % WIDTH) * Tile.SIZE
				- yOffset, (trainerPoint.x % HEIGHT) * Tile.SIZE - xOffset);

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
	 |  Method name:    [getObstacleTileSet]
	 |  Purpose:  	    [Getter for the the Obstacle tile set]
	 |  Returns:  	    [Image]
	 *---------------------------------------------------------------------*/
	public Image getObstacleTileSet() {
		return obstacleTileSet;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getGroundTileSet]
	 |  Purpose:  	    [Getter for the Ground tile set]
	 |  Returns:  	    [Image]
	 *---------------------------------------------------------------------*/
	public Image getGroundTileSet() {
		return groundTileSet;
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

	/*---------------------------------------------------------------------
	 |  Method name:    [getTrainerSheet]
	 |  Purpose:  	    [Getter for the trainer's sprite sheet]
	 |  Returns:  	    [Image: the trainer's sprite sheet]
	 *---------------------------------------------------------------------*/
	public Image getTrainerSheet() {
		return trainerSheet;
	}

	public TerrainType getCurrentTerrain() {
		// TODO Auto-generated method stub
		return groundTiles[trainerPoint.x][trainerPoint.y].getTerrainType();
	}

	public boolean isAnimating() {
		return animating;
	}

	public void setStartOffsets(int x, int y) {
		xOffset = x;
		yOffset = y;
		movementTimer.stop();
	}

	public void startTrainerMovement() {
		movementTimer.start();
	}

	//animates the hunter's image for movement
	protected class MovementTimerListener implements ActionListener {

		boolean walk = false;
		int MOVE = 2, moveAmount = 0;
		int tic = 0;

		@Override
		public void actionPerformed(ActionEvent e) {

			animating = true;

			if (xOffset > 0 || yOffset > 0)
				moveAmount = -MOVE;
			else
				moveAmount = MOVE;

			if (tic < Math.abs(Tile.SIZE / moveAmount)) {

				if (tic % 8 == 0)
					walk = !walk;

				// determine which direction to move in
				switch (dir) {

				case UP:
					if (walk)
						drawnDir = Map.TrainerDirection.UP_1;
					else
						drawnDir = Map.TrainerDirection.UP_2;
					xOffset += moveAmount;
					break;
				case DOWN:
					if (walk)
						drawnDir = Map.TrainerDirection.DOWN_1;
					else
						drawnDir = Map.TrainerDirection.DOWN_2;
					xOffset += moveAmount;
					break;
				case LEFT:
					if (walk)
						drawnDir = Map.TrainerDirection.LEFT_1;
					else
						drawnDir = Map.TrainerDirection.LEFT_2;
					yOffset += moveAmount;
					break;
				case RIGHT:
					if (walk)
						drawnDir = Map.TrainerDirection.RIGHT_1;
					else
						drawnDir = Map.TrainerDirection.RIGHT_2;
					yOffset += moveAmount;
					break;
				default:
					break;

				}

				tic++;
			} else {
				movementTimer.stop();
				drawnDir = dir;
				tic = 0;
				animating = false;
			}
			repaint();
		}

	}

	public void load() {
		try {
			trainerSheet = ImageIO.read(new File("./images/SucKeRS_TrainerSpriteSheet_Test.png"));
			groundTileSet = ImageIO.read(new File("./images/SucKeRS_PokemonTileSet.png"));
			obstacleTileSet = ImageIO.read(new File("./images/SucKeRS_PokemonObstacleTileSet.png"));
			itemImage = ImageIO.read(new File("./images/Dream_Safari_Ball_Sprite.png"));
			itemImage =
					itemImage.getScaledInstance(Tile.SIZE / 2, Tile.SIZE / 2, Image.SCALE_SMOOTH);
			mewImage = ImageIO.read(new File("./images/mew_sprite.png"));
			teleporterImage = ImageIO.read(new File("./images/teleporter.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		animating = false;
		// make animation timer for Trainer movement
		movementTimer = new Timer(MOVEMENT_SPEED, new MovementTimerListener());
		if (bgPath != null && !bgPath.equals(""))
			startNewBGMusic();

	}

	public void playWalkingSound() {
		// play sounds effect of walking on this tile
		sfxPlayer.playSound(getGroundTiles()[trainerPoint.x][trainerPoint.y].getSoundFilePath());
	}

	public void startNewBGMusic() {
		if (!muted)
			bgPlayer.loopSound(bgPath);
	}

	public void restartBGMusic() {
		if (!muted)
			bgPlayer.restartSound();
	}

	public void pauseBGMusic() {
		if (!muted)
			bgPlayer.pauseSound();
	}

	public void stopBGMusic() {
		if (!muted)
			bgPlayer.stopSound();
	}

	public void changeBGMusic(String songFilePath) {
		if (!muted)
			stopBGMusic();
		bgPath = songFilePath;
		if (!muted)
			startNewBGMusic();

	}

	public String getBGMusicFilePath() {
		return bgPath;
	}

	public boolean trainerSteppingOnItem() {
		if (itemTiles[trainerPoint.x][trainerPoint.y] != null)
			return true;
		else
			return false;
	}

	public Item getItemAtCurrentLocation() {
		Item i = itemTiles[trainerPoint.x][trainerPoint.y];
		itemTiles[trainerPoint.x][trainerPoint.y] = null;
		return i;
	}

	public void mute() {
		stopBGMusic();

		muted = true;
	}
}
