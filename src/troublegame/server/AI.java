package troublegame.server;

public class AI extends Player {

	public AI(int pid, String username, Colour colour) {
		super(pid, username, colour, Player.BOT);
	}
	
	public String getMove(Board board) {
		String move = null;
		
		for (Token token : this.getPlayerTokens()) {
			if (board.getTokenLoc(token).getSlotZone() != Board.SLOT_END) {
				move = "ROLL_DIE " + token.getTokenID();
				break;
			}
		}
		
		return move;
	}
	
}
