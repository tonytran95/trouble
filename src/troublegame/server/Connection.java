package troublegame.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
	private PrintWriter output;
	private BufferedReader input;
	private Socket clientSocket;
	private String username;
	
	// creates a connection mapping, as well as keeps the input and output stream wrapper
	public Connection(Socket c, BufferedReader i, PrintWriter o) {
		clientSocket = c;
		input = i;
		output = o;
		username = null;
	}
	
	public BufferedReader getInputStream() {
		return input;
	}
	
	public PrintWriter getOutputStream() {
		return output;
	}
	
	public void setUsername(String u) {
		this.username = u;
	}
	
	public String getUsername() {
		return username;
	}
}
