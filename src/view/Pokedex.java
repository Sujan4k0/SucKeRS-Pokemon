/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Pokedex] 
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
 | Description: Pokedex for the GUI 
 *==========================================================================*/

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.PokemonModel.Pokemon;
import model.TrainerModel.Trainer;

public class Pokedex extends JFrame {
    
    private Trainer trainer; // trainer passed in
    
    private JLabel centerLabel; // center image on left panel
    private JPanel center; // center panel on right side
    private JPanel stats; // pokemon info on right panel
    private JLabel type; // pokemon type
    private JLabel name; // pokemon name
    
    private JList<String> list; // list of pokemon available to view
    private JScrollPane scroll; // holds the list
    private ArrayList<Pokemon> trainerHasOne; // pokemon the trainer has
   
    /*---------------------------------------------------------------------
    |  Method name:    [Pokedex]
    |  Purpose:        [Constructor]
    *---------------------------------------------------------------------*/
    public Pokedex(Trainer trainer) {
        
        // get nimbus look and feel
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
        
        this.trainer = trainer; // hold the trainer
        
        // basic frame set up
        this.setTitle("Pokedex");
        this.setVisible(true);
        
        leftPanel(); // get the left side of the pokedex
        rightPanel(); // get the right side of the pokedex
        hinge(); // get the hinge between each component
        
        // center the frame
        this.setLocationRelativeTo(null);
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [leftPanel]
    |  Purpose:        [Sets up the left display of the Pokedex]
    *---------------------------------------------------------------------*/
    public void leftPanel() {
        
        // main panel that will get added to the frame
        JPanel pokeView = new JPanel(new BorderLayout());
        
        // panels for each section of the pokeView
        JPanel top = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new BorderLayout());
        
        // null placeholders for where images will be
        BufferedImage topOfPokedexPic = null;
        BufferedImage bottomOfPokedexPic = null;
        BufferedImage pokedexIntro = null;
        JLabel topLabel = null;
        JLabel bottomLabel = null;
        centerLabel = null;
        try { // read in all the images and add them appropriately
            
            // top of the Pokedex
            topOfPokedexPic = ImageIO.read(new File("./images/pokedex/pokedexTop.png"));
            topLabel = new JLabel(new ImageIcon(topOfPokedexPic));
            top.add(topLabel, BorderLayout.CENTER);
            top.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
            top.setBackground(Color.BLACK);
           
            // bottom of the Pokedex
            bottomOfPokedexPic = ImageIO.read(new File("./images/pokedex/pokedexBottom.png"));
            bottomLabel = new JLabel(new ImageIcon(bottomOfPokedexPic));
            bottom.add(bottomLabel, BorderLayout.CENTER);
            bottom.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
            bottom.setBackground(Color.BLACK);

            // starting screen display in the center of the pokedex
            pokedexIntro = ImageIO.read(new File("./images/pokedex/pokedexIntro.jpg"));
            centerLabel = new JLabel(new ImageIcon(pokedexIntro));
            center.add(centerLabel, BorderLayout.CENTER);
            center.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
            center.setBackground(Color.BLACK);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // add each section to the pokeview
        pokeView.add(top, BorderLayout.NORTH);
        pokeView.add(bottom, BorderLayout.SOUTH);
        pokeView.add(center, BorderLayout.CENTER);
        pokeView.setBorder(null); // remove panel padding
        
        // add the pokeview and fix the frame's size
        this.add(pokeView, BorderLayout.WEST);
        this.pack();
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [rightPanel]
    |  Purpose:        [Sets up the right panel display of the pokedex]
    *---------------------------------------------------------------------*/
    public void rightPanel() {
        
        // main panel to be added to the frame
        JPanel pokeList = new JPanel(new BorderLayout());
        
        // sub-panels to get added to pokeList
        JPanel top = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        center = new JPanel(new BorderLayout());
        
        // null image placeholders
        BufferedImage topOfPokedexPic = null;
        BufferedImage bottomOfPokedexPic = null;
        JLabel topLabel = null;
        JLabel bottomLabel = null;
        try {
            
            // top of the pokedex
            topOfPokedexPic = ImageIO.read(new File("./images/pokedex/pokedexLeftTop.png"));
            topLabel = new JLabel(new ImageIcon(topOfPokedexPic));
            top.add(topLabel, BorderLayout.CENTER);
            top.setBorder(null);
            top.setBackground(Color.BLACK);
            
            // bottom of the pokedex
            bottomOfPokedexPic = ImageIO.read(new File("./images/pokedex/pokedexLeftBottom.png"));
            bottomLabel = new JLabel(new ImageIcon(bottomOfPokedexPic));
            bottom.add(bottomLabel, BorderLayout.CENTER);
            bottom.setBackground(Color.BLACK);            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        updateList(); // get the list of pokemon
        stats(null); // create empty stats for now, no pokemon selected
        
        // set up stats panel
        stats = new JPanel(new GridLayout(2, 1));
        stats.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        stats.setBackground(Color.WHITE);
        
        // create the labels
        type = new JLabel("Type: ");
        name = new JLabel("Name: ");   
        type.setFont(new Font("Futura", Font.PLAIN, 20)); // fun looking font
        name.setFont(new Font("Futura", Font.PLAIN, 20)); // fun looking font

        // add the labels to stats
        stats.add(type);
        stats.add(name);
        
        // set up center panel
        center.setBackground(Color.BLACK);
        center.add(scroll, BorderLayout.NORTH); // add the list
        center.add(stats, BorderLayout.CENTER); // add the stats
        
        // create and add the select button
        JButton select = new JButton("Select");
        select.addActionListener(new DisplayListener());
        select.setBackground(new Color(12,242,238));
        center.add(select, BorderLayout.SOUTH);
        
        // add the panels to the main pokeList panel
        pokeList.add(top, BorderLayout.NORTH);
        pokeList.add(bottom, BorderLayout.SOUTH);
        pokeList.add(center, BorderLayout.CENTER);
        pokeList.setBorder(null); // remove panel padding
        
        // add the panel to the frame and adjust frame size
        this.add(pokeList, BorderLayout.EAST);
        this.pack();
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [hinge]
    |  Purpose:        [Create the middle hinge between the two Pokedex
    |                   folds]
    *---------------------------------------------------------------------*/
    public void hinge() {
        
        // panel to hold the hinge image
        JPanel hinge = new JPanel();
        
        // null placeholders for the hinge image
        JLabel hingeLabel = null;        
        BufferedImage hingePic = null;
        try {
            hingePic = ImageIO.read(new File("./images/pokedex/pokedexHinge.png"));
            hingeLabel = new JLabel(new ImageIcon(hingePic));
            hinge.add(hingeLabel, BorderLayout.CENTER);
            hinge.setBackground(Color.BLACK);
            hinge.setBorder(null); // remove panel padding
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // add the hinge and adjust the frame size
        this.add(hinge, BorderLayout.CENTER);
        this.pack();
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [updateList]
    |  Purpose:        [Sets up the JList of Pokemon the trainer has caught
    |                   so that they appear as options in the Pokedex]
    *---------------------------------------------------------------------*/
    public void updateList() {
        
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        ArrayList<Pokemon> visiblePokemon = new PokemonDatabase().getAllPokemon();
        trainerHasOne = new ArrayList<Pokemon>(); // holds each pokemon the trainer has
        
        // go through all possible pokemon
        for (int i = 0; i < visiblePokemon.size(); i++) {
            
            // go through trainer's pokemon
            for (int k = 0; k < trainer.getPokemon().size(); k++) {
                
                // trainer has this pokemon
                if (trainer.getPokemon().get(k).getName().equals(visiblePokemon.get(i).getName())) {
                    
                    boolean add = true; // we should add it by default
                    
                    // go through all pokemon that are in the list already
                    for (int m = 0; m < trainerHasOne.size(); m++) {
                        
                        // if the pokemon has already been added to the pokedex, don't add it again
                        if (trainer.getPokemon().get(k).getName().equals(trainerHasOne.get(m).getName())) {
                            
                            add = false;
                        }
                    }
                    
                    if (add) {
                    
                        trainerHasOne.add(visiblePokemon.get(i)); // pokedex has this one now
                        listModel.addElement(visiblePokemon.get(i).getName()); // add it to the list   
                    }
                }
            }
        }
        
        list = new JList<String>(listModel);
        list.setVisibleRowCount(5);
        list.setBackground(Color.green);
        list.setFont(new Font("Futura", Font.PLAIN, 20)); // fun looking font
        
        scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
    }

    /*---------------------------------------------------------------------
    |  Method name:    [stats]
    |  Purpose:        [Updates the stats display of the Pokedex]
    |  Parameters:     [Pokemon that the user is viewing]
    *---------------------------------------------------------------------*/
    public void stats(Pokemon p) {
        
        // the pokemon isn't null
        if (p != null) {
            
            type.setText("TYPE: " + p.getType());
            name.setText("NAME: " + p.getName());
        }
    }
    
    /*---------------------------------------------------------------------
    |  Class name:     [DisplayListener]
    |  Purpose:        [When the user selects a pokemon, update accordingly]
    *---------------------------------------------------------------------*/
    private class DisplayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // get the pokemon from the list
            Pokemon p = trainerHasOne.get(list.getSelectedIndex());
            stats(p); // update stats for this pokemon

            // add the picture for this pokemon on the left panel
            centerLabel.setIcon(new ImageIcon(p.getSprite()[0]));            
        }
    }
}
