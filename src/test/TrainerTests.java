package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Random;

import org.junit.Test;

import model.PokemonModel.*;
import model.ItemModel.*;
import model.TrainerModel.Trainer;

public class TrainerTests {
	
	@Test
	public void testPoint() {
		
		Trainer wendy = new Trainer();
		Point p = new Point(0,0);
		
		wendy.setPoint(p);
		assertEquals(p,wendy.getPoint());
		
		wendy.getPoint().translate(5, 10);
		assertEquals(wendy.getPoint(), new Point(5,10));
	}
	
	@Test
	public void testAddingPokemon() {
		
		Trainer keith = new Trainer();
		Pokemon pikachu = new Common(new Random(), "Pikachu", null, PokemonType.ELECTRIC);
		keith.addPokemon(pikachu);
		
		assertEquals(keith.getPokemon().get(0).getName(), "PIKACHU");
	}
	
	@Test
	public void testSteps() {
		
		Trainer sarina = new Trainer();
		assertEquals(sarina.getSteps(),500);
		sarina.decreaseSteps();
		assertEquals(sarina.getSteps(),499);
		sarina.increaseSteps(36);
		assertEquals(sarina.getSteps(), 499 + 36);
		
	}
	
	@Test
	public void testBasicStepPotion() {
		
		Trainer aang = new Trainer();
		BasicStepPotion bsp = new BasicStepPotion();
		aang.addItem(bsp);
		assertTrue(aang.getItems().contains(bsp));
		assertEquals(aang.getSteps(),500);
		aang.useItem(bsp);
		assertEquals(aang.getSteps(),510);
		assertFalse(aang.getItems().contains(bsp));
	}
	
	@Test
	public void testSuperStepPotion() {
		
		Trainer julia = new Trainer();
		SuperStepPotion ssp = new SuperStepPotion();
		julia.addItem(ssp);
		assertTrue(julia.getItems().contains(ssp));
		assertEquals(julia.getSteps(),500);
		julia.useItem(ssp);
		assertEquals(julia.getSteps(),525);
		assertFalse(julia.getItems().contains(ssp));
	}
	
	@Test
	public void testHyperStepPotion() {
		
		Trainer pedro = new Trainer(300,20);
		HyperStepPotion hsp = new HyperStepPotion();
		pedro.addItem(hsp);
		assertTrue(pedro.getItemQuantities().get(hsp.getName()) == 1);
		assertEquals(pedro.getSteps(),300);
		pedro.useItem(hsp);
		assertEquals(pedro.getSteps(),350);
		assertFalse(pedro.getItems().contains(hsp));
	}
	
	@Test
	public void testAddRemoveItems() {
		
		Trainer ash = new Trainer();
		Harmonica h = new Harmonica();
		PokeBall pb = new PokeBall();
		
		//Test adding and using a harmonica
		
		ash.useItem(h);
		ash.addItem(h);
		assertTrue(ash.getItemQuantities().get(h.getName()) == 1);
		ash.useItem(h);
		assertTrue(ash.getItemQuantities().get(h.getName()) == 0);
		
		//Test adding and using a pokeball
		
		assertTrue(ash.getItemQuantities().get(pb.getName()) == 30);
		ash.addItem(pb);
		assertTrue(ash.getItemQuantities().get(pb.getName()) == 31);
		ash.useItem(pb);
		assertTrue(ash.getItemQuantities().get(pb.getName()) == 30);
	}
	
	@Test
	public void testTeleporter() {
		
		Trainer misty = new Trainer();
		misty.setPoint(new Point(5,10));									//Set trainer's point to 5, 10
		Teleporter tp = new Teleporter();
		
		misty.useItem(tp);													//Attempt to use item while it is not in inventory. Should not throw an exception
		
		misty.addItem(tp);
		assertTrue(misty.getItemQuantities().get(tp.getName()) == 1);
		assertFalse(tp.isSet());											//Teleporter should not be set. It has not been used yet.
		
		misty.useItem(tp);													//Attempt to use item now that teleporter is in inventory.
		assertTrue(misty.getItemQuantities().get(tp.getName()) == 1);		//Teleporter should still be in inventory
		assertTrue(tp.isSet());												//Teleporter should now be set
		assertEquals(tp.getTeleportPoint(),misty.getPoint());				//Teleport point should be set to the trainer's current location on use
		
		misty.setPoint(new Point(12,37));									//Set Trainer to a new point
		assertNotEquals(misty.getPoint(), new Point(5,10));					//Verify Misty is no longer in the same spot
		
		misty.useItem(tp);
		assertTrue(misty.getItemQuantities().get(tp.getName()) == 0);		//Verify that misty no longer has a teleporter
		assertEquals(misty.getPoint(), new Point(5,10));					//Verify that misty transported to original position
	}
}
