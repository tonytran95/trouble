package troublegame.server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import troublegame.communication.CommunicationHandler;

// gameengine will call methods from board to manipulate game.
public class GameEngine {
	
	private ArrayList<Game> games;
	private HashMap<Game, ArrayList<Connection>> gameConns;
	private HashMap<Game, Queue<String>> inputQueues;
	private boolean allPlayersConnected;
	
	public GameEngine() {
		
		System.out.println(CommunicationHandler.GAME_ENGINE_INFO + " Initializing game engine...");
		games = new ArrayList<Game>();
		gameConns = new HashMap<Game, ArrayList<Connection>>();
		inputQueues = new HashMap<Game, Queue<String>>();
		allPlayersConnected = false;
	}
	
	public void removeConnection(Connection conn) {
		Iterator<Entry<Game, ArrayList<Connection>>> it = gameConns.entrySet().iterator();
	    while (it.hasNext()) {
	    	Entry<Game, ArrayList<Connection>> entry = it.next();
	    	Game g = entry.getKey();
	    	ArrayList<Connection> conns = entry.getValue();
	    	conns.remove(conn);
	        if (conns.size() == 0) {
				gameConns.remove(g);
				inputQueues.remove(g);
				g.destruct();
			}
	    }
	    /*
		for (Entry<Game, ArrayList<Connection>> c : gameConns.entrySet()) {
			Game g = c.getKey();
			ArrayList<Connection> conns = c.getValue();
			conns.remove(conn);
			if (conns.size() == 0) {
				gameConns.remove(g);
				inputQueues.remove(g);
				g.destruct();
			}
		}
		*/
	}
	
	public void createGame(ArrayList<Connection> players, int startingNum) {
		Game g = new Game(this);
		gameConns.put(g, players);
		inputQueues.put(g, new LinkedList<String>());
		
		ArrayList<Connection> sortedPlayerColors = sortByColorPref(players);
		
		for(Connection c : sortedPlayerColors) {
			
			User curr = c.getUser();
			Color prefColor = curr.getFavouriteColor();
			g.join(curr.getUsername(), g.assignPlayerColour(prefColor), false);
			
		}
		
		for(int i = 0; i < (4 - players.size()); i++) {
			g.join(g.getRandomAiName(), g.assignPlayerColour(Color.RANDOM), true);
		}
		
		games.add(g);
		startGame(g, startingNum);
	}
	
	public void startGame(Game g, int startingNum) {
		
		for (Connection c : gameConns.get(g))
			c.getOutputStream().println(CommunicationHandler.GAME_SETUP + " " + startingNum);
		g.start();
		g.showPlayers();
		for (Connection c : gameConns.get(g))
			c.getOutputStream().println(CommunicationHandler.GAME_START + " "+ g.getStartTimeMessage());
		updateTurns(g);
	}
	
	/**
	 * Sorts the list of player connections to put those with a color preference first and a random color last
	 * so that colors can be distributed as fairly as possible to player preferences
	 * @param connections List of player connections to the server
	 * @return The sorted list of player connections
	 */
	public ArrayList<Connection> sortByColorPref(ArrayList<Connection> connections) {
		
		Collections.sort(connections, new Comparator<Connection>() {

			@Override
			public int compare(Connection o1, Connection o2) {
				
				if(o1.getUser().getFavouriteColor().equals(Color.RANDOM) && o2.getUser().getFavouriteColor().equals(Color.RANDOM) == false) {
					return 1;
				} else if(o1.getUser().getFavouriteColor().equals(Color.RANDOM) == false && o2.getUser().getFavouriteColor().equals(Color.RANDOM)) {
					return -1;
				} else if(o1.getUser().getFavouriteColor().equals(o2.getUser().getFavouriteColor())) {
					// Two users have the same color, first user gets preference
					return -1;
				} else {
					return 0;
				}
			
		}
	});
	
	return connections;
	
}

	
	public void handleChat(Connection user, String message) {
		for (Game g : games) {
			for (Player player : g.getHumanPlayers()) {
				if (player.getUsername().equals(user.getUsername())) {
					for (Connection member: gameConns.get(g)) {
						PrintWriter outputStream = member.getOutputStream();
						String s = String.format(CommunicationHandler.GAME_CHAT + " %s: %s", user.getUsername(), message);
						outputStream.println(s);
					}
				}
			}
		}

	}
	
