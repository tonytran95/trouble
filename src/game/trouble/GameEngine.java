package game.trouble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.trouble.model.Colour;
import game.trouble.network.Connection;
import game.trouble.model.Player;

// gameengine will call methods from board to manipulate game.
public class GameEngine {
	
	private ArrayList<Connection> gameConn;
	private Game g;
	
	public GameEngine() {
		System.out.println("[GameEngine] Initializing game engine...");
		gameConn = new ArrayList<Connection>();
	}
	
	public void init() {
		g = new Game();
	}
	
	public void testGame() {
		g.join("test1", Colour.BLUE, true);
		g.join("test2", Colour.YELLOW, true);
		g.join("test3", Colour.GREEN, true);
		g.start();
		g.showPlayers();
	}

	public void add(Connection c) {
		gameConn.add(c);
		g.join(c.getUsername(), Colour.RED, false);
		
		// test game, game only starts if single player's name is bob
		if (c.getUsername().equalsIgnoreCase("bob"))
			testGame();
	}
	
	// process runs the game
	public void process() {
		if (!g.isStarted())
			return;
		if (!g.isOver()) {
			Player curr = g.getWhoseTurn();
			System.out.println("[GameEngine] It is "+curr.getUsername()+"'s turn now");
		}
	}
	
}
