package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Random;

import org.junit.Test;

import model.PokemonModel.*;
import model.ItemModel.*;
import model.TrainerModel.Trainer;

public class TrainerTests {

	/*@Test
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
	}*/
	
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
		
	}
	
	@Test
	public void testFatiguedSteps() {
		
		Trainer sujan = new Trainer();
		assertEquals(sujan.getSteps(),500);
		sujan.setFatigued(true);
		sujan.decreaseSteps();
		assertEquals(sujan.getSteps(),498);
	}
	
	@Test
	public void testFatiguedPotion() {
		
		Trainer ryan = new Trainer();
		Item fp = new FatiguePotion();
		ryan.addItem(fp);
		assertTrue(ryan.getItems().contains(fp));
		
		ryan.setFatigued(false);
		ryan.useItem(fp);
		assertFalse(ryan.isFatigued());
		assertTrue(ryan.getItems().contains(fp));
		
		ryan.setFatigued(true);
		assertTrue(ryan.isFatigued());
		ryan.useItem(fp);
		assertFalse(ryan.isFatigued());
		assertFalse(ryan.getItems().contains(fp));
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
		assertTrue(pedro.getItems().contains(hsp));
		assertEquals(pedro.getSteps(),300);
		pedro.useItem(hsp);
		assertEquals(pedro.getSteps(),350);
		assertFalse(pedro.getItems().contains(hsp));
	}
	
	/*@Test
	public void test() {
		fail("Not yet implemented");
	}*/

}
