package game.trouble;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import game.trouble.model.AI;
import game.trouble.model.Board;
import game.trouble.model.Colour;
import game.trouble.model.Player;
import game.trouble.model.board.Slot;
import game.trouble.model.board.Token;

public class Game {
	
	public static final int MAX_PLAYERS = 4;
	
	private GameEngine engine;
	private Board board;
	private Player[] players;
	private Calendar startTime;
	private ArrayList<Colour> availableColours;
	private int turnNum;
	private boolean started;
	private Map<Colour, String> humans;
	private Map<Colour, String> computers;
	
	
	public Game(GameEngine engine) {
		this.engine = engine;
		humans = new HashMap<Colour, String>();
		computers = new HashMap<Colour, String>();
		started = false;
	}
	
	public void start() {
		startTime = Calendar.getInstance();
		setAvailableColours();
		players = new Player[MAX_PLAYERS];
		int i = 0;
		// make the humans players
		for (Map.Entry<Colour, String> entry : humans.entrySet()) {
			Colour c = entry.getKey();
		    String name = entry.getValue();
			players[i] = createHumanPlayer(i, c, name);
			i++;
		}
		// make the AI players
		for (Map.Entry<Colour, String> entry : computers.entrySet()) {
			Colour c = entry.getKey();
		    String name = entry.getValue();
			players[i] = createAIPlayer(i, c, name);
			i++;
		}
		board = new Board(players);
		this.started = true;
		
		for (Player p : players) {
			System.out.println(p.getPlayerTokens().length);
			for (int j = 0; j < p.getPlayerTokens().length; j++)
				board.setTokenLoc(p.getToken(j), Board.SLOT_HOME, j);
			switch (p.getColour()) {
				case RED:
					engine.broadcast("COLORS " + p.getUsername() + " " + "red");
					break;
				case BLUE:
					engine.broadcast("COLORS " + p.getUsername() + " " + "blue");
					break;
				case YELLOW:
					engine.broadcast("COLORS " + p.getUsername() + " " + "yellow");
					break;
				case GREEN:
					engine.broadcast("COLORS " + p.getUsername() + " " + "green");
					break;
				default:
			}
		}
		//engine.updateMessages();
	}
	
	public void join(String username, Colour colour, boolean computer) {
		if (computer) {
			computers.put(colour, username);
			return;
		}
		humans.put(colour, username);
	}
	

	// TODO Randomise assignment order to mix up order of play within the game
	// TODO Assign actual requested colours to players instead of random colours
	// game so turn play does not always go all humanss followed by all AI's
	/**
	 * Creates players for AI and humans users who want to play a game
	 */
	public void create() {

	}
	
	/**
	 * Create a humans player with a random colour, the given id and the name Mr noName.
	 * @param id The index of the array in which the player sits. Will be used as player id
	 * @return A newly created player
	 */
	public Player createHumanPlayer(int id, Colour c, String username) {
		
		Player tmp = new Player(id, username, assignPlayerColour(c), Player.HUMAN);
		return tmp;
	}
	
