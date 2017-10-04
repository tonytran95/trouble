package game.trouble.client;

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
	 * The selected tile.
	 */
	private Tile selectedTile;
	
	/**
	 * The list of tiles.
	 */
	private List<Tile> tiles;
	
	/**
	 * The list of tokens.
	 */
	private List<Tile> tokens;
	
	/**
	 * Constructs a new user.
	 * @param username is the username.
	 */
	public User(String username) {
		this.username = username;
		this.tiles = new ArrayList<Tile>();
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
	 * @return the list of tiles.
	 */
	public List<Tile> getTiles() {
		return tiles;
	}

	/**
	 * @return the list of tokens.
	 */
	public List<Tile> getTokens() {
		return tokens;
	}


}
