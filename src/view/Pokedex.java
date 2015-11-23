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
    
    private JLabel centerLabel;
    private JList<String> list;
    private Trainer trainer;
    private JScrollPane scroll;
    private JPanel stats;
    private ArrayList<Pokemon> trainerHasOne = new ArrayList<Pokemon>();
    private JPanel center;
    private JLabel type;
    private JLabel name;
    
    public Pokedex(Trainer trainer) {
        
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

        
        this.trainer = trainer;
        trainer.addPokemon(new PokemonDatabase().getExeggutor());

        
        this.setTitle("Pokedex");
        this.setVisible(true);
        
        leftPanel();
        rightPanel();
        hinge();
        
        this.setLocationRelativeTo(null);
    }
    
    public void leftPanel() {
        
        JPanel pokeView = new JPanel(new BorderLayout());
        pokeView.setSize(250, 500);
        
        JPanel top = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        JPanel center = new JPanel(new BorderLayout());
        
        BufferedImage topOfPokedexPic = null;
        BufferedImage bottomOfPokedexPic = null;
        BufferedImage pokedexIntro = null;
        JLabel topLabel = null;
        JLabel bottomLabel = null;
        centerLabel = null;
        try {
            topOfPokedexPic = ImageIO.read(new File("./images/pokedex/pokedexTop.png"));
            topLabel = new JLabel(new ImageIcon(topOfPokedexPic));
            top.add(topLabel, BorderLayout.CENTER);
            top.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
            top.setBackground(Color.BLACK);
           
            bottomOfPokedexPic = ImageIO.read(new File("./images/pokedex/pokedexBottom.png"));
            bottomLabel = new JLabel(new ImageIcon(bottomOfPokedexPic));
            bottom.add(bottomLabel, BorderLayout.CENTER);
            bottom.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
            bottom.setBackground(Color.BLACK);

            pokedexIntro = ImageIO.read(new File("./images/pokedex/pokedexIntro.jpg"));
            centerLabel = new JLabel(new ImageIcon(pokedexIntro));
            center.add(centerLabel, BorderLayout.CENTER);
            center.setBorder(BorderFactory.createLineBorder(Color.RED, 10));
            center.setBackground(Color.BLACK);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        pokeView.add(top, BorderLayout.NORTH);
        pokeView.add(bottom, BorderLayout.SOUTH);
        pokeView.add(center, BorderLayout.CENTER);
      //  pokeView.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        pokeView.setBorder(null);
        
        this.add(pokeView, BorderLayout.WEST);
      //  this.setSize(500, 100);
        this.pack();

    }
    
    public void rightPanel() {
        
        JPanel pokeList = new JPanel(new BorderLayout());
        
        JPanel top = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        center = new JPanel(new BorderLayout());
        
        BufferedImage topOfPokedexPic = null;
        BufferedImage bottomOfPokedexPic = null;
        JLabel topLabel = null;
        JLabel bottomLabel = null;
        try {
            topOfPokedexPic = ImageIO.read(new File("./images/pokedex/pokedexLeftTop.png"));
            topLabel = new JLabel(new ImageIcon(topOfPokedexPic));
            top.add(topLabel, BorderLayout.CENTER);
          //  top.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
            top.setBorder(null);
            top.setBackground(Color.BLACK);
            
            bottomOfPokedexPic = ImageIO.read(new File("./images/pokedex/pokedexLeftBottom.png"));
            bottomLabel = new JLabel(new ImageIcon(bottomOfPokedexPic));
            bottom.add(bottomLabel, BorderLayout.CENTER);
          //  bottom.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
            bottom.setBackground(Color.BLACK);
            

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        updateList();
        stats(null);
        
        stats = new JPanel(new GridLayout(2, 1));
        stats.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
        stats.setBackground(Color.WHITE);
        
        type = new JLabel("Type: ");
        name = new JLabel("Name: ");
        
        type.setFont(new Font("Futura", Font.PLAIN, 20)); // fun looking font
        name.setFont(new Font("Futura", Font.PLAIN, 20)); // fun looking font

        
        stats.add(type);
        stats.add(name);
        
        center.setBackground(Color.BLACK);
        center.add(scroll, BorderLayout.NORTH);
        center.add(stats, BorderLayout.CENTER);
        JButton select = new JButton("Select");
        select.addActionListener(new DisplayListener());
        select.setBackground(new Color(12,242,238));
        center.add(select, BorderLayout.SOUTH);
        
        pokeList.add(top, BorderLayout.NORTH);
        pokeList.add(bottom, BorderLayout.SOUTH);
        pokeList.add(center, BorderLayout.CENTER);
        pokeList.setBorder(null);
        
        this.add(pokeList, BorderLayout.EAST);
      //  this.setSize(500, 100);
        this.pack();

    }
    
    public void hinge() {
        
        JPanel hinge = new JPanel();
        
        JLabel hingeLabel = null;
        
        BufferedImage hingePic = null;
        
        try {
            hingePic = ImageIO.read(new File("./images/pokedex/pokedexHinge.png"));
            hingeLabel = new JLabel(new ImageIcon(hingePic));
            hinge.add(hingeLabel, BorderLayout.CENTER);
            hinge.setBackground(Color.BLACK);
            hinge.setBorder(null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        this.add(hinge, BorderLayout.CENTER);
        this.pack();
    }
    
    public void updateList() {
        
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        ArrayList<Pokemon> visiblePokemon = new PokemonDatabase().getAllPokemon();
        trainerHasOne = new ArrayList<Pokemon>();
        
        for (int i = 0; i < visiblePokemon.size(); i++) {
            
            for (int k = 0; k < trainer.getPokemon().size(); k++) {
                
                if (trainer.getPokemon().get(k).getName().equals(visiblePokemon.get(i).getName())) {
                    
                    boolean add = true;
                    
                    for (int m = 0; m < trainerHasOne.size(); m++) {
                        
                        if (trainer.getPokemon().get(k).getName().equals(trainerHasOne.get(m).getName())) {
                            
                            add = false;
                        }
                    }
                    
                    if (add) {
                    
                        trainerHasOne.add(visiblePokemon.get(i));
                        listModel.addElement(visiblePokemon.get(i).getName());                  
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

    public void stats(Pokemon p) {
        
        if (p != null) {
            
            type.setText("TYPE: " + p.getType());
            name.setText("NAME: " + p.getName());
        }
    }
    
    private class DisplayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
            Pokemon p = trainerHasOne.get(list.getSelectedIndex());
            stats(p);

            centerLabel.setIcon(new ImageIcon(p.getSprite()[0]));            
        }
        
        
    }
}
