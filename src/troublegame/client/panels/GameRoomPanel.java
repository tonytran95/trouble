package troublegame.client.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

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
	private DefaultListModel<String> userModel;
	private JTextArea chatMessages;
	private JTextField newMessage;
	
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
		this.setLayout(null);
		userModel = new DefaultListModel<String>();
		
		JList<String> list = new JList<String>(userModel);
		list.setBounds(125, 101, 95, 67);
		this.add(list);
		
		JLabel lblMembers = new JLabel("Members");
		lblMembers.setBounds(125, 76, 46, 14);
		this.add(lblMembers);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(248, 108, 89, 23);
		this.add(btnStart);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_START + " " + name);
			}
		});
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.setBounds(248, 145, 89, 23);
		this.add(btnLeave);
		btnLeave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_LEAVE);
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBounds(103, 214, 503, 161);
		chatMessages = new JTextArea();
		DefaultCaret caret = (DefaultCaret)chatMessages.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//chatMessages.setBounds(103, 220, 503, 151);
		JScrollPane scroll = new JScrollPane(chatMessages);
		chatMessages.setEditable(false);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		panel.add(scroll);
		this.add(panel);
		
		newMessage = new JTextField();
		newMessage.setBounds(103, 382, 503, 27);
		this.add(newMessage);
		newMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(611, 384, 82, 23);
		this.add(btnSend);

		btnSend.addActionListener(new ActionListener() {
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