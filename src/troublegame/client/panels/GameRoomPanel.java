package troublegame.client.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

public class GameRoomPanel extends JPanel {
	
	private static final long serialVersionUID = 3282499101326401010L;

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
	JTextArea chatMessages;
	
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
				swingUI.send(CommunicationHandler.GAME_START + " " + name);
			}
		});
		JButton leaveRoom = new JButton("Leave Room");
		leaveRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_LEAVE);
			}
		});
		// chat stuff
		JPanel chatPanel = new JPanel();
		GridBagLayout gbpanel = new GridBagLayout();
		GridBagConstraints gbcPanel = new GridBagConstraints();
		chatPanel.setLayout(gbpanel);

		chatMessages = new JTextArea(10,40);
		chatMessages.setEditable(false);
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 1;
		gbcPanel.gridwidth = 48;
		gbcPanel.gridheight = 43;
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.weightx = 1;
		gbcPanel.weighty = 1;
		gbcPanel.anchor = GridBagConstraints.NORTH;
		gbpanel.setConstraints(chatMessages, gbcPanel);
		chatPanel.add(chatMessages);
		JTextField newMessage = new JTextField();
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 45;
		gbcPanel.gridwidth = 45;
		gbcPanel.gridheight = 3;
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.weightx = 1;
		gbcPanel.weighty = 1;
		gbcPanel.anchor = GridBagConstraints.NORTH;
		gbpanel.setConstraints(newMessage, gbcPanel);
		chatPanel.add(newMessage);

		JButton sendButton = new JButton("Send");
		gbcPanel.gridx = 150;
		gbcPanel.gridy = 45;
		gbcPanel.gridwidth = 5;
		gbcPanel.gridheight = 3;
		gbcPanel.fill = GridBagConstraints.BOTH;
		gbcPanel.weightx = 1;
		gbcPanel.weighty = 1;
		gbcPanel.anchor = GridBagConstraints.NORTH;
		gbpanel.setConstraints(sendButton, gbcPanel );
		chatPanel.add(sendButton);
		
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage(newMessage);
			}
		});
		newMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage(newMessage);	
			}
		});
		this.add(chatPanel);
		this.add(users);
		this.add(startGame);
		this.add(leaveRoom);
		
	}
	
	/**
	 * Handles the chat message by reading the text field
	 * @param textField is the {@link JTextField} in the game room.
	 */
	private void sendChatMessage(JTextField textField) {
		String toSend = textField.getText();
		if(toSend.equals("")) return;
		swingUI.send(CommunicationHandler.GAME_ROOM_CHAT + " " + toSend);
		textField.setText("");	
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
	 * Removes the users from our user list.
	 * @param username is the name to remove.
	 */
	public void removeUser(String username) {
		userModel.removeElement(username);
	}
	
	
	/**
	 * Removes the users from our user list.
	 */
	public void clearUsers() {
		userModel.clear();
	}
	
	/**
	 * Sets the GameRoom's name.
	 * @param name
	 */
	public void setGameRoomName(String name) {
		this.name = name;
	}
	
	public void updateChat(String message) {
		chatMessages.append(message+"\n");
	}
}