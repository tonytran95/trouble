package game.trouble.client.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.trouble.client.SwingUI;
import game.trouble.client.UserInput;
import game.trouble.client.model.BoardModel;
import game.trouble.client.model.Tile;
import game.trouble.model.Board;

/**
 * 
 * The GamePanel class handles the display of the game of the client.
 * 
 * @author Jeffrey Ung
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	/**
	 * Transparent colors.
	 */
	public final static Color TRANSPARENT_RED = new Color(1f, 0f, 0f, 0.4f);
	public final static Color TRANSPARENT_BLUE = new Color(0f, 0f, 1f, 0.4f);
	public final static Color TRANSPARENT_GREEN = new Color(0f, 1f, 0f, 0.4f);
	public final static Color TRANSPARENT_YELLOW = new Color(1f, 1f, 0f, 0.3f);
	

	/**
	 * The map of tokens. <position, color>
	 */
	private Map<Integer, Color> tokenMap;
	
	/**
	 * The map of players. <username, color>
	 */
	private Map<String, String> players;
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The user input.
	 */
	private MouseListener userInput;
	
	/**
	 * The game message.
	 */
	private JLabel message;
	
	/**
	 * The die roll button.
	 */
	private JButton rollDie;
	
	/**
	 * The board representation.
	 */
	private BoardModel board;
	
	/**
	 * The constructor for the start panel.
	 * @param swingUI is the swing user interface.
	 */
	public GamePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.tokenMap = new HashMap<Integer, Color>();
		this.players = new HashMap<String, String>();
		this.message = new JLabel("");
		this.rollDie = new JButton("Roll Die");
		this.board = new BoardModel();
		this.createTiles();
		this.createTokens();
		
		this.init();
	}

	/**
	 * Initializes the game panel.
	 */
	public void init() {
		JPanel panel = new JPanel();
		this.setLayout(new BorderLayout());
		//this.swingUI.addMouseListener(this.userInput);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.swingUI.requestFocusInWindow();
		this.setVisible(true);
		rollDie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (!swingUI.getUser().isSelectedTile()) {
					updateMessage("Please select a token in order to roll the die!");
					return;
				}
				//message.setText("Die was rolled by " + swingUI.getUser().getUsername());
				//swingUI.send("[" + swingUI.getUser().getUsername() +"] rolled die");
				updateMessage("");
				for (int i = 0; i < swingUI.getUser().getTokens().size(); i++) {
					if (swingUI.getUser().getTokens().get(i).equals(swingUI.getUser().getSelectedTile())) {
						swingUI.send("ROLLED " + i);
						break;
					}
				}
			}
		});
		panel.add(message);
		panel.add(rollDie);
		this.add(panel, BorderLayout.SOUTH);
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
	 * Creates a list of tiles.
	 */
	public void createTiles() {
		for (int i = 0; i < 28; i++) {
			// sets the location for the normal tiles && home tiles
			int tileSize = 25;
			Rectangle rectangle = new Rectangle(i * tileSize, tileSize, tileSize - 2, tileSize - 2);
			if (i == 0) {
				int count = 0;
				board.getMainZone().get(i).setTile(rectangle, Color.RED);
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					board.getRedHomeZone().get(count).setTile(homeRectangle, Color.RED);
					count++;
				}
			} else if (i == 7) {
				int count = 0;
				board.getMainZone().get(i).setTile(rectangle, Color.BLUE);
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					board.getBlueHomeZone().get(count).setTile(homeRectangle, Color.BLUE);
					count++;
				}
			} else if (i == 14) {
				int count = 0;
				board.getMainZone().get(i).setTile(rectangle, Color.YELLOW);
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					board.getYellowHomeZone().get(count).setTile(homeRectangle, Color.YELLOW);
					count++;
				}
			} else if (i == 21) {
				int count = 0;
				board.getMainZone().get(i).setTile(rectangle, Color.GREEN);
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					board.getGreenHomeZone().get(count).setTile(homeRectangle, Color.GREEN);
					count++;
				}
			} else {
				board.getMainZone().get(i).setTile(rectangle, Color.LIGHT_GRAY);
			}
			
			if (i == 6) {
				int count = 0;
				for (int j = 2; j < 6; j++) {
					Rectangle endRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					board.getBlueEndZone().get(count).setTile(endRectangle, Color.BLUE);
					count++;
				}
			} else if (i == 13) {
				int count = 0;
				for (int j = 2; j < 6; j++) {
					Rectangle endRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					board.getYellowEndZone().get(count).setTile(endRectangle, Color.YELLOW);
					count++;
				}
			} else if (i == 20) {
				int count = 0;
				for (int j = 2; j < 6; j++) {
					Rectangle endRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					board.getGreenEndZone().get(count).setTile(endRectangle, Color.GREEN);
					count++;
				}
			} else if (i == 27) {
				int count = 0;
				for (int j = 2; j < 6; j++) {
					Rectangle endRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					board.getRedEndZone().get(count).setTile(endRectangle, Color.RED);
					count++;
				}
			}
		}
	}
	
	/**
	 * Create a list of the tokens
	 */
	public void createTokens() {
		for (int i = 0; i < 28; i++) {
			// sets the location for the tokens
			int tileSize = 25;
			if (i == 0) {
				int count = 0;
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 3, tileSize - 3);
					board.getRedTokens().get(count).setTile(homeRectangle, Color.RED);
					count++;
				}
			} else if (i == 7) {
				int count = 0;
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 3, tileSize - 3);
					board.getBlueTokens().get(count).setTile(homeRectangle, Color.BLUE);
					count++;
				}
			} else if (i == 14) {
				int count = 0;
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 3, tileSize - 3);
					board.getYellowTokens().get(count).setTile(homeRectangle, Color.YELLOW);
					count++;
				}
			} else if (i == 21) {
				int count = 0;
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 3, tileSize - 3);
					board.getGreenTokens().get(count).setTile(homeRectangle, Color.GREEN);
					count++;
				}
			}
		}
	}
	
	/**
	 * Gets the position of a token from input coming from the server.
	 * @param color is the color.
	 * @param slotId is the slot Id
	 * @param pos is the position
	 * @return the position.
	 */
	public final int getPosition(Color color, int slotId, int pos) {
		switch(slotId) {
		case 0:
		case 1:
		case 2:
		}
		return -1;
	}
	
	public void updateToken(String username, int tokenID, int roll) {
		Tile token = swingUI.getUser().getTokens().get(tokenID);
		if (token.getZone() == Board.SLOT_HOME) {
			Tile newLoc = board.getMainZone().get(roll);
			token.setTile(newLoc.getShape(), Color.RED);
			token.setZone(Board.SLOT_MAIN);
			token.setIndex(roll);
		} else if (token.getZone() == Board.SLOT_MAIN) {
			Tile newLoc = board.getMainZone().get(token.getIndex() + roll);
			token.setTile(newLoc.getShape(), Color.RED);
			token.setIndex(token.getIndex() + roll);
		}
		this.repaint();
	}
	
	public void updateMessage(String message) {
		this.message.setText(message);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		
		// Draw mainZone tiles
		for (Tile tile : board.getMainZone()) {
			if (tile.getColor() == Color.RED)
				g2d.setColor(TRANSPARENT_RED);
			else if (tile.getColor() == Color.BLUE)
				g2d.setColor(TRANSPARENT_BLUE);
			else if (tile.getColor() == Color.YELLOW)
				g2d.setColor(TRANSPARENT_YELLOW);
			else if (tile.getColor() == Color.GREEN)
				g2d.setColor(TRANSPARENT_GREEN);
			else
				g2d.setColor(Color.LIGHT_GRAY);
			g2d.draw(tile.getShape());
		}
		
		// Draw homeZone tiles
		for (Tile tile : board.getRedHomeZone()) {
			g2d.setColor(TRANSPARENT_RED);
			g2d.draw(tile.getShape());
		}
		for (Tile tile : board.getBlueHomeZone()) {
			g2d.setColor(TRANSPARENT_BLUE);
			g2d.draw(tile.getShape());
		}
		for (Tile tile : board.getYellowHomeZone()) {
			g2d.setColor(TRANSPARENT_YELLOW);
			g2d.draw(tile.getShape());
		}
		for (Tile tile : board.getGreenHomeZone()) {
			g2d.setColor(TRANSPARENT_GREEN);
			g2d.draw(tile.getShape());
		}
		
		// Draw endZone tiles
		for (Tile tile : board.getRedEndZone()) {
			g2d.setColor(TRANSPARENT_RED);
			g2d.draw(tile.getShape());
		}
		for (Tile tile : board.getBlueEndZone()) {
			g2d.setColor(TRANSPARENT_BLUE);
			g2d.draw(tile.getShape());
		}
		for (Tile tile : board.getYellowEndZone()) {
			g2d.setColor(TRANSPARENT_YELLOW);
			g2d.draw(tile.getShape());
		}
		for (Tile tile : board.getGreenEndZone()) {
			g2d.setColor(TRANSPARENT_GREEN);
			g2d.draw(tile.getShape());
		}
		
		// Draw tokens
		for (Tile token : board.getRedTokens()) {
			g2d.setColor(token.getColor());
			if (swingUI.getUser().isSelectedTile(token))
				g2d.setColor(token.getColor());
			else {
				if (token.getColor() == Color.RED)
					g2d.setColor(TRANSPARENT_RED);
				else if (token.getColor() == Color.BLUE)
					g2d.setColor(TRANSPARENT_BLUE);
				else if (token.getColor() == Color.GREEN)
					g2d.setColor(TRANSPARENT_GREEN);
				else if (token.getColor() == Color.YELLOW)
					g2d.setColor(TRANSPARENT_YELLOW);
			}
			g2d.fill(token.getShape());
		}
		for (Tile token : board.getBlueTokens()) {
			g2d.setColor(token.getColor());
			if (swingUI.getUser().isSelectedTile(token))
				g2d.setColor(token.getColor());
			else {
				if (token.getColor() == Color.RED)
					g2d.setColor(TRANSPARENT_RED);
				else if (token.getColor() == Color.BLUE)
					g2d.setColor(TRANSPARENT_BLUE);
				else if (token.getColor() == Color.GREEN)
					g2d.setColor(TRANSPARENT_GREEN);
				else if (token.getColor() == Color.YELLOW)
					g2d.setColor(TRANSPARENT_YELLOW);
			}
			g2d.fill(token.getShape());
		}
		for (Tile token : board.getYellowTokens()) {
			g2d.setColor(token.getColor());
			if (swingUI.getUser().isSelectedTile(token))
				g2d.setColor(token.getColor());
			else {
				if (token.getColor() == Color.RED)
					g2d.setColor(TRANSPARENT_RED);
				else if (token.getColor() == Color.BLUE)
					g2d.setColor(TRANSPARENT_BLUE);
				else if (token.getColor() == Color.GREEN)
					g2d.setColor(TRANSPARENT_GREEN);
				else if (token.getColor() == Color.YELLOW)
					g2d.setColor(TRANSPARENT_YELLOW);
			}
			g2d.fill(token.getShape());
		}
		for (Tile token : board.getGreenTokens()) {
			g2d.setColor(token.getColor());
			if (swingUI.getUser().isSelectedTile(token))
				g2d.setColor(token.getColor());
			else {
				if (token.getColor() == Color.RED)
					g2d.setColor(TRANSPARENT_RED);
				else if (token.getColor() == Color.BLUE)
					g2d.setColor(TRANSPARENT_BLUE);
				else if (token.getColor() == Color.GREEN)
					g2d.setColor(TRANSPARENT_GREEN);
				else if (token.getColor() == Color.YELLOW)
					g2d.setColor(TRANSPARENT_YELLOW);
			}
			g2d.fill(token.getShape());
		}
		
		g2d.dispose();
	}
	
	/**
	 * @return the players map.
	 */
	public Map<String, String> getPlayers() {
		return players;
	}

	/**
	 * @return the token map.
	 */
	public Map<Integer, Color> getTokenMap() {
		return tokenMap;
	}
	
	public void setupPanel(Color color) {
		swingUI.getUser().setColor(color);
		
		if (color == Color.RED) {
			swingUI.getUser().setTokens(board.getRedTokens());
		} else if (color == Color.BLUE) {
			swingUI.getUser().setTokens(board.getBlueTokens());
		} else if (color == Color.YELLOW) {
			swingUI.getUser().setTokens(board.getYellowTokens());
		} else if (color == Color.GREEN) {
			swingUI.getUser().setTokens(board.getRedTokens());
		}
		
		this.userInput = new UserInput(swingUI);
		this.addMouseListener(this.userInput);
	}
	
}
