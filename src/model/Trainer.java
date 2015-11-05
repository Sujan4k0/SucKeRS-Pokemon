package model;

import java.util.ArrayList;

public class Trainer {
	
	private int steps; //Number of steps the trainer has left to take
	private ArrayList<Item> items; //trainer's inventory of items
	private boolean fatigued;
	//private ? pokeball   //Not sure what this instance variable is atm
	
	/***************************************
	 * public Trainer:
	 * 
	 * Creates a new instance of a 
	 * Trainer with default of 500 
	 * steps and an empty items inventory
	 ***************************************/
	
	public Trainer() {
		
		this.steps = 500;
		items = new ArrayList<Item>();
	}
	
	/***************************************
	 * public Trainer(int s):
	 * 
	 * Creates a new instance of a 
	 * Trainer with s steps 
	 * steps and an empty items inventory
	 ***************************************/
	
	public Trainer(int s) {
		
		this.steps = s;
		items = new ArrayList<Item>();
	}

}
