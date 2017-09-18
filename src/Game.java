
public class Game {
	
	private Board board;
	private Player[] players;
	private Die die; // Move this to board?
	
	public Game() {
		board = new Board();
		die = new Die();
	}
	
}
