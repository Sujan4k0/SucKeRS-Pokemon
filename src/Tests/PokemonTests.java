package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Common;
import model.Legendary;
import model.Pokemon;
import model.PokemonResponse;
import model.PokemonType;
import model.TrainerAction;
import model.Uncommon;

public class PokemonTests {

    @Test
    public void PokemonResponses() {

        // common pokemon start with these chances: run - 30, catch - 70
        Pokemon testMon = new Common("Pikachu", null, PokemonType.ELECTRIC);
        assertEquals(testMon.getState(), PokemonResponse.STAND_GROUND); // REALLY LONG COMMENT JFOIEJFOEJFOIEJFIOEJFIOJEF

        testMon.setSeed(1); // decider will be 97

        /*
         * throwing bait at a pokemon increases run chance by a fixed percentage
         * and decreases catch chance by a fixed percentage.
         * 
         * So if we throw the bait, run chance will be 40 and catch chance will
         * be 60. With the decider at 97, the Pokemon will continue to stand its
         * ground
         * 
         */
        assertEquals(testMon.respond(TrainerAction.THROW_BAIT), PokemonResponse.STAND_GROUND);

        // reset
        testMon = new Common("Pikachu", null, PokemonType.ELECTRIC);

        testMon.setSeed(1); // decider will be 97

        /*
         * throwing a rock at a pokemon decreases run chance by a fixed constant
         * and increases run chance by a fixed constant
         * 
         * So if we throw the rock, run chance will be 20 and catch chance will
         * be 80. With the decider at 97, the Pokemon will continue to stand its
         * ground.
         */
        assertEquals(testMon.respond(TrainerAction.THROW_ROCK), PokemonResponse.STAND_GROUND);

        testMon = new Common("Pikachu", null, PokemonType.ELECTRIC);
        testMon.setSeed(1);

        /*
         * throwing a pokeball simply evaluates the current state of the Pokemon
         * in this case since the decider is 97, the Pokemon will not be caught
         * since its at its original catch percentage of 70.
         */
        assertEquals(testMon.respond(TrainerAction.THROW_BALL), PokemonResponse.STAND_GROUND);

        // uncommon pokemon start with these chances - run: 50 catch: 50
        Pokemon testMon2 = new Uncommon("Cyndaquil", null, PokemonType.FIRE);

        /*
         * throwing bait at a pokemon increases run chance by a fixed percentage
         * and decreases catch chance by a fixed percentage.
         * 
         * So if we throw the bait, run chance will be 40 and catch chance will
         * be 60. With the decider at 97, the Pokemon will continue to stand its
         * ground
         * 
         */

        /*
         * throwing a rock at a pokemon decreases run chance by a fixed constant
         * and increases run chance by a fixed constant
         * 
         * So if we throw the rock, run chance will be 20 and catch chance will
         * be 80. With the decider at 97, the Pokemon will continue to stand its
         * ground.
         */

        /*
         * throwing a pokeball simply evaluates the current state of the Pokemon
         * in this case since the decider is 97, the Pokemon will not be caught
         * since its at its original catch percentage of 70.
         */

    }

    @Test
    public void LegendaryResponses() {

    }

}
