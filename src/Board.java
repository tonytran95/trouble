import java.util.ArrayList;
import java.util.List;

public class Board {

	private Slot[] positions;
	private List<Player> players;
	
	public static final int BOARD_POSITIONS = 28;
	public static final int MAX_PLAYERS = 4;
	
	// starting positions
	public static final int RED_START = 0;
	public static final int BLUE_START = 7;
	public static final int YELLOW_START = 14;
	public static final int GREEN_START = 21;
	
	// ending positions
	public static final int RED_END = 27;
	public static final int BLUE_END = 6;
	public static final int YELLOW_END = 13;
	public static final int GREEN_END = 20;
	
	public Board() {
		positions = new Slot[BOARD_POSITIONS];
		players = new ArrayList<Player>();
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
	
	// board private methods
	// give it a dice number and a starting index, it returns target position
	private int targetPosition(int start, int diceRoll) {
		int target = start + diceRoll;
		// wraparound
		if (target >= BOARD_POSITIONS) 
			target = target % BOARD_POSITIONS;
		
		return target;
	}
}
