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
	
	public Player getOwner() {
		return this.owner;
	}
	
	public int getColour() {
		return this.colour;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Token) {
			if (((Token)o).getTokenID() == this.getTokenID() && ((Token)o).getOwner() == this.getOwner()){
	            return true;
	        }
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colour;
		result = prime * result + id;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}
	
}
