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
import game.trouble.client.Tile;
import game.trouble.client.UserInput;

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
	 * The map of players. <username/color>
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
	 * The constructor for the start panel.
	 * @param swingUI is the swing user interface.
	 */
	public GamePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.createTiles();
		this.createTokens();
		this.players = new HashMap<String, String>();
		this.userInput = new UserInput(swingUI);
		this.message = new JLabel("");
		this.rollDie = new JButton("Roll Die");
		this.init();
	}

	/**
	 * Initializes the game panel.
	 */
	public void init() {
		JPanel panel = new JPanel();
		this.setLayout(new BorderLayout());
		this.addMouseListener(this.userInput);
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
		if (!swingUI.getUser().getTiles().isEmpty())
			return;
		for (int i = 0; i < 28; i++) {
			// sets the location for the normal tiles && home tiles
			int tileSize = 25;
			Rectangle rectangle = new Rectangle(i * tileSize, tileSize, tileSize - 2, tileSize - 2);
			if (i == 0) {
				swingUI.getUser().getTiles().add(new Tile(Color.RED, rectangle));
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					swingUI.getUser().getTiles().add(new Tile(Color.LIGHT_GRAY, homeRectangle));
				}
			} else if (i == 7) {
				swingUI.getUser().getTiles().add(new Tile(Color.BLUE, rectangle));
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					swingUI.getUser().getTiles().add(new Tile(Color.LIGHT_GRAY, homeRectangle));
				}
			} else if (i == 14) {
				swingUI.getUser().getTiles().add(new Tile(Color.YELLOW, rectangle));
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					swingUI.getUser().getTiles().add(new Tile(Color.LIGHT_GRAY, homeRectangle));
				}
			} else if (i == 21) {
				swingUI.getUser().getTiles().add(new Tile(Color.GREEN, rectangle));
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					swingUI.getUser().getTiles().add(new Tile(Color.LIGHT_GRAY, homeRectangle));
				}
			} else {
				swingUI.getUser().getTiles().add(new Tile(Color.LIGHT_GRAY, rectangle));
			}
			
			// sets the location for the spawn tiles
			if (i == 6) {
				for (int j = 2; j < 6; j++) {
					Rectangle spawnRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					swingUI.getUser().getTiles().add(new Tile(Color.BLUE, spawnRectangle));
				}
			} else if (i == 13) {
				for (int j = 2; j < 6; j++) {
					Rectangle spawnRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					swingUI.getUser().getTiles().add(new Tile(Color.YELLOW, spawnRectangle));
				}
			} else if (i == 20) {
				for (int j = 2; j < 6; j++) {
					Rectangle spawnRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					swingUI.getUser().getTiles().add(new Tile(Color.GREEN, spawnRectangle));
				}
			} else if (i == 27) {
				for (int j = 2; j < 6; j++) {
					Rectangle spawnRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					swingUI.getUser().getTiles().add(new Tile(Color.RED, spawnRectangle));
				}
			}
		}
	}
	
	/**
	 * Create a list of the tokens
	 */
	public void createTokens() {
		if (!swingUI.getUser().getTokens().isEmpty())
			return;
		for (int i = 0; i < 28; i++) {
			// sets the location for the tokens
			int tileSize = 25;
			if (i == 0) {
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 3, tileSize - 3);
					swingUI.getUser().getTokens().add(new Tile(Color.RED, homeRectangle));
				}
			} else if (i == 7) {
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 3, tileSize - 3);
					swingUI.getUser().getTokens().add(new Tile(Color.BLUE, homeRectangle));
				}
			} else if (i == 14) {
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 3, tileSize - 3);
					swingUI.getUser().getTokens().add(new Tile(Color.YELLOW, homeRectangle));
				}
			} else if (i == 21) {
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 3, tileSize - 3);
					swingUI.getUser().getTokens().add(new Tile(Color.GREEN, homeRectangle));
				}
			}
		}
	}
	
	public void updateToken() {
		// todo
	}
	
	public void updateMessage(String message) {
		this.message.setText(message);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		for(Tile tile : swingUI.getUser().getTiles()) {
			if (tile.getColor() == Color.RED)
				g2d.setColor(TRANSPARENT_RED);
			else if (tile.getColor() == Color.BLUE)
				g2d.setColor(TRANSPARENT_BLUE);
			else if (tile.getColor() == Color.GREEN)
				g2d.setColor(TRANSPARENT_GREEN);
			else if (tile.getColor() == Color.YELLOW)
				g2d.setColor(TRANSPARENT_YELLOW);
			g2d.draw(tile.getShape());
		}
		for (Tile token : swingUI.getUser().getTokens()) {
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
			g2d.draw(token.getShape());
		}
		g2d.dispose();
		//swingUI.getUser().getTiles().clear();
		//swingUI.getUser().getTokens().clear();
	}
	
	/**
	 * @return the players map.
	 */
	public Map<String, String> getPlayers() {
		return players;
	}
	
}
