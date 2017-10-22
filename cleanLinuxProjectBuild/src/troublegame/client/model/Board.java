package troublegame.client.model;

import java.util.ArrayList;

public class Board {

	public static final int NUM_MAIN_SLOTS = 28;
	public static final int NUM_HOME_SLOTS = 4;
	public static final int NUM_END_SLOTS = 4;
	
	public static final int SLOT_HOME = 0;
	public static final int SLOT_MAIN = 1;
	public static final int SLOT_END = 2;
	
	public static final int NUM_TOKENS = 4;
	
	private ArrayList<Slot> mainZone;
	
	private ArrayList<Slot> redHomeZone;
	private ArrayList<Slot> blueHomeZone;
	private ArrayList<Slot> yellowHomeZone;
	private ArrayList<Slot> greenHomeZone;
	
	private ArrayList<Slot> redEndZone;
	private ArrayList<Slot> blueEndZone;
	private ArrayList<Slot> yellowEndZone;
	private ArrayList<Slot> greenEndZone;
	
	private ArrayList<Slot> redTokens;
	private ArrayList<Slot> blueTokens;
	private ArrayList<Slot> yellowTokens;
	private ArrayList<Slot> greenTokens;
	
	public Board() {
		initMainTiles();
		initHomeTiles();
		initEndTiles();
		initTokens();
	}
	
	private void initMainTiles() {
		mainZone = new ArrayList<Slot>(NUM_MAIN_SLOTS);
		
		for (int i = 0; i < NUM_MAIN_SLOTS; i++) {
			mainZone.add(new Slot(SLOT_MAIN, i));
		}
	}
	
	private void initHomeTiles() {
		redHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
		blueHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
		yellowHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
		greenHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
		
		for (int i = 0; i < NUM_HOME_SLOTS; i++) {
			redHomeZone.add(new Slot(SLOT_HOME, i));
			blueHomeZone.add(new Slot(SLOT_HOME, i));
			yellowHomeZone.add(new Slot(SLOT_HOME, i));
			greenHomeZone.add(new Slot(SLOT_HOME, i));
		}
	}
	
	private void initEndTiles() {
		redEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
		blueEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
		yellowEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
		greenEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
		
		for (int i = 0; i < NUM_END_SLOTS; i++) {
			redEndZone.add(new Slot(SLOT_END, i));
			blueEndZone.add(new Slot(SLOT_END, i));
			yellowEndZone.add(new Slot(SLOT_END, i));
			greenEndZone.add(new Slot(SLOT_END, i));
		}
	}
	
	private void initTokens() {
		redTokens = new ArrayList<Slot>(NUM_TOKENS);
		blueTokens = new ArrayList<Slot>(NUM_TOKENS);
		yellowTokens = new ArrayList<Slot>(NUM_TOKENS);
		greenTokens = new ArrayList<Slot>(NUM_TOKENS);
		
		for (int i = 0; i < NUM_TOKENS; i++) {
			redTokens.add(new Slot(SLOT_HOME, i));
			blueTokens.add(new Slot(SLOT_HOME, i));
			yellowTokens.add(new Slot(SLOT_HOME, i));
			greenTokens.add(new Slot(SLOT_HOME, i));
		}
	}
	
	public ArrayList<Slot> getMainZone() {
		return mainZone;
	}
	
	public ArrayList<Slot> getRedHomeZone() {
		return redHomeZone;
	}
	
	public ArrayList<Slot> getBlueHomeZone() {
		return blueHomeZone;
	}
	
	public ArrayList<Slot> getYellowHomeZone() {
		return yellowHomeZone;
	}
	
	public ArrayList<Slot> getGreenHomeZone() {
		return greenHomeZone;
	}
	
	public ArrayList<Slot> getRedEndZone() {
		return redEndZone;
	}
	
	public ArrayList<Slot> getBlueEndZone() {
		return blueEndZone;
	}
	
	public ArrayList<Slot> getYellowEndZone() {
		return yellowEndZone;
	}
	
	public ArrayList<Slot> getGreenEndZone() {
		return greenEndZone;
	}
	
	public ArrayList<Slot> getRedTokens() {
		return redTokens;
	}
	
	public ArrayList<Slot> getBlueTokens() {
		return blueTokens;
	}
	
	public ArrayList<Slot> getYellowTokens() {
		return yellowTokens;
	}
	
	public ArrayList<Slot> getGreenTokens() {
		return greenTokens;
	}
	
}
