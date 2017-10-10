package troublegame.server;

import java.util.ArrayList;

/**
 * Representation of a game room. Game room is essentially a waiting room
 * where groups of up to 4 Connections can connect to play trouble games with friends
 * against each other or AI opponents
 * @author Nick
 */
public class GameRoom {
	
	private ArrayList<Connection> members;
	private String name;
	private Connection owner;
	
	/**
	 * Create a new room with the creating Connection as the owner, the only member the creating member and the default name
	 * as the Connection's username followed by "Trouble Game Room"
	 * @param owner
	 */
	public GameRoom(Connection owner) {
		setOwner(owner);
		setDefaultName();
		members = new ArrayList<>();
	}
	
	/**
	 * Sets the owner of this room to be the given Connection
	 * @param o The Connection who will own this room
	 * @return true if the new room owner was set successfully, false otherwise
	 */
	private boolean setOwner(Connection o) {
		this.owner = o;
		return (owner.equals(o)) ? true : false;
	}
	
	/**
	 * @return Returns the current owner of this game room
	 */
	private Connection getOwner() {
		return owner;
	}
	
	/**
	 * Add the given Connection to this room
	 * @param u The Connection to add to the room
	 */
	public void addConnection(Connection u) {
		if(isRoomFull()) return;
		members.add(u);
	}
	
	/**
	 * Removes the given Connection from the room. If the removed Connection is the group owner then set the group 
	 * owner to the next Connection in the members list. If the removed Connection is the last person in the room then 
	 * all room properties are set to null
	 * @param u The Connection to remove from the room
	 */
	public void removeConnection(Connection u) {
		
		int index = members.indexOf(u);
		boolean ConnectionWasOwner = getOwner().equals(u);
		
		if(index != -1) {
			members.remove(index);
		}
		
		if(getMembers().size() == 0) {
			setOwner(null);
			setName(null);
			members = null;
		} else if(ConnectionWasOwner && index == 0) {
			setOwner(members.get(0));
		}
	}
	
	/**
	 * Checks if the current room has 4 Connections
	 * @return true if the room is full, false if there is room for more Connections to join
	 */
	private boolean isRoomFull() {
		return (members.size() < 4) ? false : true;
	}
	
	/**
	 * @return The list of all Connections who are members of this room
	 */
	public ArrayList<Connection> getMembers() {
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
	 * The 's is shortened to just ' if the connection ends in an s (case insensitive)
	 */
	private void setDefaultName() {
		
		String ownerName = getOwner().getUsername();
		int ConnectionnameLastIndex = ownerName.length() - 1;
		char lastLetInConnectionname = Character.toLowerCase(ownerName.charAt(ConnectionnameLastIndex));
		this.name = (lastLetInConnectionname == 's') ? "' " : "'s ";
		this.name += "Trouble Game Room";
		
	}
	
}
