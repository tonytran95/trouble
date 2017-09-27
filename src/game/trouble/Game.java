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
	private ArrayList<Colour> availableColours;
	private int turnNum;
	
	public Game(int numPlayers, int numHumans, String p1, Colour c1, String p2, Colour c2, String p3, Colour c3) {
		startTime = Calendar.getInstance();
		setAvailableColours();
		createPlayers(numPlayers, numHumans);
		board = new Board(players);
		turnNum = 0;
	}
	
	// TODO Randomise assignment order to mix up order of play within the game
	// TODO Assign actual requested colours to players instead of random colours
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
				players[i] = createHumanPlayer(i, Colour.RANDOM);
			} else {
				players[i] = createAIPlayer(i, Colour.RANDOM);
			}
		}
	}
	
	/**
	 * Create a human player with a random colour, the given id and the name Mr noName.
	 * @param id The index of the array in which the player sits. Will be used as player id
	 * @return A newly created player
	 */
	public Player createHumanPlayer(int id, Colour c) {
		
		Player tmp = new Player(id, "Mr noName", assignPlayerColour(c));
		return tmp;
	}
	
	// TODO Is this player class or AI class?
	/**
	 * Creates a new AI
	 * @return The newly created AI
	 */
	public AI createAIPlayer(int id, Colour c) {
		
		AI tmp = new AI(id, "Mr noName", assignPlayerColour(c));
		return tmp;
	}
	
	/**
	 * Add the four playable colours to the list of available colours
	 */
	public void setAvailableColours() {
		availableColours = new ArrayList<Colour>();
		availableColours.add(Colour.RED);
		availableColours.add(Colour.BLUE);
		availableColours.add(Colour.GREEN);
		availableColours.add(Colour.YELLOW);
	}
	
	/**
	 * If player wants a random colour give out random colour from available colours otherwise assign player requested colour
	 * @param colour Colour the player has requested
	 * @return The next available colour
	 */
	public Colour assignPlayerColour(Colour colour) {
		
		if(colour == Colour.RANDOM || availableColours.contains(colour) == false) {
			int rand = new Random().nextInt(availableColours.size());
			colour = availableColours.get(rand);
		}
		
		availableColours.remove(colour);
		return colour;
	}
	
	// Move the selected token the number of slots rolled on the die
	public void movePlayerToken(Token token, int diceValue) {
		
		int currPos = token.getCurrPos();
		int target = currPos + diceValue;
		
		// Wraparound
		if(target >= Board.NUM_MAIN_SLOTS) {
			target -= Board.NUM_MAIN_SLOTS;
		}
		
		if(token.getColour() == Colour.RED && currPos > target) {
			
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
	
	// checks if the game is over by looking at all the player's home slots
	public boolean isOver() {
		for (Player p: players) {
			ArrayList<Slot> homeslot = board.getPlayerEndZone(p);
			int filledSlots = 0;
			for (Slot s: homeslot) {
				if (s.isOccupied()) filledSlots++;
			}
			if (filledSlots == Player.NUM_TOKENS) return true;
		}
		return false;
	}
	
	public int getTurnNum() {
		return this.turnNum;
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
