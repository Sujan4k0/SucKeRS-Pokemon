package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.PokemonModel.Pokemon;
import soundplayer.SoundPlayer;

public class EncounterPanel extends JPanel {

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
	Image trainerEncounterImg;

	// the Trainer's sprite sheet with bigger sprites mmmhmms
	Image bigTrainerSheet;

	// array of images for trainer animation
	Image[] trainerImgs;

	public EncounterPanel() {
		// get sprite sheets & tile sets
		try {
			bigTrainerSheet =
					ImageIO.read(new File("./images/SucKeRS_LargeTrainerSpriteSheet_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		trainerImgs =
				GraphicsManager.getImageArray(bigTrainerSheet,
						GraphicsManager.BIGSPRITESHEET_WIDTH,
						GraphicsManager.BIGSPRITESHEET_HEIGHT, 400);

	}

	public void hideEncounter() {
		animationTimer.stop();
		encounteredPokemon = null;
	}

	public void drawEncounter(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2.drawRect(0, 0, this.getWidth(), this.getHeight());

		g2.drawImage(encounteredPokemon.getSprite()[0], this.getWidth() - 400, 0, 400, 400, null);
		if (trainerEncounterImg != null)
			g2.drawImage(trainerEncounterImg, 0,
					this.getHeight() - trainerEncounterImg.getHeight(null), 400, 400, null);

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getBigTrainerSheet]
	 |  Purpose:  	    [Getter for the trainer's big sprite sheet, used in encounters]
	 |  Returns:  	    [Image: the trainer's big sprite sheet]
	 *---------------------------------------------------------------------*/
	public Image getBigTrainerSheet() {
		return bigTrainerSheet;
	}

	public void showEncounter(Pokemon p) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		encounteredPokemon = p;

	}

	public void animateTrainer() {
		animationTimer = new Timer(1000 / 10, new AnimationListener(trainerImgs));
		animationTimer.start();
	}

	private class AnimationListener implements ActionListener {

		int tic = 0;
		Image[] images;

		public AnimationListener(Image[] i) {
			images = i;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (tic < images.length) {

				trainerEncounterImg = images[tic];
				repaint();
				tic++;

			} else
				tic = 0;

		}

	}
}
