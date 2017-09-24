package game.trouble;

import java.util.ArrayList;

public class Board {

	private ArrayList<Slot> slots;
	private Die die;
	
	public static final int NUM_BOARD_SLOTS = 28;
	
	// Starting positions
	public static final int RED_START = 0;
	public static final int BLUE_START = 7;
	public static final int YELLOW_START = 14;
	public static final int GREEN_START = 21;
	
	// Ending positions
//	public static final int RED_END = 27;
//	public static final int BLUE_END = 6;
//	public static final int YELLOW_END = 13;
//	public static final int GREEN_END = 20;
	
	public Board() {
		slots = new ArrayList<Slot>(NUM_BOARD_SLOTS);
		die = new Die();
	}
	
	public int getTokenLoc(Token token) {
		return slots.indexOf(token);
	}
	
}
