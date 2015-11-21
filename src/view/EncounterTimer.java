package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonResponse;

public class EncounterTimer {
    
    private int MAX_TICS = 60; // how long the Ticker will go on
    private int tic; // current second we're on
    private Timer timer;
    private Pokemon pokemon;
    
    public EncounterTimer(Pokemon p) {
        
        pokemon = p;
        tic = 0;
        timer = new Timer(1000, new TimerListener());
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

                pokemon.setState(PokemonResponse.RUN_AWAY);
                timer.stop(); // stop the timer
            }             
        }       
    }        
}

