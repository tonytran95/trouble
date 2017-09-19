package game.trouble;

public class Token {
	
	private int id;
	private int currPos;
	private Player owner;
	
	public Token(int tokenID) {
		id = tokenID;
	}
	
	public int getTokenID() {
		return this.id;
	}
	
	public int getCurrPos() {
		return this.currPos;
	}
	
}
