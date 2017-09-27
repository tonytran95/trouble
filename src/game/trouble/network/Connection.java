package game.trouble.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

public class Connection {
	private DataOutputStream output;
	private BufferedReader input;
	private Socket clientSocket;
	private String username;
	
	// creates a connection mapping, as well as keeps the input and output stream wrapper
	public Connection(Socket c, BufferedReader i, DataOutputStream o) {
		clientSocket = c;
		input = i;
		output = o;
		username = null;
	}
	
	public BufferedReader getInputStream() {
		return input;
	}
	
	public DataOutputStream getOutputStream() {
		return output;
	}
	
	public void setUsername(String u) {
		this.username = u;
	}
}
