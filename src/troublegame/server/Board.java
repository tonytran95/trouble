package troublegame.server;

import java.util.ArrayList;

public class Board {
	
	// Number of slot constants
	public static final int NUM_HOME_SLOTS = 4;
	public static final int NUM_MAIN_SLOTS = 28;
	public static final int NUM_END_SLOTS = 4;
	
	// Starting positions
	public static final int RED_START = 0;
	public static final int GREEN_START = 7;
	public static final int YELLOW_START = 14;
	public static final int BLUE_START = 21;
	
	// Ending positions
	public static final int RED_END = NUM_MAIN_SLOTS - 1;
	public static final int GREEN_END = GREEN_START - 1;
	public static final int YELLOW_END = YELLOW_START - 1;
	public static final int BLUE_END = BLUE_START - 1;
	
	public static final int SLOT_HOME = 0;
	public static final int SLOT_MAIN = 1;
	public static final int SLOT_END = 2;
	
	// The main board
	private ArrayList<Slot> mainSlots;
	
	// Each players home base where the tokens spawn
	private ArrayList<Slot> redHomeZone;
	private ArrayList<Slot> greenHomeZone;
	private ArrayList<Slot> yellowHomeZone;
	private ArrayList<Slot> blueHomeZone;
	
	// Each players end zone where the tokens are safe
	private ArrayList<Slot> redEndZone;
	private ArrayList<Slot> greenEndZone;
	private ArrayList<Slot> yellowEndZone;
	private ArrayList<Slot> blueEndZone;
	
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
		ArrayList<Slot> homeZone = getPlayerHomeZone(owner.getColour());
		ArrayList<Slot> endZone = getPlayerEndZone(owner.getColour());
		ArrayList<Slot> combinedSlots = new ArrayList<Slot>();
		
		combinedSlots.addAll(homeZone);
		combinedSlots.addAll(mainSlots);
		combinedSlots.addAll(endZone);
		
		// look through and find the slot containing the token
		for (Slot s: combinedSlots) {
			if (s.getOccupyingToken() == token) {
				return s;
			}
		}
		
		return null;
	}
	
	public void setTokenLoc(Token token, int zone, int index) {
		Player owner = token.getOwner();
		Slot currentSlot = getTokenLoc(token);
		if (currentSlot != null) currentSlot.removeOccupyingToken();
		if (zone == SLOT_HOME) {
			for (Slot s : getPlayerHomeZone(owner.getColour())) {
				if (index == s.getSlotIndex()) {
					s.setOccupyingToken(token);
					break;
				}
			}
		} else if (zone == SLOT_MAIN) {
			for (Slot s : mainSlots) {
				if (index == s.getSlotIndex()) {
					s.setOccupyingToken(token);
					break;
				}
			}
		} else if (zone == SLOT_END) {
			for (Slot s : getPlayerEndZone(owner.getColour())) {
				if (index == s.getSlotIndex()) {
					s.setOccupyingToken(token);
					break;
				}
			}
		}
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
			zone.add(new Slot(zoneType, i));
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
			zone.add(new Slot(curr, zoneType, i));
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
				case GREEN: 
					greenHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
					break;
				case YELLOW: 
					yellowHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
					break;
				default: 
					blueHomeZone = new ArrayList<Slot>(NUM_HOME_SLOTS);
			}
			generateSlots(getPlayerHomeZone(p.getColour()), p, SLOT_HOME, NUM_HOME_SLOTS);
		}
	}
	
	/**
	 * Generate end zone sections for the required players
	 * @param players Array of all players who are going to play the game
	 */
	private void generateEndZones(Player[] players) {
		for(Player p: players) {
			switch(p.getColour()) {
				case RED: 
					redEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				case GREEN: 
					greenEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				case YELLOW: 
					yellowEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
					break;
				default: 
					blueEndZone = new ArrayList<Slot>(NUM_END_SLOTS);
			}
			generateSlots(getPlayerEndZone(p.getColour()), SLOT_END, NUM_END_SLOTS);
		}
	}
	
	/**
	 * Gets the home zone for the given player
	 * @param c The color of the player for the home zone to get
	 * @return The home zone for the given player
	 */
	public ArrayList<Slot> getPlayerHomeZone(Color c) {
		switch(c) {
			case RED: return redHomeZone;
			case GREEN: return greenHomeZone;
			case YELLOW: return yellowHomeZone;
			default: return blueHomeZone;
		}
	}
	
	/**
	 * Gets the endzone for the given player
	 * @param c The player color for who's endzone to get
	 * @return The end zone for the given player
	 */
	public ArrayList<Slot> getPlayerEndZone(Color c) {
		switch(c) {
			case RED: return redEndZone;
			case GREEN: return greenEndZone;
			case YELLOW: return yellowEndZone;
			default: return blueEndZone;
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
				(greenHomeZone != null && greenHomeZone.size() != NUM_HOME_SLOTS) ||
				(yellowHomeZone != null && yellowHomeZone.size() != NUM_HOME_SLOTS) || 
				(blueHomeZone != null && blueHomeZone.size() != NUM_HOME_SLOTS)) {
			return false;
		} else if ((redEndZone != null && redEndZone.size() != NUM_END_SLOTS) ||
				(greenEndZone != null && greenEndZone.size() != NUM_END_SLOTS) || 
				(yellowEndZone != null && yellowEndZone.size() != NUM_END_SLOTS) || 
				(blueEndZone != null && blueEndZone.size() != NUM_END_SLOTS)) {
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
	public int getStartIndex(Color col) {
		int startIndex;
		switch (col) {
			case RED:
				startIndex = RED_START;
				break;
			case GREEN:
				startIndex = GREEN_START;
				break;
			case YELLOW:
				startIndex = YELLOW_START;
				break;
			default:
				startIndex = BLUE_START;
		}
		return startIndex;
	}
	
	/**
	 * Returns the endindex of a colour on the board mainzone
	 * @param col
	 * @return
	 */
	public int getEndIndex(Color col) {
		int endIndex;
		switch (col) {
		case RED:
			endIndex = RED_END;
			break;
		case GREEN:
			endIndex = GREEN_END;
			break;
		case YELLOW:
			endIndex = YELLOW_END;
			break;
		default:
			endIndex = BLUE_END;
		}
		return endIndex;
	}
	
	/**
	 * Gets the slot in the main zone at the specified index. Null is returned for index out of bounds
	 * @param index The index of the slot to get the token for
	 * @return null if slot not found, otherwise the slot at index in mainzone
	 */
	public Slot getSlot(int index) {
		if(index < NUM_MAIN_SLOTS) return mainSlots.get(index);
		return null;
	}
	
	/**
	 * Get the slot at the given index of the given zone for the given color
	 * @param index The slot index to get
	 * @param zone The zone the slot is in
	 * @param col The color of the zone to get
	 * @return The found slot in the given zone if it exists, otherwise null
	 */
	public Slot getSlot(int index, int zone, Color col) {
		
		Slot slot = null;
		
		switch (zone) {
			case SLOT_HOME:
				if (index < NUM_HOME_SLOTS) slot = getPlayerHomeZone(col).get(index);
				break;
			case SLOT_END:
				if (index < NUM_END_SLOTS) slot = getPlayerEndZone(col).get(index);
				break;
			default:
				slot = getSlot(index);
		}
		
		return slot;
		
	}
	
}