package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import soundplayer.SoundPlayer;
import model.GameModel.Ground;
import model.GameModel.Obstacle;
import model.GameModel.Tile;
import model.ItemModel.Item;

public class MapPanel extends JPanel implements Observer {

	// how fast the trainer movement animation is in ms
	private static int MOVEMENT_SPEED = 7;

	// zee Map to be drawn
	Map map;

	// file path of BG music
	String bgPath = "";

	// the tile sets for Ground and Obstacle
	transient Image groundTileSet, obstacleTileSet, itemImage;

	// the Trainer's sprite sheet
	transient Image trainerSheet;
	
	// timer to animate trainer movement
	Timer movementTimer;

	// if the trainer movement is being animated
	boolean animating = false;

	// offsets for trainer animation
	int xOffset = 0, yOffset = 0;

	// Plays bg sounds :D
	SoundPlayer bgPlayer = new SoundPlayer();

	// Plays sfx :D
	SoundPlayer sfxPlayer = new SoundPlayer();
	
	Map.TrainerDirection drawnDir = Map.TrainerDirection.RIGHT;
	Map.TrainerDirection dir;

	public MapPanel(Map m) {
		map = m;
		load();
		this.addKeyListener(new OurKeyListener());
	}

	public void load() {
		try {
			trainerSheet = ImageIO.read(new File("./images/SucKeRS_TrainerSpriteSheet_Test.png"));
			groundTileSet = ImageIO.read(new File("./images/SucKeRS_PokemonTileSet.png"));
			obstacleTileSet = ImageIO.read(new File("./images/SucKeRS_PokemonObstacleTileSet.png"));
			itemImage = ImageIO.read(new File("./images/Dream_Safari_Ball_Sprite.png"));
			itemImage =
					itemImage.getScaledInstance(Tile.SIZE / 2, Tile.SIZE / 2, Image.SCALE_SMOOTH);
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
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getTrainerSheet]
	 |  Purpose:  	    [Getter for the trainer's sprite sheet]
	 |  Returns:  	    [Image: the trainer's sprite sheet]
	 *---------------------------------------------------------------------*/
	public Image getTrainerSheet() {
		return trainerSheet;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [drawMap]
	 |  Purpose:  	    [Draws the Ground, Obstacle, and Trainer]
	 |  Parameters:     [Graphics: the Graphics Object to use to draw]
	 *---------------------------------------------------------------------*/
	public void drawMap(Graphics g) {

		int x = 0, y = 0;

		map.checkMapPlacement();

		int startX = map.getStartX();
		int startY = map.getStartY();

		Ground[][] groundTiles = map.getGroundTiles();
		Obstacle[][] obstacleTiles = map.getObstacleTiles();
		Item[][] itemTiles = map.getItemTiles();
		Point trainerPoint = map.getTrainerPoint();

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

		// draw trainer sprite
		GraphicsManager.drawSprite(g, drawnDir, trainerSheet, (trainerPoint.y % WIDTH) * Tile.SIZE
				- yOffset, (trainerPoint.x % HEIGHT) * Tile.SIZE - xOffset);

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [paintComponent]
	 |  Purpose:  	    [To make it so that calling Map.repaint() draws the map
	 |					directly onto this JPanel]
	 |  Parameters:     [Graphics: this object's Graphics object]
	 *---------------------------------------------------------------------*/
	public void paintComponent(Graphics g) {
		drawMap(g);
	}

	@Override
	public void update(Observable o, Object arg) {

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

	public void playWalkingSound() {
		// play sounds effect of walking on this tile
		Point trainerPoint = map.getTrainerPoint();
		sfxPlayer.playSound(map.getGroundTiles()[trainerPoint.x][trainerPoint.y].getSoundFilePath());
	}

	public void startNewBGMusic() {
		bgPlayer.loopSound(bgPath);
	}

	public void restartBGMusic() {
		bgPlayer.restartSound();
	}

	public void pauseBGMusic() {
		bgPlayer.pauseSound();
	}

	public void stopBGMusic() {
		bgPlayer.stopSound();
	}

	public void changeBGMusic(String songFilePath) {
		stopBGMusic();
		bgPath = songFilePath;
		startNewBGMusic();

	}

	public String getBGMusicFilePath() {
		return bgPath;
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
				walk = !walk;
			}
			repaint();
		}

	}

	// TODO THIS WILL BE MOVED TO MAP
	/*---------------------------------------------------------------------
	 |  Class name:     [OurKeyListener]
	 |  Purpose:        [Used to move trainer around the map]
	 *---------------------------------------------------------------------*/
	private class OurKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		/*---------------------------------------------------------------------
		 |  Method name:    [keyPressed]
		 |  Purpose:  	    [handles KeyEvents on key press]
		 |  Parameters:     [KeyEvent]
		 *---------------------------------------------------------------------*/
		@Override
		public void keyPressed(KeyEvent e) {

			// if the trainer isn't already moving...
			if (!animating) {
				// set sprite direction and try to move trainer
				dir = map.getTrainerDir();
				map.moveTrainer(e.getKeyCode());
			}

			if (true)
				stopBGMusic();

			// map.update(trainer); // does anything the map needs to check ever
			// key press

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

}
