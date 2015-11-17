package controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.PokemonGUI;

public class RunPokemon {

	public static void main(String[] args) {
		
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

	        new PokemonGUI();
	}
}
