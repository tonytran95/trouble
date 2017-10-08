package game.trouble.model;

public class AI extends Player {

	public AI(int pid, String username, Colour colour) {
		super(pid, username, colour, Player.BOT);
	}
	
	public String getMove(Board board) {
		String move = "ROLL_DIE";
		
		return move;
	}
	
}
