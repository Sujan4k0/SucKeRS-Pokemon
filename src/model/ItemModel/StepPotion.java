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

public abstract class StepPotion extends Item{
	private int stepBonus;

	/*---------------------------------------------------------------------
	 |  Purpose:  	    [Constructor]
	 *---------------------------------------------------------------------*/
	public StepPotion(){
		super.setForPokemon(false);
		super.setForTrainer(true);
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [getStepBonus]
	 |  Purpose:  	    [returns the amount of steps to give the trainer]
	 |  Returns:  	    [step bonus]
	 *---------------------------------------------------------------------*/
	public int getStepBonus() {
		return stepBonus;
	}
	/*---------------------------------------------------------------------
	 |  Method name:    [setStepBonus]
	 |  Purpose:  	    [sets the amount of steps to give the trainer]
	 |  Parameters:     [an int for the total step bonus]
	 *---------------------------------------------------------------------*/
	public void setStepBonus(int stepBonus) {
		this.stepBonus = stepBonus;
	}
}
