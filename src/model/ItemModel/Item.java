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

public abstract class Item implements Serializable{
	protected String name;
	private boolean forTrainer;
	private boolean forPokemon;
	protected transient Image image;
	
	
	public Item(){
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getName]
	 |  Purpose:  	    [return the name of an item]
	 |  Returns:  	    [A string representing the name of an item]
	 *---------------------------------------------------------------------*/
	public String getName(){
		return name;
	}
	/*---------------------------------------------------------------------
	 |  Method name:    [setName]
	 |  Purpose:  	    [sets the name of an item]
	 |  Parameters:     [String for the name]
	 *---------------------------------------------------------------------*/
	public void setName(String n){
		name= n;
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
	
	public static Item getItemByName(String name) {
		switch (name) {
		
		case "PokeBall":
			return new PokeBall();
		case "Hyper Step Potion":
			return new HyperStepPotion();
		case "Fatigue Potion":
			return new FatiguePotion();
		case "Harmonica":
			return new Harmonica();
		case "Super Step Potion":
			return new SuperStepPotion();
		case "Basic Step Potion":
			return new BasicStepPotion();
		case "Teleporter":
			return new Teleporter();
		default:
			break;
		}
		
		return null;
	}
}
