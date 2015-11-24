/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Uncommon] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |         [Keith Smith  (browningsmith@email.arizona.edu)]
 |         [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: Subclass of Pokemon that controls the small variations that
 |              Uncommon pokemon has. 
 *==========================================================================*/
package model.PokemonModel;

import java.awt.Image;
import java.util.Random;

public class Uncommon extends Pokemon {
    
    private final int APPEARANCE_CHANCE;
    protected String RARITY;

    /*---------------------------------------------------------------------
    |  Method name:    [Uncommon]
    |  Purpose:        [Constructor]
    *---------------------------------------------------------------------*/
    public Uncommon(Random r, String n, Image[] i, PokemonType t) {
        super(r, n, i, t);
        catchPercentage = 50;
        runPercentage = 50;
        
        // constant adjustments for uncommon pokemon
        RUN_ADJUST = 5;
        CATCH_ADJUST = 5;
        APPEARANCE_CHANCE = 35;
        RARITY = "(U)";
    }

    @Override
    public String rarityString() {

        return RARITY;
    }

    @Override
    public Rarity getRarity() {
        // TODO Auto-generated method stub
        return rarity.UNCOMMON;
    }
}
