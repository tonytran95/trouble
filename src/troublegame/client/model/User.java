package troublegame.client.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The user of the client is handled in this class.
 * 
 * @author Jeffrey Ung
 *
 */
public class User {

	/**
	 * The username.
	 */
	private String username;
	
	/**
	 * The selected tile of a Token.
	 */
	private Tile selectedTile;
	
	/**
	 * The list of tokens.
	 */
	private List<Tile> tokens;
	
	/**
	 * The user's current Color.
	 */
	private Color color;
	
	/**
	 * User's games played
	 */
	private int gamesPlayed;
	
	/**
	 * User's games won
	 */
	private int gamesWon;
	
	/**
	 * Constructs a new user.
	 * @param username is the username.
	 */
	public User(String username) {
		this.username = username;
		this.tokens = new ArrayList<Tile>();
		this.gamesPlayed = 0;
		this.gamesWon = 0;
	}

	/**
	 * Deselects the current selected tile.
	 */
	public boolean deselectTile() {
		if (selectedTile == null) // nothing to deselect
			return false;
		this.selectedTile = null;
		return true;
	}
	
	/**
	 * @return if the tile is the current selected tile.
	 */
	public boolean isSelectedTile(Tile tile) {
		return this.selectedTile == tile;
	}

	/**
	 * Sets the selected tile.
	 * @param selectedTile is the tile that is selected.
	 */
	public void selectTile(Tile selectedTile) {
		this.selectedTile = selectedTile;
	}
	
	/**
	 * @return if a tile is selected.
	 */
	public boolean isSelectedTile() {
		return this.selectedTile != null;
	}
	
	/**
	 * @return the user's selected tile.
	 */
	public Tile getSelectedTile() {
		return this.selectedTile;
	}

	/**
	 * @return the username of the client.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the list of tokens.
	 */
	public List<Tile> getTokens() {
		return tokens;
	}
	
	/**
	 * Sets the token list.
	 * @param tokens is the list of tokens <Tile>.
	 */
	public void setTokens(ArrayList<Tile> tokens) {
		this.tokens = tokens;
	}
	
	/**
	 * @return the user's color.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Sets the color.
	 * @param color is the user's color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * get games played
	 */
	public int getGamesPlayed() {
		return this.gamesPlayed;
	}
	
	/**
	 * get games won
	 */
	public int getGamesWon() {
		return this.gamesWon;
	}
	
	/**
	 * set games played
	 */
	public void setGamesPlayed(int n) {
		this.gamesPlayed = n;
	}
	
	/**
	 * set games won
	 */
	public void setGamesWon(int n) {
		this.gamesWon = n;
	}
}
