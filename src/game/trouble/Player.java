package game.trouble;

public class Player {
	
	private String username;
	private int colour;
	private Token[] tokens;
	
	public static final int RED = 0;
	public static final int BLUE = 1;
	public static final int YELLOW = 2;
	public static final int GREEN = 3;
	
	public Player(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setColour(int col) {
		this.colour = col;
	}
	
	public int getColour() {
		return this.colour;
	}
	
	// we can remove one of these depending on how we use Tokens in Board/Game
	public void setTokens(Token[] tokens) {
		this.tokens = tokens;
	}
	
	public void setTokens(Token t1, Token t2, Token t3, Token t4) {
		tokens = new Token[]{t1, t2, t3, t4};
	}
	
	public Token[] getTokens() {
		return tokens;
	}
	
	// player is same is colour is same
	public boolean equals(Object o) {
		if (o instanceof Player) {
			if (((Player)o).getColour() == this.getColour() ){
	            return true;
	        }
		}
		return false;
	}
	
	// TODO Write new hashode method
	@Override
	public int hashCode() {
		
		return super.hashCode();
	}
}
