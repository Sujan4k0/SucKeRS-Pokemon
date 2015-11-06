/*=========================================================================== 
 | Assignment: FINAL PROJECT: [PokemonTests] 
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
 | Description:  
 *==========================================================================*/
package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.PokemonModel.Common;
import model.PokemonModel.Legendary;
import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonResponse;
import model.PokemonModel.PokemonType;
import model.PokemonModel.Uncommon;
import model.TrainerModel.TrainerAction;

public class PokemonTests { 

    @Test
    public void PokemonResponses() {

        // common pokemon start with these chances: run - 30, catch - 70
        Pokemon testMon = new Common("Pikachu", null, PokemonType.ELECTRIC);
        assertEquals(testMon.getState(), PokemonResponse.STAND_GROUND);

        /*
         * throwing bait at a pokemon increases run chance by a fixed percentage
         * and decreases catch chance by a fixed percentage.
         * 
         * So if we throw the bait, run chance will be 40 and catch chance will
         * be 60. With the decider at 97, the Pokemon will continue to stand its
         * ground
         * 
         */
        testMon.setSeed(1); // decider will be 97

        assertEquals(testMon.respond(TrainerAction.THROW_BAIT), PokemonResponse.STAND_GROUND);

        /*
         * throwing a rock at a pokemon decreases run chance by a fixed constant
         * and increases catch chance by a fixed constant
         * 
         * So if we throw the rock, run chance will be 20 and catch chance will
         * be 80. With the decider at 97, the Pokemon will continue to stand its
         * ground.
         */

        // reset
        testMon = new Common("Pikachu", null, PokemonType.ELECTRIC);
        testMon.setSeed(1); // decider will be 97

        assertEquals(testMon.respond(TrainerAction.THROW_ROCK), PokemonResponse.STAND_GROUND);

        /*
         * throwing a pokeball simply evaluates the current state of the Pokemon
         * in this case since the decider is 97, the Pokemon will not be caught
         * since its at its original catch percentage of 70.
         */
        testMon = new Common("Pikachu", null, PokemonType.ELECTRIC);
        testMon.setSeed(1);

        assertEquals(testMon.respond(TrainerAction.THROW_BALL), PokemonResponse.STAND_GROUND);

        // uncommon pokemon start with these chances - run: 50 catch: 50
        Pokemon testMon2 = new Uncommon("Cyndaquil", null, PokemonType.FIRE);

        /*
         * throwing bait at a pokemon increases run chance by a fixed percentage
         * and decreases catch chance by a fixed percentage.
         * 
         * So if we throw the bait, run chance will be 55 and catch chance will
         * be 45. With the decider at 5, the Pokemon will run
         * 
         */

        testMon2.setSeed(2); // decider of 5

        assertEquals(testMon2.respond(TrainerAction.THROW_BAIT), PokemonResponse.RUN_AWAY);

        /*
         * throwing a rock at a pokemon decreases run chance by a fixed constant
         * and increases run chance by a fixed constant
         * 
         * So if we throw the rock, run chance will be 45 and catch chance will
         * be 55. With the decider at 5, the Pokemon will run
         * 
         */
        testMon2 = new Uncommon("Cyndaquil", null, PokemonType.FIRE); // reset
        testMon2.setSeed(2);

        assertEquals(testMon2.respond(TrainerAction.THROW_ROCK), PokemonResponse.RUN_AWAY);

        /*
         * throwing a pokeball simply evaluates the current state of the Pokemon
         * in this case since the decider is 5, the Pokemon will be caught since
         * its catch percentage will be 50.
         */
        testMon2 = new Uncommon("Cyndaquil", null, PokemonType.FIRE); // reset
        testMon2.setSeed(2);

        assertEquals(testMon2.respond(TrainerAction.THROW_BALL), PokemonResponse.GET_CAUGHT);
        
        // using legendary so that I can test running away but not being caught when throwing a pokeball
        Pokemon testMon3 = new Legendary("Mystery", null, PokemonType.MYSTERY);
        
        // pokemon will run away since legendary starts at a catch of 5 and a run of 3 and
        // the decider is 3, so it'll bypass the run when we throw a ball

        testMon3.respond(TrainerAction.THROW_BAIT); // this shoot drain catch to set up run
        testMon3.setSeed(22); // decider of 3

        assertEquals(testMon3.respond(TrainerAction.THROW_BALL), PokemonResponse.RUN_AWAY);
    }

    @Test
    public void toStringTest() {
        
        Pokemon testMon = new Common("Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon.toString(), "Name: SARRYATHJAN" + "\n" + "Type: ELECTRIC" + "\n");       
        
        assertEquals(testMon.getName(), "SARRYATHJAN");
    }
        
}
