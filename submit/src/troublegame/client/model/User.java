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
	 * The selected slot of a Token.
	 */
	private Slot selectedSlot;
	
	/**
	 * The list of tokens.
	 */
	private List<Slot> tokens;
	
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
		this.tokens = new ArrayList<Slot>();
		this.gamesPlayed = 0;
		this.gamesWon = 0;
	}

	/**
	 * Deselects the current selected slot.
	 */
	public boolean deselectSlot() {
		if (selectedSlot == null) // nothing to deselect
			return false;
		this.selectedSlot = null;
		return true;
	}
	
	/**
	 * @return if the slot is the current selected slot.
	 */
	public boolean isSelectedSlot(Slot slot) {
		return this.selectedSlot == slot;
	}

	/**
	 * Sets the selected slot.
	 * @param selectedSlot is the slot that is selected.
	 */
	public void selectSlot(Slot selectedSlot) {
		this.selectedSlot = selectedSlot;
	}
	
	/**
	 * @return if a slot is selected.
	 */
	public boolean isSelectedSlot() {
		return this.selectedSlot != null;
	}
	
	/**
	 * @return the user's selected slot.
	 */
	public Slot getSelectedSlot() {
		return this.selectedSlot;
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
	public List<Slot> getTokens() {
		return tokens;
	}
	
	/**
	 * Sets the token list.
	 * @param tokens is the list of tokens <Slot>.
	 */
	public void setTokens(ArrayList<Slot> tokens) {
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
