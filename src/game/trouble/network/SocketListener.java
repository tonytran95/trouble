package game.trouble.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketListener {
	
	private int port;
	private ServerSocket socket;
	private ArrayList<Socket> clients;
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
	
	public boolean isListening() {
		return listening;
	}
	
	public void init() {
		System.out.println("[SocketListener] Socket listening on port: " + this.port);
		try {
			while (this.isListening()) {
				Socket clientSocket = this.getSocket().accept();
				this.addClient(clientSocket);
				
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						// do something
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