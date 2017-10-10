package troublegame.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import troublegame.server.io.FileHandler;
import troublegame.server.io.Savable;

public class Connection implements Savable {
	private PrintWriter output;
	private BufferedReader input;
	private Socket clientSocket;
	private String username;
	private String password;
	
	// creates a connection mapping, as well as keeps the input and output stream wrapper
	public Connection(Socket c, BufferedReader i, PrintWriter o) {
		clientSocket = c;
		input = i;
		output = o;
		username = null;
	}
	
	@Override
	public void save(FileHandler fileHandler) {
		fileHandler.set("username", username);
		fileHandler.set("password", password);
	}

	@Override
	public void load(FileHandler fileHandler) {
		this.username = fileHandler.get("username");
		this.password = fileHandler.get("password");
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
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
}
