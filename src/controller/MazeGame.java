package controller;

import java.awt.Point;

import model.MapModel.Map;
import model.MapModel.MazeMap;
import model.MapModel.Obstacle;
import model.TrainerModel.Trainer;

public class MazeGame extends GameMode {


	// entire catch 'em all Map is the same as maze (for now)
	public static int WIDTH = Map.WIDTH * 3, HEIGHT = Map.HEIGHT * 3;

	public MazeGame(Map m, Trainer t) {
		super(m, t);
	}

	public boolean isGameWon() {
		
		// get the obstacles of current map
		Obstacle[][] obstacle = map.getObstacleTiles();

		for (int i = 0; i < MazeMap.HEIGHT; i++) {
			if (obstacle[i][MazeMap.WIDTH - 1] == null) {
				if (trainer.getPoint().equals(
						new Point(i, MazeMap.WIDTH - 1)))
					return true;
			}
		}

		return false;
	}

	@Override
	public boolean isGameLost() {
		// TODO Add losing condition
		return false;
	}
}
