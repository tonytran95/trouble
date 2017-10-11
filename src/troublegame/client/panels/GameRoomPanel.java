package troublegame.client.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import troublegame.client.SwingUI;

@SuppressWarnings("serial")
public class GameRoomPanel extends JPanel {
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * GameRoom's name
	 */
	private String name;
	
	/**
	 * The Default List Model for JList of Users
	 */
	DefaultListModel<String> userModel;
	
	/**
	 * The constructor for the Lobby panel.
	 * @param swingUI is the swing user interface.
	 */
	public GameRoomPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.init();
	}
	
	/**
	 * Initializes the Lobby panel.
	 */
	public void init() {
		userModel = new DefaultListModel<String>();
		JList<String> users = new JList<String>(userModel);
		JButton startGame = new JButton("Start Game");
		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send("START_GAME " + name);
			}
		});
		JButton leaveRoom = new JButton("Leave Room");
		leaveRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send("LEAVE_ROOM");
			}
		});
		this.add(users);
		this.add(startGame);
		this.add(leaveRoom);
	}

	/**
	 * @return the swing user interface.
	 */
	public SwingUI getSwingUI() {
		return swingUI;
	}

	/**
	 * Sets the swing user interface.
	 * @param swingUI is the swing user interface.
	 */
	public void setSwingUI(SwingUI swingUI) {
		this.swingUI = swingUI;
	}
	
	/**
	 * Adds the user to our user list.
	 * @param username the username to add.
	 */
	public void addUser(String username) {
		userModel.addElement(username);
	}
	
	/**
	 * Sets the GameRoom's name.
	 * @param name
	 */
	public void setGameRoomName(String name) {
		this.name = name;
	}
	
}