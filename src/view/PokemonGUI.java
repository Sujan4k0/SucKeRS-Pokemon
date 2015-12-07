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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Image;
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

import model.GameModel.CEAGame;
import model.GameModel.GameMode;
import model.GameModel.MazeGame;
import model.ItemModel.Item;
import model.PokemonModel.Pokemon;
import model.TrainerModel.TrainerAction;
import soundplayer.SoundPlayer;

public class PokemonGUI {

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
    private SoundPlayer soundPlayer;
    private JTextArea battleMessages;
    private boolean battleJustEnded;
    private Pokedex dex;
    private boolean teleportMessageShown;

    /*---------------------------------------------------------------------
    |  Method name:    [PokemonGUI]
    |  Purpose:        [Constructor]
    *---------------------------------------------------------------------*/
    public PokemonGUI() {

        maze = false;
        catchEmAll = false;
        teleportMessageShown = false;
        soundPlayer = new SoundPlayer();
        startFrame();
    }

    /*---------------------------------------------------------------------
    |  Method name:    [startFrame]
    |  Purpose:        [Creates and displays the starting window for
    |                   the user. Here they get to pick which mode they
    |                   want to play.]
    *---------------------------------------------------------------------*/
    private void startFrame() {
        
        soundPlayer.playSound("./sounds/opening_song.wav");

        // create the basic frame
        startScreen = new JFrame();
        startScreen.setResizable(false);
        startScreen.setSize(600, 300);
        startScreen.setTitle("Pokemon");
        startScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // JPanel set up to show the "Pokemon" logo on the frame
        JPanel logoHeader = new JPanel();
        logoHeader.setBackground(Color.BLACK);
        Image logo = null;
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
        
        soundPlayer.stopSound();

        // create the basic frame
        mapView = new JFrame();
        mapView.setResizable(false);
        mapView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // ask to save instead
        mapView.addWindowListener(new CloseGameListener()); // when close attempted, ask to save

        // add the map (JPanel) from the GameMode to the frame
        mode.getMap().setBorder(BorderFactory.createLineBorder(new Color(244, 220, 38), 5));
        mapView.add(mode.getMap(), BorderLayout.WEST);
        mode.getMap().requestFocusInWindow();
        mode.getMap().addKeyListener(new GameWon()); // GUI will check the status of the game when the player moves

        ArrayList<String> items = new ArrayList<String>();
        items.add("TestPotion1");
        items.add("TestPotion2");
        
        // create list of pokemon
        trainerPokemon = new JComboBox<Object>();
        updatePokemonList();
        
        // create list of items
        trainerItems = new JComboBox<Object>();  
        updateItemsList();
        
        // make lists non-focusable
        trainerItems.setFocusable(false);
        trainerPokemon.setFocusable(false);

        // set up the side user panel for inventory
        JPanel userOptions = new JPanel(new BorderLayout());

        JPanel inventory = new JPanel(new GridLayout(2, 2));
        inventory.setBackground(Color.BLACK);

        trainerCheck = new JCheckBox("Use on trainer");
        trainerCheck.setSelected(true);
        trainerCheck.setFocusable(false);
        trainerCheck.setForeground(Color.WHITE);
        pokemonCheck = new JCheckBox("Use on Selected Pokemon");
        pokemonCheck.setFocusable(false);
        pokemonCheck.setForeground(Color.WHITE);
       
        // add checks to a group so only one can be chosen
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(trainerCheck);
        buttonGroup.add(pokemonCheck);

        // add checks and lists to inventory panel
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
        Image trainerFancy = null;
        try {
            trainerFancy = ImageIO.read(new File("./images/GenericTrainerTest.png"));
            trainerLabel = new JLabel(new ImageIcon(trainerFancy));
            trainerDisplay.add(trainerLabel, BorderLayout.NORTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // steps progress bar
        steps = new JProgressBar(0, mode.getTrainer().getSteps());
        steps.setValue(mode.getTrainer().getSteps()); // will need to replace this with steps
        steps.setStringPainted(true);
        steps.setString((mode.getTrainer().getSteps()) + " steps remaining");
        trainerDisplay.add(steps, BorderLayout.SOUTH);
        userOptions.add(trainerDisplay, BorderLayout.NORTH);

        // three user buttons
        JPanel userButtons = new JPanel(new GridLayout(4, 1));
        
        // create the buttons
        JButton save = new JButton("Save Game");
        JButton use = new JButton("Use Item");
        JButton pokedex = new JButton("Pokedex");
        JButton forfeit = new JButton("Forfeit");
        
        // all buttons are non-focusable
        save.setFocusable(false);
        use.setFocusable(false);
        forfeit.setFocusable(false);
        pokedex.setFocusable(false);
        
        // add action listeners to the buttons for function
        use.addActionListener(new ItemUser());
        save.addActionListener(new GameSaver());
        forfeit.addActionListener(new Forfeiter());
        pokedex.addActionListener(new PokedexCreator());
        
        // make the buttons pretty
        save.setBackground(new Color(102, 178, 255));
        use.setBackground(new Color(51, 255, 153));
        forfeit.setBackground(new Color(255, 51, 51));
        pokedex.setBackground(new Color(181,74,247));
        
        // add the buttons to the button panel
        userButtons.add(use);
        userButtons.add(save);
        userButtons.add(pokedex);
        userButtons.add(forfeit);
        
        // make button panel pretty
        userButtons.setBackground(Color.BLACK);
        
        // add panel to the options panel
        userOptions.add(userButtons, BorderLayout.SOUTH);

        // make user options pretty and non-focusable
        userOptions.setBorder(BorderFactory.createLineBorder(new Color(244, 220, 38), 5));
        userOptions.setFocusable(false);

        mapView.add(userOptions, BorderLayout.EAST); // add options to the frame

        mapView.pack();
        mapView.setLocationRelativeTo(null); // center the map
        mapView.setVisible(true);        
        
        mode.getMap().repaint();
        mapView.repaint();
    }

    /*---------------------------------------------------------------------
    |  Method name:    [endGameDisplay]
    |  Purpose:        [Creates the final stats view for the Trainer when
    |                   the game has ended.]
    *---------------------------------------------------------------------*/
    public void endGameDisplay() {
        
        soundPlayer.loopSound("./sounds/ending_song.wav");

        mapView.dispose(); // get rid of the game

        // new frame to display all the player stats
        JFrame endStats = new JFrame();
        endStats.setResizable(false);
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
        Image greatBall = null;
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

        mapView.setVisible(false); // hide map
        
        // set up new frame for the battle
        encounterFrame = new JFrame();
        encounterFrame.setResizable(false);
        encounterFrame.setTitle("Battle");
        encounterFrame.setVisible(true);
        encounterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // game will shut down
        encounterFrame.add(mode.getEncounterPanel(), BorderLayout.CENTER); // get panel from mode
        mode.getEncounterPanel().setBorder(BorderFactory.createLineBorder(new Color(76,176,159), 5)); // make it pretty
        encounterFrame.revalidate();

        JPanel battleActions = new JPanel(new BorderLayout()); // panel to hold messages and actions 
        
        // panel for the trainer controls
        JPanel trainerControls = new JPanel(new GridLayout(2, 2)); // four buttons possible
        trainerControls.setBackground(new Color(76,176,159)); // make pretty
        trainerControls.setBorder(BorderFactory.createLineBorder(new Color(163, 116, 179), 5)); // make pretty

        // make four buttons for the user battle controls
        JButton throwRock = new JButton("Throw Rock");
        JButton throwBait = new JButton("Throw Bait");
        JButton runAway = new JButton("Run");
        JButton throwBall = new JButton("Throw Pokeball");

        // add action listener for all buttons
        throwRock.addActionListener(new TrainerActionButtons());
        throwBait.addActionListener(new TrainerActionButtons());
        runAway.addActionListener(new TrainerActionButtons());
        throwBall.addActionListener(new TrainerActionButtons());
        
        // make buttons pretty
        throwRock.setBackground(new Color(255, 199, 229));
        throwBait.setBackground(new Color (232, 170, 0));
        runAway.setBackground(new Color(16, 160, 222));
        throwBall.setBackground(new Color(54, 191, 27));

        // add the buttons to the panel of buttons
        trainerControls.add(throwRock);
        trainerControls.add(throwBait);
        trainerControls.add(runAway);
        trainerControls.add(throwBall);

        battleActions.add(trainerControls, BorderLayout.CENTER);
        
        // battle messages appear here
        JPanel battleDialog = new JPanel(new BorderLayout());
        battleDialog.setBorder(BorderFactory.createLineBorder(new Color(163, 116, 179), 5)); // make pretty
        battleMessages = new JTextArea();
        battleMessages.setText(mode.getBattleMessage());
        battleMessages.setEditable(false);
        battleMessages.setRows(5);
        battleMessages.setFont(new Font("Futura", Font.PLAIN, 15)); // fun looking font
        battleDialog.add(battleMessages);
        
        battleActions.add(battleDialog, BorderLayout.WEST); // add the dialog to the actions panel
        
        encounterFrame.add(battleActions, BorderLayout.SOUTH); // add the actions panel to the frame
        
        encounterFrame.setSize(800, 550); // good looking size
        encounterFrame.setLocationRelativeTo(null); // center the frame
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

        else if (catchEmAll) { // we're playing catch 'em all, make a CEA save

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
    |  Method name:    [updatePokemonList]
    |  Purpose:        [Updates the JComboBox of caught Pokemon for the
    |                   GUI's display.]
    *---------------------------------------------------------------------*/
    private void updatePokemonList() {
        
        trainerPokemon.removeAllItems(); // remove all items from the JComboBox first    
        for (int p = 0; p < mode.getTrainer().getPokemon().size(); p++) {
        
            // add the String for the Pokemon and its rarity
            Pokemon mon = mode.getTrainer().getPokemon().get(p);
            trainerPokemon.addItem(mon.getName() + " " + mon.rarityString());
        }    
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [updateItemsList]
    |  Purpose:        [Updates the JComboBox of gathered items for the
    |                   GUI's display]
    *---------------------------------------------------------------------*/
    private void updateItemsList() {
        
        trainerItems.removeAllItems(); // remove all items from the JCombobox first
        for (String s : mode.getTrainer().getItemQuantities().keySet()) {
            
            // add the String for the item and quantity associated with it
            trainerItems.addItem(s + " " + mode.getTrainer().getItemQuantities().get(s));
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
            maze = false; // not playing maze
            boolean save = false; // currently have not loaded from a save

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

                        mode.loadImages();
                        mode.getMap().setFocusable(true);
                        save = true; // we've loaded from a save
                        
                    } catch (Exception e1) { // indicate that the save doesn't work (should never happen)
                        
                        JOptionPane.showMessageDialog(null, "The save is corrupted, please start a new game");
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

            if (!save) { // we did not load a save, show the starting dialog
                
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
                info.setEditable(false); // dialog should not be editable
                info.setFont(new Font("Futura", Font.PLAIN, 15)); // fun looking font
                dialog.add(info); // add the text to the dialog box
                dialog.setSize(500, 300); // made a little bigger for Windows machines (?)
                dialog.setLocationRelativeTo(null); // show at center of screen
                dialog.setVisible(true); // show it
            }
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
            catchEmAll = false; // not playing catchEmAll
            boolean save = false; // we have not loaded from a save

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
                        
                        mode.loadImages(); // load all the images (not persistent)
                        mode.getMap().setFocusable(true);
                        save = true; // we have loaded from a save

                    } catch (Exception e1) {// Indicate that the save doesn't work (should never happen)
                        
                        JOptionPane.showMessageDialog(null, "The save is corrupted, please start a new game");
                    }
                }

                else { // getting the was not chosen

                    mode = new MazeGame(new Random()); // no selected, create a new game
                }
            }

            else { // no save file exists, just make a new game

                mode = new MazeGame(new Random()); // no save existed, create a new game

            }
 
            startScreen.setVisible(false); // hide start screen
            mapFrame(); // start the view of the map and game

            if (!save) { // we didn't load from a save, so show new game introductory dialog
                
                // pop up dialog explaining this game mode
                JDialog dialog = new JDialog(mapView, "Welcome to the Maze");
                JTextArea info = new JTextArea();
                info.append("So you've chosen the maze? Daunting.\n"
                        + "So obviously, you gotta make it to the end of the maze. \n"
                        + "The end is denoted by an ice tile, but wait...\n"
                        + "you're a bit tired and only have 500 steps left in you.\n"
                        + "Catching some loyal Pokemon may help push you along the way.\n"
                        + "There are also some goodies throughout the maze that may\n" + "help you get out of here!");
                info.setEditable(false); // dialog should not be editable
                info.setFont(new Font("Futura", Font.PLAIN, 15)); // fun looking font
                dialog.add(info); // add the text to the dialog box
                dialog.setSize(500, 300); // size set a little bigger for Windows machines (?)
                dialog.setLocationRelativeTo(null); // put at center of screen
                dialog.setVisible(true); // show it
            }
        }
    }

    /*---------------------------------------------------------------------
    |  Class name:     [ItemUser]
    |  Purpose:        [When user selects to use an item do this]
    *---------------------------------------------------------------------*/
    private class ItemUser implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            // get the String of the item selected in the JComboBox
            String item = trainerItems.getSelectedItem().toString();
            
            boolean found = false; // we have not found the index of the quantity
            int i = 0; // index of current character
            
            while (!found) {
                
                if (i >= item.length()) { // no number found, issue with hash set
                    
                    break;
                }
                
                // found the quantity
                if (Character.isDigit(item.charAt(i))) {
                    
                    found = true;
                }
                else { // keep looking for a digit
                    
                    i++;
                }
            }
            
            item = item.substring(0, i - 1); // make a substring from the start to before the numbers
            Item use = Item.getItemByName(item); // get the item object to be used

            if (trainerCheck.isSelected()) { // user wants to use the item on the trainer
                                
                if (use.isForTrainer()) { // item is usable on a trainer
                    
                    mode.useItem(use);
                    updateItemsList(); // reflect on GUI that item was used
                }
                
                else { // item is only for a Pokemon, show error

                    JOptionPane.showMessageDialog(null, "This item is unusable on a trainer.");                    
                }
            }
            
            else { // user wants to use the item on a Pokemon
                
                if (use.isForPokemon()) { // item is usable on a pokemon
                    
                    if (mode.getTrainer().getPokemon().isEmpty()) {
                        
                        JOptionPane.showMessageDialog(null, "Brah, what are you trying to do? You have no goddamn Pokemon!"); 
                    }
                    
                    else {
                        
                        String pokemon = trainerPokemon.getSelectedItem().toString();
                        pokemon = pokemon.substring(0, pokemon.indexOf(' ')); // no Pokemon name have space, can just take index
                        updateItemsList(); // reflect on GUI that item was used
                        
                        mode.useItemOnPokemon(use, pokemon);
                    
                    }
                }
                
                else { // item is only usable on trainer, show error
                    
                    JOptionPane.showMessageDialog(null, "This item is unusable on a pokemon.");                    
                }
            }
            
            steps.setValue(mode.getTrainer().getSteps());
            steps.setString((mode.getTrainer().getSteps()) + " steps remaining");
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

            mode.forfeitGame(); // notify mode that the game is over
            endGameDisplay(); // show the end game stats
        }
    }

    /*---------------------------------------------------------------------
    |  Method name:    [PokedexCreator]
    |  Purpose:        [Shows the Pokedex when selected]
    *---------------------------------------------------------------------*/
    private class PokedexCreator implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            dex = new Pokedex(mode.getTrainer());
            mapView.setVisible(false); // hide the map
            dex.addWindowListener(new PokedexCloser());
        }   
    }
    
