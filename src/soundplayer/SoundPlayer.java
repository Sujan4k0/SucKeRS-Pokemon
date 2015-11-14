package soundplayer;

import java.io.File;
import java.util.Map;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

				/* System.out.println("Duration: " + med.getDuration().toSeconds());

				// display media's metadata
				for (Map.Entry<String, Object> entry : med.getMetadata().entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				} */

				// play when ready
				mediaPlayer.play();
			}
		});
	}

	public void stopSound() {
		mediaPlayer.stop();
	}
}
