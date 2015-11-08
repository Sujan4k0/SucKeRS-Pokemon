package model.MapModel;

public interface Tile {
	
	public static int SIZE = 50;

	public void interactWithTrainer();

	public TerrainType getTerrainType();
	
	public int getIndex();

}
