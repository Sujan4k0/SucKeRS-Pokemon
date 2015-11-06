package model.Mapping;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class TileManager {

	private static int TILESET_DIM = 20;

	public static void drawTile(Graphics g, Ground tile, Image tileSet, int x, int y) {

		Graphics2D g2 = (Graphics2D) g;

		// (x,y) coordinates of the associated sprite
		// in the tileset image
		int tsX = tile.ordinal() % TILESET_DIM;
		int tsY = tile.ordinal() / TILESET_DIM;

		// get the Tile dimensions
		int td = Tile.TILE_DIM;

		// draw the tile at the provided (x,y) location
		g2.drawImage(tileSet, x, y, x + td, y + td, tsX * td, tsY * td,
				(tsX + 1) * td, (tsY + 1) * td, null);

	}

	public static void drawTile(Graphics g, Obstacle tile, Image tileSet, int x, int y) {

		Graphics2D g2 = (Graphics2D) g;

		// (x,y) coordinates of the associated sprite
		// in the tileset image
		int tsX = tile.ordinal() % TILESET_DIM;
		int tsY = tile.ordinal() / TILESET_DIM;

		// get the Tile dimensions
		int td = Tile.TILE_DIM;

		// draw the tile at the provided (x,y) location
		g2.drawImage(tileSet, x, y, x + td, y + td, tsX * td, tsY * td,
				(tsX + 1) * td, (tsY + 1) * td, null);

	}
}
