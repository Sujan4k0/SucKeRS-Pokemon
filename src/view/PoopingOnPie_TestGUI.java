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

import controller.GameMode;
import model.MapModel.Ground;
import model.MapModel.Map;
import model.MapModel.MapType;
import model.MapModel.Tile;
import model.MapModel.TileManager;

public class PoopingOnPie_TestGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	GameMode game;

	public static void main(String[] args) {
		new PoopingOnPie_TestGUI().setVisible(true);
	}

	public PoopingOnPie_TestGUI() {
		game = new GameMode(MapType.MAZE);
		layoutGUI();
		registerListeners();

	}

	private void layoutGUI() {
		//TODO add components of GUI
		this.setLocation(0, 0);

		this.add(game.getMap(), BorderLayout.CENTER);
		//this.add(pimp2, BorderLayout.EAST);

		// fits this JFrame's size to its Components
		this.pack();

		// paints the map JPanel
		game.getMap().repaint();

	}

	private void registerListeners() {
		//TODO add necessary Listeners
		
	}

	

}
