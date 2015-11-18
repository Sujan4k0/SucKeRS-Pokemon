package test;

import static org.junit.Assert.*;

import java.util.Random;

import model.GameModel.MazeGame;

import org.junit.Test;

public class GameToConclusionTest {

	@Test
	public void testMazeGame() {
		Random r = new Random() {
			private static final long serialVersionUID = 1L;

			@Override
			public int nextInt(int i) {
				return 3; // always generate maze right
			}
		};
		
		MazeGame mg = new MazeGame(r);
		
	}

}
