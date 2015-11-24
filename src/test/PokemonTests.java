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

import java.util.Random;

import org.junit.Test;

import model.PokemonModel.Common;
import model.PokemonModel.Legendary;
import model.PokemonModel.Pokemon;
import model.PokemonModel.PokemonResponse;
import model.PokemonModel.PokemonType;
import model.PokemonModel.Rarity;
import model.PokemonModel.Uncommon;
import model.TrainerModel.TrainerAction;

public class PokemonTests {

    @Test
    public void PokemonResponses() {

        // RANDOM SEED 1 GIVES 86

        // common pokemon start with these chances: run - 30, catch - 70
        Pokemon testMon = new Common(new Random(1), "Pikachu", null, PokemonType.ELECTRIC);
        assertEquals(testMon.getState(), PokemonResponse.STAND_GROUND);

        /*
         * throwing bait at a pokemon increases run chance by a fixed percentage
         * and decreases catch chance by a fixed percentage.
         * 
         * So if we throw the bait, run chance will be 40 and catch chance will
         * be 60. With the decider at 86, the Pokemon will continue to stand its
         * ground
         * 
         */

        assertEquals(testMon.respond(TrainerAction.THROW_BAIT), PokemonResponse.STAND_GROUND);

        /*
         * throwing a rock at a pokemon decreases run chance by a fixed constant
         * and increases catch chance by a fixed constant
         * 
         * So if we throw the rock, run chance will be 20 and catch chance will
         * be 80. With the decider at 86, the Pokemon will continue to stand its
         * ground.
         */

        // reset
        testMon = new Common(new Random(1), "Pikachu", null, PokemonType.ELECTRIC);
        assertEquals(testMon.respond(TrainerAction.THROW_ROCK), PokemonResponse.STAND_GROUND);

        /*
         * throwing a pokeball simply evaluates the current state of the Pokemon
         * in this case since the decider is 86, the Pokemon will not be caught
         * since its at its original catch percentage of 70.
         */
        testMon = new Common(new Random(1), "Pikachu", null, PokemonType.ELECTRIC);
        assertEquals(testMon.respond(TrainerAction.THROW_BALL), PokemonResponse.STAND_GROUND);

        // SEED OF 2 GIVES DECIDER OF 9
        
        //uncommon pokemon start with these chances - run: 50 catch: 50
        Pokemon testMon2 = new Uncommon(new Random(2), "Cyndaquil", null, PokemonType.FIRE);

        /*
         * throwing bait at a pokemon increases run chance by a fixed percentage
         * and decreases catch chance by a fixed percentage.
         * 
         * So if we throw the bait, run chance will be 55 and catch chance will
         * be 45. With the decider at 9, the Pokemon will run
         * 
         */
 
        assertEquals(testMon2.respond(TrainerAction.THROW_BAIT), PokemonResponse.RUN_AWAY);

        /*
         * throwing a rock at a pokemon decreases run chance by a fixed constant
         * and increases run chance by a fixed constant
         * 
         * So if we throw the rock, run chance will be 45 and catch chance will
         * be 55. With the decider at 9, the Pokemon will run
         * 
         */
        testMon2 = new Uncommon(new Random(2), "Cyndaquil", null, PokemonType.FIRE); // reset
        assertEquals(testMon2.respond(TrainerAction.THROW_ROCK), PokemonResponse.RUN_AWAY);

        /*
         * throwing a pokeball simply evaluates the current state of the Pokemon
         * in this case since the decider is 9, the Pokemon will be caught since
         * its catch percentage will be 50.
         */
        testMon2 = new Uncommon(new Random(2), "Cyndaquil", null, PokemonType.FIRE); // reset
        assertEquals(testMon2.respond(TrainerAction.THROW_BALL), PokemonResponse.GET_CAUGHT);

        // SEED OF 18 GIVES A DECIDER OF 1

        // using legendary so that I can test running away but not being caught when throwing a pokeball
        Pokemon testMon3 = new Legendary(new Random(20), "Mystery", null, PokemonType.MYSTERY);

        // pokemon will run away since legendary starts at a catch of 5 and a run of 3 and
        // the decider is 1, so it'll bypass the run when we throw a ball

        testMon3.respond(TrainerAction.THROW_BAIT); // this shoot drain catch to set up run
        testMon3.getDecider();
        assertEquals(testMon3.respond(TrainerAction.THROW_BALL), PokemonResponse.RUN_AWAY);
    }

    @Test
    public void toStringTest() {

        Common testMon = new Common(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon.toString(), "Name: SARRYATHJAN" + "\n" + "Type: ELECTRIC" + "\n");
        assertEquals(testMon.getName(), "SARRYATHJAN");        
    }

    @Test
    public void rarities() {
        
        Common testMon = new Common(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon.getRarity(), Rarity.COMMON);
        
        Uncommon testMon1 = new Uncommon(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon1.getRarity(), Rarity.UNCOMMON);
        
        Legendary testMon2 = new Legendary(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon2.getRarity(), Rarity.LEGENDARY);
    }
    
    @Test
    public void genericGettersAndSetters() {
        
        Legendary testMon2 = new Legendary(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon2.getType(), PokemonType.ELECTRIC);
        
        assertEquals(testMon2.getEncounterChance(), 10);
      
        testMon2.setState(PokemonResponse.GET_CAUGHT);
        assertEquals(testMon2.getState(), PokemonResponse.GET_CAUGHT);
    }

    @Test
    public void rarityStrings() {
        
        Common testMon = new Common(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon.rarityString(), "(C)");
        
        Uncommon testMon1 = new Uncommon(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon1.rarityString(), "(U)");
        
        Legendary testMon2 = new Legendary(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon2.rarityString(), "(L)");

    }
    
    @Test
    public void spriteGetter() {
        
        Pokemon testMon = new Common(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertNull(testMon.getSprite());   
    }
    
    @Test
    public void invalidResponseBreak() {
        
        Pokemon testMon = new Common(new Random(), "Sarryathjan", null, PokemonType.ELECTRIC);
        assertEquals(testMon.respond(TrainerAction.RUN_AWAY), testMon.getState());
    }
    
    @Test
    public void enumCoverage() {
                
        for (PokemonResponse r : PokemonResponse.values()) {
            
            assertNotNull(r);            
        }
        
        PokemonResponse r = PokemonResponse.valueOf("GET_CAUGHT");
        
        for (PokemonType t : PokemonType.values()) {
            
            assertNotNull(t);
        }
        
        PokemonType t = PokemonType.valueOf("ELECTRIC");
        
        for (Rarity rt : Rarity.values()) {
            
            assertNotNull(rt);
        }
        
        Rarity rt = Rarity.valueOf("LEGENDARY");
    }
}
