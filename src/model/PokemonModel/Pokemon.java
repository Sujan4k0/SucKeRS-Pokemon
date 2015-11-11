/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Pokemon] 
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
 | Description: The superclass for defining different Pokemon. 
 *==========================================================================*/

package model.PokemonModel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.Timer;

import model.TrainerModel.TrainerAction;

public abstract class Pokemon {

    private String name; // name of this Pokemon
    private Image[] sprite; // different graphical views for this Pokemon
    
    private PokemonType type; // element mastery of the Pokemon
    protected double catchPercentage; // likelihood of being caught (adjustable)
    protected double runPercentage; // likelihood of running away
    
    protected PokemonResponse pokemonState = PokemonResponse.STAND_GROUND;
    protected Random gen;
    
    protected int decider; // determines percentage location for run and catch
    protected int RUN_ADJUST; // fixed constant of run incrementation/decrementation
    protected int CATCH_ADJUST; // fixed constant of catch incrementation/decerementation
        
    /*---------------------------------------------------------------------
    |  Method name:    [Pokemon]
    |  Purpose:        [Constructor]
    |  Parameters:     [String that is the name, BufferedImage[] that
    |                   contains all perspectives of this Pokemon, and
    |                   the type of the Pokemon.]
    *---------------------------------------------------------------------*/
    public Pokemon(String n, BufferedImage[] i, PokemonType t) {
        
        // store all of the given parameters
        this.name = n;
        this.sprite = i;
        this.type = t;
        
        // default the RUN_ADJUST and CATCH_ADJUST for no rarity pokemon
        // this should never actually be used
        RUN_ADJUST = 0;
        CATCH_ADJUST = 0;
        
        gen = new Random(); // start random here for non-testing
        decider = 0; // default decision to 0, so nothing would happen
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [respond]
    |  Purpose:        [Determines the response of a Pokemon and adjusts
    |                   the probablistic componenets of this Pokemon.]
    |  Parameters:     [TrainerAction - what the trainer did to the
    |                   Pokemon]
    |  Returns:        [PokemonResponse - how the Pokemon reacted to the
    |                   trainer.]
    *---------------------------------------------------------------------*/
    public PokemonResponse respond(TrainerAction action) {
        
        getDecider(); // get the percentage decider which will determine if the pokemon is to take a certain action
        
        // go through all possible trainer actions
        switch (action) {
        
            case THROW_BAIT:
                                
                runPercentage = Math.min(100, runPercentage + RUN_ADJUST); // increase run
                catchPercentage = Math.max(0, catchPercentage - CATCH_ADJUST); // decrease catch
                
                // evaluate with the decider if the Pokemon will run
                if (decider <= runPercentage && decider > 0) {
                    
                    pokemonState = PokemonResponse.RUN_AWAY;
                }
                break;

            case THROW_BALL:
               
                // evaluate and set pokemonState, catching gets precedence
                if (decider <= catchPercentage && decider > 0) {
                    
                    pokemonState = PokemonResponse.GET_CAUGHT;
                }
                
                else if (decider <= runPercentage && decider > 0) {
                    
                    pokemonState = PokemonResponse.RUN_AWAY;
                }             
                break;
                
            case THROW_ROCK:
 
                runPercentage = Math.max(0, runPercentage - RUN_ADJUST); // decrease run
                catchPercentage = Math.min(100, catchPercentage + CATCH_ADJUST); // increase catch
                
                // check if the pokemon runs based on the current percentage and decider
                if (decider <= runPercentage && decider > 0) {
                    
                    pokemonState = PokemonResponse.RUN_AWAY;
                }            
                break;
                
            default:
                // should never get here
                break;  
        }
                
        return pokemonState; // give back the determined response
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [getDecider]
    |  Purpose:        [Generates a random number between 1 and 100 which
    |                   is used to determine where it falls in the 
    |                   percentages for run and or catch in the 
    |                   respond method. This returns just for testing
    |                   purposes.]
    |  Returns:        [int decider used for testing]
    *---------------------------------------------------------------------*/
    public int getDecider() {
        
        decider = gen.nextInt(100) + 1;
        return decider;
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [getState]
    |  Purpose:        [Gets the current state of the Pokemon]
    |  Returns:        [PokemonResponse - what the Pokemon is doing]
    *---------------------------------------------------------------------*/
    public PokemonResponse getState() {
        
        return pokemonState;
    }

    /*---------------------------------------------------------------------
    |  Method name:    [startEncounter]
    |  Purpose:        [This will start the timer of the encounter with
    |                   this Pokemon when the Trainer encounters it.]
    *---------------------------------------------------------------------*/
    public void startEncounter() {
             
        Ticker ticker = new Ticker();
        ticker.start();
      
    }
    
    /*---------------------------------------------------------------------
    |  Class name:     [Ticker]
    |  Purpose:        [Timer that times the encounter of a Trainer and
    |                   Pokemon. Once time is up, the Pokemon will
    |                   run away.]
    *---------------------------------------------------------------------*/ 
    private class Ticker {
        
        private int MAX_TICS; // how long the Ticker will go on
        private int tic; // current second we're on
        private Timer timer;
        
        /*---------------------------------------------------------------------
        |  Method name:    [Ticker]
        |  Purpose:        [Constructor]
        *---------------------------------------------------------------------*/
        public Ticker() {
            
            MAX_TICS = 60; // 60 second encounters
            tic = 0; // start at 0 seconds
            timer = new Timer(0, new TimerListener()); // ActionListener for timer
        }
        
        /*---------------------------------------------------------------------
        |  Method name:    [start]
        |  Purpose:        [Starts the timer]
        *---------------------------------------------------------------------*/
        public void start() {
            
            timer.start();
        }
        
        /*---------------------------------------------------------------------
        |  Class name:     [TimerListener]
        |  Purpose:        [Allows for the timer to countdown once the Pokemon
        |                   is encountered.]
        *---------------------------------------------------------------------*/ 
        private class TimerListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (tic <= MAX_TICS) { // 60 seconds has not passed
                    
                    tic++;
                }
                
                else { // time is up
                    
                    pokemonState = PokemonResponse.RUN_AWAY; // time is up so the Pokemon will run
                    timer.stop(); // stop the timer
                }             
            }       
        }        
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [getName]
    |  Purpose:        [Get the name of the Pokemon]
    |  Returns:        [String - Pokemon's name]
    *---------------------------------------------------------------------*/
    public String getName() {
        
        return this.name.toUpperCase(); 
    }
    
    /*---------------------------------------------------------------------
    |  Method name:    [toString]
    |  Purpose:        [Get formatted information for this Pokemon]
    |  Returns:        [String - formatted Pokemon info]
    *---------------------------------------------------------------------*/
    public String toString() {
        
        // uppercase of both info types
        String name = "Name: " + this.name.toUpperCase() + "\n";
        String type = "Type: " + this.type + "\n";
        
        return name + type;
    }
         
    /*---------------------------------------------------------------------
    |  Method name:    [setSeed]
    |  Purpose:        [TESTING METHOD: set the seed for the Random]
    |  Parameters:     [int - the seed we're forcing]
    *---------------------------------------------------------------------*/
    public void setSeed(int seed) {
        
        gen = new Random(seed); // set random to be this one    
    }
}
