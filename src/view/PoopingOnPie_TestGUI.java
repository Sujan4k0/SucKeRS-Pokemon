package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.MapModel.Ground;
import model.MapModel.Map;
import model.MapModel.Tile;
import model.MapModel.TileManager;

public class PoopingOnPie_TestGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		new PoopingOnPie_TestGUI().setVisible(true);

	}

	public PoopingOnPie_TestGUI() {

		

		layoutGUI();
		registerListeners();

	}

	private void layoutGUI() {
		//TODO add components of GUI
		this.setLocation(0, 0);

		PeeMuffinPanel pimp = new PeeMuffinPanel();
		PeeMuffinPanel pimp2 = new PeeMuffinPanel();


		this.add(pimp, BorderLayout.CENTER);
		//this.add(pimp2, BorderLayout.EAST);

		// fits this JFrame's size to its Components
		this.pack();
		
		// paints the JPanel
		pimp.repaint();

	}

	private void registerListeners() {
		//TODO add necessary Listeners

	}

	private class PeeMuffinPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PeeMuffinPanel() {
			
			int prefw = Map.MAP_WIDTH * Tile.TILE_DIM;
			int prefh = Map.MAP_HEIGHT * Tile.TILE_DIM;
			
			this.setPreferredSize(new Dimension(prefw, prefh));

		}

		public void paintComponent(Graphics g) {

			// populate the JFrame with Tiles
			new Map().drawMap(g);
			
		}
	}

}
