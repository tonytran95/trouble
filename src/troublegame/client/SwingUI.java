package troublegame.client;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import troublegame.client.model.User;
import troublegame.client.panels.GamePanel;
import troublegame.client.panels.GameRoomPanel;
import troublegame.client.panels.LobbyPanel;
import troublegame.client.panels.LoginPanel;
import troublegame.client.panels.ProfilePanel;
import troublegame.client.panels.RulesPanel;
import troublegame.client.panels.StartPanel;

/**
 * 
 * The {@link SwingUI} class handles the user interface that is displayed.
 * 
 * @author Jeffrey Ung and Tony Tran
 *
 */
public class SwingUI extends JFrame {
	
	/**
	 * Serial ID for object serialisation
	 */
	private static final long serialVersionUID = -6406009087431782704L;

	/**
	 * The title of the game.
	 */
	public final static String GAME_NAME = "Trouble";
	
	/**
	 * Height of the title bar in pixels
	 */
	public final static int TITLE_BAR_HEIGHT = 25;
	
	/**
	 * The height of the display.
	 */
	public final static int HEIGHT = 580 + TITLE_BAR_HEIGHT;
	
	/**
	 * The width of the display.
	 */
	public final static int WIDTH = 1030;
	
	/**
	 * The current panel being displayed.
	 */
	private JPanel currentPanel;
	
	/**
	 * The start panel.
	 */
	private StartPanel startPanel;
	
	/**
	 * The game panel.
	 */
	private GamePanel gamePanel;
	
	/**
	 * The lobby panel.
	 */
	private LobbyPanel lobbyPanel;
	
	/**
	 * The login panel.
	 */
	private LoginPanel loginPanel;	
	
	/**
	 * The game room panel.
	 */
	private GameRoomPanel gameRoomPanel;
	
	/**
	 * The rules panel.
	 */
	private RulesPanel rulesPanel;
	
	/**
	 * The user profile panel.
	 */
	private ProfilePanel profilePanel;
	
	/**
	 * The client state.
	 */
	private Interface state;
	
	/**
	 * The buffered reader.
	 */
	private BufferedReader in;
	
	/**
	 * The print writer.
	 */
	private PrintWriter out;
	
	/**
	 * The user of the client.
	 */
	private User user;
	
	/**
	 * The constructor for the swing user interface.
	 */
	public SwingUI(BufferedReader in, PrintWriter out) {
		
		this.in = in;
		this.out = out;
		this.startPanel = new StartPanel(this);
		this.currentPanel = startPanel;
		this.resizeFrame();
		this.setLocationRelativeTo(null);
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.setTitle(SwingUI.GAME_NAME);
		this.state = Interface.START;
		this.switchPanel(this.startPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Resizes the main frame i.e. {@link SwingUI} to the default size.
	 */
	public void resizeFrame() {
		this.resizeFrame(SwingUI.WIDTH, SwingUI.HEIGHT);
	}
	/**
	 * Resizes the main frame i.e. {@link SwingUI}.
	 * @param height is the height of the view.
	 * @param width is the width of the view.
	 */
	public void resizeFrame(int height, int width) {
		this.setSize(new Dimension(height , width));
	}

	/**
	 * Switches the current panel.
	 * @param newPanel is the new panel being displayed.
	 */
	public void switchPanel(JPanel newPanel) {
		this.setResizable(false);
		this.getContentPane().removeAll();
		this.getContentPane().add(newPanel);
		this.currentPanel = newPanel;
		this.currentPanel.setBackground(Color.DARK_GRAY);
		this.validate();
		this.repaint();
	}
	
	/**
	 * @return the current panel.
	 */
	public JPanel getCurrentPanel() {
		return currentPanel;
	}

	/**
	 * @return the client state.
	 */
	public Interface getInterface() {
		return state;
	}

	/**
	 * Sets the client state.
	 * @param state is the client state of the player.
	 */
	public void setInterface(Interface state) {
		switch (state) {
			case START:
				if (startPanel == null)
					startPanel = new StartPanel(this);
				switchPanel(startPanel);
				break;
			case IN_GAME:
				if (gamePanel == null)
					gamePanel = new GamePanel(this);
				switchPanel(gamePanel);
				break;
			case LOBBY:
				if (lobbyPanel == null)
					lobbyPanel = new LobbyPanel(this);
				resizeFrame();
				switchPanel(lobbyPanel);
				break;
			case LOGIN:
				if (loginPanel == null)
					loginPanel = new LoginPanel(this);
				switchPanel(loginPanel);
				break;
			case PARTY:
				if (gameRoomPanel == null)
					gameRoomPanel = new GameRoomPanel(this);
				switchPanel(gameRoomPanel);
				break;
			case RULES:
				if (rulesPanel == null)
					rulesPanel = new RulesPanel(this);
				resizeFrame(870, 680);
				switchPanel(rulesPanel);
				break;
			case USER_PROFILE:
				if (profilePanel == null)
					profilePanel = new ProfilePanel(this);
				resizeFrame(550, 600);
				switchPanel(profilePanel);
				break;
			default:
				// do nothing
				break;
		}
		
		this.state = state;
	}
	
	
	/**
	 * Sets the current user.
	 * @param user is the client handler.
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * @return the user of the client.
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sends a message using the print writer.
	 * @param message is the information that is being sent.
	 */
	public void send(String message) {
		out.println(message);
	}
	
	/**
	 * @return the information sent from the server.
	 * @throws IOException is the exception.
	 */
	public String read() throws IOException {
		return in.readLine();
	}
	
	/**
	 * sets game room name
	 */
	public void setGameRoomName(String name) {
		this.gameRoomPanel.setGameRoomName(name);
	}
	
	public void pushChat(String message) {
		this.gameRoomPanel.updateChat(message);
	}
	
	public void pushGameChat(String message) {
		this.gamePanel.sendChatMessage(message);
	}
}
