package game.trouble.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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
	public final static int HEIGHT = 800;
	
	/**
	 * The width of the display.
	 */
	public final static int WIDTH = 700;
	
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
	
	/**
	 * The constructor for the swing user interface.
	 */
	public SwingUI() {
		this.startPanel = new StartPanel(this);
		this.gamePanel = new GamePanel(this);
		this.currentPanel = gamePanel; // this should be changed to start panel. (Currently debugging game panel)
		try {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			JFrame.setDefaultLookAndFeelDecorated(true);
			this.setTitle(SwingUI.GAME_NAME);
			this.setLayout(new BorderLayout());
			this.setBackground(Color.WHITE);
			this.setPreferredSize(new Dimension(SwingUI.HEIGHT, SwingUI.WIDTH));
			this.setVisible(true);
			this.setResizable(false);
			this.switchPanel(currentPanel);
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
		this.getContentPane().add(newPanel);
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
		this.clientState = clientState;
	}
}
