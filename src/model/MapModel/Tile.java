package model.MapModel;

public interface Tile {
	
	public static int TILE_DIM = 50;

	public void interactWithTrainer();

	public TerrainType getTerrainType();
	
	public int getIndex();

}
