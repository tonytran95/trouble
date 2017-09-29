package game.trouble.client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

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
	private List<Tile> tiles;
	
	/**
	 * The constructor for the user input class.
	 * @param swingUI is the swing user interface.
	 */
	public UserInput(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.tiles = new ArrayList<>(swingUI.getUser().getTiles());
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
		int count = 0;
		for (Tile tile : tiles) {
			count++;
			if (tile.getShape().contains(me.getPoint())) {
				if (swingUI.getUser().isSelectedTile(tile)) {
					swingUI.getUser().deselectTile();
					swingUI.send("[" + swingUI.getUser().getUsername() +"] deselect: " + count);					
				} else {
					swingUI.getUser().selectTile(tile);
					swingUI.send("[" + swingUI.getUser().getUsername() +"] select: " + count);
				}
				swingUI.getCurrentPanel().repaint();
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

}
