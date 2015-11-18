package view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;

import model.GameModel.CEAGame;
import model.GameModel.GameMode;
import model.GameModel.MazeGame;
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
	 	game = new CEAGame(new Random());
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
		game.getMap().addKeyListener((new GameWon()));
		game.getEncounterPanel().addKeyListener((new GameWon()));
		
	}

	private class GameWon implements KeyListener {
		
		boolean battleShown = false, mapShown = true;

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
        	System.out.println("GameWon listener");

            if (!battleShown && game.trainerInBattle()) {
            	remove(game.getMap());
            	add(game.getEncounterPanel(), BorderLayout.CENTER);
            	revalidate();
            	game.getEncounterPanel().requestFocusInWindow();
            	game.getEncounterPanel().repaint();
            	battleShown = true;
            	mapShown = false;
            	System.out.println("Showing Battle");
            } else if (!mapShown && !game.trainerInBattle()){
            	remove(game.getEncounterPanel());
            	add(game.getMap(), BorderLayout.CENTER);
            	game.getMap().repaint();
            	game.getEncounterPanel().stopEncounter();
            	revalidate();
            	game.getMap().requestFocusInWindow();
            	mapShown = true;
            	battleShown = false;
            	System.out.println("Showing Map");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }
    }
	

}
