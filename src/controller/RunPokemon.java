/*=========================================================================== 
 | Assignment: FINAL PROJECT: [RunPokemon] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |             [Keith Smith  (browningsmith@email.arizona.edu)]
 |             [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: Creates a GUI for the game. Pokemon runner.
 *===========================================================================*/


package controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.PokemonGUI;

public class RunPokemon {

	public static void main(String[] args) {
		
	     // changing L&F to nimbus 
		 try {
	            // Set cross-platform Java L&F (also called "Metal")
	            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	        } catch (UnsupportedLookAndFeelException e) {
	            // handle exception
	        } catch (ClassNotFoundException e) {
	            // handle exception
	        } catch (InstantiationException e) {
	            // handle exception
	        } catch (IllegalAccessException e) {
	            // handle exception
	        }

	        new PokemonGUI(); // creating a GUI 
	}
}
