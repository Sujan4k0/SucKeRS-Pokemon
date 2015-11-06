package model.Mapping;

public class Map {
	
	// Map will be MAP_DIM by MAP_DIM tiles
	static int MAP_DIM = 10;
	
	// Map will have width and height of TILE_DIM * MAP_DIM
	static int MAP_SIZE = MAP_DIM * Tile.TILE_DIM;
	
	private Tile[][] board;
	
	public Map() {
		
		createMap();
		
	}
	
	private void createMap() {
		
		board = new Tile[MAP_DIM][MAP_DIM];
		
		//TODO: Fill board[][] with Tiles
	}
	
	public Tile[][] getBoard() {
		
		return board;
		
	}

}
