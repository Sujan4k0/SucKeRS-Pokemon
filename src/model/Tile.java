package model;

public interface Tile {
	
	public static int TILE_DIM = 30;

	public void interactWithTrainer();

	public TerrainType getTerrainType();

}
