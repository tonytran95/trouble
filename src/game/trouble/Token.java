package game.trouble;

public class Token {
	
	private int id;
	private int currPos;
	private Player owner;
	private int colour;
	
	public Token(int tokenID, Player owner) {
		id = tokenID;
		this.owner = owner;
		setColour();
	}
	
	private void setColour() {
		if(owner != null) {
			this.colour = owner.getColour();
		}
	}

	public int getTokenID() {
		return this.id;
	}
	
	public int getCurrPos() {
		return this.currPos;
	}
	
}
