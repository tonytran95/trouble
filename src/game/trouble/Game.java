package game.trouble;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Game {
	
	public static final int MAX_PLAYERS = 4;
	
	private Board board;
	private ArrayList<Player> players;
	private Calendar startTime;
	
	public Game(int numPlayers) {
		startTime = Calendar.getInstance();
		board = new Board();
		createPlayers();
	}
	
	private void createPlayers() {
		players = new ArrayList<Player>();		
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
	
	public String getStartTimeMessage() {
		
		String message = "No game started";
		
		if(startTime != null) {
			
			String date = new SimpleDateFormat("EEEE, dd/MM/yyyy").format(startTime.getTime());
			String time = new SimpleDateFormat("hh:mm:ss.SSS a").format(startTime.getTime());
			message = "Game was started on " + date + " at " + time;
			 
		}
		
		return message;
		
	}

}
