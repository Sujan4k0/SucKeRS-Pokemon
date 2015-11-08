package model.MapModel;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {

	// Map will be MAP_WIDTH by MAP_HEIGHT tiles
	public static int MAP_WIDTH = 15, MAP_HEIGHT = 10;

	private Ground[][] groundTiles;
	private Obstacle[][] obstacleTiles;

	private Image groundTileSet, obstacleTileSet;

	public Map() {

		try {
			groundTileSet = ImageIO.read(new File(
					"./images/SucKeRS_PokemonTileSet.png"));
			obstacleTileSet = ImageIO.read(new File(
					"./images/SucKeRS_PokemonObstacleTileSet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		createMap();

	}

	private void createMap() {

		groundTiles = new Ground[MAP_HEIGHT][MAP_WIDTH];
		obstacleTiles = new Obstacle[MAP_HEIGHT][MAP_WIDTH];

		//TODO: Fill groundTiles[][] with Ground
		for (int i = 0; i < Map.MAP_HEIGHT; i++) {
			for (int j = 0; j < Map.MAP_WIDTH; j++) {
				
				// fills all ground with just these cave tiles
				groundTiles[i][j] = Ground.CAVE_1;
				
				/* 
				 * This code generates ice tiles with a border
				if (i == 0 || i == Map.MAP_HEIGHT - 1 || j == Map.MAP_WIDTH - 1
						|| j == 0)
					groundTiles[i][j] = Ground.ICE_3;
				else
					groundTiles[i][j] = Ground.ICE_1;
				*/
			}
		}
		
		//TODO: Fill obstacleTiles[][] with Obstacles
		for (int i = 0; i < Map.MAP_HEIGHT; i++) {
			for (int j = 0; j < Map.MAP_WIDTH; j++) {
				
			}
		}
		
		obstacleTiles[0][0] = Obstacle.ROCK_1;
		obstacleTiles[1][0] = Obstacle.ROCK_1;
		obstacleTiles[2][1] = Obstacle.ROCK_1;
		obstacleTiles[3][2] = Obstacle.ROCK_1; 
		
		obstacleTiles[8][13] = Obstacle.ROCK_3;
		obstacleTiles[8][14] = Obstacle.ROCK_3;
		obstacleTiles[8][12] = Obstacle.ROCK_3;
		obstacleTiles[7][11] = Obstacle.ROCK_3; 
		
	}

	public Ground[][] getGroundTiles() {

		return groundTiles;

	}

	public Obstacle[][] getObstacleTiles() {

		return obstacleTiles;

	}

	public void drawMap(Graphics g) {

		int x = 0, y = 0;

		for (int i = 0; i < Map.MAP_HEIGHT; i++) {
			y = i * Tile.TILE_DIM;
			for (int j = 0; j < Map.MAP_WIDTH; j++) {
				x = j * Tile.TILE_DIM;
				TileManager.drawTile(g, groundTiles[i][j], groundTileSet, x, y);
			}

		}
		
		for (int i = 0; i < Map.MAP_HEIGHT; i++) {
			y = i * Tile.TILE_DIM;
			for (int j = 0; j < Map.MAP_WIDTH; j++) {
				x = j * Tile.TILE_DIM;
				if (obstacleTiles[i][j] != null)
					TileManager.drawTile(g, obstacleTiles[i][j], obstacleTileSet, x, y);
			}

		}

	}

}
