package troublegame.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// gameengine will call methods from board to manipulate game.
public class GameEngine {
	
	private ArrayList<Game> games;
	private HashMap<Game, ArrayList<Connection>> gameConns;
	private HashMap<Game, Queue<String>> inputQueues;
	private boolean allPlayersConnected;
	
	public GameEngine() {
		System.out.println("[GameEngine] Initializing game engine...");
		games = new ArrayList<Game>();
		gameConns = new HashMap<Game, ArrayList<Connection>>();
		inputQueues = new HashMap<Game, Queue<String>>();
		allPlayersConnected = false;
	}
	
	/*public void testGame() {
		g.join("test1", Color.BLUE, true);
		g.join("test2", Color.YELLOW, true);
		g.join("test3", Color.GREEN, true);
		g.start();
		g.showPlayers();
		
		for (Connection c : gameConn) {
			c.getOutputStream().println("START_GAME");
		}
		updateMessages();
	}*/
	
	/*public void testGame2() {
		g.start();
		g.showPlayers();
		
		for (Connection c : gameConn) {
			c.getOutputStream().println("START_GAME");
		}
		updateMessages();
	}*/
	
	public void createGame(ArrayList<Connection> players) {
		Game g = new Game(this);
		gameConns.put(g, players);
		inputQueues.put(g, new LinkedList<String>());
		for (int i = 0; i < players.size(); i++) {
			if (i == 0) g.join(players.get(i).getUsername(), Color.RED, false);
			if (i == 1) g.join(players.get(i).getUsername(), Color.BLUE, false);
			if (i == 2) g.join(players.get(i).getUsername(), Color.YELLOW, false);
			if (i == 3) g.join(players.get(i).getUsername(), Color.GREEN, false);
		}
		games.add(g);
		startGame(g);
	}
	
	public void startGame(Game g) {
		for (Connection c : gameConns.get(g))
			c.getOutputStream().println("[SETUP_GAME]");
		g.start();
		g.showPlayers();
		for (Connection c : gameConns.get(g))
			c.getOutputStream().println("[GAME_START]");
		updateMessages(g);
	}

	public void add(Game g, Connection c) {
		ArrayList<Connection> gameConn = gameConns.get(g);
		gameConn.add(c);
		switch (gameConn.size()) {
			case 1:
				g.join(c.getUsername(), Color.RED, false);
				break;
			case 2:
				g.join(c.getUsername(), Color.BLUE, false);
				break;
			case 3:
				g.join(c.getUsername(), Color.YELLOW, false);
				break;
			case 4:
				g.join(c.getUsername(), Color.GREEN, false);
				break;
			default:
		}
		if (gameConn.size() == 4) {
			startGame(g);
		}
	}
	
	// process runs the game
	public void process() {
		for (Game g : games) {
			// not processing game if not all players connected or game has not started
			if (!g.isStarted()) {
				return;
			} else if (!allPlayersConnected) {
				checkPlayerConnections(g);	
				return;
			}
			
			if (!g.isOver()) {
				Player curr = g.getWhoseTurn();
				if (!(curr instanceof AI)) {
					int playerID = curr.getID();

					// process his moves 
					while (!inputQueues.get(g).isEmpty()) {
						String in = inputQueues.get(g).poll();
						
						// die rolls
						if (in.startsWith("[GAME_ROLL]")) {	
							String[] input = in.split(" ");
							int tokenID = Integer.parseInt(input[1]);
							System.out.println("[GAME] rolling token ID: " + tokenID);
							
							int roll = g.rollDie();
							broadcast(g, g.movePlayerToken(playerID, tokenID, roll));
						}
					}
				} else {
					AI ai = (AI) curr;
					String move = ai.getMove(g.getBoard());
					System.out.println("AI's MOVE: " + move);
					
					if (move.startsWith("[GAME_ROLL]")) {
						String input[] = move.split(" ");
						int tokenID = Integer.parseInt(input[1]);
						int roll = g.rollDie();
						broadcast(g, g.movePlayerToken(ai.getID(), tokenID, roll));
					}
				}
			}
		}
	}
	
	public void broadcast(Game g, String move) {
		for (Connection clientConn : gameConns.get(g))
			clientConn.getOutputStream().println(move);
	}
	
	/**
	 * Sends a message to all client on whose move.
	 */
	public void updateMessages(Game g) {
		for (Connection clientConn : gameConns.get(g))
			clientConn.getOutputStream().println("[GAME_TURN] " + g.getWhoseTurn().getUsername());
	}
	
	/**
	 * Filter the input and add it to the queue so process will handle it later.
	 * @param c
	 * @param input
	 */
	public void handleInput(Connection c, String input) {
		// find game with this connection
		Game g = null;
		for (Game game : inputQueues.keySet()) {
			if (gameConns.get(game).contains(c)) {
				g = game;
				break;
			}
		}
		if (g != null) {
			Player curr = g.getWhoseTurn();
			
			if (c.getUsername().equals(curr.getUsername())) {
				inputQueues.get(g).add(input);
			}
		}
	}
	
	public void handleInput(Game g, Connection c, String input) {
		// ignore the input if it's not user's turn
		if(g.isStarted()) {
			Player curr = g.getWhoseTurn();
			
			if (c.getUsername().equals(curr.getUsername())) {
				inputQueues.get(g).add(input);
			}
		}
	}
	
	/**
	 * Gets the connection associated with the username
	 * @param username
	 * @return Connection or null if no such connection
	 */
//	private Connection getConnection(String username) {
//		for (Connection c: gameConn) {
//			if (c.getUsername().equals(username)) return c;
//		}
//		return null;
//	}
	
	// checks all if all human players have a connection, if it does, sets this.allPlayersConnected to true
	private void checkPlayerConnections(Game g) {
		ArrayList<Player> humanPlayers = g.getHumanPlayers();
		for (Player p: humanPlayers) {
			if (!hasConnection(g, p.getUsername())) return;
		}
		allPlayersConnected = true;
	}
	
	// given a username, checks if connection object has been established.
	private boolean hasConnection(Game g, String username) {
		for (Connection c: gameConns.get(g)) {
			if (c.getUsername().equals(username)) return true;
		}
		return false;
	}
	
	
}
