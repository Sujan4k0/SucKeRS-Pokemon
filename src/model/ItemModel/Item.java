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

public abstract class Item {
	protected static String name;
	
	
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
}
