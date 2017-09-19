package game.trouble;

import game.trouble.client.SwingUI;

/**
 * 
 * The main class for the trouble game.
 * 
 * @author Jeffrey Ung
 *
 */
public class Main {

	public static void main(String args[]) {
		
		Die d = new Die();
		System.out.println(d.rollRie());
		
		new SwingUI();
	}
	
}
