package troublegame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketListener {
	
	private int port;
	private ServerSocket socket;
	private ArrayList<Socket> clients;
	private ArrayList<Connection> connections;
	private boolean listening;
	private LoginHandler loginHandler;
	private GameEngine gameEngine;
	
	public SocketListener(int port) {
		System.out.println("[SocketListener] Initializing socket listener...");
		this.port = port;
		clients = new ArrayList<Socket>();
		connections = new ArrayList<Connection>();
		listening = true;
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getPort() {
		return port;
	}
	
	public ArrayList<Socket> getClients() {
		return clients;
	}
	
	public boolean isListening() {
		return listening;
	}
	
	public void init() {
		System.out.println("[SocketListener] Socket listening on port: " + this.port);
		Runnable serverTask = new Runnable() {

			@Override
			public void run() {
				try {
					while (this.isListening()) {
						Socket clientSocket = this.getSocket().accept();
						this.addClient(clientSocket);
						
						System.out.println("A user has connected from " + clientSocket.getInetAddress());
						
						Thread thread = new Thread(new Runnable() {
							@Override
							public void run() {
				                
				                try {
				                	// Establish the client's input stream.
					                BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					                
					                // Establish the server's output stream.
					                PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);
					                
					                Connection conn = new Connection(clientSocket, clientInput, clientOutput);
					                addConnection(conn);
					                
					                // TODO:later on we will have a method that adds player connections to correct gameEngines
					                
					                
					                while (true) {
					                	String input = clientInput.readLine();
					                	//System.out.println(input);
					                	
					                	// TEMPORARY
					                	if (input.startsWith("CONNECTED")) {
					                		conn.setUsername(input.substring(10));
					                		loginHandler.addConnectionToQueue(conn);
					                	//} else if (input.startsWith("COLORS")) {
					                		// make it print in the format "COLORS username color(lower case)"
					                		// still need to work on this
					                		//clientOutput.println("COLORS " + conn.getUsername() + " red");
					                	} else {
					                		// give input to gameengine
					                		if (input.startsWith("ROLLED")) {
					                			gameEngine.handleInput(conn, input);
					                		}
					      
					                	}
					                }
					                
								} catch (IOException e) {
									//e.printStackTrace();
								}

							}
						});
						thread.start();
					}
				} catch (IOException e) {
					//e.printStackTrace();
				} finally {
					this.stop();
				}
				
			}// end of run

			private boolean isListening() {
				return listening;
			}
			
			public void stop() {
				try {
					for (Socket client : clients) {
						client.close();
					}
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				listening = false;
			}
			
			public void addClient(Socket client) {
				clients.add(client);
			}
			
			public void addConnection(Connection conn) {
				connections.add(conn);
			}
			
			public ServerSocket getSocket() {
				return socket;
			}
		};	
		
		Thread serverThread = new Thread(serverTask);
        serverThread.start();
	}

	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}
	
	public void addGameEngine(GameEngine g) {
		this.gameEngine = g;
	}
	
}