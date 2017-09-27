package game.trouble;

import game.trouble.network.SocketListener;

/**
 * 
 * The main class for the trouble game server.
 *
 */
public class GameServer {

	/**
	 * The port.
	 */
	public final static int PORT = 4321;
	
	/**
	 * The main function.
	 * @param args are the arguments.
	 */
	public static void main(String args[]) {
		new GameServer();
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
	 * Constructs a new server.
	 */
	public GameServer() {
		/**
		 * Initializes the socket listener.
		 */
		this.socketListener = new SocketListener(GameServer.PORT);
		this.socketListener.init();
		
		/**
		 * Initializes the game engine.
		 */
		this.gameEngine = new GameEngine();
		this.gameEngine.init();
	}

	/**
	 * @return the socket listener.
	 */
	public SocketListener getSocketListener() {
		return socketListener;
	}
	
}
