package troublegame.server;

public class LobbyHandler {

	private GameServer gameServer;
	
	public LobbyHandler(GameServer gameServer) {
		this.gameServer = gameServer;
	}
	
	public void createGameRoom(Connection owner) {
		this.gameServer.createGameRoom(owner);
	}
	
	public void joinGameRoom(Connection user, String roomName) {
		this.gameServer.joinGameRoom(user, roomName);
	}
	
}
