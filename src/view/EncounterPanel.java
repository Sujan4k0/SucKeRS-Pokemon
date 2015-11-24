package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

	// currently encountered Pokemon to draw to jpanel
	Pokemon encounteredPokemon;

	// Timer for animations
	Timer animationTimer;

	// the current image of the trainer during an encounter
	// and BG image
	transient Image trainerEncounterImage, bgImage;

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

	public EncounterPanel() {

		load();

		int prefw = WIDTH * Tile.SIZE;
		int prefh = HEIGHT * Tile.SIZE;

		this.setPreferredSize(new Dimension(prefw, prefh));

	}

	public void paintComponent(Graphics g) {

		drawEncounter(g);

	}
	
	public Pokemon getEncounteredPokemon() {
		return encounteredPokemon;
	}

	public void stopEncounter() {
		animating = false;
		if (animationTimer != null)
			animationTimer.stop();
		bgPlayer.stopSound();
	}

	public void drawEncounter(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.drawRect(0, 0, this.getWidth(), this.getHeight());

		int pokemonSize = 300;
		Image smaller = encounteredPokemon.getSprite()[0].getScaledInstance(pokemonSize, pokemonSize,
				Image.SCALE_SMOOTH);

		// draw background
		if (bgImages.length != 0) {
			Image scaledBG = bgImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
			g2.drawImage(scaledBG, 0, 0, null);
		}

		// draw pokemon
		g2.drawImage(smaller, this.getWidth() - pokemonSize, this.getHeight() / 15, pokemonSize, pokemonSize, null);

		/*
		 * draw pokemon stats g2.setColor(Color.WHITE); g2.setFont(new Font(
		 * "Comic Sans", Font.CENTER_BASELINE, 30)); FontMetrics fm =
		 * g2.getFontMetrics(); int strW =
		 * fm.stringWidth(encounteredPokemon.getName());
		 * g2.fillRect(this.getWidth() - pokemonSize - 200, this.getHeight() /
		 * 15 + 100, strW + 25, 40); g2.setColor(Color.BLACK);
		 * g2.drawString(encounteredPokemon.getName(), this.getWidth() -
		 * pokemonSize - 190, this.getHeight() / 15 + 125);
		 */
		// g2.setColor(Color.blue);
		// g2.fillRoundRect(this.getWidth() - pokemonSize - 175,
		// this.getHeight()/15 + 125,
		// encounteredPokemon.get, 25, 2, 2);*/

		// draw trainer
		if (trainerEncounterImage != null)
			g2.drawImage(trainerEncounterImage, 0, this.getHeight() - trainerEncounterImage.getHeight(null), 400, 400,
					null);

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getBigTrainerSheet]
	 |  Purpose:  	    [Getter for the trainer's big sprite sheet, used in encounters]
	 |  Returns:  	    [Image: the trainer's big sprite sheet]
	 *---------------------------------------------------------------------*/
	public Image getBigTrainerSheet() {
		return bigTrainerSheet;
	}

	public void startEncounter(Pokemon p) {

		int rand = new Random().nextInt(4) + 1;
		bgPlayer.loopSound("./sounds/Pokemon_BattleMusic_" + rand + ".wav");

		animatedBGTimer.start();

		trainerEncounterImage = trainerImages[0];
		encounteredPokemon = p;
		
		repaint();

	}

	public void animateTrainer(TrainerAction ta, PokemonResponse pr) {
		currentAction = ta;
		if (!animating) {
			animating = true;
			animationTimer.start();
		}
		
		playBattleSound(ta, pr);			//Play a sound effect
	}

	public boolean isAnimating() {
		return animating;
	}

	private class BGAnimationListener implements ActionListener {

		int num = 0;

		@Override
		public void actionPerformed(ActionEvent e) {

			// keep updating the bgImage
			if (bgImages.length > 1 && num < bgImages.length) {
				bgImage = bgImages[num];
				num++;
			} else if (num >= bgImages.length) { // set num back to 0
				num = 0;
			} else
				// if bgImages only has 1 image, stop the timer for memory
				animatedBGTimer.stop();

			repaint();

		}
	}

	private class TrainerAnimationListener implements ActionListener {

		int tic = 0;

		@Override
		public void actionPerformed(ActionEvent e) {

			animating = true;
			if (tic < trainerImages.length + 3) {

				if (tic >= trainerImages.length)
					trainerEncounterImage = trainerImages[trainerImages.length - 1];
				else
					trainerEncounterImage = trainerImages[tic];
				tic++;

			} else {
				tic = 0;
				trainerEncounterImage = trainerImages[0];
				animating = false;
				animationTimer.stop();
			}

			repaint();

		}

	}

	public void setBGImages(TerrainType tt) {
		Image[] imgs;
		if (tt.name().toUpperCase().equals("MYSTERY")) {
			imgs = new Image[3];
			for (int j = 1; j < imgs.length + 1; j++) {
				String path = "./images/bgImages/" + tt.name().toLowerCase() + "BattleBackground" + j + ".png";

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

	public void load() {
		try {
			bigTrainerSheet = ImageIO.read(new File("./images/SucKeRS_LargeTrainerSpriteSheet_1.png"));
			trainerImages = GraphicsManager.getImageArray(bigTrainerSheet, GraphicsManager.BIGSPRITESHEET_WIDTH,
					GraphicsManager.BIGSPRITESHEET_HEIGHT, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create Timers

		animationTimer = new Timer(1000 / 10, new TrainerAnimationListener());
		animatedBGTimer = new Timer(1000 / 100, new BGAnimationListener());
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [playBattleSound]
	 |  Purpose:  	    [Plays a battle sound effect based on the outcome 
	 |                  of a TrainerAction and PokemonResponse]
	 |  Parameters:     [TrainerAction ta, PokemonResponse pr]
	 *---------------------------------------------------------------------*/
	
	public void playBattleSound(TrainerAction ta, PokemonResponse pr) {
		
		if (pr == PokemonResponse.GET_CAUGHT) {		 //If the pokemon was caught
			
			sfxPlayer.playSound("sounds/battlesfx/PokemonCaught.mp3");		//Play the sound effect of a pokemon being caught
		}
		else if (ta == TrainerAction.THROW_BALL) {   //If the trainer threw a pokeball, we know it missed. Play the missed sound effect
			
			sfxPlayer.playSound("sounds/battlesfx/PokeballMisses.mp3");	    //Play the sound of a pokeball missing
		}
		else if ((ta == TrainerAction.THROW_BAIT) || (ta == TrainerAction.THROW_ROCK)) {		//Otherwise if the trainer is throwing something
			
			sfxPlayer.playSound("sounds/battlesfx/woosh.mp3");
		}	
	}

	
}
