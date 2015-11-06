package model.Mapping;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum Obstacle implements Tile{
	
	ICE_1(TerrainType.ICE), ICE_2(TerrainType.ICE), ICE_3(TerrainType.ICE),
	ICE_4(TerrainType.ICE), ICE_5(TerrainType.ICE),
	CAVE_1(TerrainType.CAVE), CAVE_2(TerrainType.CAVE), CAVE_3(TerrainType.CAVE),
	CAVE_4(TerrainType.CAVE), CAVE_5(TerrainType.CAVE);
	
	private TerrainType terrainType;
	
	Obstacle(TerrainType tt) {
		
		terrainType = tt;
	}

	@Override
	public void interactWithTrainer() {
		// TODO may or may not have interaction		
	}

	@Override
	public TerrainType getTerrainType() {
		return terrainType;
	}

}
