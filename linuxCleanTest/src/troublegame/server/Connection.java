package troublegame.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private User user;
	private boolean guest;
	
	// creates a connection mapping, as well as keeps the input and output stream wrapper
	public Connection(Socket socket, BufferedReader i, PrintWriter o) {
		this.socket = socket;
		input = i;
		output = o;
		user = null;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public BufferedReader getInputStream() {
		return input;
	}
	
	public PrintWriter getOutputStream() {
		return output;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User u) {
		this.user = u;
	}
	
	/**
	 * @return The username of the user who holds this connection
	 */
	public String getUsername() {
		return getUser().getUsername();
	}

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest) {
		this.guest = guest;
	}
	
}
