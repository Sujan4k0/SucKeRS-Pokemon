package model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum Obstacle implements Tile{
	
	ICE(TerrainType.ICE), CAVE(TerrainType.CAVE), POOP(TerrainType.POOP);
	
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
