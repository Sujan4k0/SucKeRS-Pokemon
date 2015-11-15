/*=========================================================================== 
 | Assignment: FINAL PROJECT: [ItemTests] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |	           [Keith Smith  (browningsmith@email.arizona.edu)]
 |	           [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: Junit tests to test Item functionality 
 *===========================================================================*/

package test;

import static org.junit.Assert.*;

import java.awt.Point;

import model.TrainerModel.Trainer;
import model.ItemModel.*;

import org.junit.Test;

public class ItemTests {

	/*---------------------------------------------------------------------
	 |  Method name:    [testStepPotion]
	 |  Purpose:  	    [Test the functionality of step potions
	 *---------------------------------------------------------------------*/
	@Test
	public void TestStepPotions() {
		SuperStepPotion Super = new SuperStepPotion();
		BasicStepPotion Basic = new BasicStepPotion();
		HyperStepPotion Hyper = new HyperStepPotion();
		Trainer ash = new Trainer();

		ash.addItem(Basic);
		assertTrue(ash.getItems().contains(Basic));
		assertTrue(ash.getItemQuantities().get(Basic.getName())==1);
		ash.addItem(Super);
		assertTrue(ash.getItems().contains(Super));
		assertTrue(ash.getItemQuantities().get(Super.getName())==1);
		ash.addItem(Hyper);
		assertTrue(ash.getItems().contains(Hyper));
		assertTrue(ash.getItemQuantities().get(Hyper.getName())==1);
		ash.useItem(Basic);
		assertTrue(ash.getSteps()==510);
		assertTrue(ash.getItemQuantities().get(Basic.getName())==0);
		ash.useItem(Super);
		assertTrue(ash.getSteps()==535);
		assertTrue(ash.getItemQuantities().get(Super.getName())==0);
		ash.useItem(Hyper);
		assertTrue(ash.getSteps()==585);
		assertTrue(ash.getItemQuantities().get(Hyper.getName())==0);
		assertTrue((! ash.getItems().contains(Hyper))&& (! ash.getItems().contains(Super))&& (! ash.getItems().contains(Basic)));

	}

	/*---------------------------------------------------------------------
	 |  Method name:    [testTeleporter]
	 |  Purpose:  	    [Test the functionality of teleporter
	 *---------------------------------------------------------------------*/
	@Test
	public void testTeleporter(){
		Trainer brock = new Trainer();
		Item teleporter = new Teleporter();
		Point p = new Point(0,0);
		brock.setPoint(p);
		Point newPoint = new Point((int)p.getX()+1, (int)p.getY()+1);

		brock.addItem(teleporter);
		assertTrue(brock.getItems().contains(teleporter));
		assertTrue(brock.getItemQuantities().get(teleporter.getName())==1);

		brock.useItem(teleporter);
		assertTrue(brock.getItems().contains(teleporter));
		assertTrue(((Teleporter) teleporter).isSet());
		assertTrue(((Teleporter) teleporter).getTeleportPoint().equals(p));
		assertTrue(brock.getItemQuantities().get(teleporter.getName())==1);

		brock.setPoint(newPoint);
		assertTrue(!brock.getPoint().equals(p));

		brock.useItem(teleporter);
		assertTrue(((Teleporter)teleporter).isSet());
		assertTrue(brock.getPoint().equals(p));
		assertTrue(! brock.getItems().contains(teleporter));
		assertTrue(brock.getItemQuantities().get(teleporter.getName())==0);

	}
	/*---------------------------------------------------------------------
	 |  Method name:    [testFatiguePotion]
	 |  Purpose:  	    [Test the functionality of Fatigue potions
	 *---------------------------------------------------------------------*/
	@Test
	public void testFatiguePotion(){
		Trainer misty = new Trainer();
		Item fPotion = new FatiguePotion();

		misty.addItem(fPotion);
		assertTrue(misty.getItems().contains(fPotion));
		assertTrue(misty.getItemQuantities().get(fPotion.getName())==1);

		misty.useItem(fPotion);
		assertTrue(misty.getItems().contains(fPotion));
		assertTrue(misty.getItemQuantities().get(fPotion.getName())==1);
		assertTrue(!misty.isFatigued());

		misty.setFatigued(true);

		misty.useItem(fPotion);
		assertTrue(!misty.isFatigued());
		assertFalse(misty.getItems().contains(fPotion));
		assertTrue(misty.getItemQuantities().get(fPotion.getName())==0);
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [testPokeBalls]
	 |  Purpose:  	    [Test the functionality of pokeballs
	 *---------------------------------------------------------------------*/
	@Test
	public void testPokeBalls(){
		Trainer Ryan = new Trainer();
		Item pb = new PokeBall();
		Ryan.addItem(pb);
		assertTrue(Ryan.getItemQuantities().get("PokeBall")==31);
		assertTrue(Ryan.getItems().contains(pb));

		Ryan.useItem(pb);
		assertTrue(Ryan.getItemQuantities().get("PokeBall")==30);
		assertTrue(!Ryan.getItems().contains(pb));
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [testHarmonica]
	 |  Purpose:  	    [Test the functionality of Harmonicas
	 *---------------------------------------------------------------------*/
	public void testHarmonica(){
		Trainer Ryan = new Trainer();
		Item h = new Harmonica();
		Ryan.addItem(h);
		assertTrue(Ryan.getItemQuantities().get("Harmonica")==1);
		assertTrue(Ryan.getItems().contains(h));

		Ryan.useItem(h);
		assertTrue(Ryan.getItemQuantities().get("Hamonica")==0);
		assertTrue(!Ryan.getItems().contains(h));
	}

}
