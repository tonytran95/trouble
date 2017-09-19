
public class Slot {
	
	private Token occupyingToken;
	private boolean occupied;
	private Player occupiedBy;
	
	public Slot() {
		occupied = false;
		occupiedBy = null;
		occupyingToken = null;
	}
	
	public void setOccupiedState(boolean isOccupied) {
		occupied = isOccupied;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public Token getOccupyingToken() {
		return occupyingToken;
	}
	
	public void setSlotToken(Token slotToken) {
		occupyingToken = slotToken;
	}
	
	public Player getSlotOccupant() {
		return occupiedBy;
	}
}
