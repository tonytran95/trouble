package troublegame.client.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

public class GameRoomPanel extends JPanel {
	private JTextField textField;

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
	 * The Default List Model for JList of Users & friends
	 */
	private DefaultListModel<String> roomUserModel;
	private DefaultListModel<String> friendsModel;
	
	private JTextArea chatMessages;
	private JTextField newMessage;
	private Image backgroundImage;
	
	/**
	 * The constructor for the Lobby panel.
	 * @param swingUI is the swing user interface.
	 */
	public GameRoomPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		/**
		 * set background image
		 */
		try {
			backgroundImage = ImageIO.read(new File("./data/img/background2.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.init();
	}
	
	/**
	 * Create the panel.
	 */
	public void init() {
		setLayout(null);
		
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1x.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_3.png");
		Image newimg1 = image1.getScaledInstance(143, 23, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(143, 23, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		roomUserModel = new DefaultListModel<String>();
		
		JList<String> memberList = new JList<String>(roomUserModel);
		memberList.setBounds(227, 118, 132, 111);
		add(memberList);
		
		JLabel lblUsersInRoom = new JLabel("Users in Room");
		lblUsersInRoom.setLabelFor(memberList);
		lblUsersInRoom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsersInRoom.setBounds(227, 96, 109, 16);
		add(lblUsersInRoom);
		
		JPanel panel = new JPanel();
		panel.setBounds(227, 276, 637, 234);
		chatMessages = new JTextArea();
		DefaultCaret caret = (DefaultCaret)chatMessages.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		//chatMessages.setBounds(103, 220, 503, 151);
		JScrollPane scroll = new JScrollPane(chatMessages);
		chatMessages.setEditable(false);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		panel.add(scroll);
		this.add(panel);
		//JTextArea chatTextArea = new JTextArea();
		//chatTextArea.setBounds(227, 276, 637, 234);
		//add(chatTextArea);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setLabelFor(chatMessages);
		lblChat.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChat.setBounds(227, 251, 62, 24);
		add(lblChat);
		
		JButton startButton = new JButton("Start Game");
		startButton.setBounds(371, 140, 108, 25);
		add(startButton);
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.send(CommunicationHandler.GAME_START + " " + name);
			}
		});
		
		startButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				startButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				startButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		startButton.setIcon(imgIcon1);
		startButton.setHorizontalTextPosition(SwingConstants.CENTER);
		startButton.setBorderPainted(false);
		
		JButton leaveButton = new JButton("Leave Room");
		leaveButton.setBounds(371, 178, 108, 25);
		add(leaveButton);
		
		leaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.send(CommunicationHandler.GAME_ROOM_LEAVE);
			}
		});
		leaveButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				leaveButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				leaveButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		leaveButton.setIcon(imgIcon1);
		leaveButton.setHorizontalTextPosition(SwingConstants.CENTER);
		leaveButton.setBorderPainted(false);
		
		friendsModel = new DefaultListModel<String>();
		JList<String> friendList = new JList<String>(friendsModel);
		friendList.setBounds(592, 118, 270, 111);
		add(friendList);
		
		JLabel lblNewLabel = new JLabel("Friends");
		lblNewLabel.setLabelFor(friendList);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(592, 96, 62, 16);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Invite");
		btnNewButton.setBounds(766, 228, 97, 25);
		add(btnNewButton);
		
		newMessage = new JTextField();
		newMessage.setBounds(227, 512, 535, 22);
		add(newMessage);
		newMessage.setColumns(10);
		
		JButton sendButton = new JButton("Send");
		sendButton.setBounds(767, 511, 97, 25);
		add(sendButton);
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				sendChatMessage(newMessage);
			}
		});
		sendButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				sendButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				sendButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		sendButton.setIcon(imgIcon1);
		sendButton.setHorizontalTextPosition(SwingConstants.CENTER);
		sendButton.setBorderPainted(false);
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
		roomUserModel.addElement(username);
	}
	
	/**
	 * Removes the users from our user list.
	 * @param username is the name to remove.
	 */
	public void removeUser(String username) {
		roomUserModel.removeElement(username);
	}
	
	
	/**
	 * Removes the users from our user list.
	 */
	public void clearUsers() {
		roomUserModel.clear();
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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, this);
	}
}
