package troublegame.client.panels;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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

public class LobbyPanel extends JPanel {
	
	private static final long serialVersionUID = 3647535279313225581L;

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
		
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_2.png");
		Image newimg1 = image1.getScaledInstance(89, 23, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(89, 23, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		this.setLayout(null);
		JList<String> list = new JList<String>();
		list.setBounds(746, 105, 100, 349);
		this.add(list);
		
		JButton btnProfile = new JButton("My profile");
		btnProfile.setBounds(657, 11, 89, 23);
		this.add(btnProfile);
		btnProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.setInterface(Interface.USER_PROFILE);
			}
		});
		btnProfile.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				btnProfile.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnProfile.setIcon(imgIcon2);
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		btnProfile.setIcon(imgIcon1);
		btnProfile.setHorizontalTextPosition(SwingConstants.CENTER);
		btnProfile.setBorderPainted(false);
		
		
		JButton btnSignOff = new JButton("Sign off");
		btnSignOff.setBounds(757, 11, 89, 23);
		this.add(btnSignOff);
		btnSignOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to exit the game?", "Exit Trouble Message",
						JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION)
						swingUI.send(CommunicationHandler.LOGOUT_REQUEST);
			}
		});
		btnSignOff.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				btnSignOff.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnSignOff.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		btnSignOff.setIcon(imgIcon1);
		btnSignOff.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSignOff.setBorderPainted(false);
		

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
		btnCreate.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				btnCreate.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnCreate.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		btnCreate.setIcon(imgIcon1);
		btnCreate.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCreate.setBorderPainted(false);
		
		JButton btnJoin = new JButton("Join");
		btnJoin.setBounds(543, 76, 89, 23);
		this.add(btnJoin);
		btnJoin.setEnabled(false);
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.send(CommunicationHandler.GAME_ROOM_JOIN + " " + gameRoom.getSelectedValue());
			}
		});
		btnJoin.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				btnJoin.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (btnJoin.isEnabled())
					btnJoin.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		btnJoin.setDisabledIcon(imgIcon1);
		btnJoin.setIcon(imgIcon1);
		btnJoin.setHorizontalTextPosition(SwingConstants.CENTER);
		btnJoin.setBorderPainted(false);
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
