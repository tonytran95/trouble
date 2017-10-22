package troublegame.server;

public class Token {
	
	private int id;
	private int currPos;
	private Player owner;
	private Color color;
	
	public Token(int tokenID, Player owner) {
		id = tokenID;
		this.owner = owner;
		setColour();
	}
	
	private void setColour() {
		if(owner != null) {
			this.color = owner.getColour();
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
	
	public Color getColour() {
		return this.color;
	}
	
	public int getTokenStart() {
		switch(getColour()) {
			case RED: return Board.RED_START;
			case BLUE: return Board.BLUE_START;
			case GREEN: return Board.GREEN_START;
			default: return Board.YELLOW_START;
		}
	}
	
	public int getTokenEnd() {
		switch(getColour()) {
			case RED: return Board.RED_END;
			case BLUE: return Board.BLUE_END;
			case GREEN: return Board.GREEN_END;
			default: return Board.YELLOW_END;
		}
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
		result = prime * result + color.hashCode();
		result = prime * result + id;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}
	
}
