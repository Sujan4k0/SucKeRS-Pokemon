/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Legendary] 
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
 |              Lgendary pokemon has. 
 *==========================================================================*/
package model.PokemonModel;

import java.awt.Image;
import java.util.Random;


public class Legendary extends Pokemon {
    
    /*---------------------------------------------------------------------
    |  Method name:    [Legendary]
    |  Purpose:        [Constructor]
    *---------------------------------------------------------------------*/
    public Legendary(Random r, String n, Image[] i, PokemonType t) {
        super(r, n, i, t);
        catchPercentage = 5;
        runPercentage = 3;
        
        // constant values for legendary adjustments
        RUN_ADJUST = 1;
        CATCH_ADJUST = 5; 
    }    
}
