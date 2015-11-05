/*=========================================================================== 
 | Assignment: FINAL PROJECT: [] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |         [Keith Smith  (browningsmith@email.arizona.edu)]
 |         [Ryan Kaye    (kayeryan1@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: The superclass for defining different Pokemon. 
 *==========================================================================*/

package model;

import java.awt.image.BufferedImage;

public abstract class Pokemon {

	private String name; // name of this Pokemon
	private BufferedImage[] sprite; // different graphical views for this Pokemon
	
	private PokemonType type; // element mastery of the Pokemon
	private double catchPercentage; // likelihood of being caught (adjustable)
	private double runPercentage; // likelihood of running away
	
	public Pokemon(String n, BufferedImage[] i, PokemonType t, double cp, double rp) {
		
	    // store all of the given parameters
		this.name = n;
		this.sprite = i;
		this.type = t;
		this.catchPercentage = cp;
		this.runPercentage = rp;
	}
	
	public abstract void adjustCatch(TrainerAction action);
	
	public abstract void adjustRun(TrainerAction action);
	
	public void respond() {
	    
	    // generic body, if other types (common) do not write their
	    // own, this one gets used.
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
}
