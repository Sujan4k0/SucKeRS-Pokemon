package model.MapModel;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum Obstacle implements Tile{
	
	ROCK_1(TerrainType.GENERIC), ROCK_2(TerrainType.FOREST), ROCK_3(TerrainType.CAVE), 
	ROCK_4(TerrainType.GENERIC);
	
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

	@Override
	public int getIndex() {
		return this.ordinal();
	}

}
