package game.trouble;

import java.util.ArrayList;

public class Board {
	
	public static final int NUM_MAIN_SLOTS = 28;
	public static final int NUM_HOME_SLOTS = 4;
	public static final int NUM_END_SLOTS = 4;
	
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
	
	public static final int SLOT_HOME = 0;
	public static final int SLOT_MAIN = 1;
	public static final int SLOT_END = 2;
	
	// The main board
	private ArrayList<Slot> mainSlots;
	
	// Each players home base where the tokens spawn
	private ArrayList<Slot> redHomeZone;
	private ArrayList<Slot> blueHomeZone;
	private ArrayList<Slot> yellowHomeZone;
	private ArrayList<Slot> greenHomeZone;
	
	// Each players end zone where the tokens are safe
	private ArrayList<Slot> redEndZone;
	private ArrayList<Slot> blueEndZone;
	private ArrayList<Slot> yellowEndZone;
	private ArrayList<Slot> greenEndZone;
	private Die die;
	
	public Board(Player[] players) {
		mainSlots = new ArrayList<Slot>(NUM_MAIN_SLOTS);
		die = new Die();
		generateHomeZones(players);
		generateEndZones(players);
	}
	
	/**
	 * Get the location of a certain token on the board
	 * @param token The token to find
	 * @return The current slot the token is occupying. If the token is not found, null is returned 
	 */
	public Slot getTokenLoc(Token token) {
		
		Player owner = token.getOwner();
		ArrayList<Slot> homeZone = getPlayerHomeZone(owner);
		ArrayList<Slot> endZone = getPlayerEndZone(owner);
		
		if(mainSlots.contains(token)) {
			int index = mainSlots.indexOf(token);
			return mainSlots.get(index);
		} else if(homeZone.contains(token)) {
			int index = homeZone.indexOf(token);
			return homeZone.get(index);
		} else if(endZone.contains(token)) {
			int index = endZone.indexOf(token);
			return endZone.get(index);
		} else {
			return null;
		}
	}
	
	/**
	 * Generate start zones for the required players
	 * @param players Array of all players who are going to play the game
	 */
	private void generateHomeZones(Player[] players) {
		for(Player p : players) {
			switch(p.getColour()) {
				case Player.RED: 
					redHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
					break;
				case Player.BLUE: 
					blueHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
					break;
				case Player.GREEN: 
					greenHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
					break;
				default: 
					yellowHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
			}
		}
	}
	
	/**
	 * Generate end zone sections for the required players
	 * @param players Array of all players who are going to play the game
	 */
	private void generateEndZones(Player[] players) {
		for(Player p: players) {
			switch(p.getColour()) {
				case Player.BLUE: 
					blueEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				case Player.YELLOW: 
					yellowEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				case Player.GREEN: 
					greenEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				default: 
					redEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
			}
		}
	}
	
	/**
	 * Gets the home zone for the given player
	 * @param player The player who's home zone to get
	 * @return The home zone for the given player
	 */
	public ArrayList<Slot> getPlayerHomeZone(Player player) {
		switch(player.getColour()) {
			case Player.RED: return redHomeZone;
			case Player.BLUE: return blueHomeZone;
			case Player.GREEN: return greenHomeZone;
			default: return yellowHomeZone;
		}
	}
	
	/**
	 * Gets the endzone for the given player
	 * @param player The player who's endzone to get
	 * @return The end zone for the given player
	 */
	public ArrayList<Slot> getPlayerEndZone(Player player) {
		switch(player.getColour()) {
			case Player.RED: return redEndZone;
			case Player.BLUE: return blueEndZone;
			case Player.GREEN: return greenEndZone;
			default: return yellowEndZone;
		}
	}
	
	/**
	 * Gets the die for this board
	 * @return The die for this board
	 */
	public Die getDie() {
		return die;
	}
	
}
