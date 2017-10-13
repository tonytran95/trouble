package troublegame.client.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import troublegame.client.SwingUI;

/**
 * 
 * The LobbyPanel class handles the display of the Lobby screen of the client.
 * 
 * @author Jeffrey Ung and Tony Tran
 *
 */
@SuppressWarnings("serial")
public class LobbyPanel extends JPanel {
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	private JButton createRoom;
	private JButton joinRoom;
	
	/**
	 * The constructor for the Lobby panel.
	 * @param swingUI is the swing user interface.
	 */
	public LobbyPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.init();
	}

	/**
	 * Initializes the Lobby panel.
	 */
	public void init() {
		swingUI.getGameRooms().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!joinRoom.isEnabled()) joinRoom.setEnabled(true);
			}
		});
		
		createRoom = new JButton("Create Room");
		createRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send("NEW_GAMEROOM");
			}
		});
		
		joinRoom = new JButton("Join Room");
		joinRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send("[JOIN_GAMEROOM] " + swingUI.getGameRooms().getSelectedValue());
			}
		});
		joinRoom.setEnabled(false);
		
		this.add(swingUI.getGameRooms());
		this.add(createRoom);
		this.add(joinRoom);
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

}
