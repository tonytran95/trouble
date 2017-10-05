package game.trouble.client;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import game.trouble.client.panels.GamePanel;
import game.trouble.model.board.Tile;

/**
 * 
 * The UserInput class handles all the inputs from the player and sends
 * the information to the game.
 * 
 * @author Jeffrey Ung
 *
 */
public class UserInput implements MouseListener {

	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;

	/**
	 * A list of tiles.
	 */
	private List<Tile> tokens;
	
	/**
	 * The constructor for the user input class.
	 * @param swingUI is the swing user interface.
	 */
	public UserInput(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.tokens = new ArrayList<>(swingUI.getUser().getTokens());
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent me) {
		GamePanel gamePanel = (GamePanel) swingUI.getCurrentPanel();
		for (Tile tile : tokens) {
			if (tile.getShape().contains(me.getPoint())) {
				try {
					if (swingUI.getUser().isSelectedTile(tile)) {
						swingUI.getUser().deselectTile();
					} else if (gamePanel.getPlayers().get(
							swingUI.getUser().getUsername()).equals(getColor(tile.getColor()))) {
						swingUI.getUser().selectTile(tile);
					}
					swingUI.getCurrentPanel().repaint();
				} catch (Exception e) {	
				}
			}
		}
	}


	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @return the swing user interace.
	 */
	public SwingUI getSwingUI() {
		return swingUI;
	}
	
	/**
	 * @param colour is the string color.
	 * @return the string corresponding to the colour.
	 */
	public String getColor(Color color) {
		if (color == Color.RED) {
			return "red";
		} else if (color == Color.BLUE) {
			return "blue";
		} else if (color == Color.YELLOW) {
			return "yellow";
		} else if (color == Color.GREEN) {
			return "green";
		}
		return "empty";
	}


}
