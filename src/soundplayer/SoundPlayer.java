package soundplayer;

import java.io.File;
import java.util.Map;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundPlayer {

	MediaPlayer mediaPlayer;

	public SoundPlayer() {
		JFXPanel fxPanel = new JFXPanel();
	}

	public void playSound(String filePath) {

		File file = new File(filePath);
		Media med = new Media(file.toURI().toString());

		mediaPlayer = new MediaPlayer(med);
		mediaPlayer.setOnReady(new Runnable() {
			@Override
			public void run() {
				// play when ready
				mediaPlayer.play();
			}
		});
	}

	public void stopSound() {
		mediaPlayer.stop();
	}

	public void loopSound(String filePath) {
		File file = new File(filePath);
		Media med = new Media(file.toURI().toString());

		mediaPlayer = new MediaPlayer(med);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});

		mediaPlayer.play();
	}
}