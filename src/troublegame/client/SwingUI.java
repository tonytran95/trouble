package troublegame.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

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
	 * The height of the display.
	 */
	public final static int HEIGHT = 800;
	
	/**
	 * The width of the display.
	 */
	public final static int WIDTH = 620;
	
	/**
	 * The stretch value.
	 */
	public final static int STRETCH = 50;
	
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
		try {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			this.setTitle(SwingUI.GAME_NAME);
			this.setLayout(new BorderLayout());
			this.setBackground(Color.WHITE);
			resizeFrame();
			this.setVisible(true);
			this.setResizable(false);
			this.state = Interface.START;
			this.switchPanel(this.startPanel);
			/**
			 * Side panels.
			 */
			JPanel westPanel = new JPanel();
			JPanel eastPanel = new JPanel();
			JPanel southPanel = new JPanel();
			JPanel northPanel = new JPanel();
			westPanel.setPreferredSize(new Dimension(SwingUI.STRETCH, SwingUI.STRETCH));
			eastPanel.setPreferredSize(new Dimension(SwingUI.STRETCH, SwingUI.STRETCH));
			northPanel.setPreferredSize(new Dimension(SwingUI.STRETCH * 2, SwingUI.STRETCH * 2));
			southPanel.setPreferredSize(new Dimension(SwingUI.STRETCH * 2, SwingUI.STRETCH * 2));
			this.add(westPanel, BorderLayout.WEST);
			this.add(eastPanel, BorderLayout.EAST);
			this.add(southPanel, BorderLayout.SOUTH);
			this.add(northPanel, BorderLayout.NORTH);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resizes the main frame i.e. {@link SwingUI} to the default size.
	 */
	public void resizeFrame() {
		this.resizeFrame(SwingUI.HEIGHT + SwingUI.STRETCH, SwingUI.WIDTH + (SwingUI.STRETCH * 2));
	}
	/**
	 * Resizes the main frame i.e. {@link SwingUI}.
	 * @param height is the height of the view.
	 * @param width is the width of the view.
	 */
	public void resizeFrame(int height, int width) {
		this.setPreferredSize(new Dimension(height , width));
	}

	/**
	 * Switches the current panel.
	 * @param newPanel is the new panel being displayed.
	 */
	public void switchPanel(JPanel newPanel) {
		this.setResizable(true);
		this.getContentPane().remove(currentPanel);
		this.getContentPane().add(newPanel, BorderLayout.CENTER);
		this.currentPanel = newPanel;
		this.pack();
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
				resizeFrame(HEIGHT, WIDTH);
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
				resizeFrame(950, 870);
				switchPanel(rulesPanel);
				break;
			case USER_PROFILE:
				if (profilePanel == null)
					profilePanel = new ProfilePanel(this);
				resizeFrame(600, 550);
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
