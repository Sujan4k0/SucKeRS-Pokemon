package test;

import static org.junit.Assert.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import model.MapModel.*;

import org.junit.Test;

import view.CEAMap;
import view.GraphicsManager;
import view.MazeMap;

public class MapModelTests {

	/*
	 * @Test public void testMazeMap() {
	 * 
	 * MazeMap map = new MazeMap();
	 * 
	 * assertEquals(map.getTrainerPoint().getClass(), Point.class);
	 * 
	 * for (int i = -5; i < 6; i++) { for (int j = 5; j < 6; j++) {
	 * map.setTrainerPoint(new Point(i, j)); assertEquals(map.getTrainerPoint(),
	 * new Point(i, j)); assertEquals(map.getTrainerPoint().x, i);
	 * assertEquals(map.getTrainerPoint().y, j); } }
	 * 
	 * for (Map.TrainerDirection d : Map.TrainerDirection.values()) {
	 * map.setTrainerDir(d); assertEquals(map.getTrainerDir(), d); }
	 * 
	 * Ground[][] ground = map.getGroundTiles();
	 * 
	 * assertEquals(ground.length, MazeMap.HEIGHT);
	 * assertEquals(ground[0].length, MazeMap.WIDTH);
	 * 
	 * // there should be no null Ground Tiles for (int i = 0; i <
	 * ground.length; i++) { for (int j = 0; j < ground[0].length; j++) {
	 * assertFalse(ground[i][j] == null); } }
	 * 
	 * Obstacle[][] obstacles = map.getObstacleTiles();
	 * assertEquals(obstacles.length, MazeMap.HEIGHT);
	 * assertEquals(obstacles[0].length, MazeMap.WIDTH);
	 * 
	 * // maze obstacles should only have 1 null on the left most // and also
	 * right most sides of the map int nullCount = 0; for (int i = 0; i <
	 * obstacles.length; i++) { if (obstacles[i][0] == null) nullCount++; if
	 * (obstacles[i][obstacles[0].length - 1] == null) nullCount++; }
	 * assertEquals(nullCount, 2);
	 * 
	 * 
	 * int h = Map.HEIGHT * Tile.SIZE; int w = Map.WIDTH * Tile.SIZE;
	 * BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	 * Graphics g = (Graphics) bi.createGraphics();
	 * 
	 * // make sure map draws the upper left area map.moveUp(); map.moveUp();
	 * map.moveDown(); map.moveRight(); map.moveLeft(); map.moveUp();
	 * map.paintComponent(g);
	 * 
	 * BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	 * g = (Graphics) bi2.createGraphics(); int x = 0, y = 0;
	 * 
	 * // draw ground and obstacle tiles for (int i = 0; i < Map.HEIGHT; i++) {
	 * y = i * Tile.SIZE; for (int j = 0; j < Map.WIDTH; j++) { x = j *
	 * Tile.SIZE; GraphicsManager.drawTile(g, ground[i][j],
	 * map.getGroundTileSet(), x, y); if (obstacles[i][j] != null)
	 * GraphicsManager.drawTile(g, obstacles[i][j], map.getObstacleTileSet(), x,
	 * y); }
	 * 
	 * }
	 * 
	 * // draw trainer sprite if necessary Point tp = map.getTrainerPoint();
	 * Map.TrainerDirection d = map.getTrainerDir(); Image img =
	 * map.getTrainerSheet(); if (tp.x < Map.HEIGHT && tp.y < Map.WIDTH) {
	 * GraphicsManager.drawTile(g, d, img, tp.y * Tile.SIZE, tp.x * Tile.SIZE);
	 * } // these two images should be exactly the same, with all matching
	 * pixels for (int i = 0; i < bi2.getHeight(); i++) { for (int j = 0; j <
	 * bi2.getWidth(); j++) { assertEquals(bi.getRGB(j, i), bi2.getRGB(j, i)); }
	 * }
	 * 
	 * g.dispose();
	 * 
	 * }
	 */

