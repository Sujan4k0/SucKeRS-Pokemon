package model.Mapping;


public enum Ground implements Tile {
	
	ICE(TerrainType.ICE), CAVE(TerrainType.CAVE);
	
	private TerrainType terrainType;
	
	Ground(TerrainType tt) {
		terrainType = tt;
	}

	@Override
	public void interactWithTrainer() {
		// TODO Sound effect on walking on
	}

	@Override
	public TerrainType getTerrainType() {
		return terrainType;
	}

}
