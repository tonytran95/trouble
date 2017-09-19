package game.trouble;

public class Slot {
	
	private Token occupyingToken;
	
	public Slot() {
		occupyingToken = null;
	}
	
	/**
	 * Determine whether the current slot has another token in it or not.
	 * @return true if the slot has a token in it, false if it is unoccupied
	 */
	public boolean isOccupied() {
		return occupyingToken != null;
	}
	
	public Token getOccupyingToken() {
		return occupyingToken;
	}
	
	public void setSlotToken(Token slotToken) {
		occupyingToken = slotToken;
	}
}
