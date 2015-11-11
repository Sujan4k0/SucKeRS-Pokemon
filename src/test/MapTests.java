package test;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.TileObserver;

import model.MapModel.*;

import org.junit.Test;

import view.GraphicsManager;

public class MapTests {

	@Test
	public void testMazeMap() {

		MazeMap map = new MazeMap();
		
		assertEquals(map.getTrainerPoint().getClass(), Point.class);

		for (int i = -5; i < 6; i++) {
			for (int j = 5; j < 6; j++) {
				map.setTrainerPoint(new Point(i, j));
				assertEquals(map.getTrainerPoint(), new Point(i, j));
				assertEquals(map.getTrainerPoint().x, i);
				assertEquals(map.getTrainerPoint().y, j);
			}
		}

		for (Map.Direction d : Map.Direction.values()) {
			map.setTrainerDir(d);
			assertEquals(map.getTrainerDir(), d);
		}
		
		Ground[][] ground = map.getGroundTiles();
		
		assertEquals(ground.length, MazeMap.HEIGHT);
		assertEquals(ground[0].length, MazeMap.WIDTH);
		
		// there should be no null Ground Tiles
		for (int i = 0; i < ground.length; i++) {
			for (int j = 0; j < ground[0].length; j++) {
				assertFalse(ground[i][j] == null);
			}
		}
		
		/* Obstacle[][] obstacles = map.getObstacleTiles();
		
		assertEquals(obstacles.length, MazeMap.HEIGHT);
		assertEquals(obstacles[0].length, MazeMap.WIDTH);
		
		BufferedImage bi = new BufferedImage(Map.WIDTH * Tile.SIZE,Map.HEIGHT * Tile.SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = (Graphics)bi.createGraphics();
		map.drawMap(g);
		
		BufferedImage bi2 = new BufferedImage(Tile.SIZE,Tile.SIZE, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics)bi2.createGraphics();
		GraphicsManager.drawTile(g, ground[0][0], map.getObstacleTileSet(), 0, 0);
		GraphicsManager.drawTile(g, obstacles[0][0], map.getObstacleTileSet(), 0, 0);

		
		g.dispose();

		assertEquals(bi., bi2.getData()); */

	}
}
