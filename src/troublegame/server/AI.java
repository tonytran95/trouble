package troublegame.server;

public class AI extends Player {

	public AI(int pid, String username, Color color) {
		super(pid, username, color, Player.BOT);
	}
	
	public String getMove(Board board) {
		String move = null;
		
		for (Token token : this.getPlayerTokens()) {
			if (board.getTokenLoc(token).getSlotZone() != Board.SLOT_END) {
				move = "[GAME_ROLL] " + token.getTokenID();
				break;
			}
		}
		
		return move;
	}
	
}
