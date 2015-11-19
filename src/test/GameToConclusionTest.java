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

        mg.moveTrainer(KeyEvent.VK_RIGHT); // 1
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 2
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 3
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 4
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 5
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 6
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 7
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 8
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 9
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 10
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 11
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 12
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 13
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 14
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 15
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 16
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 17
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 18
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 19
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 20
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 21
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 22
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 23
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 24
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 25
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 26
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 27
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 28
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 29
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 30
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 31
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 32
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 33
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 34
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 35
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 36
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 37
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 38
        assertFalse(mg.isGameWon());
        mg.moveTrainer(KeyEvent.VK_RIGHT); // 39
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
        System.out.println(mg.getTrainer().getPoint());
        //System.out.println(mg.getMap().);

        assertTrue(mg.isGameWon());				//Game should now be won
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
