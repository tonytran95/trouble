
public class Token {
	
	private int state;
	private int id;
	
	public static final int HOME = 0;
	public static final int SPAWNED = 1;
	
	// by default all tokens start at home
	public Token(int tokenID) {
		state = HOME;
		id = tokenID;
	}
	
	public int getTokenID() {
		return this.id;
	}
	
	public int getTokenState() {
		return this.state;
	}
	
}
