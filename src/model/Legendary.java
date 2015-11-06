package model;

import java.awt.image.BufferedImage;

public class Legendary extends Pokemon {
    
    public Legendary(String n, BufferedImage[] i, PokemonType t) {
        super(n, i, t);
        catchPercentage = 5;
        runPercentage = 3;
        
        RUN_ADJUST = 1;
        CATCH_ADJUST = 5; 
    }    
}
