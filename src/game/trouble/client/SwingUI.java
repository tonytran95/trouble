package game.trouble.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import game.trouble.client.panels.GamePanel;
import game.trouble.client.panels.StartPanel;

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
	public final static int WIDTH = 300;
	
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
	 * The client state.
	 */
	private ClientState clientState;
	
	private BufferedReader in;
	private PrintWriter out;
	
	/**
	 * The constructor for the swing user interface.
	 */
	public SwingUI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		
		this.startPanel = new StartPanel(this);
		this.gamePanel = new GamePanel(this);
		this.currentPanel = startPanel; // this should be changed to start panel. (Currently debugging game panel)
		try {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			JFrame.setDefaultLookAndFeelDecorated(true);
			this.setTitle(SwingUI.GAME_NAME);
			this.setLayout(new BorderLayout());
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(
					SwingUI.HEIGHT + SwingUI.STRETCH, SwingUI.WIDTH + (SwingUI.STRETCH * 2)));
			this.setVisible(true);
			this.setResizable(false);
			this.switchPanel(currentPanel);
			
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
				switchPanel(startPanel);
			case IN_GAME:
				switchPanel(gamePanel);
			case LOBBY:
				break;
			case LOGIN:
				break;
		}
		this.clientState = clientState;
	}
	
	public void send(String message) {
		out.println(message);
	}
	
	public String read() throws IOException {
		return in.readLine();
	}
}
