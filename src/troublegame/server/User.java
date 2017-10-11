package troublegame.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import troublegame.server.io.UserManager;

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
	
	// User state quantifiers
	public static final int USER_STATE_ONLINE = 0;
	public static final int USER_STATE_OFFLINE = 1;
	
	/**
	 * Emails are stored as all lowercase
	 */
	private String email;
	private UUID id;
	private String username;
	private String password;
	private Color favouriteColor;
	private String personalQuote;
	private int onlineStatus;
	private ArrayList<User> friends;
	
	public User(String email, String username, String password, Color favColor, String pQuot) {
		Objects.requireNonNull(email, "email must not be null");
		Objects.requireNonNull(username, "username must not be null");
		Objects.requireNonNull(password, "password must not be null");
		this.id = UUID.randomUUID();
		this.email = email.toLowerCase();
		this.username = username;
		this.password = password;
		this.favouriteColor = (favColor);
		this.personalQuote = pQuot;
		onlineStatus = USER_STATE_OFFLINE;
		friends = new ArrayList<>();
	}
	
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Updates this user's email and saves the user
	 * @param email the updated email
	 * @return true if the email was updated successfully, false otherwise
	 */
	public boolean updateEmail(String email) {
		email = email.toLowerCase();
		
		if(UserManager.migrateUser(getEmail(), email) == UserManager.SUCCESS) {
			this.email = email;
			UserManager.saveExistingUser(this);
			return true;
		}
		
		return false;
	}
	
	/**
	 * @return This user's id
	 */
	public UUID getId() {
		return this.id;
	}
	
	/**
	 * @return This user's username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Set this user's username and saves the user
	 * @param u The new name to give the user
	 */
	public void updateUsername(String u) {
		this.username = u;
		UserManager.saveExistingUser(this);
	}
	
	/**
	 * @return The user's password
	 */
	protected String getPassword() {
		return this.password;
	}
	
	/**
	 * Updates the user's password and saves the user
	 * @param pass The new password
	 */
	public void updatePassword(String pass) {
		this.password = pass;
		UserManager.saveExistingUser(this);
	}
	
	/**
	 * @return This user's favourite color
	 */
	public Color getPreferedColor() {
		return this.favouriteColor;
	}
	
	/**
	 * Set the favourite color for this user
	 * @param favColor This user's favourite color
	 */
	public void updateFavouriteColor(Color favColor) {
		if(favColor == null) favColor = Color.RANDOM;
		this.favouriteColor = favColor;
		UserManager.saveExistingUser(this);
	}
	
	/**
	 * @return The personal quote for this user
	 */
	public String getPersonalQuote() {
		return this.personalQuote;
	}
	
	/**
	 * Set this user's personal quote
	 * @param quote The quote to use for the personal quote. If null, quote will be set to ""
	 */
	public void updatePersonalQuote(String quote) {
		if(quote == null) quote = "";
		this.personalQuote = quote;
		UserManager.saveExistingUser(this);
	}
	
	/**
	 * Set the user's status to online/offline
	 * @param newStatus
	 */
	public void setOnlineStatus(int newStatus) {
		this.onlineStatus = newStatus;
	}
	
	/**
	 * @return Whether this player is online or not
	 */
	public int getOnlineStatus() {
		return this.onlineStatus;
	}
	
	/**
	 * @return The friend list for this user
	 */
	public ArrayList<User> getFriendList() {
		return this.friends;
	}
	
	/**
	 * Add the given user to this user's friends list and the friends friend list. Friendships must be symmetric,
	 * then save both users
	 * @param newFriend The new friend to add
	 * @return true if friend was successfully added, false otherwise
	 */
	public boolean addFriend(User newFriend) {
		
		if(newFriend == null || newFriend.equals(this) || getFriendList().contains(newFriend)) return false;
		else {
			getFriendList().add(newFriend);
			newFriend.getFriendList().add(this);
		}
		
		// Check friend was added and you are in friends
		if(getFriendList().contains(newFriend) && newFriend.getFriendList().contains(this)) {
			UserManager.saveExistingUser(this);
			UserManager.saveExistingUser(newFriend);
			return true;
		}
		else return false;
		
	}
	
	/**
	 * Remove friend from this user's list and this user from friends list and then save both users
	 * @param oldFriend The user to unfriend
	 * @return true if friend was successfully removed, false otherwise
	 */
	public boolean removeFriend(User oldFriend) {
		
		if(oldFriend == null || oldFriend.equals(this) || getFriendList().contains(oldFriend) == false) return false;
		else {
			getFriendList().remove(oldFriend);
			oldFriend.getFriendList().remove(this);
		}
		
		// Check friend was removed
		if(getFriendList().contains(oldFriend) == false && oldFriend.getFriendList().contains(this) == false) {
			UserManager.saveExistingUser(this);
			UserManager.saveExistingUser(oldFriend);
			return true;
		}
		else return false;
		
	}
	
	/**
	 * Users are equal if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
}
