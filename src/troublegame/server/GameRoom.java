package troublegame.server;

import java.util.ArrayList;

/**
 * Representation of a game room. Game room is essentially a waiting room
 * where groups of up to 4 players can connect to play trouble games with friends
 * against each other or AI opponents
 * @author Nick
 */
public class GameRoom {
	
	private ArrayList<User> members;
	private String name;
	private User owner;
	
	/**
	 * Create a new room with the creating user as the owner, the only member the creating member and the default name
	 * as the user's username followed by "Trouble Game Room"
	 * @param owner
	 */
	public GameRoom(User owner) {
		setOwner(owner);
		setDefaultName();
		members = new ArrayList<>();
	}
	
	/**
	 * Sets the owner of this room to be the given user
	 * @param o The user who will own this room
	 * @return true if the new room owner was set successfuly, false otherwise
	 */
	private boolean setOwner(User o) {
		this.owner = o;
		return (owner.equals(o)) ? true : false;
	}
	
	/**
	 * @return Returns the current owner of this gameroom
	 */
	private User getOwner() {
		return owner;
	}
	
	/**
	 * Add the given user to this room
	 * @param u The user to add to the room
	 */
	public void addUser(User u) {
		if(isRoomFull()) return;
		members.add(u);
	}
	
	/**
	 * Removes the given user from the room. If the removed user is the group owner then set the group 
	 * owner to the next user in the members list. If the removed user is the last person in the room then 
	 * all room properties are set to null
	 * @param u The user to remove from the room
	 */
	public void removeUser(User u) {
		
		int index = members.indexOf(u);
		boolean userWasOwner = getOwner().equals(u);
		
		if(index != -1) {
			members.remove(index);
		}
		
		if(getMembers().size() == 0) {
			setOwner(null);
			setName(null);
			members = null;
		} else if(userWasOwner && index == 0) {
			setOwner(members.get(0));
		}
	}
	
	/**
	 * Checks if the current room has 4 players
	 * @return true if the room is full, false if there is room for more players to join
	 */
	private boolean isRoomFull() {
		return (members.size() < 4) ? false : true;
	}
	
	/**
	 * @return The list of all users who are members of this room
	 */
	public ArrayList<User> getMembers() {
		return members;
	}
	
	/**
	 * Change the name of this room to the given name
	 * @param newName The new name of the room
	 * @return The name of the room after calling this function. If the name was updated successfully,
	 *  the new name will be returned, otherwise the original name will be returned
	 */
	public String setName(String newName) {
		this.name = newName;
		return getName();
	}
	
	/**
	 * @return The name of this room
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the default name of the room to be "<username>'s trouble game room"
	 * The 's is shortened to just ' if the username ends in an s (case insensitive)
	 */
	private void setDefaultName() {
		
		String ownerName = getOwner().getUsername();
		int usernameLastIndex = ownerName.length() - 1;
		char lastLetInUsername = Character.toLowerCase(ownerName.charAt(usernameLastIndex));
		this.name = (lastLetInUsername == 's') ? "' " : "'s ";
		this.name += "Trouble Game Room";
		
	}
	
}
