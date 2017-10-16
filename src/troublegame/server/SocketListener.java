package troublegame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import troublegame.communication.CommunicationHandler;
import troublegame.server.io.UserManager;

public class SocketListener {
	
	private int port;
	private ServerSocket socket;
	private ArrayList<Socket> clients;
	private ArrayList<Connection> connections;
	private boolean listening;
	private LoginHandler loginHandler;
	private Lobby lobby;
	private GameEngine gameEngine;
	
	public SocketListener(int port) {
		System.out.println(CommunicationHandler.SOCKET_LISTENER_INFO + " Initializing socket listener...");
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
		System.out.println(CommunicationHandler.SOCKET_LISTENER_INFO + " Socket listening on port: " + this.port);
		Runnable serverTask = new Runnable() {

			@Override
			public void run() {
				try {
					while (this.isListening()) {
						
						Socket clientSocket = this.getSocket().accept();
						this.addClient(clientSocket);
						
						System.out.println(CommunicationHandler.SOCKET_LISTENER_INFO + " A user has connected from " + clientSocket.getInetAddress());
						
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
										System.out.println("Client Sent: " + input);
										// TEMPORARY
										if (input.startsWith(CommunicationHandler.LOGIN_REQUEST)) {
											
											String[] inputSplit = input.split(" ");
											
											String receivedEmail = inputSplit[1];
											String receivedPass = inputSplit[2];
											
											User tmp = UserManager.loadUserByEmail(receivedEmail);
											PrintWriter serverStream = conn.getOutputStream();
											
											if(tmp == null) {
												serverStream.println(CommunicationHandler.LOGIN_ERROR + " No user with the email " + receivedEmail + " was found");
											} else if (tmp.getPassword().equals(receivedPass)) {
												conn.setUser(tmp);
												serverStream.println(CommunicationHandler.LOGIN_SUCCESS + " " + tmp.getUsername());
												loginHandler.addConnectionToQueue(conn);
											} else {
												conn.getOutputStream().println(CommunicationHandler.LOGIN_ERROR + " Incorrect password");
											}
											
										} else if (input.equals(CommunicationHandler.GAME_ROOM_NEW)) {
											System.out.println(conn.getUser().getUsername()+" created a room");
											lobby.createGameRoom(conn);
										} else if (input.startsWith(CommunicationHandler.GAME_ROOM_JOIN)) {
											String[] inputSplit = input.split("] ");
											lobby.joinGameRoom(conn, inputSplit[1]);
										} else if (input.startsWith(CommunicationHandler.GAME_ROLL)) {
											gameEngine.handleInput(conn, input);
										} else if (input.startsWith(CommunicationHandler.GAME_ROOM_INFO)) {
											lobby.handleGameRoomQuery(conn);
										} else if (input.startsWith(CommunicationHandler.GAME_ROOM_CHAT)) {
											String message = input.substring(CommunicationHandler.GAME_ROOM_CHAT.length());
											lobby.handleChat(conn, message);
										} else if(input.startsWith(CommunicationHandler.GAME_ROOM_LEAVE)) {
											lobby.leaveGameRoom(conn);
										} else if (input.startsWith(CommunicationHandler.LOGOUT_REQUEST)) {
											// TODO Logout action
										} else if (input.startsWith(CommunicationHandler.GAME_START)) {
											String gameRoomName = input.substring(CommunicationHandler.GAME_START.length() + 1);
											gameEngine.createGame(lobby.getGameRoomByName(gameRoomName).getMembers());
											
										} else if (input.startsWith(CommunicationHandler.GAME_CHAT)) {
											String message = input.substring(CommunicationHandler.GAME_CHAT.length());
											gameEngine.handleChat(conn, message);
										} else if (input.startsWith(CommunicationHandler.UPDATE_DISPLAYNAME)) {
											String[] inputSplit = input.split(" ");
											String newDisplayname = inputSplit[1];
											String myPassword = inputSplit[2];
											User u = conn.getUser();
											if (myPassword.equals(u.getPassword())) {
												u.updateUsername(newDisplayname);
												conn.getOutputStream().println(CommunicationHandler.UPDATE_SUCCESS);
											} else {
												conn.getOutputStream().println(CommunicationHandler.UPDATE_FAIL);
											}
										} else {
											System.out.println("Unknown Command: " + input);
										}
									}
								} catch (IOException e) {
									e.printStackTrace();
								}

							}
						});
						thread.start();
					}
				} catch (IOException e) {
					e.printStackTrace();
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
	
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
	
	public void addGameEngine(GameEngine g) {
		this.gameEngine = g;
	}
	
}