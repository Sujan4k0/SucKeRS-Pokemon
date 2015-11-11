package test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;
import model.TrainerModel.Trainer;

public class TrainerTests {

	@Test
	public void testDefaultTrainer() {
		Trainer ash = new Trainer();
		
		assertEquals(ash.getSteps(), 500);
		assertEquals(ash.getPokeballs(), 30);
		assertFalse(ash.isFatigued());
	}
	
	@Test
	public void testCustomTrainer() {
		Trainer brock = new Trainer(360, 22);
		
		assertEquals(brock.getSteps(), 360);
		assertEquals(brock.getPokeballs(), 22);
		assertFalse(brock.isFatigued());
	}
	
	@Test
	public void testPoint() {
		
		Trainer wendy = new Trainer();
		Point p = new Point(0,0);
		
		wendy.setPoint(p);
		assertEquals(p,wendy.getPoint());
		
		wendy.getPoint().translate(5, 10);
		assertEquals(wendy.getPoint(), new Point(5,10));
	}
	
	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/

}
