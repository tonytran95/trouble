package game.trouble;

import java.util.ArrayList;

public class Board {
	
	public static final int NUM_MAIN_SLOTS = 28;
	public static final int NUM_ENDZONE_SLOTS = 4;
	
	// Starting positions
	public static final int RED_START = 0;
	public static final int BLUE_START = 7;
	public static final int YELLOW_START = 14;
	public static final int GREEN_START = 21;
	
	// Ending positions
	public static final int RED_END = NUM_MAIN_SLOTS - 1;
	public static final int BLUE_END = BLUE_START - 1;
	public static final int YELLOW_END = YELLOW_START - 1;
	public static final int GREEN_END = GREEN_START - 1;
	
	private ArrayList<Slot> mainSlots;
	private ArrayList<Slot> redEndZone;
	private ArrayList<Slot> blueEndZone;
	private ArrayList<Slot> yellowEndZone;
	private ArrayList<Slot> greenEndZone;
	private Die die;
	
	public Board(Player[] players) {
		mainSlots = new ArrayList<Slot>(NUM_MAIN_SLOTS);
		die = new Die();
		generateEndZones(players);
	}
	
	public int getTokenLoc(Token token) {
		return mainSlots.indexOf(token);
	}
	
	private void generateEndZones(Player[] players) {
		for(Player p: players) {
			switch(p.getColour()) {
				case Player.BLUE: 
					blueEndZone = new ArrayList<Slot>(NUM_ENDZONE_SLOTS);
					break;
				case Player.YELLOW: 
					yellowEndZone = new ArrayList<Slot>(NUM_ENDZONE_SLOTS);
					break;
				case Player.GREEN: 
					greenEndZone = new ArrayList<Slot>(NUM_ENDZONE_SLOTS);
					break;
				default: 
					redEndZone = new ArrayList<Slot>(NUM_ENDZONE_SLOTS);
			}
		}
	}
	
}
