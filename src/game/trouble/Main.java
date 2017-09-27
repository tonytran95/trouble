package game.trouble;

import game.trouble.client.SwingUI;
import game.trouble.model.Colour;

/**
 * 
 * The main class for the trouble game.
 * 
 * @author Jeffrey Ung
 *
 */
public class Main {

	public static void main(String args[]) {
		
		// new SwingUI();
		
		// num players = 4, 3 human 1 ai
		
		// user1
		String n1 = "Bob";
		Colour c1 = Colour.RED;
		
		// user2
		String n2 = "Kelly";
		Colour c2 = Colour.BLUE;
		
		// user3
		String n3 = "Mary";
		Colour c3 = Colour.GREEN;
		
		Game g1 = new Game(4, 3, n1, c1, n2, c2, n3, c3);
		System.out.println(g1.getStartTimeMessage());
		
	}
	
}
