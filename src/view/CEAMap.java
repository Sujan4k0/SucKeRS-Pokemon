/*=========================================================================== 
 | Assignment: FINAL PROJECT: [CEAMap] 
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
 | Description: This class extends Map and is used to make a CEAMap, this Map
 | used in the CEAGame (CATCH EM ALL!!!!). The createMap() method is automatically
 | called in the super() constructor.
 *===========================================================================*/
package view;

import java.awt.Point;
import java.util.Random;

import javax.swing.Timer;

import model.GameModel.Ground;
import model.GameModel.Obstacle;
import model.ItemModel.*;
import model.TrainerModel.Trainer;

public class CEAMap extends Map {

	private static final long serialVersionUID = 1L;

	Ground grassy, deserty, icey, cavey, plainy, secrety1, secrety2;

	boolean inLastPart = false; // if player should be in last part of map

	// entire CEAMap is 3 by 3 Maps combined
	public static int WIDTH = (Map.WIDTH * 3), HEIGHT = (Map.HEIGHT * 3);

	/*---------------------------------------------------------------------
	 |  Method name:    [CEAMap]
	 |  Purpose:  	    [Constructs a CEAMap]
	 *---------------------------------------------------------------------*/
	public CEAMap(Random rand) {
		super(rand);
		bgPath = "./sounds/Ruby_Sapphire_SafariZone.mp3";
	}

	/*---------------------------------------------------------------------
	 |  Method name:    [createMap]
	 |  Purpose:  	    [To fill the Ground and Obstacle arrays with the necessary
	 | 					Tiles that are unique to the CEAMap format]
	 *---------------------------------------------------------------------*/
	@Override
	public void createMap() {

		// these values are needed in order for the move
		// methods to work (refer to Map.java)
		w = CEAMap.WIDTH;
		h = CEAMap.HEIGHT;

		grassy = Ground.FOREST_1;
		deserty = Ground.SAND_2;
		icey = Ground.ICE_1;
		cavey = Ground.CAVE_1;
		plainy = Ground.GRASS_2;
		secrety1 = Ground.BINARY_3;
		secrety2 = Ground.BINARY_3;

		groundTiles = new Ground[h][w];
		obstacleTiles = new Obstacle[h][w];

		// fill all groundTiles with some Ground so game doesn't explode yoyoyoyo
		for (int i = 0; i < groundTiles.length; i++) {
			for (int j = 0; j < groundTiles[0].length; j++)
				groundTiles[i][j] = Ground.CAVE_1;

		}

		makePlainyLand();
		makeGrassyLand();
		makeDesertyLand();
		makeIceyLand();
		makeCaveyLand();
		makeSuperySecretyLand();

		// grassy land = bush
		// cave = rocks
		// ice = ice blocks
		// desert = cactus
		// rocky enclosure with entrance
		// zig-zag rocky paths?

		// first area is all teleport land YO
		// obstacles[0][0] to height - 1, width - 1

		// grassy land
		// circly enclosure 
		// obstacleTiles[Map.HEIGHT][Map.WIDTH]

		// desert land
		// randomly placed cactus :D

		// ice land
		// look at pic

		// teleport land
		// alternating 0 1 tiles

		// starts the game in the plain area
		setTrainerPoint(new Point(Map.HEIGHT + Map.HEIGHT / 2, 2));
		moveDown();

		// starts in secrety land
		//setTrainerPoint(new Point(Map.HEIGHT / 2, 2));

	}