	// TODO Is this player class or AI class?
	/**
	 * Creates a new AI
	 * @return The newly created AI
	 */
	public AI createAIPlayer(int id, Colour c, String username) {
		
		AI tmp = new AI(id, username, assignPlayerColour(c));
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
	
	/**
	 * Applies a roll to a player's token given the playerID, tokenID and the roll value
	 * @param playerID
	 * @param tokenID
	 * @param diceValue
	 */
	public String movePlayerToken(int playerID, int tokenID, int diceValue) {
		String command = null;
		Player p = players[playerID];
		Token token = p.getToken(tokenID);
		Slot currentSlot = board.getTokenLoc(token);
		Colour col = token.getColour();
		int currPos = -1;
		int target = -1;
		// first we check if token is already in play		
		switch (currentSlot.getSlotZone()) {
			case Board.SLOT_HOME:
				// sorry can't move
				if (diceValue == 6) {
					command = "ROLL_FAIL " + diceValue;
					turnNum++;
					engine.updateMessages();
				} else {
					int startIndex = board.getStartIndex(col);
					command = "ROLL_AGAIN " + diceValue + " " + tokenID + " " + p.getUsername() + " " + Board.SLOT_MAIN + " " + startIndex;
					board.setTokenLoc(token, Board.SLOT_MAIN, startIndex);
				}
				break;
			case Board.SLOT_MAIN:
				int startIndex = board.getStartIndex(col);
				int endIndex = board.getEndIndex(col);
				ArrayList<Slot> mainSlots = board.getMainZone();
				currPos = currentSlot.getSlotIndex();	
				if (currPos == endIndex) { // move into endzone
					switch (diceValue) {
						case 1: 
							if (board.getPlayerEndZone(p).get(0).getOccupyingToken() == null) {
								command = "ROLL_SUCCESS " + diceValue + " " + tokenID + " " + p.getUsername() + " " + Board.SLOT_END + " " + 0;
								board.setTokenLoc(token, Board.SLOT_END, 0);
							} else {
								command = "ROLL_FAIL " + + diceValue + ", token already occupying end slot";
							}
							break;
						case 2:
							if (board.getPlayerEndZone(p).get(1).getOccupyingToken() == null) {
								command = "ROLL_SUCCESS " + diceValue + " " + tokenID + " " + p.getUsername() + " " + Board.SLOT_END + " " + 1;
								board.setTokenLoc(token, Board.SLOT_END, 1);
							} else {
								command = "ROLL_FAIL " + + diceValue + ", token already occupying end slot";
							}
							break;
						case 3:
							if (board.getPlayerEndZone(p).get(2).getOccupyingToken() == null) {
								command = "ROLL_SUCCESS " + diceValue + " " + tokenID + " " + p.getUsername() + " " + Board.SLOT_END + " " + 2;
								board.setTokenLoc(token, Board.SLOT_END, 2);
							} else {
								command = "ROLL_FAIL " + + diceValue + ", token already occupying end slot";
							}
							break;
						case 4:
							if (board.getPlayerEndZone(p).get(3).getOccupyingToken() == null) {
								command = "ROLL_SUCCESS " + diceValue + " " + tokenID + " " + p.getUsername() + " " + Board.SLOT_END + " " + 3;
								board.setTokenLoc(token, Board.SLOT_END, 3);
							} else {
								command = "ROLL_FAIL " + + diceValue + ", token already occupying end slot";
							}
							break;
						default:
							command = "ROLL_FAIL " + + diceValue + ", must roll a value of 1-4 to enter the end zone";
					}
					turnNum++;
				} else { // keep moving alone mainzone
					target = currPos + diceValue;
					if (startIndex < endIndex) { // ONLY TRUE FOR RED
						if (target > endIndex) target = endIndex;
					} else { // EVERY OTHER COLOUR
						if (target > (Board.NUM_MAIN_SLOTS - 1)) target = target % Board.NUM_MAIN_SLOTS;
						if (currPos < endIndex) {
							if (target > endIndex) target = endIndex;
						}
					}
				}
				
				break;
		}
		
		// Wraparound
		//if (target >= Board.NUM_MAIN_SLOTS) 	target -= Board.NUM_MAIN_SLOTS;
		
		// actually do the move
		if (target != -1) {
			command = "ROLLED " + diceValue + " " + tokenID + " " + p.getUsername() + " " + Board.SLOT_MAIN + " " + target;
			board.setTokenLoc(token, Board.SLOT_MAIN, target);
			turnNum++;
			engine.updateMessages();
		}
		
		return command;
	}
	
	// uses turn number to determine who's turn it is, then returns the player object
	// by using the store players - this means players must always be ordered red blue yellow green as 0-3
	public Player getWhoseTurn() {
		int turnID = turnNum % MAX_PLAYERS;
		return players[turnID];
	}
	
	// checks if the game is over by looking at all the player's home slots
	public boolean isOver() {
		if (!started)
			return false;
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
			message = "[Game] Game was started on " + date + " at " + time;
			 
		}
		
		return message;
		
	}
	
	// returns an arraylist containing all human players
	public ArrayList<Player> getHumanPlayers() {
		ArrayList<Player> human = new ArrayList<Player>();
		for(int i =0; i<players.length; i++) {
			if (players[i].getType() == Player.HUMAN)
				human.add(players[i]);
		}
		
		return human;
	}
	
	public void showPlayers() {
		System.out.print("[Game]Players: ");
		for(int i = 0; i < MAX_PLAYERS; i++) {
			System.out.print(players[i].getUsername()+" ");
		}
		System.out.print("\n");
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public int rollDie() {
		return board.getDie().rollDie();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void incrementTurn() {
		turnNum++;
	}
	
}
