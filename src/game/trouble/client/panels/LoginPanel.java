package game.trouble.client.panels;

import javax.swing.JPanel;

import game.trouble.client.ClientState;
import game.trouble.client.SwingUI;

/**
 * 
 * The LoginPanel class handles the display of the Login screen of the client.
 * 
 * @author Jeffrey Ung
 *
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel {
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The constructor for the Login panel.
	 * @param swingUI is the swing user interface.
	 */
	public LoginPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		swingUI.setClientState(ClientState.LOGIN);
		init();
	}
	
	/**
	 * Initializes the Login panel.
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
