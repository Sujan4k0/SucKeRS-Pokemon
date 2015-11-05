package model;

import java.awt.image.BufferedImage;

public class Common extends Pokemon {
    
    public Common(String n, BufferedImage[] i, PokemonType t) {
        super(n, i, t);
        catchPercentage = 50;
        runPercentage = 30;
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
