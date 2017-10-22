package troublegame.client.panels;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import troublegame.client.Interface;
import troublegame.client.SwingUI;
import troublegame.client.model.User;
import troublegame.communication.CommunicationHandler;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPasswordField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;

public class ProfilePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1959684201644950741L;

	/**
	 * The background image.
	 */
	private Image backgroundImage;
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	private JTextField usernameTextField;
	private JPasswordField newpassword;
	
	private DefaultListModel<String> friendlistModel;
	/**
	 * Create the panel.
	 */
	public ProfilePanel(SwingUI swingUI) {
		/**
		 * set background image
		 */
		try {
			backgroundImage = ImageIO.read(new File("./data/img/background4.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		backgroundImage = backgroundImage.getScaledInstance(725, 500, Image.SCALE_SMOOTH);
		friendlistModel = new DefaultListModel<String>();
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1x.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_3.png");
		Image newimg1 = image1.getScaledInstance(252, 25, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(252, 25, Image.SCALE_SMOOTH);
		Image newimg3 = image1.getScaledInstance(103, 25, Image.SCALE_SMOOTH);
		Image newimg4 = image2.getScaledInstance(103, 25, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		ImageIcon imgIcon3 = new ImageIcon(newimg3);
		ImageIcon imgIcon4 = new ImageIcon(newimg4);
		
		User me = swingUI.getUser();
		String username = me.getUsername();
		int gamesPlayed = me.getGamesPlayed();
		int gamesWon = me.getGamesWon();
		float winRate = (gamesWon * 100.0f) / gamesPlayed;
		
		this.swingUI = swingUI;
		setLayout(null);
		
		JLabel lblUpdateParticulars = new JLabel("Change Display Name");
		lblUpdateParticulars.setBounds(128, 207, 267, 16);
		add(lblUpdateParticulars);
		
		JLabel lblUsername = new JLabel("Display Name");
		lblUsername.setBounds(32, 239, 77, 16);
		add(lblUsername);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(128, 236, 252, 22);
		usernameTextField.setText(username);
		add(usernameTextField);
		
		JButton updateButton = new JButton("Update Display Name");
		updateButton.setBounds(128, 271, 252, 25);
		add(updateButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 185, 408, 2);
		add(separator);
		
		JLabel label_3 = new JLabel("Game Statistics");
		label_3.setBounds(128, 13, 267, 16);
		add(label_3);
		
		JLabel label_2 = new JLabel("Games Played");
		label_2.setBounds(32, 38, 80, 16);
		add(label_2);
		
		JLabel gamesPlayedDisplay = new JLabel(Integer.toString(gamesPlayed));
		gamesPlayedDisplay.setBounds(121, 38, 198, 16);
		add(gamesPlayedDisplay);
		
		JLabel label_1 = new JLabel("Games Won");
		label_1.setBounds(43, 67, 69, 16);
		add(label_1);
		
		JLabel gamesWonDisplay = new JLabel(Integer.toString(gamesWon));
		gamesWonDisplay.setBounds(121, 67, 135, 16);
		add(gamesWonDisplay);
		
		JLabel lblWinRate = new JLabel("Win Rate");
		lblWinRate.setBounds(60, 96, 52, 16);
		add(lblWinRate);
		
		JLabel winRateDisplay = new JLabel(String.format ("%.1f%%", winRate));
		winRateDisplay.setBounds(121, 96, 161, 16);
		add(winRateDisplay);
		
		JButton backButton = new JButton("Back to Lobby");
		backButton.setBounds(128, 134, 252, 25);
		add(backButton);
		
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.playButtonSound();
				String username = usernameTextField.getText();
				if (username.contains(" ")) {
					JOptionPane.showMessageDialog(null, "Space is not allowed in Display name!");
					return;
				} else if (username.length() == 0) {
					JOptionPane.showMessageDialog(null, "Display name cannot be blank");
					return;
				}
				String[] options = new String[]{"OK", "Cancel"};
				JPanel passwordPanel = new JPanel();
				JLabel label = new JLabel("Enter your password");
				JPasswordField passwordField = new JPasswordField(10);
				passwordPanel.add(label);
				passwordPanel.add(passwordField);
				int option = JOptionPane.showOptionDialog(null, passwordPanel, "Password Required",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
				if(option == 0) {
					swingUI.playButtonSound();
					String password = String.valueOf(passwordField.getPassword());
					
					if (password.contains(" ")) {
						JOptionPane.showMessageDialog(null, "Space is not allowed in Password");
						return;
					}else if (password.length() == 0) {
						JOptionPane.showMessageDialog(null, "Password cannot be blank");
						return;
					}
					swingUI.send(CommunicationHandler.UPDATE_DISPLAYNAME + " " + username + " " + password);
				}
				
				
				
			}
		});
		updateButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				updateButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				updateButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		updateButton.setIcon(imgIcon1);
		updateButton.setHorizontalTextPosition(SwingConstants.CENTER);
		updateButton.setBorderPainted(false);
		
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.playButtonSound();
				swingUI.setInterface(Interface.LOBBY);
			}
		});
		
		backButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				backButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				backButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		backButton.setIcon(imgIcon1);
		backButton.setHorizontalTextPosition(SwingConstants.CENTER);
		backButton.setBorderPainted(false);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(-19, 309, 427, 18);
		add(separator_1);
		
		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setBounds(128, 340, 267, 16);
		add(lblChangePassword);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setBounds(25, 372, 84, 16);
		add(lblNewPassword);
		
		newpassword = new JPasswordField();
		newpassword.setBounds(128, 369, 252, 22);
		add(newpassword);
		
		JButton updatePassword = new JButton("Change Password");
		updatePassword.setHorizontalTextPosition(SwingConstants.CENTER);
		updatePassword.setBorderPainted(false);
		updatePassword.setBounds(128, 404, 252, 25);
		add(updatePassword);
		
		updatePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.playButtonSound();
				String newpass = String.valueOf(newpassword.getPassword());
				if (newpass.contains(" ")) {
					JOptionPane.showMessageDialog(null, "Space is not allowed in passwords");
					return;
				} else if (newpass.length() == 0) {
					JOptionPane.showMessageDialog(null, "Password cannot be blank");
					return;
				}
				String[] options = new String[]{"OK", "Cancel"};
				JPanel passwordPanel = new JPanel();
				JLabel label = new JLabel("Enter your current password");
				JPasswordField passwordField = new JPasswordField(10);
				passwordPanel.add(label);
				passwordPanel.add(passwordField);
				int option = JOptionPane.showOptionDialog(null, passwordPanel, "Password Required",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
				if(option == 0) {
					swingUI.playButtonSound();
					String password = String.valueOf(passwordField.getPassword());
					
					if (password.contains(" ")) {
						JOptionPane.showMessageDialog(null, "Space is not allowed in Password");
						return;
					}else if (password.length() == 0) {
						JOptionPane.showMessageDialog(null, "Password cannot be blank");
						return;
					}
					swingUI.send(CommunicationHandler.CHANGE_PASSWORD + " " + newpass + " " + password);
				}
				
				
				
			}
		});
		updatePassword.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				updatePassword.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				updatePassword.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		updatePassword.setIcon(imgIcon1);
		updatePassword.setHorizontalTextPosition(SwingConstants.CENTER);
		updatePassword.setBorderPainted(false);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(407, 0, 2, 465);
		add(separator_2);
		
		JList<String> friendlist = new JList<String>(friendlistModel);
		friendlist.setBounds(437, 37, 260, 392);
		add(friendlist);
		
		JLabel lblFriendList = new JLabel("Friend List");
		lblFriendList.setLabelFor(friendlist);
		lblFriendList.setBounds(437, 13, 103, 16);
		add(lblFriendList);
		
		JButton unfriendButton = new JButton("Unfriend");
		unfriendButton.setBounds(594, 9, 103, 25);
		add(unfriendButton);
		unfriendButton.setVisible(false);
		unfriendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.send(CommunicationHandler.UNFRIEND + friendlist.getSelectedValue());
				unfriendButton.setVisible(false);
			}
		});
		unfriendButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				unfriendButton.setIcon(imgIcon3);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				unfriendButton.setIcon(imgIcon4);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		unfriendButton.setIcon(imgIcon3);
		unfriendButton.setHorizontalTextPosition(SwingConstants.CENTER);
		unfriendButton.setBorderPainted(false);
		
		friendlist.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!unfriendButton.isVisible()) unfriendButton.setVisible(true);
			}
		});
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
	
	public void displayFriendList(String[] friends) {
		friendlistModel.clear();
		for (String friend: friends) {
			friendlistModel.addElement(friend);
		}
	}
	
	public void removeFriend(String friend) {
		friendlistModel.removeElement(friend);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		// Draw the background image
		g.drawImage(backgroundImage, 0, 0, this);
	}
}
