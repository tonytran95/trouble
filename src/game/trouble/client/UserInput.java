package game.trouble.client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

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
	private List<Tile> tiles;
	
	/**
	 * The constructor for the user input class.
	 * @param swingUI is the swing user interface.
	 */
	public UserInput(SwingUI swingUI, List<Tile> tiles) {
		this.swingUI = swingUI;
		this.tiles = new ArrayList<>(tiles);
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
				swingUI.send("MOVE TOKEN TO [" + count + "]");
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
