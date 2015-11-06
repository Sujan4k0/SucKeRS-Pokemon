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
package model;

import java.awt.image.BufferedImage;

public class Uncommon extends Pokemon {

    /*---------------------------------------------------------------------
    |  Method name:    [Uncommon]
    |  Purpose:        [Constructor]
    *---------------------------------------------------------------------*/
    public Uncommon(String n, BufferedImage[] i, PokemonType t) {
        super(n, i, t);
        catchPercentage = 50;
        runPercentage = 50;
        
        // constant adjustments for uncommon pokemon
        RUN_ADJUST = 5;
        CATCH_ADJUST = 5;
    }
}
