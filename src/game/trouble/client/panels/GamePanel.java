package game.trouble.client.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import game.trouble.client.SwingUI;
import game.trouble.client.UserInput;
import game.trouble.model.board.Tile;

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
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The user input.
	 */
	private MouseListener userInput;
	
	/**
	 * The list of tiles.
	 */
	private List<Tile> tiles;
	
	/**
	 * The list of tokens.
	 */
	private List<Tile> tokens;
	
	/**
	 * The constructor for the start panel.
	 * @param swingUI is the swing user interface.
	 */
	public GamePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.tiles = new ArrayList<Tile>();
		this.tokens = new ArrayList<Tile>();
		this.createTiles();
		this.createTokens();
		this.userInput = new UserInput(swingUI, tiles);
		this.init();
	}

	/**
	 * Initializes the game panel.
	 */
	public void init() {
		this.addMouseListener(this.userInput);
		//this.swingUI.addMouseListener(this.userInput);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.swingUI.requestFocusInWindow();
		this.setVisible(true);
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
		if (!tiles.isEmpty())
			return;
		for (int i = 0; i < 28; i++) {
			// sets the location for the normal tiles && home tiles
			int tileSize = 25;
			Rectangle rectangle = new Rectangle(i * tileSize, tileSize, tileSize - 2, tileSize - 2);
			if (i == 0) {
				this.tiles.add(new Tile(Color.RED, rectangle));
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					this.tiles.add(new Tile(Color.LIGHT_GRAY, homeRectangle));
				}
			} else if (i == 7) {
				this.tiles.add(new Tile(Color.BLUE, rectangle));
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					this.tiles.add(new Tile(Color.LIGHT_GRAY, homeRectangle));
				}
			} else if (i == 14) {
				this.tiles.add(new Tile(Color.YELLOW, rectangle));
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					this.tiles.add(new Tile(Color.LIGHT_GRAY, homeRectangle));
				}
			} else if (i == 21) {
				this.tiles.add(new Tile(Color.GREEN, rectangle));
				for (int j = i; j < i + 4; j++) {
					Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
					this.tiles.add(new Tile(Color.LIGHT_GRAY, homeRectangle));
				}
			} else {
				this.tiles.add(new Tile(Color.LIGHT_GRAY, rectangle));
			}
			
			// sets the location for the spawn tiles
			if (i == 6) {
				for (int j = 2; j < 6; j++) {
					Rectangle spawnRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					this.tiles.add(new Tile(Color.BLUE, spawnRectangle));
				}
			} else if (i == 13) {
				for (int j = 2; j < 6; j++) {
					Rectangle spawnRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					this.tiles.add(new Tile(Color.YELLOW, spawnRectangle));
				}
			} else if (i == 20) {
				for (int j = 2; j < 6; j++) {
					Rectangle spawnRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					this.tiles.add(new Tile(Color.GREEN, spawnRectangle));
				}
			} else if (i == 27) {
				for (int j = 2; j < 6; j++) {
					Rectangle spawnRectangle = new Rectangle(i * tileSize, j * tileSize, tileSize - 2, tileSize - 2);
					this.tiles.add(new Tile(Color.RED, spawnRectangle));
				}
			}
		}
	}
	
	/**
	 * Create a list of the tokens
	 */
	public void createTokens() {
		if (!tokens.isEmpty()) {
			return;
		} else {
			for (int i = 0; i < 28; i++) {
				// sets the location for the tokens
				int tileSize = 25;
				if (i == 0) {
					for (int j = i; j < i + 4; j++) {
						Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
						this.tokens.add(new Tile(Color.RED, homeRectangle));
					}
				} else if (i == 7) {
					for (int j = i; j < i + 4; j++) {
						Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
						this.tokens.add(new Tile(Color.BLUE, homeRectangle));
					}
				} else if (i == 14) {
					for (int j = i; j < i + 4; j++) {
						Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
						this.tokens.add(new Tile(Color.YELLOW, homeRectangle));
					}
				} else if (i == 21) {
					for (int j = i; j < i + 4; j++) {
						Rectangle homeRectangle = new Rectangle(j * tileSize, tileSize - tileSize, tileSize - 2, tileSize - 2);
						this.tokens.add(new Tile(Color.GREEN, homeRectangle));
					}
				}
			}
		}
	}
	
	public void updateToken() {
		// todo
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		for(Tile tile : tiles) {
			g2d.setColor(tile.getColor());
			g2d.fill(tile.getShape());
			g2d.draw(tile.getShape());
		}
		for (Tile token : tokens) {
			g2d.setColor(token.getColor());
			g2d.fill(token.getShape());
			g2d.draw(token.getShape());
		}
		g2d.dispose();
		this.tiles.clear();
		this.tokens.clear();
	}
	

	
}
