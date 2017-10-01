package game.trouble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.trouble.model.Colour;
import game.trouble.network.Connection;

// gameengine will call methods from board to manipulate game.
public class GameEngine {
	
	private ArrayList<Connection> gameConn;
	
	public GameEngine() {
		System.out.println("[GameEngine] Initializing game engine...");
		gameConn = new ArrayList<Connection>();
	}
	
	public void init() {
		
		Map<Colour, String> human = new HashMap<Colour, String>();
		Map<Colour, String> computers = new HashMap<Colour, String>();
		human.put(Colour.RED, "Bob");
		human.put(Colour.BLUE, "Kelly");		
		computers.put(Colour.YELLOW, "BOT Mary");
		computers.put(Colour.GREEN, "BOT George");
		
		Game g1 = new Game(human, computers);
		System.out.println(g1.getStartTimeMessage());
		System.out.print("[GameEngine] Made game with ");
		g1.showPlayers();
	}
	
	// connections added to a gameengine should be connections relevant to that game only
	public void addConn(Connection c) {
		gameConn.add(c);
	}
}
