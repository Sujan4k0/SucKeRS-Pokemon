package model;

public class Map {
	
	static int MAP_DIM = 100;
	
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
