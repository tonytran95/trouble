package game.trouble;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Game {
	
	public static final int MAX_PLAYERS = 4;
	
	private Board board;
	private Player[] players;
	private Calendar startTime;
	private ArrayList<Integer> availableColours;
	
	public Game(int numPlayers, int numHumans) {
		startTime = Calendar.getInstance();
		setAvailableColours();
		createPlayers(numPlayers, numHumans);
		//TODO distribute player colours here
		board = new Board(players);
	}
	
	// TODO Possibly randomise assignment order to mix up order of play within the 
	// game so turn play does not always go all humans followed by all AI's
	/**
	 * Creates players for AI and human users who want to play a game
	 * @param numPlayers
	 * @param numHumans
	 */
	public void createPlayers(int numPlayers, int numHumans) {
		players = new Player[numPlayers];
		for(int i = 0; i < players.length; i++) {
			if(i < numHumans) {
				players[i] = createHumanPlayer(i);
			} else {
				players[i] = createAIPlayer(i);
			}
		}
	}
	
	/**
	 * Create a human player with a random colour, the given id and the name Mr noName.
	 * @param id The index of the array in which the player sits. Will be used as player id
	 * @return A newly created player
	 */
	public Player createHumanPlayer(int id) {
		int randColour = new Random().nextInt(availableColours.size());
		
		Player tmp = new Player(id, "Mr noName", availableColours.get(randColour));
		availableColours.remove(randColour);
		return tmp;
	}
	
	// TODO Is this player class or AI class?
	/**
	 * Creates a new AI
	 * @return The newly created AI
	 */
	public AI createAIPlayer(int id) {
		int randColour = new Random().nextInt(availableColours.size());
		
		AI tmp = new AI(id, "Mr noName", availableColours.get(randColour));
		availableColours.remove(randColour);
		return tmp;
	}
	
	/**
	 * Add the four playable colours to the list of available colours
	 */
	public void setAvailableColours() {
		availableColours = new ArrayList<Integer>();
		availableColours.add(Player.RED);
		availableColours.add(Player.BLUE);
		availableColours.add(Player.GREEN);
		availableColours.add(Player.YELLOW);
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
			
			// Red token has finished their board rotation and can go to the endzone 
			// if they havent rolled too high a number
			if(target - token.getTokenEnd() < 5) {
				
			}
			
		} else if(currPos < token.getTokenStart() && target > token.getTokenEnd()) {
			
			// All tokens except red token have finished their board rotation and can 
			// go to the endzone if they havent rolled too high a number
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
