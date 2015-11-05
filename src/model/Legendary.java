package model;

import java.awt.image.BufferedImage;

public class Legendary extends Pokemon {

    public Legendary(String n, BufferedImage[] i, PokemonType t, double cp, double rp) {
        super(n, i, t, cp, rp);
        // TODO Auto-generated constructor stub
    }

    public void respond(TrainerAction action) {
        
        
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
