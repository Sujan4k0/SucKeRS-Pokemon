/*=========================================================================== 
 | Assignment: FINAL PROJECT: [PokemonGUI] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |             [Keith Smith  (browningsmith@email.arizona.edu)]
 |             [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: Controls the graphical view of the game.
 *==========================================================================*/

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import controller.CEAGame;
import controller.GameMode;
import controller.MazeGame;
import javafx.scene.control.ProgressBar;
import model.PokemonModel.Pokemon;
import model.TrainerModel.TrainerAction;

public class PokemonGUI {

    private static final String encoutnerFrame = null;
    private JFrame startScreen;
    private JFrame encounterFrame;
    private GameMode mode;
    private JFrame mapView;
    private boolean maze;
    private boolean catchEmAll;
    private JComboBox<Object> trainerItems;
    private JComboBox<Object> trainerPokemon;
    private JCheckBox trainerCheck;
    private JCheckBox pokemonCheck;
    private JProgressBar steps;

    public static void main(String[] args) {

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }

        new PokemonGUI();
    }

    /*---------------------------------------------------------------------
    |  Method name:    [PokemonGUI]
    |  Purpose:        [Constructor]
    *---------------------------------------------------------------------*/
    public PokemonGUI() {

        maze = false;
        catchEmAll = false;
        startFrame();
    }

    /*---------------------------------------------------------------------
    |  Method name:    [startFrame]
    |  Purpose:        [Creates and displays the starting window for
    |                   the user. Here they get to pick which mode they
    |                   want to play.]
    *---------------------------------------------------------------------*/
    private void startFrame() {

        // create the basic frame
        startScreen = new JFrame();
        startScreen.setSize(600, 300);
        startScreen.setTitle("Pokemon");
        startScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JPanel set up to show the "Pokemon" logo on the frame
        JPanel logoHeader = new JPanel();
        logoHeader.setBackground(Color.BLACK);
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(new File("./images/PokemonLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        logoHeader.add(logoLabel);
        startScreen.add(logoHeader, BorderLayout.NORTH);

        // Panel with the buttons for the game types
        JPanel startOptions = new JPanel(new FlowLayout());
        startOptions.setBackground(Color.BLACK);
        startOptions.setSize(100, 100);
        JButton gameMode1 = new JButton("Catch Em' All");
        JButton gameMode2 = new JButton("Maze Escape");
        startOptions.add(gameMode1);
        startOptions.add(gameMode2);

        // action listeners for each game selection button
        gameMode1.addActionListener(new CatchEmAllSelected());
        gameMode2.addActionListener(new MazeSelected());
        startScreen.add(startOptions, BorderLayout.CENTER);

        startScreen.setLocationRelativeTo(null);
        startScreen.setVisible(true);
    }

    /*---------------------------------------------------------------------
    |  Method name:    [mapFrame]
    |  Purpose:        [Once a game is started, this is the main display
    |                   in view. It primarily holds the map in addition to
    |                   player options like inventory.]
    *---------------------------------------------------------------------*/
    private void mapFrame() {

        // create the basic frame
        mapView = new JFrame();
        mapView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // ask to save instead
        mapView.addWindowListener(new CloseGameListener()); // when close attempted, ask to save

        // add the map (JPanel) from the GameMode to the frame
        mode.getMap().setBorder(BorderFactory.createLineBorder(new Color(244, 220, 38), 5));
        mapView.add(mode.getMap(), BorderLayout.WEST);
        mode.getMap().addKeyListener(new GameWon()); // GUI will check the status of the game when the player moves

        ArrayList<String> items = new ArrayList<String>();
        items.add("TestPotion1");
        items.add("TestPotion2");
        
        trainerPokemon = new JComboBox<Object>();
        for (int p = 0; p < mode.getTrainer().getPokemon().size(); p++) {
        
            trainerPokemon.addItem(mode.getTrainer().getPokemon().get(p).getName());
        }

        trainerItems = new JComboBox<Object>(items.toArray());
        trainerItems.setFocusable(false);
        trainerPokemon.setFocusable(false);

        // set up the side user panel for inventory
        JPanel userOptions = new JPanel(new BorderLayout());

        JPanel inventory = new JPanel(new GridLayout(2, 2));
        inventory.setBackground(Color.BLACK);

        trainerCheck = new JCheckBox("Use on trainer");
        trainerCheck.setFocusable(false);
        trainerCheck.setForeground(Color.WHITE);
        pokemonCheck = new JCheckBox("Use on Selected Pokemon");
        pokemonCheck.setFocusable(false);
        pokemonCheck.setForeground(Color.WHITE);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(trainerCheck);
        buttonGroup.add(pokemonCheck);

        inventory.add(trainerCheck);
        inventory.add(pokemonCheck);
        inventory.add(trainerPokemon);
        inventory.add(trainerItems);
        userOptions.add(inventory, BorderLayout.CENTER);
        userOptions.setBackground(Color.BLACK);

        // image of the trainer to put on the side
        JPanel trainerDisplay = new JPanel(new BorderLayout());
        trainerDisplay.setBackground(Color.BLACK);
        JLabel trainerLabel = new JLabel();
        BufferedImage trainerFancy = null;
        try {
            trainerFancy = ImageIO.read(new File("./images/GenericTrainerTest.png"));
            trainerLabel = new JLabel(new ImageIcon(trainerFancy));
            trainerDisplay.add(trainerLabel, BorderLayout.NORTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        steps = new JProgressBar(0, mode.getTrainer().getSteps());
        steps.setValue(mode.getTrainer().getSteps()); // will need to replace this with steps
        steps.setStringPainted(true);
        steps.setString((steps.getValue()) + " steps remaining");
        trainerDisplay.add(steps, BorderLayout.SOUTH);
        userOptions.add(trainerDisplay, BorderLayout.NORTH);

        JPanel userButtons = new JPanel(new GridLayout(3, 1));
        JButton save = new JButton("Save Game");
        save.setFocusable(false);
        JButton use = new JButton("Use Item");
        use.setFocusable(false);
        JButton forfeit = new JButton("Forfeit");
        forfeit.setFocusable(false);
        save.setOpaque(true);
        save.setBackground(new Color(102, 178, 255));
        use.setOpaque(true);
        use.setBackground(new Color(51, 255, 153));
        forfeit.setOpaque(true);
        forfeit.setBackground(new Color(255, 51, 51));
        userButtons.add(use);
        userButtons.add(save);
        userButtons.add(forfeit);
        userButtons.setBackground(Color.BLACK);
        userOptions.add(userButtons, BorderLayout.SOUTH);

        userOptions.setBorder(BorderFactory.createLineBorder(new Color(244, 220, 38), 5));
        userOptions.setFocusable(false);

        mapView.add(userOptions, BorderLayout.EAST);

        mapView.pack();
        mapView.setLocationRelativeTo(null);
        mapView.setVisible(true);

        use.addActionListener(new ItemUser());
        save.addActionListener(new GameSaver());
        forfeit.addActionListener(new Forfeiter());
    }

    /*---------------------------------------------------------------------
    |  Method name:    [endGameDisplay]
    |  Purpose:        [Creates the final stats view for the Trainer when
    |                   the game has ended.]
    *---------------------------------------------------------------------*/
    public void endGameDisplay() {

        mapView.dispose(); // get rid of the game

        // new frame to display all the player stats
        JFrame endStats = new JFrame();
        endStats.setSize(500, 500);
        endStats.setTitle("Game Over");
        endStats.setVisible(true);

        // left Panel with image and TRAINER STATS title
        JPanel ballImage = new JPanel(new BorderLayout());
        JLabel header = new JLabel("   TRAINER STATS");
        header.setFont(new Font("Chalkboard", Font.BOLD, 30));
        header.setForeground(Color.WHITE);
        ballImage.add(header, BorderLayout.NORTH);
        ballImage.setBorder(BorderFactory.createLineBorder(new Color(233, 41, 41), 10));
        BufferedImage greatBall = null;
        try {
            greatBall = ImageIO.read(new File("./images/Pokeball.png"));
            JLabel ballLabel = new JLabel(new ImageIcon(greatBall));
            ballImage.add(ballLabel, BorderLayout.SOUTH);
            ballImage.setBackground(Color.BLACK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        endStats.add(ballImage, BorderLayout.WEST);

        // panel will hold end message and all stats from the stats panel
        JPanel end = new JPanel(new BorderLayout());
        end.setBackground(Color.BLACK);
        end.setBorder(BorderFactory.createLineBorder(new Color(233, 41, 41), 10));

        JLabel endMessage = new JLabel(" " + mode.getEndMessage().toUpperCase() + " \n");
        endMessage.setForeground(Color.WHITE);
        endMessage.setFont(new Font("Chalkboard", Font.BOLD, 30));
        end.add(endMessage, BorderLayout.NORTH);

        // panel for all the stats
        JPanel stats = new JPanel(new GridLayout(2, 2));
        stats.setBackground(Color.WHITE);
        stats.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));

        // pokemon caught
        JLabel pokemonCaught = new JLabel("   Pokemon Caught: ");
        JLabel caughtNum = new JLabel("" + mode.getTrainer().getPokemon().size());
        pokemonCaught.setForeground(Color.BLACK);
        pokemonCaught.setFont(new Font("Chalkboard", Font.PLAIN, 20));
        caughtNum.setForeground(new Color(244, 220, 38));
        caughtNum.setFont(new Font("Chalkboard", Font.BOLD, 40));

        // remaining steps
        JLabel stepsRemaining = new JLabel("   Steps Left: ");
        JLabel stepsNum = new JLabel("" + mode.getTrainer().getSteps());
        stepsRemaining.setFont(new Font("Chalkboard", Font.PLAIN, 20));
        stepsRemaining.setForeground(Color.BLACK);
        stepsNum.setForeground(new Color(244, 220, 38));
        stepsNum.setFont(new Font("Chalkboard", Font.BOLD, 40));

        // add stats labels and fields to stats panel
        stats.add(pokemonCaught);
        stats.add(caughtNum);
        stats.add(stepsRemaining);
        stats.add(stepsNum);

        end.add(stats, BorderLayout.CENTER); // add the panel to the entire end right pane            

        // add it all to the frame
        endStats.add(end, BorderLayout.EAST);
        endStats.pack();

        endStats.setLocationRelativeTo(null);
        endStats.addWindowListener(new CloseStatsListener());
    }

    /*---------------------------------------------------------------------
    |  Method name:    [battleFrame]
    |  Purpose:        [Creates the frame for a Trainer encounter with a
    |                   Pokemon.]
    *---------------------------------------------------------------------*/
    private void battleFrame() {

        mapView.setVisible(false);
        encounterFrame = new JFrame();
        encounterFrame.setTitle("Battle");
        encounterFrame.setVisible(true);
        encounterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        encounterFrame.add(mode.getEncounterPanel(), BorderLayout.CENTER);
        mode.getEncounterPanel().setBorder(BorderFactory.createLineBorder(new Color(76,176,159), 5));
        encounterFrame.revalidate();

        JPanel trainerControls = new JPanel(new GridLayout(2, 2));
        trainerControls.setBackground(new Color(76,176,159));
        trainerControls.setBorder(BorderFactory.createLineBorder(new Color(163, 116, 179), 5));

        JButton throwRock = new JButton("Throw Rock");
        JButton throwBait = new JButton("Throw Bait");
        JButton runAway = new JButton("Run");
        JButton throwBall = new JButton("Throw Pokeball");

        throwRock.addActionListener(new TrainerActionButtons());
        throwBait.addActionListener(new TrainerActionButtons());
        runAway.addActionListener(new TrainerActionButtons());
        throwBall.addActionListener(new TrainerActionButtons());
        
        throwRock.setBackground(new Color(255, 199, 229));
        throwBait.setBackground(new Color (232, 170, 0));
        runAway.setBackground(new Color(16, 160, 222));
        throwBall.setBackground(new Color(54, 191, 27));

        trainerControls.add(throwRock);
        trainerControls.add(throwBait);
        trainerControls.add(runAway);
        trainerControls.add(throwBall);

        encounterFrame.add(trainerControls, BorderLayout.SOUTH);
        
        encounterFrame.setSize(800, 550);
        encounterFrame.setLocationRelativeTo(null);
    }

    /*---------------------------------------------------------------------
    |  Method name:    [saveState]
    |  Purpose:        [Save this instance of the game]
    *---------------------------------------------------------------------*/
    private void saveState() {

        if (maze) { // we're playing Maze, make a maze save 
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

        if (catchEmAll) { // we're playing catch 'em all, make a CEA save

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

    /*---------------------------------------------------------------------
    |  Class name:     [CatchEmAllSelected]
    |  Purpose:        [Sets up the CEA game if the user wants to play this]
    *---------------------------------------------------------------------*/
    private class CatchEmAllSelected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            catchEmAll = true; // we are playing CEA

            if (new File("PokemonCEASave").isFile()) { // if a previous CEA save file exists, ask the user if they want to load

                // options for loading
                int getSave = JOptionPane.showConfirmDialog(null, "Would you like to load from a save?", null,
                        JOptionPane.YES_NO_OPTION);

                if (getSave == JOptionPane.YES_OPTION) { // user wants to load the old save

                    try {
                        FileInputStream fis = new FileInputStream("PokemonCEASave");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        mode = (CEAGame) ois.readObject(); // get a CEAGAME object 
                        ois.close(); // close object stream
                        fis.close(); // close file stream

                    } catch (Exception e1) {
                    }
                }

                else {

                    mode = new CEAGame(new Random()); // no selected, create a new game
                }
            }

            else {

                mode = new CEAGame(new Random()); // no save existed, create a new game
            }

            // hide the start menu
            startScreen.setVisible(false);
            mapFrame(); // set up the game visibility and map

            // explanatory dialog that appears for this game type
            JDialog dialog = new JDialog(mapView, "Gotta Catch 'Em All");
            JTextArea info = new JTextArea();
            info.append("Hey there noob trainer, you wanna be a master!?.\n"
                    + "We've been hearing some strange rumors in the area. \n"
                    + "There have been rare sightings of some beast Pokemon.\n"
                    + "He seems to appear when lots of Pokemon have been caught.\n"
                    + "He must have some problem with captivity. But we're human\n"
                    + "and we don't listen to nature, so I have a genius idea:\n"
                    + "maybe if you catch all the 6 common and 4 uncommon in the\n" + "area he'll appear.\n");
            info.setEditable(false);
            info.setFont(new Font("Futura", Font.PLAIN, 15));
            dialog.add(info);
            dialog.setSize(500, 300);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    /*---------------------------------------------------------------------
    |  Class name:     [MazeSelected]
    |  Purpose:        [Sets up the Maze game if the user wants to play this]
    *---------------------------------------------------------------------*/
    private class MazeSelected implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            maze = true; // we are playing maze

            if (new File("PokemonMazeSave").isFile()) { // if a save exists, ask if they would like to load it

                // options for loading from a save
                int getSave = JOptionPane.showConfirmDialog(null, "Would you like to load from a save?", null,
                        JOptionPane.YES_NO_OPTION);

                if (getSave == JOptionPane.YES_OPTION) { // user wants to load from the save

                    try {
                        FileInputStream fis = new FileInputStream("PokemonMazeSave");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        mode = (MazeGame) ois.readObject(); // get the MazeGame object
                        ois.close(); // close object stream
                        fis.close(); // close file stream

                    } catch (Exception e1) {// default model
                    }
                }

                else {

                    mode = new MazeGame(new Random()); // no selected, create a new game
                }
            }

            else {

                mode = new MazeGame(new Random()); // no save existed, create a new game

            }

            startScreen.setVisible(false); // hide start screen
            mapFrame(); // start the view of the map and game

            // pop up dialog explaining this game mode
            JDialog dialog = new JDialog(mapView, "Welcome to the Maze");
            JTextArea info = new JTextArea();
            info.append("So you've chosen the maze? Daunting.\n"
                    + "So obviously, you gotta make it to the end of the maze. \n"
                    + "The end is denoted by an ice tile, but wait...\n"
                    + "you're a bit tired and only have 500 steps left in you.\n"
                    + "Catching some loyal Pokemon may help push you along the way.\n"
                    + "There are also some goodies throughout the maze that may\n" + "help you get out of here!");
            info.setEditable(false);
            info.setFont(new Font("Futura", Font.PLAIN, 15));
            dialog.add(info);
            dialog.setSize(500, 300);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    /*---------------------------------------------------------------------
    |  Class name:     [ItemUser]
    |  Purpose:        [When user selects to use an item do this]
    *---------------------------------------------------------------------*/
    private class ItemUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // use item here
        }
    }

    /*---------------------------------------------------------------------
    |  Class name:     [GameSaver]
    |  Purpose:        [When user clicks save game do this]
    *---------------------------------------------------------------------*/
    private class GameSaver implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            saveState();
        }
    }

    /*---------------------------------------------------------------------
    |  Class name:     [Forfeiter]
    |  Purpose:        [When user clicks forfeit do this]
    *---------------------------------------------------------------------*/
    private class Forfeiter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            mode.forfeitGame();
            endGameDisplay();
        }
    }

    /*---------------------------------------------------------------------
    |  Class name:     [CloseGameListener]
    |  Purpose:        [Allows the user to save the game if they just
    |                   attempt to close the window.]
    *---------------------------------------------------------------------*/
    private class CloseGameListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowClosing(WindowEvent e) {

            // option pane for saving state
            int reply = JOptionPane.showConfirmDialog(null, "Do you want to save?", null,
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (reply == JOptionPane.YES_OPTION) { // yes was selected

                saveState(); // save the game
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

    /*---------------------------------------------------------------------
    |  Class name:     [CloseStatsListener]
    |  Purpose:        [When the user exits the end game stats the 
    |                   start screen should appear.]
    *---------------------------------------------------------------------*/
    private class CloseStatsListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void windowClosing(WindowEvent e) {
            // TODO Auto-generated method stub

            startScreen.setVisible(true);

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
    
    /*---------------------------------------------------------------------
    |  Class name:     [TrainerActionButtons]
    |  Purpose:        [When in battle mode, these are the controls and
    |                   their corresponding actions.]
    *---------------------------------------------------------------------*/
    private class TrainerActionButtons implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // go through available actions and do the corresponding TrainerAction
            switch (e.getActionCommand()) {

            case "Throw Rock":
                mode.doTrainerAction(TrainerAction.THROW_ROCK);
                break;

            case "Throw Bait":
                mode.doTrainerAction(TrainerAction.THROW_BAIT);
                break;

            case "Throw Pokeball":
                mode.doTrainerAction(TrainerAction.THROW_BALL);
                break;

            case "Run":
                mode.doTrainerAction(TrainerAction.RUN_AWAY);
                break;
            default:
                break;
            }
            
            // previous action ended the battle
            if (!mode.trainerInBattle()) {
                
                encounterFrame.dispose();
                mapView.setVisible(true);
                
                trainerPokemon.removeAllItems(); 
                
                for (int p = 0; p < mode.getTrainer().getPokemon().size(); p++) {
                
                    Pokemon mon = mode.getTrainer().getPokemon().get(p);
                    
                    trainerPokemon.addItem(mon.getName() + " " + mon.rarityString());
                }

                mapView.revalidate();

            }
        }
    }

    /*---------------------------------------------------------------------
    |  Class name:     [GameWon]
    |  Purpose:        [When the player is moving we check the status of
    |                   the game to display a message that it's over.]
    *---------------------------------------------------------------------*/
    private class GameWon implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub

            if (!mode.isGameActive()) {

                endGameDisplay();
            }

            else if (mode.trainerInBattle()) {

                battleFrame();
                System.out.println("Starting battle sequence");
            }

            else {

                steps.setValue(mode.getTrainer().getSteps());
                steps.setString((steps.getValue()) + " steps remaining");
                
                trainerPokemon.removeAllItems();
                
                for (int p = 0; p < mode.getTrainer().getPokemon().size(); p++) {
                
                    Pokemon mon = mode.getTrainer().getPokemon().get(p);
                    
                    trainerPokemon.addItem(mon.getName() + " " + mon.rarityString());
                }

                mapView.revalidate();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }
    }
}
