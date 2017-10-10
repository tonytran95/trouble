package troublegame.server;

import java.util.ArrayList;

public class BoardModel {

	public static final int NUM_MAIN_SLOTS = 28;
	public static final int NUM_HOME_SLOTS = 4;
	public static final int NUM_END_SLOTS = 4;
	
	public static final int SLOT_HOME = 0;
	public static final int SLOT_MAIN = 1;
	public static final int SLOT_END = 2;
	
	public static final int NUM_TOKENS = 4;
	
	private ArrayList<Tile> mainZone;
	
	private ArrayList<Tile> redHomeZone;
	private ArrayList<Tile> blueHomeZone;
	private ArrayList<Tile> yellowHomeZone;
	private ArrayList<Tile> greenHomeZone;
	
	private ArrayList<Tile> redEndZone;
	private ArrayList<Tile> blueEndZone;
	private ArrayList<Tile> yellowEndZone;
	private ArrayList<Tile> greenEndZone;
	
	private ArrayList<Tile> redTokens;
	private ArrayList<Tile> blueTokens;
	private ArrayList<Tile> yellowTokens;
	private ArrayList<Tile> greenTokens;
	
	public BoardModel() {
		initMainTiles();
		initHomeTiles();
		initEndTiles();
		initTokens();
	}
	
	private void initMainTiles() {
		mainZone = new ArrayList<Tile>(NUM_MAIN_SLOTS);
		
		for (int i = 0; i < NUM_MAIN_SLOTS; i++) {
			mainZone.add(new Tile(SLOT_MAIN, i));
		}
	}
	
	private void initHomeTiles() {
		redHomeZone = new ArrayList<Tile>(NUM_HOME_SLOTS);
		blueHomeZone = new ArrayList<Tile>(NUM_HOME_SLOTS);
		yellowHomeZone = new ArrayList<Tile>(NUM_HOME_SLOTS);
		greenHomeZone = new ArrayList<Tile>(NUM_HOME_SLOTS);
		
		for (int i = 0; i < NUM_HOME_SLOTS; i++) {
			redHomeZone.add(new Tile(SLOT_HOME, i));
			blueHomeZone.add(new Tile(SLOT_HOME, i));
			yellowHomeZone.add(new Tile(SLOT_HOME, i));
			greenHomeZone.add(new Tile(SLOT_HOME, i));
		}
	}
	
	private void initEndTiles() {
		redEndZone = new ArrayList<Tile>(NUM_END_SLOTS);
		blueEndZone = new ArrayList<Tile>(NUM_END_SLOTS);
		yellowEndZone = new ArrayList<Tile>(NUM_END_SLOTS);
		greenEndZone = new ArrayList<Tile>(NUM_END_SLOTS);
		
		for (int i = 0; i < NUM_END_SLOTS; i++) {
			redEndZone.add(new Tile(SLOT_END, i));
			blueEndZone.add(new Tile(SLOT_END, i));
			yellowEndZone.add(new Tile(SLOT_END, i));
			greenEndZone.add(new Tile(SLOT_END, i));
		}
	}
	
	private void initTokens() {
		redTokens = new ArrayList<Tile>(NUM_TOKENS);
		blueTokens = new ArrayList<Tile>(NUM_TOKENS);
		yellowTokens = new ArrayList<Tile>(NUM_TOKENS);
		greenTokens = new ArrayList<Tile>(NUM_TOKENS);
		
		for (int i = 0; i < NUM_TOKENS; i++) {
			redTokens.add(new Tile(SLOT_HOME, i));
			blueTokens.add(new Tile(SLOT_HOME, i));
			yellowTokens.add(new Tile(SLOT_HOME, i));
			greenTokens.add(new Tile(SLOT_HOME, i));
		}
	}
	
	public ArrayList<Tile> getMainZone() {
		return mainZone;
	}
	
	public ArrayList<Tile> getRedHomeZone() {
		return redHomeZone;
	}
	
	public ArrayList<Tile> getBlueHomeZone() {
		return blueHomeZone;
	}
	
	public ArrayList<Tile> getYellowHomeZone() {
		return yellowHomeZone;
	}
	
	public ArrayList<Tile> getGreenHomeZone() {
		return greenHomeZone;
	}
	
	public ArrayList<Tile> getRedEndZone() {
		return redEndZone;
	}
	
	public ArrayList<Tile> getBlueEndZone() {
		return blueEndZone;
	}
	
	public ArrayList<Tile> getYellowEndZone() {
		return yellowEndZone;
	}
	
	public ArrayList<Tile> getGreenEndZone() {
		return greenEndZone;
	}
	
	public ArrayList<Tile> getRedTokens() {
		return redTokens;
	}
	
	public ArrayList<Tile> getBlueTokens() {
		return blueTokens;
	}
	
	public ArrayList<Tile> getYellowTokens() {
		return yellowTokens;
	}
	
	public ArrayList<Tile> getGreenTokens() {
		return greenTokens;
	}
	
}
