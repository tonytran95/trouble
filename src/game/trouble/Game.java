package game.trouble;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Game {
	
	public static final int MAX_PLAYERS = 4;
	
	private Board board;
	private Player[] players;
	private Calendar startTime;
	private String[] availableColours;
	
	public Game(int numPlayers) {
		startTime = Calendar.getInstance();
		availableColours = new String[]{"R", "B", "Y", "G"};
		players = new Player[numPlayers];
		board = new Board(players);
	}
	
	// Move the selected token the number of slots rolled on the die
	public void movePlayerToken(Token token, int diceValue) {
		
		int currPos = token.getCurrPos();
		int target = currPos + diceValue;
		
		// Wraparound
		if(target >= Board.NUM_MAIN_SLOTS) {
			target -= Board.NUM_MAIN_SLOTS;
		}
		
		if(token.getColour() == Player.RED && currPos > target) {
			
		} else if(currPos < token.getTokenStart() && target > token.getTokenEnd()) {
			
			if(target - token.getTokenEnd() < 5) {
				
			}
			
		} else {
			
		}
		
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
