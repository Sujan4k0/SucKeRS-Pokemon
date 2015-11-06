package model;

import java.awt.image.BufferedImage;

public class Uncommon extends Pokemon {

    public Uncommon(String n, BufferedImage[] i, PokemonType t) {
        super(n, i, t);
        catchPercentage = 50;
        runPercentage = 50;
        
        RUN_ADJUST = 5;
        CATCH_ADJUST = 5;
        // TODO Auto-generated constructor stub
    }
    
//    @Override
//    public void adjustCatch(TrainerAction action) {
//        // TODO Auto-generated method stub
//        
//    }
//
//    @Override
//    public void adjustRun(TrainerAction action) {
//        // TODO Auto-generated method stub
//        
//    }

}
