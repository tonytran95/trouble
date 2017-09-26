import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TroubleServer {
	
	private int port;
	private ServerSocket socket;
	private ArrayList<Socket> clients;
	private boolean listening;
	
	public TroubleServer(int port) {
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
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("usage: java -jar TroubleServer.jar <port>");
			return;
		}
		TroubleServer server = new TroubleServer(Integer.parseInt(args[0]));
		try {
			while (server.isListening()) {
				Socket clientSocket = server.getSocket().accept();
				server.addClient(clientSocket);
				
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
			server.stop();
		}
		
	}
	
}