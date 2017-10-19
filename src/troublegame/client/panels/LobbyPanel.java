package troublegame.client.panels;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import troublegame.client.Interface;
import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class LobbyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5762982387707435312L;
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The Default List Model for JList of Users
	 */
	private DefaultListModel<String> gameRoomModel;
	
	/**
	 * The constructor for the Lobby panel.
	 * @param swingUI is the swing user interface.
	 */
	public LobbyPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.init();
	}

	private JTextArea chatMessages;
	
	/**
	 * Create the panel.
	 */
	public void init() {
		
		setLayout(null);
		
		gameRoomModel = new DefaultListModel<String>();
		
		JList<String> gameList = new JList<String>(gameRoomModel);
		gameList.setBounds(43, 67, 553, 196);
		this.add(gameList);
		
		JButton createButton = new JButton("Create Game");
		createButton.setBounds(371, 263, 125, 25);
		this.add(createButton);
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_NEW);
			}
		});
		
		JButton joinButton = new JButton("Join");
		joinButton.setBounds(499, 263, 97, 25);
		this.add(joinButton);
		joinButton.setEnabled(false);	
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_JOIN + " " + gameList.getSelectedValue());
			}
		});
		
		gameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!joinButton.isEnabled()) joinButton.setEnabled(true);
			}
		});
		
		JButton profileButton = new JButton("My Profile");
		profileButton.setBounds(718, 13, 97, 25);
		this.add(profileButton);
		
		profileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.setInterface(Interface.USER_PROFILE);
			}
		});
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(827, 13, 97, 25);
		this.add(logoutButton);
		
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to exit the game?", "Exit Trouble Message",
						JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION)
						swingUI.send(CommunicationHandler.LOGOUT_REQUEST);
			}
		});
		
		JList<String> onlineUsers = new JList<String>();
		onlineUsers.setBounds(615, 67, 309, 196);
		this.add(onlineUsers);
		
		JLabel lblGameRooms = new JLabel("Game Rooms");
		lblGameRooms.setLabelFor(gameList);
		lblGameRooms.setBounds(43, 46, 90, 16);
		this.add(lblGameRooms);
		
		JLabel lblUsersOnline = new JLabel("Users Online");
		lblUsersOnline.setLabelFor(onlineUsers);
		lblUsersOnline.setBounds(616, 46, 84, 16);
		this.add(lblUsersOnline);
		
		JPanel panel = new JPanel();
		panel.setBounds(440, 318, 484, 196);
		chatMessages = new JTextArea();
		DefaultCaret caret = (DefaultCaret)chatMessages.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//chatMessages.setBounds(103, 220, 503, 151);
		JScrollPane scroll = new JScrollPane(chatMessages);
		chatMessages.setEditable(false);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		panel.add(scroll);
		this.add(panel);
		
		//chatMessages = new JTextArea();
		//chatMessages.setBounds(440, 318, 484, 196);
		//this.add(chatMessages);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setLabelFor(chatMessages);
		lblChat.setBounds(440, 296, 56, 16);
		this.add(lblChat);
		
		JTextField newMessage = new JTextField();
		newMessage.setBounds(440, 516, 386, 24);
		this.add(newMessage);
		newMessage.setColumns(10);
		
		JButton sendChatButton = new JButton("Send");
		sendChatButton.setBounds(827, 516, 97, 25);
		this.add(sendChatButton);
		
		sendChatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage(newMessage);	
			}
		});
		
		JTextArea activityTextArea = new JTextArea();
		activityTextArea.setBounds(43, 318, 376, 222);
		this.add(activityTextArea);
		
		JLabel lblActivity = new JLabel("Activity Feed");
		lblActivity.setLabelFor(activityTextArea);
		lblActivity.setBounds(43, 296, 106, 16);
		this.add(lblActivity);

	}
	
	/**
	 * @return the swing user interface.
	 */
	public SwingUI getSwingUI() {
		return swingUI;
	}

	/**
	 * Handles the chat message by reading the text field
	 * @param textField is the {@link JTextField} in the game room.
	 */
	private void sendChatMessage(JTextField textField) {
		String toSend = textField.getText();
		if(toSend.equals("")) return;
		swingUI.send(CommunicationHandler.LOBBY_CHAT + " " + toSend);
		textField.setText("");	
	}
	
	/**
	 * Sets the swing user interface.
	 * @param swingUI is the swing user interface.
	 */
	public void setSwingUI(SwingUI swingUI) {
		this.swingUI = swingUI;
	}
	
	public void addGameRoom(String username) {
		gameRoomModel.addElement(username);
	}
	
	public void removeGameRoom(String gameRoomName) {
		gameRoomModel.removeElement(gameRoomName);
	}
	
	public void clearGameRooms() {
		gameRoomModel.clear();
	}
	
	public void updateChat(String message) {
		chatMessages.append(message+"\n");
	}
}
