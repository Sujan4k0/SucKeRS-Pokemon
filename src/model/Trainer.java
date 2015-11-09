package model;

import java.awt.Point;
import java.util.ArrayList;

import model.ItemModel.Item;
import model.ItemModel.StepPotion;
import model.ItemModel.Teleporter;
import model.MapModel.Map.Direction;

public class Trainer {

	private int steps; //Number of steps the trainer has left to take
	private ArrayList<Item> items; //trainer's inventory of items
	private boolean fatigued;
	private Point trainerPosition;

	//private ? pokeball   //Not sure what this instance variable is atm

	/***************************************
	 * public Trainer:
	 * 
	 * Creates a new instance of a Trainer with default of 500 steps and an
	 * empty items inventory
	 ***************************************/

	public Trainer() {

		this.steps = 500;
		items = new ArrayList<Item>();
	}

	/***************************************
	 * public Trainer(int s):
	 * 
	 * Creates a new instance of a Trainer with s steps steps and an empty items
	 * inventory
	 ***************************************/

	public Trainer(int s) {

		this.steps = s;
		items = new ArrayList<Item>();
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

	public Point getPoint() {

		return trainerPosition;
	}

	public void setPoint(Point p) {

		trainerPosition = new Point(p);
	}
	
	public boolean isFatigued() {
		
		return fatigued;
	}
	
	public void decreaseSteps() {
		
		if (isFatigued()) steps -= 2;
		else steps -= 1;
		
	}

}
