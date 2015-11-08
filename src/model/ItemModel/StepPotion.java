/*=========================================================================== 
 | Assignment: FINAL PROJECT: [StepPotion] 
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
 | Description:  An item that adds steps to a trainer's remaining step count
 *==========================================================================*/
package model.ItemModel;

import java.util.Random;

public class StepPotion extends Item{
	private int stepBonus;

	/*---------------------------------------------------------------------
	 |  Purpose:  	    [Constructor, determines the step value of the potion]
	 *---------------------------------------------------------------------*/
	public StepPotion(){
		Random r = new Random();
		int random = r.nextInt(10);
		if (random<6){
			setStepBonus(10);
			super.setName("Basic Step Potion");
		}
		else if (random<9){
			setStepBonus(25);
			super.setName("Super Step Potion");		
		}
		else {
			setStepBonus(50);
			super.setName("Hyper Step Potion");
		}
	}

	public int getStepBonus() {
		return stepBonus;
	}

	public void setStepBonus(int stepBonus) {
		this.stepBonus = stepBonus;
	}
}
