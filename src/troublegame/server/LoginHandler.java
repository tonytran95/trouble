package troublegame.server;

import java.util.LinkedList;
import java.util.Queue;

import troublegame.communication.CommunicationHandler;

/**
 * 
 * A connection that has been entered in the server will be handled
 * in this class.
 * 
 * @author Jeffrey Ung
 * 
 */
public class LoginHandler {

	/**
	 * The login queue.
	 */
	private Queue<Connection> loginQueue;
	
	/**
	 * The game server.
	 */
	private GameServer gameServer;
	
	/**
	 * Constructs a new login handler.
	 */
	public LoginHandler(GameServer gameServer) {
		this.gameServer = gameServer;
		this.loginQueue = new LinkedList<Connection>();
	}
	
	/**
	 * Adds a connection to the login queue.
	 * @param connection being added to the queue.
	 */
	public void addConnectionToQueue(Connection connection) {
		loginQueue.add(connection);
	}
	
	/**
	 * @param player is the user connecting the server.
	 * @return true if the player is not already logged in.
	 */
	public void login(Connection connection) {
		for (Connection other : gameServer.getSocketListener().getConnections()) {
			if (connection != other && connection.getUser().getEmail().equals(other.getUser().getEmail())) {
				connection.getOutputStream().println(CommunicationHandler.LOGIN_ERROR + " The user you have entered is already connected!");
				return;
			}
		}
		this.gameServer.login(connection);
		connection.getOutputStream().println(CommunicationHandler.LOGIN_SUCCESS + " " + connection.getUsername());
	}
	
	/**
	 * The process method runs in an infinite loop until
	 * the server stops.
	 */
	public void process() {
		if (loginQueue.isEmpty())
			return;
		login(loginQueue.poll());
	}
	
}
