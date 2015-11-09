package model.MapModel;

public interface Tile {
	
	public static final int SIZE = 50;

	public void interactWithTrainer();

	public TerrainType getTerrainType();

}
