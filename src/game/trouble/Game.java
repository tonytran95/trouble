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
	
	private Board board;
	private Player[] players;
	private Calendar startTime;
	private ArrayList<Colour> availableColours;
	private int turnNum;
	private boolean started;
	private Map<Colour, String> humans;
	private Map<Colour, String> computers;
	
	
	public Game() {
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
			players[i] = createhumanPlayer(i, c, name);
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
	public Player createhumanPlayer(int id, Colour c, String username) {
		
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
		
		Player p = players[playerID];
		Token token = p.getToken(tokenID);
		Slot currentSlot = board.getTokenLoc(token);
		Colour col= token.getColour();
		int currPos = -1;
		int target = -1;
		
		// first we check if token is already in play		
		switch (currentSlot.getSlotLocation()) {
			case Board.SLOT_HOME:
				// sorry can't move
				if (diceValue != 6) {
					return "ROLL FAIL "+ diceValue;
				} else {
					int startIndex = board.getStartIndex(col);
					target = startIndex + diceValue;
				}
				break;
			case Board.SLOT_MAIN:
				ArrayList<Slot> mainSlots = board.getMainZone();
				currPos = mainSlots.indexOf(token);
				target = currPos + diceValue;
				break;
		}
		
		// Wraparound
		if(target >= Board.NUM_MAIN_SLOTS) 	target -= Board.NUM_MAIN_SLOTS;
		
		// actually do the move
		if(target != -1) {
			String s = "ROLLED " + diceValue + " " + tokenID + " " + p.getUsername();
			Slot targetSlot = board.getMainSlot(target);
			if (!targetSlot.isOccupied()) {
				targetSlot.setOccupyingToken(token);
				currentSlot.setOccupyingToken(null);
			}
			return s;
		}
		
		return null;
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
		for(int i =0; i<MAX_PLAYERS; i++) {
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
	
}
