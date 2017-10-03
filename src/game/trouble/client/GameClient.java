package game.trouble.client;

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
		    	
		    	if (input.startsWith("ROLLED")) {
		    		JPanel panel = ui.getCurrentPanel();
		    		GamePanel gamePanel = (GamePanel) panel;
		    		gamePanel.updateMessage(input.substring(9) + " rolled a " + input.substring(7, 8));
		    	} else if (input.startsWith("COLORS")) {
		    		JPanel panel = ui.getCurrentPanel();
		    		GamePanel gamePanel = (GamePanel) panel;
		    		String[] inputSplit = input.split(" ");
		    		gamePanel.getPlayers().put(inputSplit[1], inputSplit[2]);
		    	}
		    }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
