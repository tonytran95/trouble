
public class Player {
	
	private String username;
	private int id;
	private Token[] tokens;
	
	public Player(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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
	
}
