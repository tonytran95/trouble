package game.trouble;
import java.util.ArrayList;
import java.util.List;

public class Game {
	
	public static final int MAX_PLAYERS = 4;
	
	private Board board;
	private ArrayList<Player> players;
	
	public Game() {
		board = new Board();
		players = new ArrayList<Player>(4);
	}
	
	public void movePlayerToken(Token token, int diceValue) {
		
		int start = token.getCurrPos();
		int target = start + diceValue;
		
		// Wraparound
		if(target >= Board.NUM_BOARD_SLOTS) {
			target -= Board.NUM_BOARD_SLOTS;
		}
		
		
		
	}
	
	// returns true if successfully added, false if player already exists or game full (4 players)
		public boolean addPlayer(Player p) {
			if (players.size() >= MAX_PLAYERS) return false;
			
			for (Player q: players) {
				if (p.equals(q)) return false;
			}
			
			players.add(p);
			return true;
		}
	
}
