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
	private JPasswordField passwordField;
	private JTextField usernameTextField;
	
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
		
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1x.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_3.png");
		Image newimg1 = image1.getScaledInstance(252, 25, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(252, 25, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		User me = swingUI.getUser();
		String username = me.getUsername();
		int gamesPlayed = me.getGamesPlayed();
		int gamesWon = me.getGamesWon();
		float winRate = (gamesWon * 100.0f) / gamesPlayed;
		
		this.swingUI = swingUI;
		setLayout(null);
		
		JLabel lblUpdateParticulars = new JLabel("Update Particulars");
		lblUpdateParticulars.setBounds(126, 35, 267, 16);
		add(lblUpdateParticulars);
		
		JLabel lblUsername = new JLabel("Display Name");
		lblUsername.setBounds(27, 67, 77, 16);
		add(lblUsername);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(128, 64, 252, 22);
		usernameTextField.setText(username);
		add(usernameTextField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(49, 102, 55, 16);
		add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(128, 99, 252, 22);
		add(passwordField);
		
		JButton updateButton = new JButton("Update Display Name");
		updateButton.setBounds(128, 134, 252, 25);
		add(updateButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 172, 459, 2);
		add(separator);
		
		JLabel label_3 = new JLabel("Game Statistics");
		label_3.setBounds(126, 184, 267, 16);
		add(label_3);
		
		JLabel label_2 = new JLabel("Games Played");
		label_2.setBounds(24, 213, 80, 16);
		add(label_2);
		
		JLabel gamesPlayedDisplay = new JLabel(Integer.toString(gamesPlayed));
		gamesPlayedDisplay.setBounds(126, 213, 198, 16);
		add(gamesPlayedDisplay);
		
		JLabel label_1 = new JLabel("Games Won");
		label_1.setBounds(35, 242, 69, 16);
		add(label_1);
		
		JLabel gamesWonDisplay = new JLabel(Integer.toString(gamesWon));
		gamesWonDisplay.setBounds(128, 242, 135, 16);
		add(gamesWonDisplay);
		
		JLabel lblWinRate = new JLabel("Win Rate");
		lblWinRate.setBounds(52, 270, 52, 16);
		add(lblWinRate);
		
		JLabel winRateDisplay = new JLabel(String.format ("%.1f%%", winRate));
		winRateDisplay.setBounds(125, 270, 161, 16);
		add(winRateDisplay);
		
		JButton backButton = new JButton("Back to Lobby");
		backButton.setBounds(128, 299, 252, 25);
		add(backButton);
		
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String username = usernameTextField.getText();
				String password = String.valueOf(passwordField.getPassword());

				swingUI.playButtonSound();
				if (username.contains(" ") || password.contains(" ")) {
					JOptionPane.showMessageDialog(null, "Space is not allowed in Display name or Password");
					return;
				}
				if(password.length() == 0 || username.length() == 0) {
					JOptionPane.showMessageDialog(null, "Display name or password cannot be blank");
					return;
				}
				swingUI.send(CommunicationHandler.UPDATE_DISPLAYNAME + " " + username + " " + password);
				passwordField.setText("");
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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, this);
	}
}
