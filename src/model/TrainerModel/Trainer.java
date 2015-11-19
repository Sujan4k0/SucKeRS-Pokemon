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
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import model.ItemModel.Item;
import model.ItemModel.PokeBall;
import model.ItemModel.StepPotion;
import model.ItemModel.Teleporter;
import model.PokemonModel.Pokemon;

public class Trainer implements Serializable {

	private int steps; //Number of steps the trainer has left to take
	private HashMap<String, Integer> itemQuantities; //trainer's inventory of items
	private List<Item> items;
	private List<Pokemon> capturedPokemon; //trainer's collection of pokemon
	private boolean fatigued;
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
		itemQuantities = new HashMap<String, Integer>();
		capturedPokemon = new ArrayList<Pokemon>();
		fatigued = false;
		trainerPosition = null;
		for (int i = 0; i < 30; i++) {
			addItem(new PokeBall());
		}
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
		itemQuantities = new HashMap<String, Integer>();
		capturedPokemon = new ArrayList<Pokemon>();
		fatigued = false;
		trainerPosition = null;
		for (int i = 0; i < p; i++) {
			addItem(new PokeBall());
		}
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
	 |  Method name:    [getItemQuantities]
	 |  Purpose:  	    [Returns the list of the quantity of each item that the trainer has]
	 |  Parameters:     [none]
	 |  Returns:        [HashMap<String, Integer>]
	 *---------------------------------------------------------------------*/
	public HashMap<String, Integer> getItemQuantities() {

		return itemQuantities;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getItems]
	 |  Purpose:  	    [Returns the list of items that the trainer has]
	 |  Parameters:     [none]
	 |  Returns:        [ArrayList<Item>]
	 *---------------------------------------------------------------------*/
	public List<Item> getItems() {
		return items;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [addItem]
	 |  Purpose:  	    [puts a found item in the trainer's inventory and
	 |						increments the amount of that item in the hash table]
	 |  Parameters:     [an Item i]
	 *---------------------------------------------------------------------*/
	public void addItem(Item i) {
		items.add(i);
		if (!itemQuantities.containsKey(i.getName())) {
			itemQuantities.put(i.getName(), 1);
		} else {
			itemQuantities.put(i.getName(), (itemQuantities.get(i.getName()) + 1));
		}
		//notifyObservers
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [useItem]
	 |  Purpose:  	    [Uses an item and removes it from inventory]
	 |  Parameters:     [an Item i]
	 *---------------------------------------------------------------------*/
	public void useItem(Item i) {
		
		if (i.getName() == "Teleporter") {
			//find the teleporter for the arraylist of items
			//because we need the point associated with it
			Teleporter t = null;
			for (int j = 0; j < items.size(); j++) {
				Item x = items.get(j);
				if (x.getName().equals("Teleporter")) {
					t = (Teleporter) x;
					break;
				}
			}
			
			if (t == null) 
				return;
			else if (t.isSet()) {
				
				//Teleport animation
				trainerPosition =
						new Point((int) t.getTeleportPoint().getX(), (int) t.getTeleportPoint()
								.getY());
				items.remove(t);
				itemQuantities.put(t.getName(), (itemQuantities.get(t.getName()) - 1));
				//update observers for inventory
			} else {
				t.setPoint(trainerPosition);
				
				return;
			}
		} else

		if (items.contains(i)) {
			if (i.getName().equals("Fatigue Potion")) {
				if (isFatigued()) {
					setFatigued(false);
					//update observers for inventory

				} else {
					return;
				}
			} else if (i.getName().equals("Basic Step Potion")
					|| i.getName().equals("Super Step Potion")
					|| i.getName().equals("Hyper Step Potion")) {
				this.steps += ((StepPotion) i).getStepBonus();
				//update observers for inventory
			} else if (i.getName().equals("PokeBall")) {
			} else if (i.getName().equals("Harmonica")) {

			}

			items.remove(i);
			itemQuantities.put(i.getName(), (itemQuantities.get(i.getName()) - 1));

		} else {
		}
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getPokemon]
	 |  Purpose:  	    [Returns the list of Pokemon]
	 |  Parameters:     [none]
	 |  Returns:  	    [ArrayList<Pokemon>]
	 *---------------------------------------------------------------------*/

	public List<Pokemon> getPokemon() {

		return capturedPokemon;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [addPokemon]
	 |  Purpose:  	    [Adds a new Pokemon to the list of captured pokemon]
	 |  Parameters:     [Pokemon p: the Pokemon being added]
	 |  Returns:  	    [none]
	 *---------------------------------------------------------------------*/

	public void addPokemon(Pokemon p) {

		capturedPokemon.add(p);
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isFatigued]
	 |  Purpose:  	    [Returns whether the trainer is fatigued]
	 |  Parameters:     [none]
	 |  Returns:  	    [boolean: whether the trainer is fatigued]
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

		if (isFatigued())
			steps -= 2;
		else
			steps -= 1;

	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [increaseSteps]
	 |  Purpose:  	    [Increase steps by int 's']
	 |  Parameters:     [int 's']
	 |  Returns:  	    [none]
	 *---------------------------------------------------------------------*/
	
	public void increaseSteps(int s) {
		
		steps += s;
	}
}
