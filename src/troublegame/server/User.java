package troublegame.server;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representation of a user of the troublegame software. Users are people
 * who are required to sign up to play the game online and with their friends.
 * Having a user profile allows people to connect with each other within the game
 * as well as track user features and connect with friends
 * 
 */
public class User implements Serializable {
	
	/**
	 * Unique ID for object serialisation
	 */
	private static final long serialVersionUID = -8845969610546524630L;
	private int id;
	
	/**
	 * Emails are stored as all lowercase
	 */
	private String username;
	private String password;
	private String email;
	private String status;
	private Color favouriteColor;
	private ArrayList<User> friends;
	
	public User(String email, String username, String password, Color favColor) {
		this.email = email.toLowerCase();
		setUsername(username);
		setPassword(password);
		setPreferedColor(favColor);
	}
	
	private void setPreferedColor(Color favColor) {
		this.favouriteColor = favColor;
	}
	
	protected String getPassword() {
		return this.password;
	}
	
	private void setPassword(String pass) {
		this.password = pass;
	}

	/**
	 * Set this user's username
	 * @param u The new name to give the user
	 */
	public void setUsername(String u) {
		this.username = u;
	}
	
	/**
	 * @return This user's username
	 */
	public String getUsername() {
		return this.username;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String updateStatus(String status) {
		this.status = status;
		return getStatus();
	}
	
	public String getStatus() {
		return this.status;
	}
	
}
