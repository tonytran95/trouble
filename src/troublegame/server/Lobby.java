package troublegame.server;

import java.util.ArrayList;

public class Lobby {

	private ArrayList<Connection> users;
	private ArrayList<GameRoom> gameRooms;
	
	public Lobby() {
		users = new ArrayList<Connection>();
		gameRooms = new ArrayList<GameRoom>();
	}
	
	public ArrayList<Connection> getUsers() {
		return users;
	}
	
	public ArrayList<GameRoom> getGameRooms() {
		return gameRooms;
	}
	
	public void addUser(Connection user) {
		users.add(user);
		for (GameRoom gameRoom : gameRooms)
			user.getOutputStream().println("[GAME_ROOM] " + gameRoom.getName());
	}
	
	public void createGameRoom(Connection owner) {
		GameRoom gameRoom = new GameRoom(owner);
		gameRooms.add(gameRoom);
		for (Connection user : users)
			if (!user.equals(owner)) {
				user.getOutputStream().println("[GAME_ROOM] " + gameRoom.getName());
			} else {
				user.getOutputStream().println("[CREATED_GAME_ROOM] " + gameRoom.getName());
				gameRoom.addConnection(user);
			}

	}
	
	public void joinGameRoom(Connection user, String gameName) {
		GameRoom game = null;
		for (GameRoom gameRoom : gameRooms)
			if (gameRoom.getName().equals(gameName)) game = gameRoom;
		if (game != null) {
			user.getOutputStream().println("[JOINED_GAME_ROOM] " + gameName);
			game.addConnection(user);
		}
	}
	
}