/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Common] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |             [Keith Smith  (browningsmith@email.arizona.edu)]
 |             [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: Subclass of Pokemon that controls the small variations that
 |              Common pokemon has.  
 *==========================================================================*/
package model;

import java.awt.image.BufferedImage;

public class Common extends Pokemon {
        
    /*---------------------------------------------------------------------
    |  Method name:    [Common]
    |  Purpose:        [Constructor]
    *---------------------------------------------------------------------*/
    public Common(String n, BufferedImage[] i, PokemonType t) {
        super(n, i, t);
        catchPercentage = 70;
        runPercentage = 30;
        
        // constant adjustments for common pokemon
        RUN_ADJUST = 10;
        CATCH_ADJUST = 10;
    }

}
