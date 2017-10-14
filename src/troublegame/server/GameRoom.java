package troublegame.server;

import java.io.PrintWriter;
import java.util.ArrayList;

import troublegame.communication.CommunicationHandler;

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
		members = new ArrayList<>();
		setOwner(owner);
		setDefaultName();
	}
	
	/**
	 * Sets the owner of this room to be the given Connection
	 * @param o The Connection who will own this room
	 * @return true if the new room owner was set successfully, false otherwise
	 */
	private boolean setOwner(Connection o) {
		this.owner = o;
		return owner.equals(o);
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
		for (Connection member : members)
			member.getOutputStream().println(CommunicationHandler.GAME_ROOM_JOIN + " " + u.getUsername());
		for (Connection member : members)
			if (member.getUser().equals(u.getUser()) == false) u.getOutputStream().println(CommunicationHandler.GAME_ROOM_MEMBER + " " + member.getUsername());
	}
	
	/**
	 * Removes the given Connection from the room. If the removed Connection is the group owner then set the group 
	 * owner to the next Connection in the members list
	 * @param u The Connection to remove from the room
	 * @return The number of members in the remove after the player was removed
	 */
	public int removeConnection(Connection u) {
		
		int connIndex = members.indexOf(u);
		boolean wasRoomOwner = getOwner().equals(u);
		
		if(connIndex != -1) {
			members.remove(connIndex);
		}
		
		if(members.size() != 0 && wasRoomOwner) {
			
			setOwner(members.get(0));
			
		}
		
		return members.size();
	}
	
	/**
	 * Checks if the current room has 4 Connections
	 * @return true if the room is full, false if there is room for more Connections to join
	 */
	private boolean isRoomFull() {
		return members.size() == 4;
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
		this.name = ownerName;
		this.name += (lastLetInConnectionname == 's') ? "' " : "'s ";
		this.name += "Trouble Game Room";
	}
	
	/**
	 * Broadcasts to all members of this GameRoom to start the game
	 */
	public void startGame() {
		for (Connection member : members) 
			member.getOutputStream().println(CommunicationHandler.GAME_START);
	}
	
	/**
	 * checks if a connection is a member inside the game room
	 */
	public boolean isMember(Connection c) {
		for (Connection member: members) {
			if (member.getUser().equals(c.getUser())) return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	public void doChat(Connection sender, String message) {
		for (Connection member: members) {
			PrintWriter outputStream = member.getOutputStream();
			String s = String.format(CommunicationHandler.GAME_ROOM_CHAT + "%s: %s", sender.getUsername(), message);
			outputStream.println(s);
		}
	}
}
