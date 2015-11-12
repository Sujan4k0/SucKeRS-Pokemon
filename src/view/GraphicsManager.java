/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Graphics Manager] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |	     	   [Keith Smith  (browningsmith@email.arizona.edu)]
 |	     	   [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: This class has static methods used to draws tiles/sprite
 | given the specific tileset/spritesheet image. Tile sets and 10 by 10 and
 | sprite sheets are 6 by 2.
 *===========================================================================*/
package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import model.MapModel.Tile;

public class GraphicsManager {

	// tile sets are currently 10 by 10
	private static final int TILESET_DIM = 10;
	
	// sprite sheets are currently 6 by 2
	private static final int SPRITESHEET_WIDTH = 6, SPRITESHEET_HEIGHT = 2;

	/*---------------------------------------------------------------------
	 |  Method name:    [drawTile]
	 |  Purpose:  	    [Draws tile from tile set based on the ordinal of an enum]
	 |  Parameters:     [Graphics: graphics to draw tile with
	 |					 Enum<?>: an enum associated with the given tile set image]
	 |					 int: the x-position to draw the tile
	 |					 int: the y-position to draw the tile]
	 *---------------------------------------------------------------------*/
	public static void drawTile(Graphics g, Enum<?> tile, Image tileSet, int x, int y) {

		Graphics2D g2 = (Graphics2D) g;

		// (x,y) coordinates of the associated sprite
		// in the tileset image
		int tsX = tile.ordinal() % TILESET_DIM;
		int tsY = tile.ordinal() / TILESET_DIM;

		// get the Tile dimensions
		int td = Tile.SIZE;

		// draw the tile at the provided (x,y) location
		g2.drawImage(tileSet, x, y, x + td, y + td, tsX * td, tsY * td,
				(tsX + 1) * td, (tsY + 1) * td, null);

	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [drawSprite]
	 |  Purpose:  	    [Draws sprite associated with an enum and sprite sheet]
	 |  Parameters:     [Graphics: graphics to draw sprite with
	 |					 Enum<?>: an enum associated with the given sprite sheet image]
	 |					 int: the x-position to draw the tile
	 |					 int: the y-position to draw the tile]
	 *---------------------------------------------------------------------*/
	public static void drawSprite(Graphics g, Enum<?> tile, Image tileSet, int x, int y) {

		Graphics2D g2 = (Graphics2D) g;

		// (x,y) coordinates of the associated sprite
		// in the tileset image
		int tsX = tile.ordinal() % SPRITESHEET_WIDTH;
		int tsY = tile.ordinal() / SPRITESHEET_HEIGHT;

		// get the Tile dimensions
		int td = Tile.SIZE;

		// draw the tile at the provided (x,y) location
		g2.drawImage(tileSet, x, y, x + td, y + td, tsX * td, tsY * td,
				(tsX + 1) * td, (tsY + 1) * td, null);
	}
	
}
