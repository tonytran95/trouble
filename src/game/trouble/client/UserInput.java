package game.trouble.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * The UserInput class handles all the inputs from the player and sends
 * the information to the game.
 * 
 * @author Jeffrey Ung
 *
 */
public class UserInput implements KeyListener {

	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The constructor for the user input class.
	 * @param swingUI is the swing user interface.
	 */
	public UserInput(SwingUI swingUI) {
		this.swingUI = swingUI;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return the swing user interace.
	 */
	public SwingUI getSwingUI() {
		return swingUI;
	}
	

}
