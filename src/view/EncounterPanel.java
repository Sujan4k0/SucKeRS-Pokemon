package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.GameModel.TerrainType;
import model.GameModel.Tile;
import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonResponse;
import model.TrainerModel.TrainerAction;
import soundplayer.SoundPlayer;

public class EncounterPanel extends JPanel implements Serializable {

	private static final long serialVersionUID = 1L;

	// Plays bg sounds :D
	SoundPlayer bgPlayer = new SoundPlayer();

	// Plays sfx :D
	SoundPlayer sfxPlayer = new SoundPlayer();

	// Current action to animate
	TrainerAction currentAction;

	// Current response from pokemon
	PokemonResponse currentResponse;

	// Location to draw the pokemon
	int pokemonX, pokemonY;

	// currently encountered Pokemon to draw to jpanel
	Pokemon encounteredPokemon;

	int trainerOffset, pokemonOffset, pokemonSize;

	// Timer for animations and flashing/wiggle when caught pokemon
	Timer actionAnimationTimer, introAnimationTimer, flashTimer;
	Timer wiggleTimer, pmWiggleTimer;

	// tics and such for the timers
	int pmWiggleTic = -3, wiggleTic = -3, trainerTic = 1, bgNum = 0;

	// the rotation of the ball during wiggle time
	AffineTransform rotation = new AffineTransform();
	AffineTransform pmRotation = new AffineTransform();

	boolean flashing = false;

	boolean muted = false;

	// the current image of the trainer during an encounter
	// and BG image
	transient Image trainerEncounterImage, bgImage;

	// images for ball, bait, and rock being thrown
	transient Image ballImage, baitImage, rockImage, itemImage;

	// where to draw the item image
	Point itemLoc;

	// stores all possible bgImages for animation
	transient Image[] bgImages = new Image[0];

	// the Trainer's sprite sheet with bigger sprites mmmhmms
	transient Image bigTrainerSheet;

	// array of images for trainer animation
	transient Image[] trainerImages;

	// so that the trainer animation can't be spammed
	boolean animating = false;