	// process runs the game
	public void process() {
		for (Game g : games) {
			// not processing game if not all players connected or game has not started
			if (!g.isStarted())
				continue;
			if (!allPlayersConnected) {
				checkPlayerConnections(g);	
				continue;
			}
			if (!g.isOver()) {
				Player curr = g.getWhoseTurn();
				if (!(curr instanceof AI)) {
					int playerID = curr.getID();

					// process his moves 
					while (!inputQueues.get(g).isEmpty()) {
						String in = inputQueues.get(g).poll();
						
						// die rolls
						if (in.startsWith(CommunicationHandler.GAME_ROLL)) {
							String[] input = in.split(" ");
							int tokenID = Integer.parseInt(input[1]);
							System.out.println(CommunicationHandler.GAME_INFO + " Rolling Token ID: " + tokenID);
							g.rollDie();
							g.setTick(2);
							
							String command = g.movePlayerToken(playerID, tokenID);
							broadcast(g, command);
							if (!command.startsWith(CommunicationHandler.GAME_ROLL_AGAIN)) {
								g.incrementTurn();
								updateTurns(g);
							}
						}
					}
				} else {
					if (g.getTick() == 0) {
						AI ai = (AI) curr;
						String move = ai.getMove(g.getBoard());
						System.out.println("AI's MOVE: " + move);
						
						g.setTick(2);
						if (move.startsWith(CommunicationHandler.GAME_ROLL)) {
							String input[] = move.split(" ");
							int tokenID = Integer.parseInt(input[1]);
							g.rollDie();
							String command = g.movePlayerToken(ai.getID(), tokenID);
							broadcast(g, command);
							if (!command.startsWith(CommunicationHandler.GAME_ROLL_AGAIN)) {
								g.incrementTurn();
								updateTurns(g);
							}
						}
					} else
						g.setTick(g.getTick() - 1);
				}
			} else {
				
				// if game is over
				Player winner = g.getWinner();
				
				// grab the connections and message them
				ArrayList<Connection> gameConnections = getConnections(g);
				
				for (Connection c: gameConnections) {
					PrintWriter clientOutput = c.getOutputStream();
					if (c.getUsername().equals(winner.getUsername())) {
						clientOutput.println(CommunicationHandler.GAME_OVER + " Congratulations, you have won!");
					} else {
						clientOutput.println(CommunicationHandler.GAME_OVER + " " +  winner.getUsername() + " has won the game!");
					}
				}
				
				// update user statistics
				for (Player p: g.getHumanPlayers()) {
					User u = getUser(g, p.getUsername());
					if (p.equals(winner)) {
						u.finishedGame(true);
					} else {
						u.finishedGame(false);
					}
				}		
			}
		}
	}
	
	public ArrayList<Connection> getConnections(Game g) {
		return gameConns.get(g);
	}
	
	public void broadcast(Game g, String move) {
		for (Connection clientConn : gameConns.get(g))
			clientConn.getOutputStream().println(move);
	}
	
	/**
	 * Sends a message to all client on whose move.
	 */
	public void updateTurns(Game g) {
		for (Connection clientConn : gameConns.get(g))
			clientConn.getOutputStream().println(CommunicationHandler.GAME_TURN + " " + g.getWhoseTurn().getUsername());
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
	
	/**
	 * Gets the user from the connection associated with the username 
	 * @param username
	 * @return Connection or null if no such connection
	 */
	private User getUser(Game g, String username) {
		for (Connection c: gameConns.get(g)) {
			if (c.getUsername().equals(username)) return c.getUser();
		}
		return null;
	}
	
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