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
		
		// this is essentially how we will "make" a game
		Map<Colour, String> human = new HashMap<Colour, String>();
		Map<Colour, String> computers = new HashMap<Colour, String>();
		
		human.put(Colour.RED, "Bob");
		human.put(Colour.BLUE, "Kelly");		
		computers.put(Colour.YELLOW, "BOT Mary");
		computers.put(Colour.GREEN, "BOT George");
		
		g = new Game(human, computers);
		System.out.println(g.getStartTimeMessage());
		System.out.print("[GameEngine] Created game with ");
		g.showPlayers();
	}
	
	// connections added to a gameengine should be connections relevant to that game only
	public void addConn(Connection c) {
		gameConn.add(c);
	}
	
	// process runs the game
	public void process() {
		if (!g.isOver()) {
			Player curr = g.getWhoseTurn();
			System.out.println("[GameEngine] It is "+curr.getUsername()+"'s turn now");
		}
	}
	
}
