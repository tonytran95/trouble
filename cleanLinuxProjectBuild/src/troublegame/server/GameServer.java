package troublegame.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import troublegame.communication.CommunicationHandler;

/**
 * 
 * The main class for the trouble game server.
 *
 */
public class GameServer {
	
	private static final int TIME_PERIOD = 1000;
	
	/**
	 * The main function.
	 * @param args are the arguments.
	 */
	public static void main(String args[]) {
		
		GameServer gs = new GameServer();
		gs.startProcessing();
		
	}
	
	/**
	 * The socket listener.
	 */
	private SocketListener socketListener;
	
	/**
	 * The game engine.
	 */
	private GameEngine gameEngine;
	
	/**
	 * The login handler.
	 */
	private LoginHandler loginHandler;
	
	/**
	 * The lobby.
	 */
	private Lobby lobby;
	
	/**
	 * Constructs a new server.
	 */
	public GameServer() {
		
		System.out.println(CommunicationHandler.GAME_SERVER_INFO + " Starting game server...");
		
		/**
		 * Initializes the game engine.
		 */
		this.gameEngine = new GameEngine();
		
		this.lobby = new Lobby(this);
		
		/**
		 * Initializes the socket listener and login handler.
		 */
		this.socketListener = new SocketListener(getServerInfo().split(":"));
		this.loginHandler = new LoginHandler(this);
		this.lobby = new Lobby(this);
		this.socketListener.setLoginHandler(loginHandler);
		this.socketListener.setLobby(lobby);
		this.socketListener.init();
		
		/**
		 * Add the gameengine to the socketListener (temporary)
		 */
		this.socketListener.addGameEngine(this.gameEngine);
	}

	/**
	 * @return the server's ip and port in the format <ip:port>
	 */
	private String getServerInfo() {
		String line = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("./server.txt"));
			line = bufferedReader.readLine();
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
	
	public void login(Connection user) {
		lobby.addUser(user);
	}
	
	/**
	 * Finds which game room the user is in and returns the name of the game room
	 * @param user User to find
	 * @return Game room the user is in
	 */
	public GameRoom getGameRoomName(Connection user) {
		ArrayList<GameRoom> gameRooms = lobby.getGameRooms();
		for (GameRoom g: gameRooms) {
			if (g.isMember(user)) {
				return g;
			}
		}
		return null;
	}
	
	/**
	 * @return the socket listener.
	 */
	public SocketListener getSocketListener() {
		return socketListener;
	}
	
	public Lobby getLobby() {
		return this.lobby;
	}
	
	private void startProcessing() {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				gameEngine.process();
				loginHandler.process();
			}
		}, 0, TIME_PERIOD);
	}
}
