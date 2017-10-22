package troublegame.client.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
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
	
	private InfoPanel infoPanel;
	
	/**
	 * The map of players <username, color>
	 */
	private Map<String, String> players;
	private Image backgroundImage;
	
	/**
	 * The constructor for the start panel.
	 * @param swingUI is the swing user interface.
	 */
	public GamePanel(SwingUI swingUI) {
		
		this.swingUI = swingUI;
		this.boardPanel = new BoardPanel(swingUI);
		this.chatPanel = new GameChatPanel(swingUI);
		this.infoPanel = new InfoPanel(swingUI);
		this.players = new HashMap<String, String>();
		
		/**
		 * set background image
		 */
		try {
			backgroundImage = ImageIO.read(new File("./data/img/background2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		boardPanel.setOpaque(false);
		chatPanel.setBounds(589, 263, 422, 300);
		chatPanel.setOpaque(false);
		infoPanel.setBounds(595, 13, 410, 237);
		infoPanel.setOpaque(false);
		this.add(boardPanel);
		this.add(chatPanel);
		this.add(infoPanel);
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
	
	/**
	 * @return The info panel associated with this game panel
	 */
	public InfoPanel getInfoPanel() {
		return this.infoPanel;
	}
	
	/**
	 * @return the players map.
	 */
	public Map<String, String> getPlayers() {
		return players;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, this);
	}
	
}