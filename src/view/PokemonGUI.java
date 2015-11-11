package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.GameMode;

public class PokemonGUI extends JFrame {
    
    private JFrame startScreen;
    private JPanel map;
    private GameMode mode;
    
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
	    startScreen.setSize(600, 300);
	    startScreen.setTitle("Pokemon");
	    
	    JPanel logoHeader = new JPanel();
	    BufferedImage logo = null;
	    try {
            logo = ImageIO.read(new File("./images/PokemonLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	    JLabel logoLabel = new JLabel(new ImageIcon(logo));
	    logoHeader.add(logoLabel);
	    startScreen.add(logoHeader, BorderLayout.NORTH);
	    
	    JPanel startOptions = new JPanel(new FlowLayout());
	    startOptions.setSize(100, 100);
	    JButton gameMode1 = new JButton("Catch Em' All");
	    JButton gameMode2 = new JButton("Maze Escape");	    
	    startOptions.add(gameMode1);
	    startOptions.add(gameMode2);   
	    startScreen.add(startOptions, BorderLayout.CENTER);    
	    
	    gameMode1.addActionListener(new GameMode1Selected());
	    gameMode2.addActionListener(new GameMode2Selected());
	    
//	    JPanel descriptors = new JPanel();
//	    JButton descriptions = new JButton("Game Mode Descriptions");
//	    descriptors.add(descriptions);
//	    startScreen.add(descriptors, BorderLayout.SOUTH);	    
	}
	
	private void mapFrame() {
	    
	    this.setVisible(true);
	    this.setSize(500, 500);
	    this.add(mode.getMap(), BorderLayout.CENTER);
	}
	
	private class GameMode1Selected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            // communicate with GameMode
      //      mode = new GameMode(MapType.CEA);      
            startScreen.setVisible(false);    
            mapFrame();
        }   
	}
	
	private class GameMode2Selected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            // communicate with GameMode
    //        mode = new GameMode(MapType.MAZE);
            
            startScreen.setVisible(false);
            
        }   
    }
	
	private void registerListeners() {

	}

}
