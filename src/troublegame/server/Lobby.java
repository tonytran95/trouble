package troublegame.server;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Lobby {

	private ArrayList<Connection> users;
	private ArrayList<GameRoom> gameRooms;
	private GameServer gameServer;
	
	public Lobby(GameServer gameServer) {
		this.gameServer = gameServer;
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
		for (GameRoom gameRoom : gameRooms) {
			if (gameRoom.getName().equals(gameName)) {
				game = gameRoom;
				break;
			}
		}
		if (game != null) {
			user.getOutputStream().println("[JOINED_GAME_ROOM] " + gameName);
			game.addConnection(user);
		}
	}
	
	public void leaveGameRoom(Connection user) {
		GameRoom game = null;
		for (GameRoom gameRoom : gameRooms) {
			if (gameRoom.getMembers().contains(user)) {
				game = gameRoom;
				break;
			}
		}
		if (game != null) {
			user.getOutputStream().println("[GAME_ROOM_LEAVE]");
			for (Connection member : game.getMembers())
				if (member != user)
					member.getOutputStream().println("[GAME_ROOM_LEAVE] " + user.getUsername());
			game.removeConnection(user);
			if (game.getMembers().size() == 0)
				this.gameRooms.remove(game);
		}
	}
	
	public void handleGameRoomQuery(Connection user) {
		GameRoom gameroom = gameServer.getGameRoomName(user);
		PrintWriter outputStream = user.getOutputStream();
		if (gameroom != null) {
			outputStream.println("[GAME_ROOM_INFO] "+gameroom.getName());
		}
	}
	
	public void handleChat(Connection user, String message) {
		GameRoom gameroom = gameServer.getGameRoomName(user);
		gameroom.doChat(user, message);
	}
	
}