	private void makeGrassyLand() {
		for (int i = Map.HEIGHT; i < 2 * Map.HEIGHT; i++) {
			for (int j = Map.WIDTH; j < 2 * Map.WIDTH; j++) {
				groundTiles[i][j] = grassy;
				if (i == Map.HEIGHT || j == Map.WIDTH || j == 2 * Map.WIDTH - 1
						|| i == 2 * Map.HEIGHT - 1)
					obstacleTiles[i][j] = Obstacle.TREE_PALM;
				// grassy to plainy tiles
				if (j == Map.WIDTH) {
					groundTiles[i][Map.WIDTH] = Ground.GRASS_2_1_HOR;
				}
			}
		}

		// the exit/entrance to plainy land
		for (int i = Map.HEIGHT + Map.HEIGHT / 2 - 1; i < Map.HEIGHT + Map.HEIGHT / 2 + 2; i++) {
			obstacleTiles[i][Map.WIDTH] = null;
		}

		// the exit/entrance to deserty land
		for (int i = Map.HEIGHT + Map.HEIGHT / 2 - 1; i < Map.HEIGHT + Map.HEIGHT / 2 + 2; i++) {
			obstacleTiles[i][Map.WIDTH * 2 - 1] = null;
			groundTiles[i][Map.WIDTH * 2 - 1] = deserty;
		}

		// the exit/entrance to cavey land
		for (int i = Map.WIDTH + Map.WIDTH / 2 - 1; i < Map.WIDTH + Map.WIDTH / 2 + 2; i++) {
			obstacleTiles[Map.HEIGHT][i] = null;
			groundTiles[Map.HEIGHT][i] = cavey;
		}

		// the exit/entrance to icey land
		for (int i = Map.WIDTH + Map.WIDTH / 2 - 1; i < Map.WIDTH + Map.WIDTH / 2 + 2; i++) {
			obstacleTiles[Map.HEIGHT * 2 - 1][i] = null;
			groundTiles[Map.HEIGHT * 2 - 1][i] = icey;
		}
		//tree clusters
		//top left item tree		
		obstacleTiles[Map.HEIGHT+1][Map.WIDTH+3]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+1][Map.WIDTH+4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+1][Map.WIDTH+5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+2][Map.WIDTH+5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+3][Map.WIDTH+3]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+3][Map.WIDTH+4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+3][Map.WIDTH+5]= Obstacle.TREE_PALM;
		
		//bottom left solid tree
		obstacleTiles[Map.HEIGHT*2-4][Map.WIDTH+3]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-4][Map.WIDTH+4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-4][Map.WIDTH+5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-3][Map.WIDTH+3]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-3][Map.WIDTH+4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-3][Map.WIDTH+5]= Obstacle.TREE_PALM;		
		obstacleTiles[Map.HEIGHT*2-2][Map.WIDTH+3]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-2][Map.WIDTH+4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-2][Map.WIDTH+5]= Obstacle.TREE_PALM;
		
		//top right solid tree
		obstacleTiles[Map.HEIGHT+1][Map.WIDTH*2-5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+1][Map.WIDTH*2-4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+1][Map.WIDTH*2-3]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+2][Map.WIDTH*2-5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+2][Map.WIDTH*2-4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+2][Map.WIDTH*2-3]= Obstacle.TREE_PALM;		
		obstacleTiles[Map.HEIGHT+3][Map.WIDTH*2-5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+3][Map.WIDTH*2-4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT+3][Map.WIDTH*2-3]= Obstacle.TREE_PALM;
		
		//bottom right item tree
		obstacleTiles[Map.HEIGHT*2-4][Map.WIDTH*2-5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-4][Map.WIDTH*2-4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-4][Map.WIDTH*2-3]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-3][Map.WIDTH*2-5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-2][Map.WIDTH*2-5]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-2][Map.WIDTH*2-4]= Obstacle.TREE_PALM;
		obstacleTiles[Map.HEIGHT*2-2][Map.WIDTH*2-3]= Obstacle.TREE_PALM;
	}

	private void makeIceyLand() {

		int minH = Map.HEIGHT * 2;
		int maxH = Map.HEIGHT * 3;

		int minW = Map.WIDTH;
		int maxW = Map.WIDTH * 2;
		// places ground, border, and path thing
		for (int i = minH; i < maxH; i++) {
			for (int j = minW; j < maxW; j++) {
				groundTiles[i][j] = icey;
				if (i == minH || j == minW || j == maxW - 1 || i == maxH - 1)
					obstacleTiles[i][j] = Obstacle.TREE_SNOWY; // placeholder

				if ((j == minW + 2 || j == maxW - 2) && i > minH + 2 && i < minH - 2)
					obstacleTiles[i][j] = Obstacle.TREE_SNOWY;
			}
		}

		// the exit/entrance to grassy land
		for (int i = Map.WIDTH + Map.WIDTH / 2 - 1; i < Map.WIDTH + Map.WIDTH / 2 + 2; i++) {
			obstacleTiles[Map.HEIGHT * 2][i] = null;
			groundTiles[Map.HEIGHT * 2][i] = grassy;
		}
		//left outer wall
		for (int i=(Map.HEIGHT*2+2); i<=(Map.HEIGHT*3-3);i++){
			obstacleTiles[i][Map.WIDTH+3]=Obstacle.TREE_SNOWY;
		}
		//right outer wall
		for (int i=(Map.HEIGHT*2+2); i<=(Map.HEIGHT*3-3);i++){
			obstacleTiles[i][Map.WIDTH*2-4]=Obstacle.TREE_SNOWY;
		}
		//top wall
		for (int i=(Map.WIDTH+4); i<Map.WIDTH*2-4;i++){
			obstacleTiles[Map.HEIGHT*2+2][i]=Obstacle.TREE_SNOWY;
		}
		//outer bottom
		for (int i=(Map.WIDTH+4); i<Map.WIDTH*2-4;i++){
			if(i==Map.WIDTH+7)i++;
			obstacleTiles[Map.HEIGHT*3-3][i]=Obstacle.TREE_SNOWY;
		}
		//inner top
		for (int i=(Map.WIDTH+5); i<Map.WIDTH+10;i++){
			if(i==Map.WIDTH+7)i++;
			obstacleTiles[Map.HEIGHT*2+4][i]=Obstacle.TREE_SNOWY;
		}
		//inner bottom
		for (int i=(Map.WIDTH+5); i<Map.WIDTH+10;i++){
			obstacleTiles[Map.HEIGHT*2+6][i]=Obstacle.TREE_SNOWY;
		}
		//inner left
		obstacleTiles[Map.HEIGHT*2+5][Map.WIDTH+5]=Obstacle.TREE_SNOWY;
		//inner right
		obstacleTiles[Map.HEIGHT*2+5][Map.WIDTH+9]=Obstacle.TREE_SNOWY;
	}

	private void makePlainyLand() {

		for (int i = Map.HEIGHT; i < 2 * Map.HEIGHT; i++) {
			for (int j = 0; j < Map.WIDTH; j++) {
				groundTiles[i][j] = plainy;

				// border of rocks
				if (i == Map.HEIGHT || j == 0 || i == Map.HEIGHT * 2 - 1)
					obstacleTiles[i][j] = Obstacle.TREE_LIGHT;

				// plainy to grassy tiles
				if (j == Map.WIDTH - 1) {
					groundTiles[i][j] = Ground.GRASS_2_1_HOR;
					obstacleTiles[i][j] = Obstacle.TREE_DARK;
				}
			}
		}
		//the exit/entrance to grassy 
		for (int i = Map.HEIGHT + Map.HEIGHT / 2 - 1; i < Map.HEIGHT + Map.HEIGHT / 2 + 2; i++) {
			obstacleTiles[i][Map.WIDTH - 1] = null;
		}

		// the 3 by 3 patches of trees
		for (int i = Map.WIDTH - 4; i < Map.WIDTH - 1; i++) {
			for (int j = Map.HEIGHT + 2; j < 2 * Map.HEIGHT - 2; j++) {
				if (j == Map.HEIGHT + 5)
					j++;
				obstacleTiles[j][i] = Obstacle.TREE_DARK;
			}
		}

		// random tree
		obstacleTiles[Map.HEIGHT + Map.HEIGHT / 2][5] = Obstacle.TREE_WEIRD;

	}

	private void makeDesertyLand() {

		Obstacle obstToUse = Obstacle.CACTUS_2;

		for (int i = Map.HEIGHT; i < 2 * Map.HEIGHT; i++) {
			for (int j = Map.WIDTH * 2; j < 3 * Map.WIDTH; j++) {
				groundTiles[i][j] = deserty;
				if (i == Map.HEIGHT || j == Map.WIDTH * 2 || j == 3 * Map.WIDTH - 1
						|| i == 2 * Map.HEIGHT - 1)
					obstacleTiles[i][j] = obstToUse;
			}
		}

		// the exit/entrance to grassy land
		for (int i = Map.HEIGHT + Map.HEIGHT / 2 - 1; i < Map.HEIGHT + Map.HEIGHT / 2 + 2; i++) {
			obstacleTiles[i][Map.WIDTH * 2] = null;
			groundTiles[i][Map.WIDTH * 2] = grassy;
		}

		/* generate 5 randomly placed cacti in left half
		for (int i = 0; i < 5; i++) {
			int x = (int) (Math.random() * (Map.HEIGHT - 4)) + 2 + Map.HEIGHT;
			int y = (int) (Math.random() * (Map.WIDTH / 2 - 4)) + 2 + 2 * Map.WIDTH;

			if (obstacleTiles[x][y] == null)
				obstacleTiles[x][y] = obstToUse;
			else
				i--;
		}
		// generate 5 randomly placed cacti in right half
		for (int i = 0; i < 5; i++) {
			int x = (int) (Math.random() * (Map.HEIGHT - 4)) + 2 + Map.HEIGHT;
			int y = (int) (Math.random() * (Map.WIDTH / 2 - 4)) + 2 + 2 * Map.WIDTH + Map.WIDTH / 2;

			if (obstacleTiles[x][y] == null)
				obstacleTiles[x][y] = obstToUse;
			else
				i--;
		}
		*/
		//left wall
		for (int i = Map.HEIGHT+1; i<Map.HEIGHT*2-1; i++){
			if (i==Map.HEIGHT+5)i++;
			obstacleTiles[i][Map.WIDTH*2+3]=Obstacle.CACTUS_1;
		}
		//top center wall
		for (int i = Map.WIDTH*2+4; i<Map.WIDTH*3-3; i++){
			obstacleTiles[Map.HEIGHT+4][i]=Obstacle.CACTUS_1;
		}
		
		//bottom center wall
		for (int i = Map.WIDTH*2+4; i<Map.WIDTH*3-3; i++){
			obstacleTiles[Map.HEIGHT+6][i]=Obstacle.CACTUS_1;
		}
		//bottom inner wall
		for (int i = Map.WIDTH*2+5; i<Map.WIDTH*3-3; i++){
			obstacleTiles[Map.HEIGHT+8][i]=Obstacle.CACTUS_1;
		} 
		//top inner wall
		for (int i = Map.WIDTH*2+5; i<Map.WIDTH*3-3; i++){
			obstacleTiles[Map.HEIGHT+2][i]=Obstacle.CACTUS_1;
		} 
		//top side wall
		obstacleTiles[Map.HEIGHT+3][Map.WIDTH*3-3]=Obstacle.CACTUS_1;
		
		//bottom side wall
		obstacleTiles[Map.HEIGHT*2-2][Map.WIDTH*3-3]=Obstacle.CACTUS_1;
		
		
	}

	private void makeCaveyLand() {

		for (int i = 0; i < Map.HEIGHT; i++) {
			for (int j = Map.WIDTH; j < 2 * Map.WIDTH; j++) {
				groundTiles[i][j] = cavey;
				if (i == 0 || j == Map.WIDTH || j == 2 * Map.WIDTH - 1 || i == Map.HEIGHT - 1)
					obstacleTiles[i][j] = Obstacle.ROCK_1;// placeholder
			}
		}

		// the exit/entrance to grassy land
		for (int i = Map.WIDTH + Map.WIDTH / 2 - 1; i < Map.WIDTH + Map.WIDTH / 2 + 2; i++) {
			obstacleTiles[Map.HEIGHT - 1][i] = null;
			groundTiles[Map.HEIGHT - 1][i] = grassy;
		}
		
		//bottom wall
		for (int i=Map.WIDTH+2; i<Map.WIDTH*2-2;i++){
			obstacleTiles[Map.HEIGHT-3][i]=Obstacle.ROCK_1;
		}
		//top wall
		for (int i=Map.WIDTH+2; i<Map.WIDTH*2-2;i++){
			if (i==Map.WIDTH+7)i++;
			obstacleTiles[2][i]=Obstacle.ROCK_1;
		}
		//inner horizontal
		for (int i=Map.WIDTH+4; i<Map.WIDTH*2-4;i++){
			if (i==Map.WIDTH+7)i++;
			obstacleTiles[Map.HEIGHT-6][i]=Obstacle.ROCK_1;
		}
		//inner left
		for (int i = 3; i<7;i++){
			obstacleTiles[i][Map.WIDTH+6]=Obstacle.ROCK_1;
		}
		//inner right
		for (int i = 3; i<7;i++){
			obstacleTiles[i][Map.WIDTH+8]=Obstacle.ROCK_1;
		}
		//outer left
		for (int i = 3; i<Map.HEIGHT-2;i++){
			obstacleTiles[i][Map.WIDTH+2]=Obstacle.ROCK_1;
		}
		//outer right
		for (int i = 3; i<Map.HEIGHT-2;i++){
			obstacleTiles[i][Map.WIDTH*2-3]=Obstacle.ROCK_1;
		}
	}

	private void makeSuperySecretyLand() {
		for (int i = 0; i < Map.HEIGHT; i++) {
			for (int j = 0; j < Map.WIDTH - 1; j++) {
				if (i % 2 == 0) {
					groundTiles[i][j] = secrety1;
					if (i == 0 || j == 0 || j == Map.WIDTH - 1 || i == Map.HEIGHT - 1)
						obstacleTiles[i][j] = Obstacle.ROCK_3;// placeholder
					j++;
					groundTiles[i][j] = secrety2;
					if (i == 0 || j == 0 || j == Map.WIDTH - 1 || i == Map.HEIGHT - 1)
						obstacleTiles[i][j] = Obstacle.ROCK_3;// placeholder
				} else {
					groundTiles[i][j] = secrety2;
					if (i == 0 || j == 0 || j == Map.WIDTH - 1 || i == Map.HEIGHT - 1)
						obstacleTiles[i][j] = Obstacle.ROCK_3;// placeholder
					j++;
					groundTiles[i][j] = secrety1;
					if (i == 0 || j == 0 || j == Map.WIDTH - 1 || i == Map.HEIGHT - 1)
						obstacleTiles[i][j] = Obstacle.ROCK_3;// placeholder
				}
			}
		}
	}
	
	public void lastPartCheck(Trainer t) {
		if (!inLastPart) {
			inLastPart = true; // time for secrety secret time
			Teleporter tele = new Teleporter(); // create teleporter to go to secret
			tele.setPoint(new Point(Map.HEIGHT - 2, Map.WIDTH / 2)); // set point
			t.addItem(tele); // add Teleporter to Trainer's inventory
			setStartOffsets(0, 0);
			movementTimer.stop();
			movementTimer = new Timer(movementTimer.getDelay(), new MovementTimerListener());
		}
	}

	@Override
	public void initializeItems() {
		itemTiles = new Item[h][w];
		//Start area items
		itemTiles[Map.HEIGHT+5][6]=new Harmonica();
		itemTiles[Map.HEIGHT+1][Map.WIDTH-2]=new BasicStepPotion();
		itemTiles[Map.HEIGHT*2-2][Map.WIDTH-2]=new BasicStepPotion();
		//Grass area items
		itemTiles[Map.HEIGHT+5][Map.WIDTH+7]=new Harmonica();
		itemTiles[Map.HEIGHT+2][Map.WIDTH+3]=new SuperStepPotion();
		itemTiles[Map.HEIGHT*2-3][Map.WIDTH*2-4]=new BasicStepPotion();
		//Ice area items
		itemTiles[Map.HEIGHT*3-2][Map.WIDTH*2-2]=new BasicStepPotion();
		itemTiles[Map.HEIGHT*3-4][Map.WIDTH*2-8]=new Harmonica();
		itemTiles[Map.HEIGHT*3-6][Map.WIDTH*2-8]=new Teleporter();
		//desert area items
		itemTiles[Map.HEIGHT+3][Map.WIDTH*3-5]=new HyperStepPotion();
		itemTiles[Map.HEIGHT*2-2][Map.WIDTH*3-5]=new HyperStepPotion();
		itemTiles[Map.HEIGHT+5][Map.WIDTH*3-3]=new Harmonica();
		itemTiles[Map.HEIGHT*2-2][Map.WIDTH*2+2]=new BasicStepPotion();
		//cave area items
		itemTiles[3][Map.WIDTH+4]=new BasicStepPotion();
		itemTiles[3][Map.WIDTH+9]=new SuperStepPotion();
		itemTiles[6][Map.WIDTH+7]=new Harmonica();
		//Secret area items
		itemTiles[2][1]=new Harmonica();
		itemTiles[Map.HEIGHT-2][1]=new Harmonica();
		itemTiles[2][Map.WIDTH-2]=new Harmonica();
		itemTiles[Map.HEIGHT-2][Map.WIDTH-2]=new Harmonica();
	}

}
