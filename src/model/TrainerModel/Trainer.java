/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Trainer] 
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
 | Description: This class implements the pokemon trainer of the game. The
 | trainer keeps track of its location, inventory of items and captured
 | pokemon, number of pokeballs left, number of steps left, whether or not
 | the trainer is fatigued, and the last action the trainer used during a 
 | battle
 *===========================================================================*/

package model.TrainerModel;

import java.awt.Point;
import java.util.ArrayList;

import model.ItemModel.Item;
import model.ItemModel.StepPotion;
import model.ItemModel.Teleporter;
//import model.MapModel.Map.Direction; //This was removed ?
import model.PokemonModel.Pokemon;

public class Trainer {

	private int steps; //Number of steps the trainer has left to take
	private ArrayList<Item> items; //trainer's inventory of items
	private ArrayList<Pokemon> capturedPokemon; //trainer's collection of pokemon
	private int currentPokeballs;
	private boolean fatigued;
	private TrainerAction trainerAction;
	private Point trainerPosition;

	/*-----------------------------------------------------------------------------
	 |  Method name:    [Trainer]
	 |  Purpose:  	    [Create a new Trainer with default 500 steps 30 pokeballs]
	 |  Parameters:     [none]
	 |  Returns:  	    [N/A]
	 *-----------------------------------------------------------------------------*/

	public Trainer() {

		steps = 500;
		items = new ArrayList<Item>();
		capturedPokemon = new ArrayList<Pokemon>();
		currentPokeballs = 30;
		fatigued = false;
		trainerAction = TrainerAction.STAND_GROUND;
		trainerPosition = null;
	}

	/*-----------------------------------------------------------------------------
	 |  Method name:    [Trainer]
	 |  Purpose:  	    [Create a new Trainer with 's' steps 'p' pokeballs]
	 |  Parameters:     [int s: Number of steps to start the trainer with]
	 |                  [int p: Number of pokeballs to start the trainer with]
	 |  Returns:  	    [N/A]
	 *-----------------------------------------------------------------------------*/

	public Trainer(int s, int p) {

		steps = s;
		items = new ArrayList<Item>();
		capturedPokemon = new ArrayList<Pokemon>();
		currentPokeballs = p;
		fatigued = false;
		trainerAction = TrainerAction.STAND_GROUND;
		trainerPosition = null;
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getPoint]
	 |  Purpose:  	    [gets the trainer's position]
	 |  Parameters:     [none]
	 |  Returns:  	    [Point]
	 *---------------------------------------------------------------------*/
	
	public Point getPoint() {

		return trainerPosition;
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [setPoint]
	 |  Purpose:  	    [Sets a new Point representing the trainer's position]
	 |  Parameters:     [Point p: The trainer's new position]
	 |  Returns:  	    [none]
	 *---------------------------------------------------------------------*/

	public void setPoint(Point p) {

		trainerPosition = new Point(p);
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getItems]
	 |  Purpose:  	    [Returns the list of items that the trainer has]
	 |  Parameters:     [none]
	 |  Returns:        [ArrayList<Item>]
	 *---------------------------------------------------------------------*/
	
	public ArrayList<Item> getItems() {
		
		return items;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [addItem]
	 |  Purpose:  	    [puts a found item in the trainer's inventory]
	 |  Parameters:     [an Item i]
	 *---------------------------------------------------------------------*/
	public void addItem(Item i) {
		items.add(i);
		//notifyObservers
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [useItem]
	 |  Purpose:  	    [Uses an item and removes it from inventory]
	 |  Parameters:     [an Item i]
	 *---------------------------------------------------------------------*/
	public void useItem(Item i) {
		if (i.getName() == "Teleporter") {
			Teleporter t = (Teleporter) i;
			if (t.isSet()) {
				//Teleport animation
				trainerPosition = new Point((int) t.getTeleportPoint().getX(),
						(int) t.getTeleportPoint().getY());
				items.remove(i);
				//update observers for inventory
			} else {
				t.setPoint(trainerPosition);
			}
		} else if (i.getName().equals("FatiguePotion")) {
			if (this.fatigued) {
				fatigued = false;
				items.remove(i);
				//update observers for inventory

			} else {
				System.out.println("You don't need that now.");//just for testing, will later update view to display this message
			}
		} else if (i.getName().equals("Basic Step Potion")
				|| i.getName().equals("Super Step Potion")
				|| i.getName().equals("Hyper Step Potion")) {
			this.steps += ((StepPotion) i).getStepBonus();
			items.remove(i);
			//update observers for inventory
		}
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getPoint]
	 |  Purpose:  	    [Returns the current Point of the trainer]
	 |  Parameters:     [none]
	 |  Returns:  	    [Point]
	 *---------------------------------------------------------------------*/
	
	public boolean isFatigued() {
		
		return fatigued;
	}
	
	/*-----------------------------------------------------------------------------
	 |  Method name:    [setFatigued]
	 |  Purpose:  	    [Changes whether or not the trainer is fatigued]
	 |  Parameters:     [boolean v, true to set trainer to fatigued and vice versa]
	 |  Returns:  	    [none]
	 *-----------------------------------------------------------------------------*/
	
	public void setFatigued(boolean v) {
		
		fatigued = v;
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getSteps]
	 |  Purpose:  	    [Returns number of steps the trainer has left]
	 |  Parameters:     [none]
	 |  Returns:  	    [int steps]
	 *---------------------------------------------------------------------*/
	
	public int getSteps() {
		
		return steps;
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [decreaseSteps]
	 |  Purpose:  	    [Decreases steps by either 1, or 2 if trainer is fatigued]
	 |  Parameters:     [none]
	 |  Returns:  	    [none]
	 *---------------------------------------------------------------------*/
	
	public void decreaseSteps() {
		
		if (isFatigued()) steps -= 2;
		else steps -= 1;
		
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    []
	 |  Purpose:  	    []
	 |  Parameters:     []
	 |  Returns:  	    []
	 *---------------------------------------------------------------------*/
	
	/*public void throwRock() {
		
		
	}
	
	public void giveBait() {
		
		
	}
	
	public void throwPokeball() {
		
		
	}
	
	public void runAway() {
		
		
	}*/

}
