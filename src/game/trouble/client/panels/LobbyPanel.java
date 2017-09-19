package game.trouble.client.panels;

import javax.swing.JPanel;

import game.trouble.client.ClientState;
import game.trouble.client.SwingUI;

/**
 * 
 * The LobbyPanel class handles the display of the Lobby screen of the client.
 * 
 * @author Jeffrey Ung
 *
 */
@SuppressWarnings("serial")
public class LobbyPanel extends JPanel {
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The constructor for the Lobby panel.
	 * @param swingUI is the swing user interface.
	 */
	public LobbyPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		init();
		swingUI.setClientState(ClientState.LOBBY);
	}
	
	/**
	 * Initializes the Lobby panel.
	 */
	public void init() {
		// TODO Auto-generated method stub
	}

	/**
	 * @return the swing user interface.
	 */
	public SwingUI getSwingUI() {
		return swingUI;
	}

	/**
	 * Sets the swing user interface.
	 * @param swingUI is the swing user interface.
	 */
	public void setSwingUI(SwingUI swingUI) {
		this.swingUI = swingUI;
	}

}
