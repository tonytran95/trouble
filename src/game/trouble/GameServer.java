package game.trouble;

import java.util.Timer;
import java.util.TimerTask;

import game.trouble.network.SocketListener;

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
	 * Constructs a new server.
	 */
	public GameServer() {
		System.out.println("[Game Server] Starting game server...");
		
		/**
		 * Initializes the game engine.
		 */
		this.gameEngine = new GameEngine();
		this.gameEngine.init();
		
		/**
		 * Initializes the socket listener.
		 */
		this.socketListener = new SocketListener(GameServer.PORT);
		this.socketListener.init();
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
		}
	}
}

