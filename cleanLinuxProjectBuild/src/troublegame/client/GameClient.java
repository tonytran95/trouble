package troublegame.client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import troublegame.client.model.User;
import troublegame.client.panels.BoardPanel;
import troublegame.client.panels.GameChatPanel;
import troublegame.client.panels.GamePanel;
import troublegame.client.panels.GameRoomPanel;
import troublegame.client.panels.InfoPanel;
import troublegame.client.panels.LobbyPanel;
import troublegame.client.panels.RegisterPanel;
import troublegame.communication.CommunicationHandler;

public class GameClient {
	
	public static final String GAME_MESSAGE = "GAME: ";
	
	/**
	 * The buffered reader.
	 */
	private BufferedReader in;
	
	/**
	 * The print writer.
	 */
    private PrintWriter out;
	
    /**
     * The socket.
     */
    private Socket socket;
    
	public static void main(String[] args) {
		String[] info = getServerInfo().split(":");
		new GameClient(info[0], Integer.parseInt(info[1]));
	}
	
	/**
	 * Constructs a new game client.
	 * @param ip is the IP address.
	 * @param port is the port
	 */
	public GameClient(String ip, int port) {
		this.socket = null;
		try {
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    out = new PrintWriter(socket.getOutputStream(), true);
			SwingUI ui = new SwingUI(this, in , out);
			
			ui.setVisible(true);
		    while (true) {
		    	
		    	String input = in.readLine();
		    	System.out.println("Server returned:" + input);
	    		
		    	String[] inputSplit = input.split(" ");
		    	if (input.equals(CommunicationHandler.LOGOUT_SUCCESS)) {
		    		this.socket.close();
		    		this.restart();
		    		return;
		    	}
		    	switch (ui.getInterface()) {
		    		case START:
		    			break;
		    		case LOGIN:
		    			if (input.startsWith(CommunicationHandler.LOGIN_SUCCESS)) {
		    				String username = input.substring(CommunicationHandler.LOGIN_SUCCESS.length() + 1);
		    				ui.send(CommunicationHandler.GET_STATISTICS);
		    				ui.setUser(new User(username));
		    				ui.setInterface(Interface.LOBBY);							
				    	} else if (input.startsWith(CommunicationHandler.LOGIN_ERROR)) {
				    		String errorMsg = input.substring(14);
				    		JOptionPane.showMessageDialog(null, errorMsg, "Please Try again", JOptionPane.PLAIN_MESSAGE);
				    	} 
		    			break;
		    		case LOBBY:
		    			String[] lobbySplit = input.split("] ");
		    			LobbyPanel lobbyPanel = (LobbyPanel) ui.getCurrentPanel();
		    			if (input.startsWith(CommunicationHandler.GAME_ROOM_OPEN)) {
		    				lobbyPanel.addGameRoom(lobbySplit[1]);
		    				lobbyPanel.addActivityFeed("[Server] A game has been created");
		    			} else if (input.startsWith(CommunicationHandler.GAME_ROOM_NEW)) {
		    				ui.setInterface(Interface.PARTY);
		    				// query for game room name
		    				ui.send(CommunicationHandler.GAME_ROOM_INFO);
		    				lobbyPanel.clearGameRooms();
		    			} else if (input.startsWith(CommunicationHandler.GAME_ROOM_JOIN)) {
		    				ui.setInterface(Interface.PARTY);
		    				// query for game room name
		    				ui.send(CommunicationHandler.GAME_ROOM_INFO);
		    				lobbyPanel.clearGameRooms();
		    			} else if (input.startsWith(CommunicationHandler.GAME_ROOM_CLOSE)) {
		    				lobbyPanel.removeGameRoom(lobbySplit[1]);
		    			} else if (input.startsWith(CommunicationHandler.GET_STATISTICS)) {
				    		User me = ui.getUser();
				    		me.setGamesPlayed(Integer.parseInt(inputSplit[1]));
				    		me.setGamesWon(Integer.parseInt(inputSplit[2]));
				    		ui.send((CommunicationHandler.LOBBY_ONLINE_LIST));
				    	} else if (input.startsWith(CommunicationHandler.LOBBY_CHAT)) {
		    				String chatMessage = input.substring(CommunicationHandler.LOBBY_CHAT.length() + 1);
		    				ui.pushChat(chatMessage, SwingUI.LOBBY);
		    			} else if (input.startsWith(CommunicationHandler.LOBBY_ONLINE_LIST)) {
				    		ui.updateOnlineList(input.substring(CommunicationHandler.LOBBY_ONLINE_LIST.length() + 1));
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROOM_QUERY)) {
				    		input = input.substring(CommunicationHandler.GAME_ROOM_QUERY.length());
				    		
				    		if(input != null && !input.isEmpty()) {
					    		String[] rooms = input.split("@");
					    		for (String room: rooms) {
					    			lobbyPanel.addGameRoom(room);
					    		}
				    		}
				    	} else if (input.startsWith(CommunicationHandler.LOBBY_ACTIVITY_FEED)) {
				    		ui.addActivityFeed(input.substring(CommunicationHandler.LOBBY_ACTIVITY_FEED.length() + 1));
				    	} else if (input.startsWith(CommunicationHandler.FRIEND_ADD_FAIL)) {
				    		JOptionPane.showMessageDialog(null, "Unable to add friend, please try again later.");
				    	} else if (input.startsWith(CommunicationHandler.FRIEND_ADD_SUCCESS)) {
				    		String username = input.substring(CommunicationHandler.FRIEND_ADD_SUCCESS.length());
				    		JOptionPane.showMessageDialog(null, "You are now friends with "+username);
				    	} else if (input.startsWith(CommunicationHandler.FRIENDS_ALREADY)) {
				    		String username = input.substring(CommunicationHandler.FRIENDS_ALREADY.length());
				    		JOptionPane.showMessageDialog(null, "You are already friends with "+username);
				    	} else if (input.startsWith(CommunicationHandler.FRIEND_INVITE)) {
				    		input = input.substring(CommunicationHandler.FRIEND_INVITE.length());
				    		String[] split = input.split("%");
				    		JOptionPane.showMessageDialog(null, split[0]+" invites you for a game! Join "+split[1]+" to play!");
				    	}
		    			break;
		    		case IN_GAME:
		    			GamePanel gamePanel = (GamePanel) ui.getCurrentPanel();
		    			GameChatPanel chatPanel = gamePanel.getChatPanel();
		    			BoardPanel boardPanel = gamePanel.getBoardPanel();
		    			InfoPanel infoPanel = gamePanel.getInfoPanel();
		    			if (input.startsWith(CommunicationHandler.GAME_START)) {
		    				String startTime = input.substring(CommunicationHandler.GAME_START.length() + 1);
		    				chatPanel.sendMessageToChatBox(startTime);
		    				boardPanel.setupPanel();
				    	} else if (input.startsWith(CommunicationHandler.GAME_COLORS)) {
				    		boardPanel.setupPlayer(inputSplit[1], inputSplit[2]);
				    		infoPanel.setupPlayer(inputSplit[1], inputSplit[2]);
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROLL)) {
				    		int rolled = Integer.valueOf(inputSplit[1]);
				    		boardPanel.rollAndShow(rolled);
				    		new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									if (ui.getUser().getUsername().equals(inputSplit[3])) {
										chatPanel.sendMessageToChatBox(GAME_MESSAGE + "You rolled a " + rolled);
									} else {
										chatPanel.sendMessageToChatBox(GAME_MESSAGE + inputSplit[3] + " rolled a " + rolled);
									}
						    		boardPanel.updateToken(inputSplit[3], Integer.parseInt(inputSplit[2]), Integer.parseInt(inputSplit[4]), Integer.parseInt(inputSplit[5]));
								}
							}, 1500);
				    		
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROLL_AGAIN)) {
				    		int rolled = Integer.valueOf(inputSplit[1]);
				    		boardPanel.rollAndShow(rolled);
				    		new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
						    		boardPanel.updateTurn(inputSplit[3]);
									if (ui.getUser().getUsername().equals(inputSplit[3])) {
										chatPanel.sendMessageToChatBox(GAME_MESSAGE + "You rolled a " + rolled + ". Roll again to move.");
									} else {
										chatPanel.sendMessageToChatBox(GAME_MESSAGE + inputSplit[3] + " rolled a " + rolled + ". They can roll again.");
									}
						    		boardPanel.updateToken(inputSplit[3], Integer.parseInt(inputSplit[2]), Integer.parseInt(inputSplit[4]), Integer.parseInt(inputSplit[5]));
								}
							}, 1500);
				    		
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROLL_SUCCESS)) {
				    		int rolled = Integer.valueOf(inputSplit[1]);
				    		boardPanel.rollAndShow(rolled);
				    		new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									if (ui.getUser().getUsername().equals(inputSplit[4])) {
										chatPanel.sendMessageToChatBox(GAME_MESSAGE + "You rolled a " + rolled + "! Moving token into end zone!");
									} else {
										chatPanel.sendMessageToChatBox(GAME_MESSAGE + inputSplit[3] + " rolled a " + rolled + "! Moving token into end zone!");
									}
						    		boardPanel.updateToken(inputSplit[3], Integer.parseInt(inputSplit[2]), Integer.parseInt(inputSplit[4]), Integer.parseInt(inputSplit[5]));
								}
							}, 1500);
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROLL_FAIL)) {
				    		int rolled = Integer.valueOf(inputSplit[1]);
				    		boardPanel.rollAndShow(rolled);
				    		new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									if (ui.getUser().getUsername().equals(inputSplit[2])) {
										chatPanel.sendMessageToChatBox(GAME_MESSAGE + "You rolled a " + rolled + ". Unable to move!");
									} else {
										chatPanel.sendMessageToChatBox(GAME_MESSAGE + inputSplit[2] + " rolled a " + rolled + ". Unable to move!");
									}
								}
							}, 1500);
				    	} else if (input.startsWith(CommunicationHandler.GAME_EAT_TOKEN)) {
				    		boardPanel.updateToken(inputSplit[2], Integer.parseInt(inputSplit[1]), Integer.parseInt(inputSplit[3]), Integer.parseInt(inputSplit[1]));
				    		if (inputSplit[2].equals(ui.getUser().getUsername())) {
				    			chatPanel.sendMessageToChatBox(GAME_MESSAGE + "Oh no! Your token has been eaten and sent back to home.");
				    		}
				    	} else if (input.startsWith(CommunicationHandler.GAME_TURN)) {
				    		new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									boardPanel.updateTurn(inputSplit[1]);
									infoPanel.updateTurn(inputSplit[1]);
						    		if (ui.getUser().getUsername().equals(inputSplit[1])) {
						    			chatPanel.sendMessageToChatBox(GAME_MESSAGE + "It's your turn! Click the dice to roll");
						    		} else {
						    			String currPlayer = inputSplit[1];
						    			int lastCharIndex = currPlayer.length() - 1;
						    			char lastLetInPlayerName = Character.toLowerCase(currPlayer.charAt(lastCharIndex));
						    			currPlayer += (lastLetInPlayerName == 's') ? "'" : "'s";
						    			chatPanel.sendMessageToChatBox(GAME_MESSAGE + "It's " + currPlayer + " turn");
						    		}
								}
							}, 1750);
		    			} else if (input.startsWith(CommunicationHandler.GAME_CHAT)) {
		    				String message = input.substring(CommunicationHandler.GAME_CHAT.length() + 1);
		    				chatPanel.sendMessageToChatBox(message);
		    			} else if (input.startsWith(CommunicationHandler.GAME_OVER)) {
		    				ui.setInterface(Interface.LOBBY);
		    				String message = input.substring(CommunicationHandler.GAME_OVER.length() + 1);
		    				ui.addActivityFeed(message);
		    			}
		    			break;
		    		case PARTY:
		    			GameRoomPanel gameRoomPanel = (GameRoomPanel) ui.getCurrentPanel();
		    			if (input.startsWith(CommunicationHandler.GAME_ROOM_MEMBER)) {
		    				gameRoomPanel.addUser(inputSplit[1]);
		    			} else if (input.startsWith(CommunicationHandler.GAME_ROOM_JOIN)) {
		    				gameRoomPanel.addUser(inputSplit[1]);
		    			} else if (input.startsWith(CommunicationHandler.GAME_SETUP)) {
		    				ui.setInterface(Interface.IN_GAME);
		    				BoardPanel board = ((GamePanel) ui.getCurrentPanel()).getBoardPanel();
		    				String[] setupArgs = input.split(" ");
		    				board.setDie(Integer.valueOf(setupArgs[1]));
		    			} else if (input.startsWith(CommunicationHandler.GAME_ROOM_INFO)) {
		    				String name = input.substring(16);
		    				name = name.trim();
		    				ui.setGameRoomName(name);
		    				// query for friend list
		    				ui.send(CommunicationHandler.FRIENDS_GET_LIST);
		    			} else if (input.startsWith(CommunicationHandler.GAME_ROOM_CHAT)) {
		    				String chatMessage = input.substring(CommunicationHandler.GAME_ROOM_CHAT.length());
		    				ui.pushChat(chatMessage, SwingUI.GAME_ROOM);
		    			} else if (input.equals(CommunicationHandler.GAME_ROOM_LEAVE)) {
		    				ui.setInterface(Interface.LOBBY);
		    				gameRoomPanel.clearUsers();
		    			} else if (input.startsWith(CommunicationHandler.GAME_ROOM_LEAVE)) {
		    				gameRoomPanel.removeUser(inputSplit[1]);
		    			} else if (input.startsWith(CommunicationHandler.GAME_START_FAIL)) {
		    				JOptionPane.showMessageDialog(null, "Only the owner can start the game!");
		    			} else if (input.startsWith(CommunicationHandler.FRIENDS_GET_LIST)) {
		    				input = input.substring(CommunicationHandler.FRIENDS_GET_LIST.length());
		    				String[] friends = input.split("%");
		    				ui.displayFriends(friends, SwingUI.GAME_ROOM);
		    			}
		    			break;
		    		case USER_PROFILE:
		    			if (input.startsWith(CommunicationHandler.UPDATE_SUCCESS)) {
		    				String username = input.substring(CommunicationHandler.UPDATE_SUCCESS.length() + 1);
		    				ui.setUser(new User(username));
		    				JOptionPane.showMessageDialog(null, "Your display name has been changed");
		    			} else if (input.startsWith(CommunicationHandler.UPDATE_FAIL)) {
		    				JOptionPane.showMessageDialog(null, "Incorrect password.");
		    			} else if (input.startsWith(CommunicationHandler.CHANGE_SUCCESS)) {
		    				JOptionPane.showMessageDialog(null, "Your password has been changed");
		    			} else if (input.startsWith(CommunicationHandler.FRIENDS_GET_LIST)) {
		    				input = input.substring(CommunicationHandler.FRIENDS_GET_LIST.length());
		    				String[] friends = input.split("%");
		    				ui.displayFriends(friends, SwingUI.USER_PROFILE);
		    			} else if (input.startsWith(CommunicationHandler.UNFRIEND_SUCCESS)) {
		    				String username = input.substring(CommunicationHandler.UNFRIEND_SUCCESS.length());
		    				JOptionPane.showMessageDialog(null, "You have successfully removed "+ username +" from your friend list");
		    				ui.removeFriend(username, SwingUI.USER_PROFILE);
		    			} else if (input.startsWith(CommunicationHandler.UNFRIEND_FAIL)) {
		    				JOptionPane.showMessageDialog(null, "An error occured while trying to remove this user.");
		    			}
		    			break;
		    		case SIGN_UP:
		    			RegisterPanel registerPanel = (RegisterPanel) ui.getCurrentPanel();
		    			if (input.equals(CommunicationHandler.REGISTER_SUCCESS)) {
		    				ui.setInterface(Interface.LOGIN);
		    				JOptionPane.showMessageDialog(null, "Account has been successfully created.");
		    			} else if (input.equals(CommunicationHandler.REGISTER_ERROR)) {
		    				JOptionPane.showMessageDialog(null, "The email you have entered already exists!", "Please Try again", JOptionPane.ERROR_MESSAGE);
		    				registerPanel.getEmailField().setBackground(Color.RED);
		    			}
		    			break;
		    		default:
		    			break;
		    	}
		    }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return the server's ip and port in the format <ip:port>
	 */
	private static String getServerInfo() {
		String line = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("./client.txt"));
			line = bufferedReader.readLine();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	/**
	 * Restart the program
	 * @throws IOException 
	 */
	public void restart() throws IOException {
        StringBuilder cmd = new StringBuilder();
        cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            cmd.append(jvmArg + " ");
        }
        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
        cmd.append(GameClient.class.getName()).append(" ");
        Runtime.getRuntime().exec(cmd.toString());
        System.exit(0);
	}
	
	/**
	 * Gets the socket.
	 */
	public Socket getSocket() {
		return socket;
	}
	
}