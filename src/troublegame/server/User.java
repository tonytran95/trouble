package troublegame.server;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import troublegame.communication.CommunicationHandler;
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
	private ArrayList<UUID> friends;
	private int gamesPlayed;
	private int gamesWon;
	
	/**
	 * Constructor used specifically to create Guest users
	 * when a player clicks 'Play as Guest' on the login screen
	 * 
	 * @param username of the guest
	 */
	public User(String username) {
		this.email = username.toLowerCase() + "@guest.com";
		setUsername(username);
		setFavouriteColor(Color.RANDOM);
	}
	
	public User(String email, String username, String password, Color favColor, String pQuot) {
		Objects.requireNonNull(email, "email must not be null");
		Objects.requireNonNull(username, "username must not be null");
		Objects.requireNonNull(password, "password must not be null");
		this.id = UUID.randomUUID();
		this.email = email.toLowerCase();
		this.gamesPlayed = 0;
		this.gamesWon = 0;
		setUsername(username);
		setPassword(password);
		setFavouriteColor(favColor);
		setPersonalQuote(pQuot);
		onlineStatus = USER_STATE_OFFLINE;
		friends = new ArrayList<>();
	}
	
	/**
	 * @return Email in lowercase
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Updates this user's email and saves the user
	 * @param email the updated email
	 * @return true if the email was updated successfully, false otherwise
	 */
	public boolean updateEmail(String newEmail) {
		
		String currentEmail = getEmail();
		newEmail = newEmail.toLowerCase();
		this.email = newEmail;
		
		if(UserManager.migrateUser(currentEmail, this) == UserManager.SUCCESS) {
			UserManager.saveExistingUser(this);
			return true;
		} else {
			this.email = currentEmail;
			return false;
		}
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
	 * Set this user's username
	 * @param u The new name to give the user
	 */
	public void setUsername(String u) {
		this.username = u;
	}
	
	/**
	 * Updates this user's username and saves the user
	 * @param u The new name to give the user
	 */
	public void updateUsername(String u) {
		setUsername(u);
		UserManager.saveExistingUser(this);
	}
	
	/**
	 * @return The user's password
	 */
	protected String getPassword() {
		return this.password;
	}
	
	/**
	 * Set the user's password
	 * @param pass The new password
	 */
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	/**
	 * Updates the user's password and saves the user
	 * @param pass The new password
	 */
	public void updatePassword(String pass) {
		setPassword(pass);
		UserManager.saveExistingUser(this);
	}
	
	/**
	 * @return This user's favourite color
	 */
	public Color getFavouriteColor() {
		return this.favouriteColor;
	}
	
	/**
	 * Set the favourite color for this user
	 * @param favColor This user's favourite color
	 */
	public void setFavouriteColor(Color favColor) {
		if(favColor == null) this.favouriteColor = Color.RANDOM;
		else this.favouriteColor = favColor;
	}
	
	/**
	 * Updates the favourite color for this user and then saves the user
	 * @param favColor This user's favourite color
	 */
	public void updateFavouriteColor(Color favColor) {
		setFavouriteColor(favColor);
		UserManager.saveExistingUser(this);
	}
	
	/**
	 * @return The personal quote for this user
	 */
	public String getPersonalQuote() {
		return this.personalQuote;
	}
	
	/**
	 * @return number of games this user played
	 */
	public int getGamesPlayed() {
		return this.gamesPlayed;
	}
	
	/**
	 * @return number of games this user won
	 */
	public int getGamesWon() {
		return this.gamesWon;
	}
	
	/**
	 * call this function after a game to increase statistics
	 */
	public void finishedGame(boolean won) {
		if (won) this.gamesWon++;
		this.gamesPlayed++;
		UserManager.saveExistingUser(this);
	}
	
	/**
	 * Set this user's personal quote
	 * @param quote The quote to use for the personal quote. If null, quote will be set to ""
	 */
	public void setPersonalQuote(String quote) {
		if(quote == null) this.personalQuote = "";
		else this.personalQuote = quote;
	}
	
	/**
	 * Set this user's personal quote and then save the user
	 * @param quote The quote to use for the personal quote. If null, quote will be set to ""
	 */
	public void updatePersonalQuote(String quote) {
		setPersonalQuote(quote);
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
	public ArrayList<UUID> getFriendList() {
		return this.friends;
	}
	
	/**
	 * Checks if this user is friends with user
	 * @return true if friends, false otherwise
	 */
	public boolean isFriend(User user) {
		ArrayList<UUID> friendList = this.getFriendList();
		UUID userID = user.getId();
		for (UUID id: friendList) {
			if (id.equals(userID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add the given user to this user's friends list and the friends friend list. Friendships must be symmetric,
	 * then save both users
	 * @param newFriend The new friend to add
	 * @return true if friend was successfully added, false otherwise
	 */
	public boolean addFriend(User newFriend) {
		return addFriend(newFriend.getId());
	}
	
	/**
	 * Add the user with the given UUID to this user's friends list and the friends friend list. Friendships must be symmetric,
	 * then save both users
	 * @param newFriend The new friend to add
	 * @return true if friend was successfully added, false otherwise
	 */
	public boolean addFriend(UUID newFriendId) {
		
		User newFriend = UserManager.loadUserById(newFriendId);
		
		if(newFriendId == null || newFriendId.equals(this.getId()) || getFriendList().contains(newFriendId)) return false;
		else {
			getFriendList().add(newFriendId);
			newFriend.getFriendList().add(this.getId());
		}
		
		// Check friend was added and you are in friends
		if(getFriendList().contains(newFriendId) && newFriend.getFriendList().contains(this.getId())) {
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
	public boolean removeFriend(User newFriend) {
		return removeFriend(newFriend.getId());
	}
	
	/**
	 * Remove friend with the given UUID from this user's list and this user from friends list and then save both users
	 * @param oldFriend The user to unfriend
	 * @return true if friend was successfully removed, false otherwise
	 */
	public boolean removeFriend(UUID oldFriendId) {
		
		User oldFriend = UserManager.loadUserById(oldFriendId);
		
		if(oldFriendId == null || oldFriendId.equals(this.getId()) || getFriendList().contains(oldFriendId) == false) return false;
		else {
			getFriendList().remove(oldFriendId);
			oldFriend.getFriendList().remove(this.getId());
		}
		
		// Check friend was removed
		if(getFriendList().contains(oldFriendId) == false && oldFriend.getFriendList().contains(this.getId()) == false) {
			UserManager.saveExistingUser(this);
			UserManager.saveExistingUser(oldFriend);
			return true;
		}
		else return false;
		
	}
	
	/**
	 * Given the user's outputstream, send his friends over in a string, separated by %
	 */
	public void sendFriendList(PrintWriter outputStream) {
		String friends = "";
		for(UUID friendID: this.getFriendList()) {
			if (!friends.equals("")) friends += "%";
			User friend = UserManager.loadUserById(friendID);
			friends += friend.getUsername();
		}
		outputStream.println(CommunicationHandler.GAME_ROOM_FRIENDS+friends);
	}
	/**
	 * Representation of this user as a string
	 */
	@Override
	public String toString() {
		String user = "Id: " + getId();
		user += "\nEmail: " + getEmail();
		user += "\nUsername: " + getUsername();
		user += "\nPassword: " + getPassword();
		user += "\nFavourite Color: " + getFavouriteColor();
		user += "\nPersonal Quote: " + getPersonalQuote();
		user += "\nOnline Status: " + getOnlineStatus();
		user += "\nFriends: ";
		for(int i = 0; i < getFriendList().size(); i++) {
			UUID friendID = getFriendList().get(i);
			User friend = UserManager.loadUserById(friendID);
			if(i == 0) user += friend.getUsername();
			else user += ", " + friend.getUsername();
		}
		
		return user;
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
