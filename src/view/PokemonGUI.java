package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;

public class PokemonGUI extends JFrame {
    
    private JFrame startScreen;
    
    public static void main(String[] args) {
        
        new PokemonGUI();
    }

	public PokemonGUI() {

		layoutGUI();
		registerListeners();

	}

	private void layoutGUI() {

	    startFrame();
	}
	
	private void startFrame() {
	    
	    startScreen = new JFrame();
	    startScreen.setVisible(true);
	    startScreen.setSize(300, 300);
	    startScreen.setTitle("Pokemon");
	    
	    JPanel startOptions = new JPanel(new FlowLayout());
	    startOptions.setSize(100, 100);
	    JButton gameMode1 = new JButton("Catch Em' All");
	    JButton gameMode2 = new JButton("Maze Escape");
	    
	    startOptions.add(gameMode1);
	    startOptions.add(gameMode2);
	    
	    startScreen.add(startOptions, BorderLayout.CENTER);
	    
	}

	private void registerListeners() {
		//TODO add necessary Listeners

	}

}
