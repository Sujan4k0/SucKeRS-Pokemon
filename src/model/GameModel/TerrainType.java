/*=========================================================================== 
 | Assignment: FINAL PROJECT: [TerrainType] 
 | 
 | Authors:    [Sujan Patel  (sujan4k0@email.arizona.edu)] 
 |	     	   [Keith Smith  (browningsmith@email.arizona.edu)]
 |	     	   [Ryan Kaye    (rkaye@email.arizona.edu)]
 |             [Sarina White (sarinarw@email.arizona.edu)]
 | 
 | Course: 335 
 | Instructor: Mercer
 | Project Manager/Section Leader: Jeremy Mowery 
 | Due Date: [12.7.15] 
 | 
 | Description: This will later be used for Ground in order to associate
 | specific Pokemon with different TerrainTypes - will also be used for
 | specific sound effects when trainer walks on a Ground Tile.
 *===========================================================================*/ 
package model.GameModel;

public enum TerrainType {
	
	//TODO add walking sound effects for dif terrain types
	ICE("Grass_Step_1.wav", "Grass_Step_2.wav"), GRASS("Grass_Step_1.wav", "Grass_Step_2.wav"), 
	FOREST("Grass_Step_1.wav", "Grass_Step_2.wav"), CAVE("Grass_Step_1.wav", "Grass_Step_2.wav"),
	MYSTERY("Grass_Step_1.wav", "Grass_Step_2.wav"), GENERIC("Grass_Step_1.wav", "Grass_Step_2.wav"), 
	DESERT("Grass_Step_1.wav", "Grass_Step_2.wav"); // etc...
	
	public static final String BASEDIR = "./sounds/walkingSFX/";
	
	private String sfxFilePath1, sfxFilePath2;
	/*---------------------------------------------------------------------
	 |  Method name:    [TerrainType]
	 |  Purpose:  	    [(no purpose currently - may or may not have purpose later)]
	 *---------------------------------------------------------------------*/
	TerrainType(String s1, String s2) {
		setSoundFilePaths(BASEDIR + s1, BASEDIR + s2);
	}
	
	public void setSoundFilePaths(String s1, String s2) {
		sfxFilePath1 = s1;
		sfxFilePath2 = s2;
	}
	
	/*---------------------------------------------------------------------
	 |  Method name:    [getSound]
	 |  Purpose:  	    [TODO: gets the sound associated with the trainer walking
	 |					 on a tile with a specific TerrainType]
	 |  Returns:  	    [TODO: a sound?? currently a String as placeholder]
	 *---------------------------------------------------------------------*/
	public String getSound1() {
		return sfxFilePath1;
	}
	
	public String getSound2() {
		return sfxFilePath2;
	}

}