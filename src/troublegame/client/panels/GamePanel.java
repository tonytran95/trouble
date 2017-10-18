package troublegame.client.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import troublegame.client.SwingUI;
import troublegame.client.UserInput;
import troublegame.client.model.Board;
import troublegame.client.model.Tile;
import troublegame.communication.CommunicationHandler;

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
	 * The map of tokens <position, color>
	 */
	private Map<Integer, Color> tokenMap;
	
	/**
	 * The map of players <username, color>
	 */
	private Map<String, String> players;
	
	
	
	/**
	 * The user input
	 */
	private MouseListener userInput;
	
	/**
	 * The game messages <index, message>.
	 */
	private Map<Integer, String> messages;
	
	
	
	
	
	
	
	/**
	 * The constructor for the start panel.
	 * @param swingUI is the swing user interface.
	 */
	public GamePanel(SwingUI swingUI) {
		
		this.swingUI = swingUI;
		this.boardPanel = new BoardPanel(swingUI);
		this.chatPanel = new GameChatPanel(swingUI);
		
		this.tokenMap = new HashMap<Integer, Color>();
		this.players = new HashMap<String, String>();
		this.messages = new LinkedHashMap<Integer, String>();
		
		this.init();
	}

	/**
	 * Initializes the game panel.
	 */
	public void init() {
		
		this.setLayout(null);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.swingUI.requestFocusInWindow();
		
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
//	public void setupPanel() {
//		String color = players.get(swingUI.getUser().getUsername());
//		
//		switch(color) {
//			case "red":
//				swingUI.getUser().setColor(Color.RED);
//				swingUI.getUser().setTokens(board.getRedTokens());
//				break;
//			case "blue":
//				swingUI.getUser().setColor(Color.BLUE);
//				swingUI.getUser().setTokens(board.getBlueTokens());
//				break;
//			case "yellow":
//				swingUI.getUser().setColor(Color.YELLOW);
//				swingUI.getUser().setTokens(board.getYellowTokens());
//				break;
//			case "green":
//				swingUI.getUser().setColor(Color.GREEN);
//				swingUI.getUser().setTokens(board.getGreenTokens());
//				break;
//			default:
//		}
//		
//		this.userInput = new UserInput(swingUI);
//		this.addMouseListener(this.userInput);
//	}
//		
//		/**
//		 * Create a list of the tokens
//		 */
//		public void createTokens() {
//			for (Tile token : board.getRedTokens()) {
//				Tile tile;
//				switch (token.getZone()) {
//					case Board.SLOT_HOME:
//						tile = board.getRedHomeZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.RED);
//						break;
//					case Board.SLOT_MAIN:
//						tile = board.getMainZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.RED);
//						break;
//					case Board.SLOT_END:
//						tile = board.getRedEndZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.RED);
//						break;
//				}
//			}
//			
//			for (Tile token : board.getBlueTokens()) {
//				Tile tile;
//				switch (token.getZone()) {
//					case Board.SLOT_HOME:
//						tile = board.getBlueHomeZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.BLUE);
//						break;
//					case Board.SLOT_MAIN:
//						tile = board.getMainZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.BLUE);
//						break;
//					case Board.SLOT_END:
//						tile = board.getBlueEndZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.BLUE);
//						break;
//				}
//			}
//			
//			for (Tile token : board.getYellowTokens()) {
//				Tile tile;
//				switch (token.getZone()) {
//					case Board.SLOT_HOME:
//						tile = board.getYellowHomeZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.YELLOW);
//						break;
//					case Board.SLOT_MAIN:
//						tile = board.getMainZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.YELLOW);
//						break;
//					case Board.SLOT_END:
//						tile = board.getYellowEndZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.YELLOW);
//						break;
//				}
//			}
//			
//			for (Tile token : board.getGreenTokens()) {
//				Tile tile;
//				switch (token.getZone()) {
//					case Board.SLOT_HOME:
//						tile = board.getGreenHomeZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.GREEN);
//						break;
//					case Board.SLOT_MAIN:
//						tile = board.getMainZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.GREEN);
//						break;
//					case Board.SLOT_END:
//						tile = board.getGreenEndZone().get(token.getIndex());
//						token.setTile(tile.getShape(), Color.GREEN);
//						break;
//				}
//			}
//		}
//		
//		@Override
//		protected void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			Graphics2D g2d = (Graphics2D) g.create();
//			
//			createTokens();
//			
//			// Draw tokens
//			for (Tile token : board.getRedTokens()) {
//				g2d.setColor(token.getColor());
//				if (swingUI.getUser().isSelectedTile(token))
//					g2d.setColor(token.getColor());
//				else {
//					if (token.getColor() == Color.RED)
//						g2d.setColor(TRANSPARENT_RED);
//					else if (token.getColor() == Color.BLUE)
//						g2d.setColor(TRANSPARENT_BLUE);
//					else if (token.getColor() == Color.GREEN)
//						g2d.setColor(TRANSPARENT_GREEN);
//					else if (token.getColor() == Color.YELLOW)
//						g2d.setColor(TRANSPARENT_YELLOW);
//				}
//				g2d.fill(token.getShape());
//			}
//			for (Tile token : board.getBlueTokens()) {
//				g2d.setColor(token.getColor());
//				if (swingUI.getUser().isSelectedTile(token))
//					g2d.setColor(token.getColor());
//				else {
//					if (token.getColor() == Color.RED)
//						g2d.setColor(TRANSPARENT_RED);
//					else if (token.getColor() == Color.BLUE)
//						g2d.setColor(TRANSPARENT_BLUE);
//					else if (token.getColor() == Color.GREEN)
//						g2d.setColor(TRANSPARENT_GREEN);
//					else if (token.getColor() == Color.YELLOW)
//						g2d.setColor(TRANSPARENT_YELLOW);
//				}
//				g2d.fill(token.getShape());
//			}
//			for (Tile token : board.getYellowTokens()) {
//				g2d.setColor(token.getColor());
//				if (swingUI.getUser().isSelectedTile(token))
//					g2d.setColor(token.getColor());
//				else {
//					if (token.getColor() == Color.RED)
//						g2d.setColor(TRANSPARENT_RED);
//					else if (token.getColor() == Color.BLUE)
//						g2d.setColor(TRANSPARENT_BLUE);
//					else if (token.getColor() == Color.GREEN)
//						g2d.setColor(TRANSPARENT_GREEN);
//					else if (token.getColor() == Color.YELLOW)
//						g2d.setColor(TRANSPARENT_YELLOW);
//				}
//				g2d.fill(token.getShape());
//			}
//			for (Tile token : board.getGreenTokens()) {
//				g2d.setColor(token.getColor());
//				if (swingUI.getUser().isSelectedTile(token))
//					g2d.setColor(token.getColor());
//				else {
//					if (token.getColor() == Color.RED)
//						g2d.setColor(TRANSPARENT_RED);
//					else if (token.getColor() == Color.BLUE)
//						g2d.setColor(TRANSPARENT_BLUE);
//					else if (token.getColor() == Color.GREEN)
//						g2d.setColor(TRANSPARENT_GREEN);
//					else if (token.getColor() == Color.YELLOW)
//						g2d.setColor(TRANSPARENT_YELLOW);
//				}
//				g2d.fill(token.getShape());
//			}
//
//			g2d.dispose();
}