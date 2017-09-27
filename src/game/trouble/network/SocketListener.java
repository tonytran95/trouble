package game.trouble.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketListener {
	
	private int port;
	private ServerSocket socket;
	private ArrayList<Socket> clients;
	private ArrayList<Connection> connections;
	private boolean listening;
	
	public SocketListener(int port) {
		System.out.println("[SocketListener] Initializing socket listener...");
		this.port = port;
		clients = new ArrayList<Socket>();
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
	
	public ServerSocket getSocket() {
		return socket;
	}
	
	public ArrayList<Socket> getClients() {
		return clients;
	}
	
	public void addClient(Socket client) {
		clients.add(client);
	}
	
	public void addConnection(Connection c) {
		connections.add(c);
	}
	
	public boolean isListening() {
		return listening;
	}
	
	public void init() {
		System.out.println("[SocketListener] Socket listening on port: " + this.port);
		try {
			while (this.isListening()) {
				Socket clientSocket = this.getSocket().accept();
				this.addClient(clientSocket);
				
				System.out.println("A user has connected from " + clientSocket.getInetAddress());
				
				// setup the connection mapping
				try {
                	// Establish the client's input stream.
	                BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	                
	                // Establish the server's output stream.
	                DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
	                
	                Connection c = new Connection(clientSocket, clientInput, clientOutput);
	                this.addConnection(c);
	                
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {

					}

				});
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.stop();
		}
		
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
	
}