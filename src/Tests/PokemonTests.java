package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Common;
import model.Legendary;
import model.PokemonResponse;
import model.PokemonType;
import model.TrainerAction;
import model.Uncommon;

public class PokemonTests {

    @Test
    public void CommonPokemonResponses() {
        
        Common testMon = new Common("Pikachu", null, PokemonType.ELECTRIC);
        assertEquals(testMon.getState(), PokemonResponse.STAND_GROUND); // start default state
        
        /* Seed 5 generates: true for taking the bait, 41 for deciding in the range, 
        * 14 for run adjust and 4 for catch adjust
        * 
        * Based on default run and catch percentages
        * takeBait = true, decider = 41, runPercentage = 44, catchPercentage = 54
        */
        testMon.setSeed(5);         
        assertEquals(testMon.respond(TrainerAction.GIVE_BAIT), PokemonResponse.ACCEPTS_BAIT);
        
        testMon = new Common("Pikachu", null, PokemonType.ELECTRIC); // reset to initial pokemon
        testMon.setSeed(5);
        assertEquals(testMon.respond(TrainerAction.THROW_BALL), PokemonResponse.GET_CAUGHT);
        
        testMon = new Common("Pikachu", null, PokemonType.ELECTRIC); // reset to inital pokemon
        testMon.setSeed(5);
        assertEquals(testMon.respond(TrainerAction.THROW_ROCK), PokemonResponse.RUN_AWAY);
    }
    
    @Test 
    public void LegendaryResponses() {
        
        // legendary has more of a chance to qualify for running and not catching or vice versa
        // so we're gonna do those here
        
        Legendary testMon = new Legendary("Saryathjan", null, PokemonType.FIRE);
        
        /* Seed 59057 generates: false for taking the bait, 62 for deciding in the range,
         * 9 for run adjustment, -19 for catch adjustment.
         * 
         * Based on default catch and run percentages
         * takeBait = false, decider = 41, runPercentage = 12, catchPercentage = 0
         */
        testMon.setSeed(59062);
        testMon.respond(TrainerAction.GIVE_BAIT);
        
//        int seed = 0;
//        
//        while (true) {
//                       
//            boolean found = false;
//            
//            testMon = new Legendary("Saryathjan", null, PokemonType.FIRE);
//            testMon.setSeed(seed);
//            System.out.println(seed);
//            testMon.getAdjustments();
//            
//            seed++;
//        }
    }

}
