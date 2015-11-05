package model;

import java.awt.image.BufferedImage;

public abstract class Pokemon {

	private String name;
	private BufferedImage[] sprite;
	/*
	 * type is currently a String. I believe we were talking about changing this to an enum
	 */
	
	private String type;
	private double catchPercentage;
	private double runPercentage;
	
	public Pokemon(String n, BufferedImage[] i, String t, double cp, double rp) {
		
		this.name = n;
		this.sprite = i;
		this.type = t;
		this.catchPercentage = cp;
		this.runPercentage = rp;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
	
}
