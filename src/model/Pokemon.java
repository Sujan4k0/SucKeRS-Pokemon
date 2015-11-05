/*=========================================================================== 
 | Assignment: FINAL PROJECT: [] 
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
    protected int runAdjust = 0;
    protected int catchAdjust = 0;

    
    public Pokemon(String n, BufferedImage[] i, PokemonType t) {
        
        // store all of the given parameters
        this.name = n;
        this.sprite = i;
        this.type = t;
    }
    
    public abstract void adjustCatch(TrainerAction action);
    
    public abstract void adjustRun(TrainerAction action);
    
    public PokemonResponse respond(TrainerAction action) {
        
        // generic body, if other types (common) do not write their
        // own, this one gets used.
        
        pokemonState = PokemonResponse.STAND_GROUND;
                
        getAdjustments();
        
        // hard-coding action impact for now, will want to use random in the future
        switch (action) {
            case GIVE_BAIT:
                                
                if (takeBait) {
                
                    catchPercentage = Math.max(0, catchPercentage + catchAdjust);
                    pokemonState = PokemonResponse.ACCEPTS_BAIT;
                }
                
                else {
                    
                    runPercentage = Math.max(0, runPercentage + runAdjust);
                }
                
                break;

            case THROW_BALL:
               
                runPercentage = Math.max(0, runPercentage + runAdjust);
                catchPercentage = Math.max(0, catchPercentage + catchAdjust);
                
                // evaluate and set pokemonState
                
                if (decider <= catchPercentage) {
                    
                    pokemonState = PokemonResponse.GET_CAUGHT;
                }
                
                else if (decider <= runPercentage) {
                    
                    pokemonState = PokemonResponse.RUN_AWAY;
                }
                
                break;
                
            case THROW_ROCK:
                runPercentage = Math.max(0, runPercentage + runAdjust);
                catchPercentage = Math.max(0, catchPercentage + catchAdjust);
                
                if (decider <= runPercentage) {
                    
                    pokemonState = PokemonResponse.RUN_AWAY;
                }
                
                break;
                
            default:
                break;  
        }
        
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
    
    public void getAdjustments() {
        
        takeBait = gen.nextBoolean();
        decider = gen.nextInt(101);
        runAdjust = gen.nextInt(40) - 20;
        catchAdjust = gen.nextInt(40) - 20;
        
        System.out.println(takeBait + " d" + decider + " r" + runAdjust + " c" + catchAdjust);
    }
    
    public void setSeed(int seed) {
        
        gen = new Random(seed);     
        
     
    }
    
    public void findSeed() {
        
        int seed = 0;
        
        boolean found = false;
        
        while (!found) {
            
            boolean test = gen.nextBoolean();
            
            if (test == false) {
                
                System.out.println(seed);
                found = true;
                
            }
            
            else {
                
                seed++;
            }
        }
    }
}
