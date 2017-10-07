package game.trouble.client;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JPanel;

import game.trouble.client.panels.GamePanel;

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
		try {
			Socket socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    out = new PrintWriter(socket.getOutputStream(), true);
			SwingUI ui = new SwingUI(in , out);
			
		    while (true) {
		    	String input = in.readLine();
		    	System.out.println(input);
		    	
		    	if (input.equals("START_GAME")) {
		    		GamePanel gamePanel = (GamePanel) ui.getCurrentPanel();
		    		gamePanel.setupPanel();
		    	} else if (input.startsWith("COLORS")) {
		    		GamePanel gamePanel = (GamePanel) ui.getCurrentPanel();
		    		String[] inputSplit = input.split(" ");
		    		gamePanel.getPlayers().put(inputSplit[1], inputSplit[2]);
		    	} else if (input.startsWith("ROLLED")) {
		    		GamePanel gamePanel = (GamePanel) ui.getCurrentPanel();
		    		String[] inputSplit = input.split(" ");
		    		gamePanel.updateMessage(inputSplit[3] + " rolled a " + inputSplit[1]);
		    		gamePanel.updateToken(inputSplit[3], Integer.parseInt(inputSplit[2]), Integer.parseInt(inputSplit[1]));
		    	} else if (input.startsWith("ROLL FAIL")) {
		    		GamePanel gamePanel = (GamePanel) ui.getCurrentPanel();
		    		String[] inputSplit = input.split(" ");
		    		gamePanel.updateMessage("You rolled a "+inputSplit[2]+". Unable to move.");
		    	}
		    }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
