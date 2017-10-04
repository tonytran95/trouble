package game.trouble;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import game.trouble.model.Colour;
import game.trouble.network.Connection;
import game.trouble.model.Player;

// gameengine will call methods from board to manipulate game.
public class GameEngine {
	
	private ArrayList<Connection> gameConn;
	private Game g;
	private boolean allPlayersConnected;
	private Queue<String> inputQueue;
	
	public GameEngine() {
		System.out.println("[GameEngine] Initializing game engine...");
		gameConn = new ArrayList<Connection>();
		allPlayersConnected = false;
		inputQueue = new LinkedList<String>();
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
		
		// not processing game if not all players connected or game has not started
		if (!g.isStarted()) {
			return;
		} else if (!allPlayersConnected) {
			checkPlayerConnections();	
			return;
		}
		
		if (!g.isOver()) {
			Player curr = g.getWhoseTurn();
			Connection clientConn = getConnection(curr.getUsername());
			PrintWriter clientOutput = clientConn.getOutputStream();
			
			// process his moves 
			for (String i: inputQueue) {
				if (i.startsWith("ROLLED")) {
					int value = new Random().nextInt(6) + 1;
	        		clientOutput.println("ROLLED " + value + " [" + curr.getUsername() + "]");
				}		
				inputQueue.remove();
			}
		}
	}
	
	/**
	 * Filter the input and add it to the queue so process will handle it later.
	 * @param c
	 * @param input
	 */
	public void handleInput(Connection c, String input) {
		// ignore the input if it's not user's turn
		if(g.isStarted()) {
			Player curr = g.getWhoseTurn();
			
			if (c.getUsername().equals(curr.getUsername())) {
				inputQueue.add(input);
			}
		}
		
	}
	
	/**
	 * Gets the connection associated with the username
	 * @param username
	 * @return Connection or null if no such connection
	 */
	private Connection getConnection(String username) {
		for (Connection c: gameConn) {
			if (c.getUsername().equals(username)) return c;
		}
		return null;
	}
	
	// checks all if all human players have a connection, if it does, sets this.allPlayersConnected to true
	private void checkPlayerConnections() {
		ArrayList<Player> humanPlayers = g.getHumanPlayers();
		for (Player p: humanPlayers) {
			if (!hasConnection(p.getUsername())) return;
		}
		allPlayersConnected = true;
	}
	
	// given a username, checks if connection object has been established.
	private boolean hasConnection(String username) {
		for (Connection c: gameConn) {
			if (c.getUsername().equals(username)) return true;
		}
		return false;
	}
}
