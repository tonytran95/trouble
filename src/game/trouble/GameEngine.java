package game.trouble;

import game.trouble.model.Colour;

// gameengine will call methods from board to manipulate game.
public class GameEngine {
	
	public GameEngine() {
		
	}
	
	public void init() {
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
