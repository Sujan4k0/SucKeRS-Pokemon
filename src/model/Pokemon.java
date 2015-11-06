/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Pokemon] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |         [Keith Smith  (browningsmith@email.arizona.edu)]
 |         [Ryan Kaye    (kayeryan1@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: The superclass for defining different Pokemon. 
 *==========================================================================*/

package model;

import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Pokemon {

    private String name; // name of this Pokemon
    private BufferedImage[] sprite; // different graphical views for this Pokemon
    
    private PokemonType type; // element mastery of the Pokemon
    protected double catchPercentage; // likelihood of being caught (adjustable)
    protected double runPercentage; // likelihood of running away
    
    protected PokemonResponse pokemonState = PokemonResponse.STAND_GROUND;
    protected Random gen = new Random();
    
    protected boolean takeBait = false;
    protected int decider = 0;
    protected int RUN_ADJUST;
    protected int CATCH_ADJUST;


    /*---------------------------------------------------------------------
    |  Method name:    [Pokemon]
    |  Purpose:        [Constructor]
    |  Parameters:     []
    *---------------------------------------------------------------------*/
    public Pokemon(String n, BufferedImage[] i, PokemonType t) {
        
        // store all of the given parameters
        this.name = n;
        this.sprite = i;
        this.type = t;
        
        RUN_ADJUST = 0;
        CATCH_ADJUST = 0;
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    []
    |  Purpose:        []
    |  Parameters:     []
    |  Returns:        []
    *---------------------------------------------------------------------*/
    public PokemonResponse respond(TrainerAction action) {
        
        // generic body, if other types (common) do not write their
        // own, this one gets used.


        getDecider();
        
        switch (action) {
            case THROW_BAIT:
                                
                runPercentage = Math.max(0, runPercentage + RUN_ADJUST);
                catchPercentage = Math.max(0, catchPercentage - CATCH_ADJUST);
                
                if (decider <= runPercentage && decider > 0) {
                    
                    pokemonState = PokemonResponse.RUN_AWAY;
                }
                
                break;

            case THROW_BALL:
               
                // evaluate and set pokemonState
                
                if (decider <= catchPercentage && decider > 0) {
                    
                    pokemonState = PokemonResponse.GET_CAUGHT;
                }
                
                else if (decider <= runPercentage && decider > 0) {
                    
                    pokemonState = PokemonResponse.RUN_AWAY;
                }
                
                break;
                
            case THROW_ROCK:
 
                runPercentage = Math.max(0, runPercentage - RUN_ADJUST);
                catchPercentage = Math.max(0, catchPercentage + CATCH_ADJUST);
                
                if (decider <= runPercentage && decider > 0) {
                    
                    pokemonState = PokemonResponse.RUN_AWAY;
                }
                
                break;
                
            default:
                break;  
        }
        
        System.out.println(RUN_ADJUST + " " + CATCH_ADJUST);
        System.out.println(runPercentage + " " + catchPercentage);
        
        return pokemonState;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String toString() {
        return this.name;
    }
    
    public PokemonResponse getState() {
        
        return pokemonState;
    }
        
    public void setSeed(int seed) {
        
        gen = new Random(seed);      
    }
    
    public int getDecider() {
        
        decider = gen.nextInt(101);
        return decider;
    }
}
