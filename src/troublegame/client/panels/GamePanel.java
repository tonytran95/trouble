package troublegame.client.panels;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

import troublegame.client.Interface;
import troublegame.client.SwingUI;

/**
 * 
 * The {@link GamePanel} class handles the display of the game of the client.
 * 
 * @author Jeffrey Ung and Tony Tran
 *
 */
public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = -1640618945771077108L;
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * Panel containing board
	 */
	private BoardPanel boardPanel;
	
	/**
	 * Panel containing chat window
	 */
	private GameChatPanel chatPanel;
	
	/**
	 * The map of players <username, color>
	 */
	private Map<String, String> players;
	
	
	/**
	 * The constructor for the start panel.
	 * @param swingUI is the swing user interface.
	 */
	public GamePanel(SwingUI swingUI) {
		
		this.swingUI = swingUI;
		this.boardPanel = new BoardPanel(swingUI);
		this.chatPanel = new GameChatPanel(swingUI);
		this.players = new HashMap<String, String>();
		
		this.init();
	}

	/**
	 * Initializes the game panel.
	 */
	public void init() {
		
		this.setLayout(null);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.swingUI.setFocusable(true);
		this.swingUI.requestFocusInWindow();

		swingUI.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
				case KeyEvent.VK_P:
					swingUI.setInterface(Interface.PAUSE);
					break;
				default:
					System.out.println("Unhandled key button in game panel: " + e.getKeyChar());
					break;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

		});
		
		
		boardPanel.setBounds(0, 0, 576, 576);
		chatPanel.setBounds(589, 301, 422, 262);
		
		this.add(boardPanel);
		this.add(chatPanel);
		this.setVisible(true);
		
	}
	
	/**
	 * @return The board panel associated with this game panel
	 */
	public BoardPanel getBoardPanel() {
		return this.boardPanel;
	}
	
	/**
	 * @return The chat panel associated with this game panel
	 */
	public GameChatPanel getChatPanel() {
		return this.chatPanel;
	}
		
		
//		rollDie.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent event) {
//				if (!swingUI.getUser().isSelectedTile()) {
//					//updateMessage("Please select a token in order to roll the die!", 0);
//					sendChatMessage("Please select a token in order to roll the die!");
//					return;
//				}
//
//				if (swingUI.getUser().getSelectedTile().getZone() == Board.SLOT_END) {
//					//updateMessage("Cannot roll a token in the end zone", 0);
//					sendChatMessage("Cannot roll a token in the end zone");
//					return;
//				}
//
//				//updateMessage(" ", 0);
//				for (int i = 0; i < swingUI.getUser().getTokens().size(); i++) {
//					if (swingUI.getUser().getTokens().get(i).equals(swingUI.getUser().getSelectedTile())) {
//						swingUI.send(CommunicationHandler.GAME_ROLL + " " + i);
//						break;
//					}
//				}
//			}
//		});
//		
//		JPanel chatPanel = new JPanel();
//		JTextField newMessage = new JTextField();
//		JButton sendButton = new JButton("Send");
//		chatMessages = new JTextArea(10, 80);
//		JScrollPane scroll = new JScrollPane(chatMessages);
//
//		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
//			public void adjustmentValueChanged(AdjustmentEvent e) {
//				// auto scroll to the bottom
//				e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
//			}
//		});
//		newMessage.setPreferredSize(new Dimension(585, 20));
//		chatMessages.setEditable(false);
//		chatPanel.add(scroll);
//		chatPanel.add(newMessage);
//		chatPanel.add(sendButton);
//	
//		sendButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				swingUI.send(CommunicationHandler.GAME_CHAT + " " + newMessage.getText());
//				newMessage.setText("");	
//			}
//		});
//		newMessage.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (!newMessage.getText().equals("")) {
//					swingUI.send(CommunicationHandler.GAME_CHAT + " " + newMessage.getText());
//					newMessage.setText("");	
//				}
//			}
//		});
//		this.add(gameSpace);
//		this.add(label);
//		this.add(rollDie);
//		this.add(chatPanel);
//	}
//
//	
//	
//	public void updateMessage(String msg, int index) {
//		this.messages.put(index, msg);
//		String text = "<html>";
//		for (Entry<Integer, String> s : messages.entrySet()) {
//			if (s.getValue().equals(""))
//				continue;
//			text += s.getValue() + "<br>";
//		}
//		text += "</html>";
//		this.label.setText(text);
//	}
//	
//	/**
//	 * @return the players map.
//	 */
	public Map<String, String> getPlayers() {
		return players;
	}
//
//	/**
//	 * @return the token map.
//	 */
//	public Map<Integer, Color> getTokenMap() {
//		return tokenMap;
//	}
//	
	
}