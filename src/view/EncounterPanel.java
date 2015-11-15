package view;

import java.awt.Color;
import java.awt.Dimension;
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

import javax.imageio.ImageIO;
import javax.swing.*;

import model.MapModel.Tile;
import model.PokemonModel.Pokemon;
import soundplayer.SoundPlayer;

public class EncounterPanel extends JPanel implements Serializable {

	private static final long serialVersionUID = 1L;

	// Plays bg sounds :D
	SoundPlayer bgPlayer = new SoundPlayer();

	// Plays sfx :D
	SoundPlayer sfxPlayer = new SoundPlayer();

	// currently encountered Pokemon to draw to jpanel
	Pokemon encounteredPokemon;

	// Timer for animations
	Timer animationTimer;

	// the current image of the trainer during an encounter
	Image trainerEncounterImage;

	// the Trainer's sprite sheet with bigger sprites mmmhmms
	Image bigTrainerSheet;

	// array of images for trainer animation
	Image[] trainerImages;

	// so that the trainer animation can't be spammed
	boolean animating = false;

	public EncounterPanel() {
		// get sprite sheets & tile sets
		try {
			bigTrainerSheet =
					ImageIO.read(new File("./images/SucKeRS_LargeTrainerSpriteSheet_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		trainerImages =
				GraphicsManager.getImageArray(bigTrainerSheet,
						GraphicsManager.BIGSPRITESHEET_WIDTH,
						GraphicsManager.BIGSPRITESHEET_HEIGHT, 400);
		int prefw = WIDTH * Tile.SIZE;
		int prefh = HEIGHT * Tile.SIZE;

		this.setPreferredSize(new Dimension(prefw, prefh));

		this.addKeyListener(new ThisKeyListener());

	}

	public void paintComponent(Graphics g) {

		drawEncounter(g);

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
		Image smaller =
				encounteredPokemon.getSprite()[0].getScaledInstance(pokemonSize, pokemonSize,
						Image.SCALE_SMOOTH);

		g2.drawImage(smaller, this.getWidth() - pokemonSize, this.getHeight() / 15, pokemonSize,
				pokemonSize, null);

		if (trainerEncounterImage != null)
			g2.drawImage(trainerEncounterImage, 0,
					this.getHeight() - trainerEncounterImage.getHeight(null), 400, 400, null);

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
		bgPlayer.playSound("./sounds/Pokemon_BattleMusic_1.wav");

		p.startEncounter();
		trainerEncounterImage = trainerImages[0];
		encounteredPokemon = p;
		this.repaint();

	}
	

	public void animateTrainer() {
		if (!animating) {
			animating = true;
			animationTimer = new Timer(1000 / 10, new TrainerAnimationListener());
			animationTimer.start();
		}
	}
	
	public boolean isAnimating() {
		return animating;
	}

	private class ThisKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// System.out.println("Encounter Listener");
			if (!animating && e.getKeyCode() == KeyEvent.VK_SPACE)
				animateTrainer();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

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
}
