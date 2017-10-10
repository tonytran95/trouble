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

/**
 * 
 * The SwingUI class handles the user interface that is displayed.
 * 
 * @author Jeffrey Ung
 *
 */
@SuppressWarnings("serial")
public class SwingUI extends JFrame {
	
	/**
	 * The title of the game.
	 */
	public final static String GAME_NAME = "Trouble";
	
	/**
	 * The height of the display.
	 */
	public final static int HEIGHT = 780;
	
	/**
	 * The width of the display.
	 */
	public final static int WIDTH = 400;
	
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
	 * The client state.
	 */
	private ClientState clientState;
	
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
			this.setPreferredSize(new Dimension(
					SwingUI.HEIGHT + SwingUI.STRETCH, SwingUI.WIDTH + (SwingUI.STRETCH * 2)));
			this.setVisible(true);
			this.setResizable(false);
			this.clientState = ClientState.START;
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
	public ClientState getClientState() {
		return clientState;
	}

	/**
	 * Sets the client state.
	 * @param clientState is the client state of the player.
	 */
	public void setClientState(ClientState clientState) {
		switch (clientState) {
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
				switchPanel(lobbyPanel);
				break;
			case LOGIN:
				if (loginPanel == null)
					loginPanel = new LoginPanel(this);
				switchPanel(loginPanel);
				break;
			default:
				// do nothing
				break;
		}
		
		this.clientState = clientState;
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
}
