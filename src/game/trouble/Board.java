package game.trouble;


public class Board {

	private Slot[] positions;
	private Die die;
	
	public static final int NUM_BOARD_SLOTS = 28;
	
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
		
		positions = new Slot[NUM_BOARD_SLOTS];
	}
	
}