	// timer for animated encounter BGs
	Timer animatedBGTimer;

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public EncounterPanel() {

		load();

		int prefw = WIDTH * Tile.SIZE;
		int prefh = HEIGHT * Tile.SIZE;

		this.setPreferredSize(new Dimension(prefw, prefh));

	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public void paintComponent(Graphics g) {

		drawEncounter(g);

	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public Pokemon getEncounteredPokemon() {
		return encounteredPokemon;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public void endEncounter() {
		// System.out.println("Ending Encounter on Panel");
		actionAnimationTimer.stop();
		wiggleTimer.stop();
		pmWiggleTimer.stop();
		flashTimer.stop();
		bgPlayer.stopSound();
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public void drawEncounter(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		if (!flashing) {
			if (pokemonX < -250) {
				pokemonX = this.getWidth() - pokemonSize;
				// System.out.println("pokemonX now = " + pokemonX);
				pokemonY = this.getHeight() / 15;
			}

			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			g2.drawRect(0, 0, this.getWidth(), this.getHeight());

			// draw background
			if (bgImages.length != 0) {
				Image scaledBG =
						bgImage.getScaledInstance(this.getWidth(), this.getHeight(),
								Image.SCALE_SMOOTH);
				g2.drawImage(scaledBG, 0, 0, null);
			}

			if (encounteredPokemon != null) {
				Image smaller =
						encounteredPokemon.getSprite()[0].getScaledInstance(pokemonSize,
								pokemonSize, Image.SCALE_SMOOTH);
				// draw pokemon
				/*
				 * g2.drawImage(smaller, pokemonX + pokemonOffset, pokemonY,
				 * null);
				 */
				// System.out.println("drawing pokemon");
				if ((int) pmRotation.getTranslateX() != pokemonX + pokemonOffset) {
					pmRotation = new AffineTransform();
					pmRotation.translate(pokemonX + pokemonOffset, pokemonY);
				}
				g2.drawImage(smaller, pmRotation, null);
				// g2.drawImage(itemImage, itemLoc.x, itemLoc.y, 75, 75, null);
			}

			/*
			 * draw pokemon stats g2.setColor(Color.WHITE); g2.setFont(new Font(
			 * "Comic Sans", Font.CENTER_BASELINE, 30)); FontMetrics fm =
			 * g2.getFontMetrics(); int strW =
			 * fm.stringWidth(encounteredPokemon.getName());
			 * g2.fillRect(this.getWidth() - pokemonSize - 200, this.getHeight()
			 * / 15 + 100, strW + 25, 40); g2.setColor(Color.BLACK);
			 * g2.drawString(encounteredPokemon.getName(), this.getWidth() -
			 * pokemonSize - 190, this.getHeight() / 15 + 125);
			 */
			// g2.setColor(Color.blue);
			// g2.fillRoundRect(this.getWidth() - pokemonSize - 175,
			// this.getHeight()/15 + 125,
			// encounteredPokemon.get, 25, 2, 2);*/

			// draw trainer
			if (trainerEncounterImage != null)
				g2.drawImage(trainerEncounterImage, 0 - trainerOffset, this.getHeight()
						- trainerEncounterImage.getHeight(null), 400, 400, null);

			// draw object being used
			if (itemImage != null) {
				if ((int) rotation.getTranslateX() != itemLoc.x) {
					rotation = new AffineTransform();
					rotation.translate(itemLoc.x, itemLoc.y);
				}
				g2.drawImage(itemImage, rotation, null);
				// g2.drawImage(itemImage, itemLoc.x, itemLoc.y, 75, 75, null);
			}
		} else {

			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		}

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getBigTrainerSheet]
	 |  Purpose:  	    [Getter for the trainer's big sprite sheet, used in encounters]
	 |  Returns:  	    [Image: the trainer's big sprite sheet]
	 *---------------------------------------------------------------------*/
	public Image getBigTrainerSheet() {
		return bigTrainerSheet;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public void startEncounter(Pokemon p, TerrainType tt) {
		startEncounter(p, tt, true);
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public void startEncounter(Pokemon p, TerrainType tt, boolean animate) {

		int songInt;

		actionAnimationTimer.stop();
		wiggleTimer.stop();
		pmWiggleTimer.stop();
		flashTimer.stop();
		itemImage = null;

		if (tt == TerrainType.MYSTERY)
			songInt = 2;
		else
			songInt = 1;

		String str = "./sounds/Pokemon_BattleMusic_" + songInt + ".wav";

		if (!muted)
			if (new File("./sounds/Pokemon_BattleMusic_" + songInt + ".wav").isFile())
				bgPlayer.loopSound(str);
			else
				System.out.println("Battle music file not found");

		if (bgImages.length > 1)
			animatedBGTimer.start();

		// System.out.println("starting encounter on panel");
		trainerEncounterImage = trainerImages[0];
		encounteredPokemon = p;

		pokemonSize = 300;

		trainerOffset = 200;
		pokemonOffset = 200;

		pokemonX = this.getWidth() - pokemonSize;
		pokemonY = this.getHeight() / 15;

		if (animate)
			introAnimationTimer.start();

		repaint();

	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public void animateTrainer(TrainerAction ta, PokemonResponse pr) {
		currentAction = ta;
		currentResponse = pr;
		if (!animating) {

			itemLoc =
					new Point((int) (trainerEncounterImage.getWidth(null) * 0.8), this.getHeight()
							- (int) (trainerEncounterImage.getHeight(null) * 0.7));
			animating = true;

			pokemonX = this.getWidth() - pokemonSize;
			pokemonY = this.getHeight() / 15;

			switch (currentAction) {
			case THROW_BAIT:
				itemImage = baitImage;
				break;
			case THROW_ROCK:
				itemImage = rockImage;
				break;
			case THROW_BALL:
				itemImage = ballImage;
				break;
			default:
				break;
			}

			if (itemImage != null)
				itemImage = itemImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);

			actionAnimationTimer.start();
		}
		if (!muted)
			playBattleSound(ta, pr); //Play a sound effect
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public boolean isAnimating() {
		return animating;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public void setBGImages(TerrainType tt) {
		Image[] imgs;
		if (tt.name().toUpperCase().equals("MYSTERY")) {
			imgs = new Image[3];
			for (int j = 1; j < imgs.length + 1; j++) {
				String path =
						"./images/bgImages/" + tt.name().toLowerCase() + "BattleBackground" + j
								+ ".png";

				Image i;
				try {
					i = ImageIO.read(new File(path));
					imgs[j - 1] = i;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Tried reading Image at: " + path);
					e.printStackTrace();
				}
			}
		} else {
			imgs = new Image[1];
			String path = "./images/bgImages/" + tt.name().toLowerCase() + "BattleBackground.png";

			Image i;
			try {
				i = ImageIO.read(new File(path));
				imgs[0] = i;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Tried reading Image at: " + path);
				e.printStackTrace();
			}
		}

		animatedBGTimer.stop();
		bgImages = imgs;
		bgImage = bgImages[0];
		animatedBGTimer.start();

	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	@SuppressWarnings("serial")
	public void load() {
		try {
			bigTrainerSheet =
					ImageIO.read(new File("./images/SucKeRS_LargeTrainerSpriteSheet_1.png"));
			trainerImages =
					GraphicsManager.getImageArray(bigTrainerSheet,
							GraphicsManager.BIGSPRITESHEET_WIDTH,
							GraphicsManager.BIGSPRITESHEET_HEIGHT, 400);
			ballImage = ImageIO.read(new File("./images/Dream_Safari_Ball_Sprite.png"));
			rockImage = ImageIO.read(new File("./images/Rock_Sprite.png"));
			baitImage = ImageIO.read(new File("./images/Bait_Sprite.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create Timers

		actionAnimationTimer = new Timer(1000 / 10, new TrainerAnimationListener()) {
			@Override
			public void start() {
				super.start();
				animating = true;
			}

			@Override
			public void stop() {
				super.stop();
				trainerTic = 1;
				trainerEncounterImage = trainerImages[0];
				if (currentResponse != PokemonResponse.GET_CAUGHT) {
					itemImage = null;
					animating = false;
				} else {
					if (encounteredPokemon != null)
						if (encounteredPokemon.getState() == PokemonResponse.GET_CAUGHT) {
							System.out.println("Setting pkmon to null");
							encounteredPokemon = null;
						}
				}

				currentAction = null;
				currentResponse = null;
			}
		};
		introAnimationTimer = new Timer(5, new IntroAnimationListener()) {
			@Override
			public void start() {
				super.start();
				animating = true;
			}

			@Override
			public void stop() {
				super.stop();
				animating = false;
			}
		};
		animatedBGTimer = new Timer(1000 / 100, new BGAnimationListener()) {
			@Override
			public void start() {
				super.start();
			}

			@Override
			public void stop() {
				super.stop();
				bgNum = 0;
			}
		};
		flashTimer = new Timer(100, new FlashListener()) {
			@Override
			public void start() {
				super.start();
				flashing = true;
			}

			@Override
			public void stop() {
				super.stop();
				flashing = false;
			}
		};
		wiggleTimer = new Timer(50, new WiggleListener()) {
			@Override
			public void start() {
				super.start();
				animating = true;
			}

			@Override
			public void stop() {
				super.stop();
				animating = false;
				wiggleTic = -3;
			}
		};
		pmWiggleTimer = new Timer(50, new PokemonWiggleListener()) {
			@Override
			public void start() {
				super.start();
				animating = true;
			}

			@Override
			public void stop() {
				super.stop();
				animating = false;
				pmWiggleTic = -3;
			}
		};
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [playBattleSound]
	 |  Purpose:  	    [Plays a battle sound effect based on the outcome 
	 |                  of a TrainerAction and PokemonResponse]
	 |  Parameters:     [TrainerAction ta, PokemonResponse pr]
	 *---------------------------------------------------------------------*/

	public void playBattleSound(TrainerAction ta, PokemonResponse pr) {

		if ((ta == TrainerAction.THROW_BAIT) || (ta == TrainerAction.THROW_ROCK)) { //If the trainer is throwing something other than a Pokeball

			sfxPlayer.playSound("sounds/battlesfx/woosh.wav"); //Play the woosh sound effect!
		} else if (ta == TrainerAction.THROW_BALL) { //If the trainer was throwing a pokeball

			if (pr == PokemonResponse.GET_CAUGHT) { //If the pokemon was caught by the pokeball

				sfxPlayer.playSound("sounds/battlesfx/PokemonCaught.wav"); //Play caught sound effect
			} else { //Otherwise (The pokeball missed)

				sfxPlayer.playSound("sounds/battlesfx/PokeballMisses.wav"); //Play the missed sound effect
			}
		}
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	public void mute() {
		muted = true;
		bgPlayer.stopSound();
		sfxPlayer.stopSound();
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	private class BGAnimationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// keep updating the bgImage
			if (bgImages.length > 1 && bgNum < bgImages.length) {
				bgImage = bgImages[bgNum];
				bgNum++;
			} else if (bgNum >= bgImages.length) { // set num back to 0
				bgNum = 0;
			} else
				// if bgImages only has 1 image, stop the timer for memory
				animatedBGTimer.stop();

			repaint();

		}
	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	private class WiggleListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			rotation = new AffineTransform();
			rotation.translate(itemLoc.x, itemLoc.y);
			if (wiggleTic <= 0) {
				rotation.rotate(wiggleTic * Math.PI / 15);
			} else if (wiggleTic <= 3) {
				rotation.rotate(-wiggleTic * Math.PI / 15);
			} else {
				wiggleTic = -3;
				wiggleTimer.stop();
			}
			wiggleTic++;
			repaint();
		}

	}

	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Return:         []
	 *---------------------------------------------------------------------*/
	private class PokemonWiggleListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			pmRotation = new AffineTransform();
			pmRotation.translate(itemLoc.x, itemLoc.y);
			if (pmWiggleTic <= 0) {
				pmRotation.rotate(pmWiggleTic * Math.PI / 15);
			} else if (pmWiggleTic <= 3) {
				pmRotation.rotate(-pmWiggleTic * Math.PI / 15);
			} else {
				pmWiggleTimer.stop();
			}
			
			pmWiggleTic++;
			repaint();
		}

	}

	
	private class TrainerAnimationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (trainerTic < trainerImages.length + 6) {

				if (currentResponse == PokemonResponse.GET_CAUGHT) {
					//	System.out.println("pokemon being caught");
					pokemonSize *= 0.9;
					pokemonY *= 1.2;
					if (trainerTic > trainerImages.length + 4 && !flashing) {
						flashTimer.start();
					}
				} else
					pmWiggleTimer.start();

				if (trainerTic >= trainerImages.length)
					trainerEncounterImage = trainerImages[trainerImages.length - 1];
				else
					trainerEncounterImage = trainerImages[trainerTic];
				trainerTic++;

				int dx = 20, dy = -20;

				if (trainerTic > (trainerImages.length + 6) / 2)
					dy = (int) (-dy / 1f);

				itemLoc = new Point(itemLoc.x + dx, itemLoc.y + dy);

			} else {
				actionAnimationTimer.stop();

			}

			repaint();

		}
	}

	private class FlashListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			wiggleTimer.start();
			flashTimer.stop();
			repaint();
		}

	}

	private class IntroAnimationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (trainerOffset <= 0) {
				introAnimationTimer.stop();
			} else {
				trainerOffset -= 50;
				pokemonOffset -= 50;
				repaint();
			}

		}

	}

}
