package test;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;
import controller.CEAGame;
import model.TrainerModel.Trainer;
import view.PokemonDatabase;

public class CEAGameTestWin {

	@Test
	public void testWinCondition() {
		
		CEAGame game = new CEAGame(new Random());
		
		Trainer ash = game.getTrainer();
		PokemonDatabase database = new PokemonDatabase();
		
		ash.addPokemon(database.getMew());
		ash.addPokemon(database.getCyndaquil());
		ash.addPokemon(database.getDiglett());
		ash.addPokemon(database.getExeggutor());
		ash.addPokemon(database.getPikachu());
		ash.addPokemon(database.getGyrados());
		ash.addPokemon(database.getMagikarp());
		ash.addPokemon(database.getLuvdisc());
		ash.addPokemon(database.getRhydon());
		ash.addPokemon(database.getSimisage());
		
		assertFalse(game.isGameWon());
		
		ash.addPokemon(database.getSteelix());
		
		assertTrue(game.isGameWon());
	}

}
