package troublegame.client;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import troublegame.client.model.Slot;
import troublegame.client.panels.GamePanel;

/**
 * 
 * The {@link UserInput} class handles all the inputs from the player and sends
 * the information to the game.
 * 
 * @author Jeffrey Ung and Tony Tran
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
	private List<Slot> tokens;
	
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
		for (Slot slot : tokens) {
			if (slot.getShape().contains(me.getPoint())) {
				try {
					if (swingUI.getUser().isSelectedSlot(slot)) {
						swingUI.getUser().deselectSlot();
					} else if (gamePanel.getBoardPanel().getPlayers().get(
							swingUI.getUser().getUsername()).equals(getColor(slot.getColor()))) {
						swingUI.getUser().selectSlot(slot);
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
