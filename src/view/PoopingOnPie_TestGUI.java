package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.MapModel.Ground;
import model.MapModel.Map;
import model.MapModel.MapType;
import model.MapModel.Tile;
import model.MapModel.TileManager;

public class PoopingOnPie_TestGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private Map map;

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

		map = new Map(MapType.MAZE);
		
		map.addKeyListener(new OurKeyListener());
		map.setFocusable(true);

		this.add(map, BorderLayout.CENTER);
		//this.add(pimp2, BorderLayout.EAST);

		// fits this JFrame's size to its Components
		this.pack();

		// paints the map JPanel
		map.repaint();

	}

	private void registerListeners() {
		//TODO add necessary Listeners
		
	}

	private class OurKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("Key pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				map.moveUp();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				map.moveDown();
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				map.moveRight();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				map.moveLeft();
			}

			repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

}
