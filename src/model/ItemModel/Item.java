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
	protected static String name;
	private boolean forTrainer;
	private boolean forPokemon;
	protected transient Image image;
	
	
	public Item(){
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getName]
	 |  Purpose:  	    [return the name of an item]
	 |  Parameters:     []
	 |  Returns:  	    [A string representing the name of an item]
	 *---------------------------------------------------------------------*/
	public String getName(){
		return name;
	}
	public void setName(String n){
		name= n;
	}

	public boolean isForPokemon() {
		return forPokemon;
	}

	public void setForPokemon(boolean forPokemon) {
		this.forPokemon = forPokemon;
	}

	public boolean isForTrainer() {
		return forTrainer;
	}

	public void setForTrainer(boolean forTrainer) {
		this.forTrainer = forTrainer;
	}
}
