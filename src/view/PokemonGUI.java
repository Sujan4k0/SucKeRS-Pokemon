package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.GameMode;
import controller.MazeGame;

public class PokemonGUI {

    private JFrame startScreen;
    private GameMode mode;
    private JFrame mapView;
    private boolean maze;
    private boolean catchEmAll;

    public static void main(String[] args) {

        new PokemonGUI();
    }

    public PokemonGUI() {

        maze = false;
        catchEmAll = false;
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
    }

    private void mapFrame() {

        mapView = new JFrame();
        mapView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mapView.addWindowListener(new CloseGameListener());
        mapView.setVisible(true);
        mapView.add(mode.getMap(), BorderLayout.CENTER);
        mode.getMap().addKeyListener(new GameWon());
        mapView.pack();
    }

    private class GameWon implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub

            if (!mode.isGameActive()) {

                String endMessage = mode.getEndMessage();
                System.out.println(endMessage);

            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }
    }

    private class GameMode1Selected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            catchEmAll = true;
            
            if (new File("PokemonCEASave").isFile()) {

                // user says no, wants to continue from a previous Jukebox save 
                int getSave = JOptionPane.showConfirmDialog(null, "Would you like to load from a save?", null,
                        JOptionPane.YES_NO_OPTION);

                if (getSave == JOptionPane.YES_OPTION) {

                    try {
                        FileInputStream fis = new FileInputStream("PokemonCEASave"); // jukebox persistance file 
                        ObjectInputStream ois = new ObjectInputStream(fis); // file stream contains jukeboxfile 
                  //      mode = (CEAGame) ois.readObject(); // get a jukebox object 
                        ois.close(); // close object stream
                        fis.close(); // close file stream

                    } catch (Exception e1) {// default model
                    }
                }

                else {

                    mode = new MazeGame(new Random());
                }
            }

            else {

                mode = new MazeGame(new Random());
            }

            // communicate with GameMode
            //      mode = new CEAGame(MapType.CEA);      
            startScreen.setVisible(false);
            mapFrame();

            JDialog dialog = new JDialog(mapView, "Gotta Catch 'Em All");
            JTextArea info = new JTextArea();
            info.append("Hey there noob trainer, you wanna be a master!?.\n"
                    + "We've been hearing about some rumors in the area. \n"
                    + "There have been some sightings of some beast Pokemon.\n"
                    + "He seems to appear when lots of Pokemon have been caught.\n"
                    + "Maybe if you catch all the 6 common and 4 uncommon in the\n" + "area he'll appear.\n");
            info.setEditable(false);
            info.setFont(new Font("Futura", Font.PLAIN, 15));
            dialog.add(info);
            dialog.setSize(500, 150);
            dialog.setVisible(true);
        }
    }

    private class GameMode2Selected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            maze = true;

            if (new File("PokemonMazeSave").isFile()) {

                // user says no, wants to continue from a previous Jukebox save 
                int getSave = JOptionPane.showConfirmDialog(null, "Would you like to load from a save?", null,
                        JOptionPane.YES_NO_OPTION);

                if (getSave == JOptionPane.YES_OPTION) {

                    try {
                        FileInputStream fis = new FileInputStream("PokemonMazeSave"); // jukebox persistance file 
                        ObjectInputStream ois = new ObjectInputStream(fis); // file stream contains jukeboxfile 
                        mode = (MazeGame) ois.readObject(); // get a jukebox object 
                        ois.close(); // close object stream
                        fis.close(); // close file stream

                    } catch (Exception e1) {// default model
                    }
                }

                else {

                    mode = new MazeGame(new Random());
                }
            }

            else {

                mode = new MazeGame(new Random());

            }

            startScreen.setVisible(false);
            mapFrame();

            JDialog dialog = new JDialog(mapView, "Welcome to the Maze");
            JTextArea info = new JTextArea();
            info.append("So you've chosen the maze? Daunting.\n"
                    + "So obviously, you gotta make it to the end of the maze. \n"
                    + "The end is denoted by an ice tile, but wait...\n"
                    + "you're a bit tired and only have 500 steps left in you.\n"
                    + "Catching some loyal Pokemon may help push you along the way,\n"
                    + "and there are also some goodies throughout the maze that may\n" + "help you get out of here!");
            info.setEditable(false);
            info.setFont(new Font("Futura", Font.PLAIN, 15));
            dialog.add(info);
            dialog.setSize(500, 150);
            dialog.setVisible(true);
        }
    }

    private void saveState() {

        if (maze) {
            try {
                FileOutputStream fos = new FileOutputStream("PokemonMazeSave");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(mode); // save the object
                fos.close(); // close file stream
                oos.close(); // close object stream
            } catch (Exception e1) {

                e1.printStackTrace();
            }
        }

        if (catchEmAll) {

            try {
                FileOutputStream fos = new FileOutputStream("PokemonCEASave");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(mode); // save the object
                fos.close(); // close file stream
                oos.close(); // close object stream
            } catch (Exception e1) {

                e1.printStackTrace();
            }
        }
    }

    private class CloseGameListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub
            
         // option pane for saving state
            int reply = JOptionPane.showConfirmDialog(null, "Do you want to save?", null, JOptionPane.YES_NO_CANCEL_OPTION);

            if (reply == JOptionPane.YES_OPTION) { // yes was selected

                saveState();
            }

            else if (reply == JOptionPane.CANCEL_OPTION) {

                // does nothing, goes back to yes or no
            }

            else if (reply == JOptionPane.NO_OPTION) {

                System.exit(0); // quit program don't do anything
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowIconified(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowActivated(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // TODO Auto-generated method stub
            
        } 
    }
    
    private void registerListeners() {

    }
}
