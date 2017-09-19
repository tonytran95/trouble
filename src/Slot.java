
public class Slot {
	
	private Token occupyingToken;
	private boolean occupied;
	
	public Slot() {
		occupied = false;
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
	
}
