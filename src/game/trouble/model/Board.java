package game.trouble.model;

import java.util.ArrayList;
import java.util.List;

import game.trouble.model.board.Die;
import game.trouble.model.board.Slot;
import game.trouble.model.board.Token;

public class Board {
	
	// Number of slot constants
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
	
	// The die
	private Die die;
	
	public Board(Player[] players) {
		die = new Die();
		generateMainZone();
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
		List<Slot> combinedSlots = new ArrayList<Slot>();
		
		combinedSlots.addAll(mainSlots);
		combinedSlots.addAll(homeZone);
		combinedSlots.addAll(endZone);
		
		// look through and find the slot containing the token
		for (Slot s: combinedSlots) {
			if (s.getOccupyingToken() == token) {
				return s;
			}
		}
		
		return null;
	}
	
	public void generateMainZone() {
		
		mainSlots = new ArrayList<Slot>(NUM_MAIN_SLOTS);
		generateSlots(mainSlots, SLOT_MAIN, NUM_MAIN_SLOTS);
		
	}
	
	/**
	 * Generate new slots to fill the zone arraylist with. Slots will be unoccupied and have the type zoneType
	 * @param zone Collection of slots from a specific area of the board
	 * @param zoneType The area on the board the slots belong with
	 * @param numSlots The number of slots to create
	 */
	public void generateSlots(ArrayList<Slot> zone, int zoneType, int numSlots) {
		
		for(int i = 0; i < numSlots; i++) {
			zone.add(new Slot(zoneType));
		}
		
	}
	
	/**
	 * Generate new slots to fill the zone arraylist with. Slots will be occupied by tokens belonging to the 
	 * owner player and will have the type zoneType.
	 * @param zone Collection of slots from a specific area of the board
	 * @param owner The owner of the given zone
	 * @param zoneType The area on the board the slots belong with
	 * @param numSlots The number of slots to create
	 */
	public void generateSlots(ArrayList<Slot> zone, Player owner, int zoneType, int numSlots) {
		
		for(int i = 0; i < numSlots; i++) {
			Token curr = owner.getToken(i);
			zone.add(new Slot(curr, zoneType));
		}
		
	}
	
	/**
	 * Generate start zones for the required players
	 * @param players Array of all players who are going to play the game
	 */
	private void generateHomeZones(Player[] players) {
		for(Player p : players) {
			switch(p.getColour()) {
				case RED: 
					redHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
					break;
				case BLUE: 
					blueHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
					break;
				case GREEN: 
					greenHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
					break;
				default: 
					yellowHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
			}
			generateSlots(getPlayerHomeZone(p), p, SLOT_HOME, NUM_HOME_SLOTS);
		}
	}
	
	/**
	 * Generate end zone sections for the required players
	 * @param players Array of all players who are going to play the game
	 */
	private void generateEndZones(Player[] players) {
		for(Player p: players) {
			switch(p.getColour()) {
				case BLUE: 
					blueEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				case YELLOW: 
					yellowEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				case GREEN: 
					greenEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				default: 
					redEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
			}
			generateSlots(getPlayerEndZone(p), SLOT_END, NUM_END_SLOTS);
		}
	}
	
	/**
	 * Gets the home zone for the given player
	 * @param player The player who's home zone to get
	 * @return The home zone for the given player
	 */
	public ArrayList<Slot> getPlayerHomeZone(Player player) {
		switch(player.getColour()) {
			case RED: return redHomeZone;
			case BLUE: return blueHomeZone;
			case GREEN: return greenHomeZone;
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
			case RED: return redEndZone;
			case BLUE: return blueEndZone;
			case GREEN: return greenEndZone;
			default: return yellowEndZone;
		}
	}
	
	/**
	 * gets the mainslots of the board
	 */
	public ArrayList<Slot> getMainZone() {
		return this.mainSlots;
	}
	
	/**
	 * Gets the die for this board
	 * @return The die for this board
	 */
	public Die getDie() {
		return die;
	}
	
	/**
	 * Checks that the board has the valid properties
	 * @return true if board is valid. false if there was an error in generation
	 */
	public boolean isValidBoard() {
		
		if(mainSlots.size() != NUM_MAIN_SLOTS) {
			return false;
		} else if ((redHomeZone != null && redHomeZone.size() != NUM_HOME_SLOTS) ||
				(yellowHomeZone != null && yellowHomeZone.size() != NUM_HOME_SLOTS) || 
				(blueHomeZone != null && blueHomeZone.size() != NUM_HOME_SLOTS) || 
				(greenHomeZone != null && greenHomeZone.size() != NUM_HOME_SLOTS)) {
			return false;
		} else if ((redEndZone != null && redEndZone.size() != NUM_END_SLOTS) ||
				(yellowEndZone != null && yellowEndZone.size() != NUM_END_SLOTS) || 
				(blueEndZone != null && blueEndZone.size() != NUM_END_SLOTS) || 
				(greenEndZone != null && greenEndZone.size() != NUM_END_SLOTS)) {
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Returns the startindex of a colour on the board mainzone
	 * @param col
	 * @return
	 */
	public int getStartIndex(Colour col) {
		int startIndex;
		switch (col) {
			case RED:
				startIndex = RED_START;
				break;
			case BLUE:
				startIndex = BLUE_START;
				break;
			case GREEN:
				startIndex = GREEN_START;
				break;
			case YELLOW:
				startIndex = YELLOW_START;
				break;
			default:
				startIndex = 0;
				break;
		}
		return startIndex;
	}
	
	/**
	 * returns a slot in the mainSlots given the slotIndex
	 */
	public Slot getMainSlot(int slotIndex) {
		return mainSlots.get(slotIndex);
	}
}
