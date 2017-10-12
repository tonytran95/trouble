package troublegame.server;

import java.io.PrintWriter;

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
	
	public void handleGameRoomQuery(Connection user) {
		String gameroom = gameServer.getGameRoomName(user);
		PrintWriter outputStream = user.getOutputStream();
		outputStream.println("[GAME_ROOM_INFO] "+gameroom);
	}
}
