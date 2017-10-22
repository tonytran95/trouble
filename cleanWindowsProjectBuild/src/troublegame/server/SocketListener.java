package troublegame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

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
	
	public SocketListener(String[] strings) {
		System.out.println(CommunicationHandler.SOCKET_LISTENER_INFO + " Initializing socket listener...");
		this.port = Integer.parseInt(strings[1]);
		clients = new ArrayList<Socket>();
		connections = new ArrayList<Connection>();
		listening = true;
		try {
			socket = new ServerSocket(this.port);
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
	
	public void disconnect(Connection conn, Socket clientSocket) throws IOException {
		System.out.println(conn.getUsername() + " has disconnected from the server.");
		connections.remove(conn);
		clients.remove(clientSocket);
		lobby.leaveGameRoom(conn);
		lobby.removeUser(conn);
		gameEngine.removeConnection(conn);
		clientSocket.close();
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
									
									
									while (clientSocket.isConnected()) {
										
										String input = clientInput.readLine();
										if (input == null) {
											disconnect(conn, clientSocket);
											return;
										}
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
												conn.setGuest(false);
												loginHandler.addConnectionToQueue(conn);
											} else {
												conn.getOutputStream().println(CommunicationHandler.LOGIN_ERROR + " Incorrect password");
											}
										} else if (input.startsWith(CommunicationHandler.REGISTER_REQUEST)) {
											String[] registerSplit = input.split("\t");
											Color favColor = null;
											switch (registerSplit[4]) {
											case "RED": favColor = Color.RED; break;
											case "BLUE": favColor = Color.BLUE; break;
											case "GREEN": favColor = Color.GREEN; break;
											case "YELLOW": favColor = Color.YELLOW; break;
											}
											int i = UserManager.createAndSaveNewUser(registerSplit[1], registerSplit[2], registerSplit[3], favColor, registerSplit[5]);
											if (i == 0) conn.getOutputStream().println(CommunicationHandler.REGISTER_SUCCESS);
											else if (i == 1) conn.getOutputStream().println(CommunicationHandler.REGISTER_ERROR);
										} else if (input.startsWith(CommunicationHandler.LOGIN_GUEST)) {
											int guestCount = 0;
											for (Connection user : connections) {
												if (user.equals(conn))
													continue;
												if (user.getUser().getPassword() == null) {
													guestCount++;
												}
											}
											User guestUser = new User("GUEST_" + guestCount);
											conn.setUser(guestUser);
											loginHandler.addConnectionToQueue(conn);
											conn.setGuest(true);
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
											lobby.handleGameroomChat(conn, message);
										} else if(input.startsWith(CommunicationHandler.GAME_ROOM_LEAVE)) {
											lobby.leaveGameRoom(conn);
										} else if (input.equals(CommunicationHandler.LOGOUT_REQUEST)) {
											conn.getOutputStream().println(CommunicationHandler.LOGOUT_SUCCESS);				
											disconnect(conn, clientSocket);
											lobby.broadcastOnlineList();
										} else if (input.startsWith(CommunicationHandler.GAME_START)) {
											String gameRoomName = input.substring(CommunicationHandler.GAME_START.length() + 1, input.length() - 2);
											int startNum = Integer.parseInt(input.substring(input.length() - 1));
											GameRoom g = lobby.getGameRoomByName(gameRoomName);
											if (g.isOwner(conn)) {
												gameEngine.createGame(lobby.getGameRoomByName(gameRoomName).getMembers(), startNum);
												lobby.broadcastActivity(conn.getUsername()+ " has started his game.");
												for (Connection u : connections)
													u.getOutputStream().println(CommunicationHandler.GAME_ROOM_CLOSE + " " + gameRoomName);
												lobby.getGameRooms().remove(g);
											} else {
												conn.getOutputStream().println(CommunicationHandler.GAME_START_FAIL);
											}
										} else if (input.startsWith(CommunicationHandler.GAME_CHAT)) {
											String message = input.substring(CommunicationHandler.GAME_CHAT.length() + 1);
											gameEngine.handleChat(conn, message);
										} else if (input.startsWith(CommunicationHandler.UPDATE_DISPLAYNAME)) {
											String[] inputSplit = input.split(" ");
											String newDisplayname = inputSplit[1];
											String myPassword = inputSplit[2];
											User u = conn.getUser();
											if (myPassword.equals(u.getPassword())) {
												u.updateUsername(newDisplayname);
												conn.getOutputStream().println(CommunicationHandler.UPDATE_SUCCESS + " " + newDisplayname);
											} else {
												conn.getOutputStream().println(CommunicationHandler.UPDATE_FAIL);
											}

										} else if (input.startsWith(CommunicationHandler.CHANGE_PASSWORD)) {
											String[] inputSplit = input.split(" ");
											String newPassword = inputSplit[1];
											String myPassword = inputSplit[2];
											User u = conn.getUser();
											if (myPassword.equals(u.getPassword())) {
												u.updatePassword(newPassword);
												conn.getOutputStream().println(CommunicationHandler.CHANGE_SUCCESS);
											} else {
												conn.getOutputStream().println(CommunicationHandler.UPDATE_FAIL);
											}

										} else if (input.startsWith(CommunicationHandler.GET_STATISTICS)) {
											User u = conn.getUser();
											String statistics = " "+u.getGamesPlayed()+" "+ u.getGamesWon();
											conn.getOutputStream().println(CommunicationHandler.GET_STATISTICS+ statistics);
										} else if (input.startsWith(CommunicationHandler.LOBBY_CHAT)) {
											String message = input.substring(CommunicationHandler.LOBBY_CHAT.length() + 1);
											lobby.handleLobbyChat(conn, message);
										} else if (input.startsWith(CommunicationHandler.LOBBY_ONLINE_LIST)) {
											lobby.broadcastOnlineList();
										} else if (input.startsWith(CommunicationHandler.GAME_ROOM_QUERY)) {
											lobby.showGamerooms(conn);
										} else if (input.startsWith(CommunicationHandler.UNFRIEND)) {
											String userName = input.substring(CommunicationHandler.UNFRIEND.length());
											User u = UserManager.loadUserByEmail(conn.getUser().getEmail());
											User userToUnfriend = null;
											for (UUID friendUID: u.getFriendList()) {
												User tmp = UserManager.loadUserById(friendUID);
												if (tmp.getUsername().equals(userName)) {
													userToUnfriend = tmp;
													break;
												}
											} 
											if (userToUnfriend != null && u.removeFriend(userToUnfriend)) {
												conn.getOutputStream().println(CommunicationHandler.UNFRIEND_SUCCESS + userName);
											} else {
												conn.getOutputStream().println(CommunicationHandler.UNFRIEND_FAIL);
											}
										} else if (!conn.isGuest()) {
											if (input.startsWith(CommunicationHandler.FRIEND_ADD_ATTEMPT)) {
												String userName = input.substring(CommunicationHandler.FRIEND_ADD_ATTEMPT.length()+1);
												User u = UserManager.loadUserByEmail(conn.getUser().getEmail());
												User userToAdd = getUserByUsername(userName);
												
												// some error occured, maybe guy disconnected
												if (userToAdd == null) {
													conn.getOutputStream().println(CommunicationHandler.FRIEND_ADD_FAIL);
												} else if (u.isFriend(userToAdd)) {
													conn.getOutputStream().println(CommunicationHandler.FRIENDS_ALREADY + userToAdd.getUsername());
												} else {
													if (u.addFriend(userToAdd))
														conn.getOutputStream().println(CommunicationHandler.FRIEND_ADD_SUCCESS + userToAdd.getUsername());
													else
														conn.getOutputStream().println(CommunicationHandler.FRIEND_ADD_FAIL);
												}
											}  else if (input.startsWith(CommunicationHandler.FRIENDS_GET_LIST)) {
												User u = UserManager.loadUserByEmail(conn.getUser().getEmail());
												u.sendFriendList(conn.getOutputStream());
											} else if (input.startsWith(CommunicationHandler.FRIEND_INVITE)) {
												input = input.substring(CommunicationHandler.FRIEND_INVITE.length());
												String[] inputSplit = input.split("%");
												Connection c = getConnection(inputSplit[0]);
												if (c != null) {
													c.getOutputStream().println(CommunicationHandler.FRIEND_INVITE+conn.getUsername()
																				+ "%"+ inputSplit[1]);
												}
											}
										} else if (conn.isGuest()) {
											System.out.println("Unknown Command: " + input);
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

	public ArrayList<Connection> getConnections() {
		return connections;
	}
	
	/**
	 * Get the connection given a username
	 * @param username
	 * @return username if found, null otherwise
	 */
	public Connection getConnection(String username) {
		for (Connection c: this.getConnections()) {
			if (c.getUsername().equals(username)) return c;
		}
		return null;
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
	
	/**
	 * Gets the user if he is in the lobby, else returns null
	 * @param username
	 * @return
	 */
	private User getUserByUsername(String username) {
		for (Connection conn: connections) {
			if (conn.getUsername().equals(username)) return conn.getUser();
		}
		return null;
	}
}