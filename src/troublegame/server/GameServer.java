package troublegame.server;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import troublegame.server.io.UserManager;

/**
 * 
 * The main class for the trouble game server.
 *
 */
public class GameServer {
	
	private static final int TIME_PERIOD = 500;
	
	/**
	 * The port.
	 */
	public final static int PORT = 4321;
	
	/**
	 * The main function.
	 * @param args are the arguments.
	 */
	public static void main(String args[]) {
		GameServer GS = new GameServer();
		Runner gameRunner = GS.new Runner();
		Timer serverTimer = new Timer();
		
		// Test users
		UserManager.createAndSaveNewUser("tsmex@hotmail.com", "Tilly", "Smexy", Color.RANDOM, "Life is a highway");
		User tilly = UserManager.loadUser("tsmex@hotmail.com");
		
		UserManager.createAndSaveNewUser("bob@bob.com", "Bobby", "BillyBox", null, null);
		User bobby = UserManager.loadUser("bob@bob.com");
		
		UserManager.createAndSaveNewUser("asdf@asdf.asdf", "asdffffff", "random", Color.RED, "I love the beatles");
		User asdf = UserManager.loadUser("asdf@asdf.asdf");
		
		bobby.addFriend(bobby);
		bobby.addFriend(asdf);
		asdf.addFriend(bobby);
		asdf.addFriend(tilly);
		tilly.addFriend(bobby);
		bobby.removeFriend(tilly);
		bobby.updateUsername("Pirate");
		
		System.out.println("bobbys has " + bobby.getFriendList().size() + " friends. His friends are:");
		for(User friend : bobby.getFriendList()) System.out.println(" " + friend.getUsername());
		System.out.println();
		
		System.out.println("tilly has " + tilly.getFriendList().size() + " friends. Her friends are:");
		for(User friend : tilly.getFriendList()) System.out.println(" " + friend.getUsername());
		System.out.println();
		
		System.out.println("asdf has " + asdf.getFriendList().size() + " friends. Her friends are:");
		for(User friend : asdf.getFriendList()) System.out.println(" " + friend.getUsername());
		System.out.println();
		
		serverTimer.schedule(gameRunner, 0, TIME_PERIOD);
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
	 * The lobby handler.
	 */
	private LobbyHandler lobbyHandler;
	
	/**
	 * Constructs a new server.
	 */
	public GameServer() {
		System.out.println("[Game Server] Starting game server...");
		
		/**
		 * Initializes the game engine.
		 */
		this.gameEngine = new GameEngine();
		this.gameEngine.init();
		
		this.lobby = new Lobby();
		
		/**
		 * Initializes the socket listener and login handler.
		 */
		this.socketListener = new SocketListener(GameServer.PORT);
		this.loginHandler = new LoginHandler(this);
		this.lobbyHandler = new LobbyHandler(this);
		this.socketListener.setLoginHandler(loginHandler);
		this.socketListener.setLobbyHandler(lobbyHandler);
		this.socketListener.init();
		
		/**
		 * Add the gameengine to the socketListener (temporary)
		 */
		this.socketListener.addGameEngine(this.gameEngine);
	}
	
	public void login(Connection user) {
		lobby.addUser(user);
	}
	
	public void createGameRoom(Connection owner) {
		lobby.createGameRoom(owner);
	}

	public void joinGameRoom(Connection user, String roomName) {
		lobby.joinGameRoom(user, roomName);
	}
	
	/*
	 * finds which game room the user is in and returns the name of the game room
	 */
	public String getGameRoomName(Connection user) {
		ArrayList<GameRoom> gameRooms = lobby.getGameRooms();
		for (GameRoom g: gameRooms) {
			if (g.isMember(user)) {
				return g.getName();
			}
		}
		return "Not in any room";
	}
	
	/**
	 * @return the socket listener.
	 */
	public SocketListener getSocketListener() {
		return socketListener;
	}
	
	class Runner extends TimerTask {
		public void run() {
			gameEngine.process();
			loginHandler.process();
		}
	}
}

