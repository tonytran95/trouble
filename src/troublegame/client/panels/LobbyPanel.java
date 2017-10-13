package troublegame.client.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

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
	
	/**
	 * The Default List Model for JList of Users
	 */
	private DefaultListModel<String> gameRoomModel = new DefaultListModel<String>();
	
	private JList<String> gameRooms;
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
		gameRoomModel = new DefaultListModel<String>();
		gameRooms = new JList<String>(gameRoomModel);
		gameRooms.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!joinRoom.isEnabled()) joinRoom.setEnabled(true);
			}
		});
		
		createRoom = new JButton("Create Room");
		createRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_NEW);
			}
		});
		
		joinRoom = new JButton("Join Room");
		joinRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_JOIN + " " + gameRooms.getSelectedValue());
			}
		});
		joinRoom.setEnabled(false);
		
		this.add(gameRooms);
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
	
	public void addGameRoom(String username) {
		gameRoomModel.addElement(username);
	}

}