	/*
	 * @Test public void testCEAMap() {
	 * 
	 * CEAMap map = new CEAMap();
	 * 
	 * assertEquals(map.getTrainerPoint().getClass(), Point.class);
	 * 
	 * for (int i = -5; i < 6; i++) { for (int j = 5; j < 6; j++) {
	 * map.setTrainerPoint(new Point(i, j)); assertEquals(map.getTrainerPoint(),
	 * new Point(i, j)); assertEquals(map.getTrainerPoint().x, i);
	 * assertEquals(map.getTrainerPoint().y, j); } }
	 * 
	 * for (Map.TrainerDirection d : Map.TrainerDirection.values()) {
	 * map.setTrainerDir(d); assertEquals(map.getTrainerDir(), d); }
	 * 
	 * Ground[][] ground = map.getGroundTiles();
	 * 
	 * assertEquals(ground.length, CEAMap.HEIGHT);
	 * assertEquals(ground[0].length, CEAMap.WIDTH);
	 * 
	 * // there should be no null Ground Tiles for (int i = 0; i <
	 * ground.length; i++) { for (int j = 0; j < ground[0].length; j++) {
	 * assertFalse(ground[i][j] == null); } }
	 * 
	 * Obstacle[][] obstacles = map.getObstacleTiles();
	 * assertEquals(obstacles.length, CEAMap.HEIGHT);
	 * assertEquals(obstacles[0].length, CEAMap.WIDTH);
	 * 
	 * int h = Map.HEIGHT * Tile.SIZE; int w = Map.WIDTH * Tile.SIZE;
	 * BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	 * Graphics g = (Graphics) bi.createGraphics();
	 * 
	 * // make sure map draws the upper left area map.moveUp(); map.moveUp();
	 * map.moveDown(); map.moveRight(); map.moveLeft(); map.moveUp();
	 * map.drawMap(g);
	 * 
	 * BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	 * g = (Graphics) bi2.createGraphics(); int x = 0, y = 0;
	 * 
	 * // draw ground and obstacle tiles for (int i = 0; i < Map.HEIGHT; i++) {
	 * y = i * Tile.SIZE; for (int j = 0; j < Map.WIDTH; j++) { x = j *
	 * Tile.SIZE; GraphicsManager.drawTile(g, ground[i][j],
	 * map.getGroundTileSet(), x, y); if (obstacles[i][j] != null)
	 * GraphicsManager.drawTile(g, obstacles[i][j], map.getObstacleTileSet(), x,
	 * y); }
	 * 
	 * }
	 * 
	 * // draw trainer sprite if necessary Point tp = map.getTrainerPoint();
	 * Map.TrainerDirection d = map.getTrainerDir(); Image img =
	 * map.getTrainerSheet(); if (tp.x < Map.HEIGHT && tp.y < Map.WIDTH) {
	 * GraphicsManager.drawTile(g, d, img, tp.y * Tile.SIZE, tp.x * Tile.SIZE);
	 * }
	 * 
	 * // these two images should be exactly the same, with all matching pixels
	 * for (int i = 0; i < bi2.getHeight(); i++) { for (int j = 0; j <
	 * bi2.getWidth(); j++) { assertEquals(bi.getRGB(j, i), bi2.getRGB(j, i)); }
	 * }
	 * 
	 * g.dispose();
	 * 
	 * }
	 */

	@Test
	public void testMazeGenerator() {
		Obstacle[][] obstacles = new Obstacle[11][11];
		for (int i = 0; i < 11; i++)
			for (int j = 0; j < 11; j++)
				obstacles[i][j] = Obstacle.ROCK_1;

		new MazeGenerator(new Point(5, 1), obstacles).generateMaze();

		// maze obstacles should only have 1 null on the left most 
		// and also right most sides of the map
		int nullCount = 0;
		for (int i = 0; i < obstacles.length; i++) {
			if (obstacles[i][0] == null)
				nullCount++;
			if (obstacles[i][obstacles[0].length - 1] == null)
				nullCount++;
		}
		assertEquals(nullCount, 2);

	}

	@Test
	public void testTiles() {

		assertEquals(Obstacle.CACTUS_1.getTerrainType(), TerrainType.DESERT);

		for (Obstacle o : Obstacle.values()) {
			assertEquals(o, Obstacle.valueOf(o.name()));
			assertEquals(o.interactWithTrainer(), "successfully called");
		}

		assertEquals(Ground.CAVE_1.getTerrainType(), TerrainType.CAVE);

		for (Ground g : Ground.values()) {
			assertEquals(g, Ground.valueOf(g.name()));
			assertEquals(g.interactWithTrainer(), "successfully called");
		}

	}

	@Test
	public void testTerrainType() {

		assertEquals(TerrainType.CAVE.getSound1(), "");
		assertEquals(TerrainType.CAVE.getSound2(), "");
		
		for (TerrainType t : TerrainType.values()) {
			assertEquals(t, TerrainType.valueOf(t.name()));
		}

	}

}
