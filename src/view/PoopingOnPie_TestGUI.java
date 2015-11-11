package view;

import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.*;

import controller.GameMode;
import controller.MazeGame;
//import controller.MazeGame;
//import model.MapModel.MazeMap;
import model.TrainerModel.Trainer;

public class PoopingOnPie_TestGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	GameMode game;

	public static void main(String[] args) {
		new PoopingOnPie_TestGUI().setVisible(true);
	}

	public PoopingOnPie_TestGUI() {
	 	game = new MazeGame(new Random());
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
