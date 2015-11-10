package controller;

import model.MapModel.Map;
import model.TrainerModel.Trainer;

public class CEAGame extends GameMode {

	public CEAGame(Map m, Trainer t) {
		super(m, t);
	}

	@Override
	public boolean isGameWon() {
		// TODO add winning condition
		return false;
	}

	@Override
	public boolean isGameLost() {
		// TODO add losing conditions
		return false;
	}
	
	

}
