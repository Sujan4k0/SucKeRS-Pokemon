package model;

import java.awt.image.BufferedImage;

public class Common extends Pokemon {
        
    public Common(String n, BufferedImage[] i, PokemonType t) {
        super(n, i, t);
        catchPercentage = 70;
        runPercentage = 30;
        
        RUN_ADJUST = 10;
        CATCH_ADJUST = 10;
    }

}
