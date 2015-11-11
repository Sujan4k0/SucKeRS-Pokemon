package test;

import static org.junit.Assert.*;

import java.awt.Point;

import model.TrainerModel.Trainer;
import model.ItemModel.*;

import org.junit.Test;

public class ItemTests {

	@Test
	public void TestStepPotions() {
		Item Super = new SuperStepPotion();
		Item Basic = new BasicStepPotion();
		Item Hyper = new HyperStepPotion();
		Trainer ash = new Trainer();
		
		ash.addItem(Basic);
		assertTrue(ash.getItems().contains(Basic));
		ash.addItem(Super);
		assertTrue(ash.getItems().contains(Super));
		ash.addItem(Hyper);
		assertTrue(ash.getItems().contains(Hyper));
		ash.useItem(Basic);
		assertTrue(ash.getSteps()==510);
		ash.useItem(Super);
		assertTrue(ash.getSteps()==535);
		ash.useItem(Hyper);
		assertTrue(ash.getSteps()==585);
		assertTrue((! ash.getItems().contains(Hyper))&& (! ash.getItems().contains(Super))&& (! ash.getItems().contains(Basic)));

	}
	
	@Test
	public void testTeleporter(){
		Trainer brock = new Trainer();
		Item teleporter = new Teleporter();
		Point p = new Point(0,0);
		brock.setPoint(p);
		Point newPoint = new Point((int)p.getX()+1, (int)p.getY()+1);
		
		brock.addItem(teleporter);
		assertTrue(brock.getItems().contains(teleporter));
		
		brock.useItem(teleporter);
		assertTrue(brock.getItems().contains(teleporter));
		assertTrue(((Teleporter) teleporter).isSet());
		assertTrue(((Teleporter) teleporter).getTeleportPoint().equals(p));

		brock.setPoint(newPoint);
		assertTrue(!brock.getPoint().equals(p));
		
		brock.useItem(teleporter);
		assertTrue(((Teleporter)teleporter).isSet());
		assertTrue(brock.getPoint().equals(p));
		assertTrue(! brock.getItems().contains(teleporter));

	}
	@Test
	public void testFatiguePotion(){
		Trainer misty = new Trainer();
		Item fPotion = new FatiguePotion();
		
		misty.addItem(fPotion);
		assertTrue(misty.getItems().contains(fPotion));
		
		misty.useItem(fPotion);
		assertTrue(misty.getItems().contains(fPotion));
		assertTrue(!misty.isFatigued());
		
		misty.setFatigued(true);
		
		misty.useItem(fPotion);
		assertTrue(!misty.isFatigued());
		assertFalse(misty.getItems().contains(fPotion));
		
	}

}
