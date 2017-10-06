package game.trouble.client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import game.trouble.client.model.Tile;

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
	 * Constructs a new user.
	 * @param username is the username.
	 */
	public User(String username) {
		this.username = username;
		this.tokens = new ArrayList<Tile>();
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
	
	public boolean isSelectedTile() {
		return this.selectedTile != null;
	}
	
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
	
	public void setTokens(ArrayList<Tile> tokens) {
		this.tokens = tokens;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

}
