package troublegame.client.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import troublegame.client.Interface;
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
	private DefaultListModel<String> gameRoomModel;
	
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
		this.setLayout(null);
		JList<String> list = new JList<String>();
		list.setBounds(631+ 115, 105, 100, 349);
		this.add(list);
		
		JButton btnProfile = new JButton("My profile");
		btnProfile.setBounds(642+ 115, 11, 89, 23);
		this.add(btnProfile);
		btnProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.setInterface(Interface.USER_PROFILE);
			}
		});

		gameRoomModel = new DefaultListModel<String>();

		JList<String> gameRoom = new JList<String>(gameRoomModel);
		gameRoom.setBounds(150, 105, 482, 349);
		this.add(gameRoom);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(444, 76, 89, 23);
		this.add(btnCreate);
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_NEW);
			}
		});
		
		JButton btnJoin = new JButton("Join");
		btnJoin.setBounds(543, 76, 89, 23);
		this.add(btnJoin);
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_JOIN + " " + gameRoom.getSelectedValue());
			}
		});
		btnJoin.setEnabled(false);
		gameRoom.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!btnJoin.isEnabled()) btnJoin.setEnabled(true);
			}
		});
		
		JLabel lblMyFriends = new JLabel("My Friends");
		lblMyFriends.setBounds(784, 80, 62, 14);
		this.add(lblMyFriends);
		
		JLabel lblRooms = new JLabel("Rooms");
		lblRooms.setBounds(161, 80, 46, 14);
		this.add(lblRooms);
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
	
	public void removeGameRoom(String gameRoomName) {
		gameRoomModel.removeElement(gameRoomName);
	}
	
	public void clearGameRooms() {
		gameRoomModel.clear();
	}

}
