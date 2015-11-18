/*=========================================================================== 
 | Assignment: FINAL PROJECT: [Item] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |	       [Keith Smith  (browningsmith@email.arizona.edu)]
 |	       [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: Creates an abstract class for items to be extended by various types of items. All Items will have a name attribute. 
 *==========================================================================*/
package model.ItemModel;

import java.awt.Image;
import java.io.Serializable;

public abstract class Item implements Comparable<Item>, Serializable {

	private static final long serialVersionUID = 1L;

	protected String name;
	private boolean forTrainer;
	private boolean forPokemon;
	protected transient Image image;

	/*---------------------------------------------------------------------
	 |  Purpose:  	    [Constructor]
	 *---------------------------------------------------------------------*/
	public Item() {
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getName]
	 |  Purpose:  	    [return the name of an item]
	 |  Returns:  	    [A string representing the name of an item]
	 *---------------------------------------------------------------------*/
	public String getName() {
		return name;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [setName]
	 |  Purpose:  	    [sets the name of an item]
	 |  Parameters:     [String for the name]
	 *---------------------------------------------------------------------*/
	public void setName(String n) {
		name = n;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isForPokemon]
	 |  Purpose:  	    [return whether item can be used on pokemon]
	 |  Returns:  	    [boolean for above]
	 *---------------------------------------------------------------------*/
	public boolean isForPokemon() {
		return forPokemon;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [setForPokemon]
	 |  Purpose:  	    [sets whether item can be used on pokemon]
	 |  Parameters:  	[boolean for above]
	 *---------------------------------------------------------------------*/
	public void setForPokemon(boolean forPokemon) {
		this.forPokemon = forPokemon;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [isForTrainer]
	 |  Purpose:  	    [return whether item can be used on Trainer]
	 |  Returns:  	    [boolean for above]
	 *---------------------------------------------------------------------*/
	public boolean isForTrainer() {
		return forTrainer;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [setForTrainers]
	 |  Purpose:  	    [sets whether item can be used on pokemon]
	 |  Parameters:  	[boolean for above]
	 *---------------------------------------------------------------------*/
	public void setForTrainer(boolean forTrainer) {
		this.forTrainer = forTrainer;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getItemByName]
	 |  Purpose:  	    [returns the item based on a string representing its name]
	 |  Parameters:  	[string representing an items name]
	 |  Returns:		[an Item]
	 *---------------------------------------------------------------------*/
	public static Item getItemByName(String name) {
		switch (name.toUpperCase()) {

		case "POKEBALL":
			return new PokeBall();
		case "HYPER STEP POTION":
			return new HyperStepPotion();
		case "FATIGUE POTION":
			return new FatiguePotion();
		case "HARMONICA":
			return new Harmonica();
		case "SUPER STEP POTION":
			return new SuperStepPotion();
		case "BASIC STEP POTION":
			return new BasicStepPotion();
		case "TELEPORTER":
			return new Teleporter();
		default:
			break;
		}

		return null;
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [compareTo]
	 |  Purpose:  	    [compares two items for use with sorting]
	 |  Parameters:  	[An item to compare]
	 |  Returns:		[An int representing the relative order of the 2
	  					item's names]
	 *---------------------------------------------------------------------*/
	@Override
	public int compareTo(Item t) {
		System.out.println("Compare to called on : " + t.getName());
		return this.getName().compareTo(t.getName());
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [equals]
	 |  Purpose:  	    [compares two objects to see if they are identical]
	 |  Parameters:  	[an Object to compare]
	 |  Returns:		[a boolean representing if they are equal or not]
	 *---------------------------------------------------------------------*/
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item))
			return false;

		final Item other = (Item) obj;
		if (!name.equals(other.getName()))
			return false;
		return true;
	}
}
