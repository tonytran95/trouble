package troublegame.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import troublegame.client.panels.GamePanel;
import troublegame.client.panels.GameRoomPanel;
import troublegame.client.panels.LobbyPanel;
import troublegame.communication.CommunicationHandler;

public class GameClient {

	/**
	 * The IP Address.
	 */
	public final static String IP_ADDRESS = "127.0.0.1";
	
	/**
	 * The port.
	 */
	public final static int port = 4321;
	
	/**
	 * The buffered reader.
	 */
	private BufferedReader in;
	
	/**
	 * The print writer.
	 */
    private PrintWriter out;
	
	public static void main(String[] args) {		
		new GameClient(GameClient.IP_ADDRESS, GameClient.port);
	}
	
	/**
	 * Constructs a new game client.
	 * @param ip is the IP address.
	 * @param port is the port
	 */
	public GameClient(String ip, int port) {
		Socket socket;
		try {
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    out = new PrintWriter(socket.getOutputStream(), true);
			SwingUI ui = new SwingUI(in , out);
			
		    while (true) {
		    	String input = in.readLine();
		    	System.out.println("Server returned:" + input);
	    		String[] inputSplit = input.split(" ");
		    	switch (ui.getInterface()) {
		    		case START:
		    			break;
		    		case LOGIN:
		    			if (input.startsWith(CommunicationHandler.LOGIN_SUCCESS)) {
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
		    			}
		    			break;
		    		case IN_GAME:
		    			GamePanel gamePanel = (GamePanel) ui.getCurrentPanel();
		    			if (input.equals(CommunicationHandler.GAME_START)) {
				    		gamePanel.setupPanel();
				    	} else if (input.startsWith(CommunicationHandler.GAME_COLORS)) {
				    		gamePanel.getPlayers().put(inputSplit[1], inputSplit[2]);
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROLL)) {
				    		gamePanel.updateMessage(inputSplit[3] + " rolled a " + inputSplit[1], 0);
				    		gamePanel.updateToken(inputSplit[3], Integer.parseInt(inputSplit[2]), Integer.parseInt(inputSplit[4]), Integer.parseInt(inputSplit[5]));
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROLL_AGAIN)) {
				    		gamePanel.updateMessage("You rolled a " + inputSplit[1] + ". Roll again to move.", 0);
				    		gamePanel.updateToken(inputSplit[3], Integer.parseInt(inputSplit[2]), Integer.parseInt(inputSplit[4]), Integer.parseInt(inputSplit[5]));
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROLL_SUCCESS)) {
				    		gamePanel.updateMessage("You rolled a " + inputSplit[1] + ". Moving your token into the end zone!.", 0);
				    		gamePanel.updateToken(inputSplit[3], Integer.parseInt(inputSplit[2]), Integer.parseInt(inputSplit[4]), Integer.parseInt(inputSplit[5]));
				    	} else if (input.startsWith(CommunicationHandler.GAME_ROLL_FAIL)) {
				    		gamePanel.updateMessage("You rolled a " + inputSplit[1] + ". Unable to move.", 0);
				    	} else if (input.startsWith(CommunicationHandler.GAME_EAT_TOKEN)) {
				    		gamePanel.updateToken(inputSplit[2], Integer.parseInt(inputSplit[1]), Integer.parseInt(inputSplit[3]), Integer.parseInt(inputSplit[1]));
				    	} else if (input.startsWith(CommunicationHandler.GAME_TURN)) {
				    		if (ui.getUser().getUsername().equals(inputSplit[1])) {
				    			gamePanel.updateMessage("Your turn.", 1);
				    		} else {
				    			gamePanel.updateMessage(inputSplit[1] + "'s turn.", 1);
				    		}
				    	}
		    			break;
		    		case PARTY:
		    			GameRoomPanel gameRoomPanel = (GameRoomPanel) ui.getCurrentPanel();
		    			if (input.startsWith("[GAME_ROOM_MEMBER]")) {
		    				gameRoomPanel.addUser(inputSplit[1]);
		    			} else if (input.startsWith("[PLAYER_JOINED]")) {
		    				gameRoomPanel.addUser(inputSplit[1]);
		    			} else if (input.equals("[START_GAME]")) {
		    				
		    			} else if (input.startsWith("[GAME_ROOM_INFO]")) {
		    				String name = input.substring(16);
		    				name = name.trim();
		    				ui.setGameRoomName(name);	
		    			} else if (input.startsWith("[GAMEROOM_CHAT]")) {
		    				String chatMessage = input.substring(15);
		    				ui.pushChat(chatMessage);
		    			} else if (input.equals("[GAME_ROOM_LEAVE]")) {
		    				ui.setInterface(Interface.LOBBY);
		    				gameRoomPanel.clearUsers();
		    			} else if (input.startsWith("[GAME_ROOM_LEAVE]")) {
		    				gameRoomPanel.removeUser(inputSplit[1]);
		    			}
		    			break;
		    		default:
		    	}
		    }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
