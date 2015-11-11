/*=========================================================================== 
 | Assignment: FINAL PROJECT: [BasicStepPotion] 
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
 | Description:  a step potion which gives a trainer 10 extra steps
 *==========================================================================*/
package model.ItemModel;

public class BasicStepPotion extends StepPotion{
	/*---------------------------------------------------------------------
	 |  Purpose:  	    [Constructor]
	 *---------------------------------------------------------------------*/
	public BasicStepPotion(){
		setStepBonus(10);
		super.setName("Basic Step Potion");
	}
}
