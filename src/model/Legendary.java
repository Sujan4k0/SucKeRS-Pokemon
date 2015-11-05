package model;

import java.awt.image.BufferedImage;

public class Legendary extends Pokemon {

    public Legendary(String n, BufferedImage[] i, PokemonType t) {
        super(n, i, t);
        catchPercentage = 5;
        runPercentage = 3; 
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void adjustCatch(TrainerAction action) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void adjustRun(TrainerAction action) {
        // TODO Auto-generated method stub
        
    }

}
