package game.trouble.client.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import game.trouble.client.ClientState;
import game.trouble.client.SwingUI;
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
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The user input.
	 */
	private KeyListener userInput;
	
	/**
	 * The list of tiles.
	 */
	private List<Tile> tiles;
	
	/**
	 * The constructor for the start panel.
	 * @param swingUI is the swing user interface.
	 */
	public GamePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.userInput = new UserInput(swingUI);
		this.tiles = new ArrayList<Tile>();
		swingUI.setClientState(ClientState.IN_GAME);
		this.init();
	}

	/**
	 * Initializes the game panel.
	 */
	public void init() {
		this.addKeyListener(this.userInput);
		this.swingUI.addKeyListener(this.userInput);
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
			// sets the location of the tiles
			int tileSize = 25;
			Rectangle rectangle = new Rectangle(i * tileSize, tileSize, tileSize - 2, tileSize - 2);
			if (i == 0) this.tiles.add(new Tile(Color.RED, rectangle));
			else if (i == 7) this.tiles.add(new Tile(Color.BLUE, rectangle));
			else if (i == 14) this.tiles.add(new Tile(Color.YELLOW, rectangle));
			else if (i == 21) this.tiles.add(new Tile(Color.GREEN, rectangle));
			else this.tiles.add(new Tile(Color.LIGHT_GRAY, rectangle));
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.createTiles();
		Graphics2D g2d = (Graphics2D) g.create();
		for(Tile tile : tiles) {
			g2d.setColor(tile.color);
			g2d.fill(tile.shape);
			g2d.draw(tile.shape);
		}
		g2d.dispose();
		this.tiles.clear();
	}
	
	/**
	 * The Tile class contains data of a tile such as the color and the the shape.
	 */
	private class Tile {
		private Color color;
		private Shape shape;
		
		public Tile(Color color, Shape shape) {
			this.color = color;
			this.shape = shape;
		}
	}
	
}
