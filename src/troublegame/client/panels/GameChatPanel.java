package troublegame.client.panels;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultCaret;

import troublegame.client.Interface;
import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

public class GameChatPanel extends JPanel {

	private static final long serialVersionUID = 3532807987664167900L;
	
	private static final String DEFAULT_TEXT = "Enter chat messages here";
	
	/**
	 * The main swing ui
	 */
	private SwingUI swingUI;
	
	/**
	 * The chat text area.
	 */
	private JTextArea chatMessages;
	
	/**
	 * Scroll bar for chat messages box
	 */
	private JScrollPane chatMessageScroll;
	
	/**
	 * Button to send current message
	 */
	private JButton sendButton;
	
	/**
	 * Label displaying num of chars currently in text box
	 */
	private JLabel charCountLabel;
	
	/**
	 * Entry point to send new messages
	 */
	private JTextField newMessage;
	
	/**
	 * Marker to determine whether the text in the message box is user entered or default
	 */
	private boolean isDefaultText;
	
	public GameChatPanel(SwingUI mainFrame) {
		
		this.swingUI = mainFrame;
		chatMessages = new JTextArea();
		chatMessageScroll = new JScrollPane(chatMessages);
		newMessage = new JTextField(DEFAULT_TEXT);
		isDefaultText = true;
		sendButton = new JButton("Send");
		charCountLabel = new JLabel("000/256", SwingConstants.CENTER);
		init();
		
	}
	
	public void init() {
		
		setLayout(null);
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1x.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_3.png");
		Image newimg1 = image1.getScaledInstance(65, 20, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(65, 20, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		// Chat area
		chatMessages.setEditable(false);
		chatMessages.setLineWrap(true);
		((DefaultCaret) chatMessages.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		// Chat scroll bar
		chatMessageScroll.setBounds(6, 0, 410, 512);
		
		// New message area
		newMessage.setBounds(6, 524, 290, 20);
		
		// TODO Possibly change this over to just flat out limit the number of characters if we have time
		// For now substring is extractacted from 0 to 256 if len > 256 when sending message
		newMessage.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				int length = e.getDocument().getLength();
				String placeHold = String.valueOf(length);
				if(length < 10) placeHold = "00" + length;
				else if(length < 100) placeHold = "0" + length;
				charCountLabel.setText(placeHold + "/256");
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(newMessage.getText().equals(DEFAULT_TEXT)) return;
				int length = e.getDocument().getLength();
				String placeHold = String.valueOf(length);
				if(length < 10) placeHold = "00" + length;
				else if(length < 100) placeHold = "0" + length;
				charCountLabel.setText(placeHold + "/256");
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}
		});
		
		newMessage.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				String curr = newMessage.getText().trim();
				if(curr.length() == 0) {
					newMessage.setText(DEFAULT_TEXT);
					isDefaultText = true;
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if(newMessage.getText().equals(DEFAULT_TEXT) && isDefaultText) {
					newMessage.setText("");
					isDefaultText = false;
				}
			}
		});
		
		newMessage.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_ESCAPE:
						swingUI.setInterface(Interface.PAUSE);
						break;
					case KeyEvent.VK_ENTER:
						sendMessageToServer();
						break;
					default:
						break;
					}
			}
		});
		
		// Send button
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				sendMessageToServer();
			}
		});
		sendButton.setBounds(302, 524, 65, 20);
		
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
		
		// Character counter
		charCountLabel.setBounds(370, 524, 48, 20);
		
		this.add(chatMessageScroll);
		this.add(newMessage);
		this.add(sendButton);
		this.add(charCountLabel);
		
	}
	
	/**
	 * Sends the given string to the server as a GAME_CHAT property
	 * @param input The message to send
	 */
	private void sendMessageToServer() {
		String input = newMessage.getText().trim();
		if(input.length() != 0 && isDefaultText == false) {
			if(input.length() > 256) input = input.substring(0, 256);
			swingUI.send(CommunicationHandler.GAME_CHAT + " " + input);
			if(newMessage.hasFocus()) {
				newMessage.setText("");
			} else {
				isDefaultText = true;
				newMessage.setText(DEFAULT_TEXT);
			}
		}
	}
	
	/**
	 * Send the given message to this users chat box while in game
	 * @param message String being sent as a message to this user
	 */
	public void sendMessageToChatBox(String message) {
		if (message.trim().length() == 0) return;
		chatMessages.append(message + "\n");
	}

}
