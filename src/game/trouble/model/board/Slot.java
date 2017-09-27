package game.trouble.model.board;

import game.trouble.model.Board;

public class Slot {
	
	private Token occupyingToken;
	private int slotLocation;
	
	public Slot(int location) {
		occupyingToken = null;
	}
	
	public Slot(Token token, int location) {
		occupyingToken = token;
		slotLocation = location;
	}
	
	/**
	 * Determine whether the current slot has another token in it or not.
	 * @return true if the slot has a token in it, false if it is unoccupied
	 */
	public boolean isOccupied() {
		return occupyingToken != null;
	}
	
	public String slotLocationToString() {
		switch(getSlotLocation()) {
			case Board.SLOT_HOME: 
				return "Home base";
			case Board.SLOT_END: 
				return "Endzone";
			default: return "Main board";
		}
	}
	
	public int getSlotLocation() {
		return this.slotLocation;
	}
	
	public void setSlotLocation(int location) {
		if(location >= Board.SLOT_HOME || location <= Board.SLOT_END) {
			this.slotLocation = location;
		}
	}
	
	/**
	 * Get the token which is currently occupying this slot (if any).
	 * If no token is occupying this slot, null will be returned
	 * @return null if no token is occupying this slot, otherwise returns the occupying token
	 */
	public Token getOccupyingToken() {
		return occupyingToken;
	}
	
	/**
	 * Sets the token which will be occupying the current slot
	 * @param token The token which has moved into this slot
	 */
	public void setOccupyingToken(Token token) {
		occupyingToken = token;
	}
}
