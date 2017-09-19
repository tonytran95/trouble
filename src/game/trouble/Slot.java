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
