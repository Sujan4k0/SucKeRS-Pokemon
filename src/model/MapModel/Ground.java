package model.MapModel;


public enum Ground implements Tile {
	
	ICE_1(TerrainType.ICE), ICE_2(TerrainType.ICE), ICE_3(TerrainType.ICE),
	ICE_4(TerrainType.ICE), ICE_5(TerrainType.ICE),
	CAVE_1(TerrainType.CAVE), CAVE_2(TerrainType.CAVE), CAVE_3(TerrainType.CAVE),
	CAVE_4(TerrainType.CAVE), CAVE_5(TerrainType.CAVE);
	
	private TerrainType terrainType;
	
	Ground(TerrainType tt) {
		terrainType = tt;
	}

	@Override
	public void interactWithTrainer() {
		// TODO Sound effect when trainer walking on
		
		// play terrainType.getSound();
	}

	@Override
	public TerrainType getTerrainType() {
		return terrainType;
	}

	@Override
	public int getIndex() {
		return this.ordinal();
	}

}