    /*---------------------------------------------------------------------
    |  Class name:     [PokedexCloser]
    |  Purpose:        [Closing function of Pokedex frame]
    *---------------------------------------------------------------------*/
    private class PokedexCloser implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
            
        }

        @Override
        public void windowClosing(WindowEvent e) {

            dex.dispose(); // dispose of the Pokedex
            mapView.setVisible(true); // show the map again
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
            
            System.exit(0);     
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

            startScreen.setVisible(true); // show the start screen again
            soundPlayer.stopSound(); // stop end game sounds
            soundPlayer.loopSound("./sounds/opening_song.wav"); // play start sounds
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

            if (mode.trainerInBattle()) {
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
            }
            
            battleMessages.setText(mode.getBattleMessage());
            
            // previous action ended the battle
            if (!mode.trainerInBattle()) {
                
                JOptionPane.showMessageDialog(null, mode.getBattleMessage(), "Battle Over", JOptionPane.INFORMATION_MESSAGE);
                
                encounterFrame.dispose();
                mapView.setVisible(true);
                
                updatePokemonList();
                updateItemsList();
                
            if (!teleportMessageShown && catchEmAll) {
                
                    if (((CEAGame) mode).inLastPart()) {
                      
                        teleportMessageShown = true;
                        JOptionPane.showMessageDialog(null, "You've caught all the Pokemon dude. And holy shit, there's a teleporter in your inventory. Shaka brah!");  
                    
                    }

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

            if (!mode.isGameActive()) { // game is over

                soundPlayer.loopSound("./sounds/ending_song.wav"); // play end song
                endGameDisplay(); // show end game display
            }

            else if (mode.trainerInBattle()) { // entered battle

                battleFrame(); // show battle frame
            }
            
            else { // update everything that should be updated

                // steps update
                steps.setValue(mode.getTrainer().getSteps());
                steps.setString((mode.getTrainer().getSteps()) + " steps remaining");
                
                // inventory updates
                updatePokemonList();
                updateItemsList();
                
                mapView.revalidate();
                
                if (mode.gameAlert()) {
                    
                    JOptionPane.showMessageDialog(null, mode.getNotification(), "HEY TRAINER PERSON!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }
    }
}
