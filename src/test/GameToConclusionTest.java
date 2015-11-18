package test;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.util.Random;

import model.GameModel.MazeGame;
import model.TrainerModel.Trainer;

import org.junit.Test;

public class GameToConclusionTest {

    @Test
    public void testMazeGameWin() {
        Random r = new Random() {
            private static final long serialVersionUID = 1L;

            @Override
            public int nextInt(int i) {
                return 3; // always generate maze right
            }
        };

        MazeGame mg = new MazeGame(r);

        assertFalse(mg.isGameWon()); //Game should not be won at start

        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
  //      System.out.println(mg.getTrainer().getPoint());
        //System.out.println(mg.getMap().)

        //assertTrue(mg.isGameWon());				//Game should now be won
    }

    @Test
    public void testMazeGameLose() {
        Random r = new Random() {
            private static final long serialVersionUID = 1L;

            @Override
            public int nextInt(int i) {
                return 3; // always generate maze right
            }
        };

        MazeGame mg = new MazeGame(r);

        assertFalse(mg.isGameWon());
       // System.out.println(mg.trainerCanMove(KeyEvent.VK_RIGHT));
        System.out.println(mg.getMap().getTrainerDir());
       
        mg.moveTrainer(KeyEvent.VK_RIGHT);
       // System.out.println(mg.trainerCanMove(KeyEvent.VK_LEFT));
        System.out.println(mg.getMap().getTrainerDir());
        System.out.println(mg.getTrainer().getSteps());


        mg.moveTrainer(KeyEvent.VK_LEFT);
        System.out.println(mg.getTrainer().getSteps());
        System.out.println(mg.getMap().getTrainerDir());

        mg.moveTrainer(KeyEvent.VK_RIGHT);
        System.out.println(mg.trainerCanMove(KeyEvent.VK_RIGHT));
        
        mg.moveTrainer(KeyEvent.VK_LEFT);
        System.out.println(mg.getTrainer().getSteps());

        mg.moveTrainer(KeyEvent.VK_RIGHT);
        System.out.println(mg.getTrainer().getSteps());

        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        System.out.println(mg.getTrainer().getSteps());
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);
        mg.moveTrainer(KeyEvent.VK_RIGHT);
        mg.moveTrainer(KeyEvent.VK_LEFT);

        System.out.println(mg.getTrainer().getSteps());
        assertTrue(mg.isGameLost());
    }
}
