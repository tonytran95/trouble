package troublegame.client.panels;

import javax.swing.JPanel;
import javax.swing.JTextField;

import troublegame.client.Interface;
import troublegame.client.SwingUI;
import troublegame.client.model.User;
import troublegame.communication.CommunicationHandler;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;

public class ProfilePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1959684201644950741L;

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
		this.swingUI = swingUI;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{73, 31, 248, 0};
		gridBagLayout.rowHeights = new int[]{16, 22, 22, 22, 25, 2, 16, 16, 16, 16, 25, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblUpdateParticulars = new JLabel("Update Particulars");
		GridBagConstraints gbc_lblUpdateParticulars = new GridBagConstraints();
		gbc_lblUpdateParticulars.anchor = GridBagConstraints.NORTH;
		gbc_lblUpdateParticulars.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblUpdateParticulars.insets = new Insets(0, 0, 5, 0);
		gbc_lblUpdateParticulars.gridx = 2;
		gbc_lblUpdateParticulars.gridy = 0;
		add(lblUpdateParticulars, gbc_lblUpdateParticulars);
		
		JLabel lblUsername = new JLabel("Display Name");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 0;
		gbc_lblUsername.gridy = 1;
		add(lblUsername, gbc_lblUsername);
		
		usernameTextField = new JTextField();
		User me = swingUI.getUser();
		usernameTextField.setText(me.getUsername());
		GridBagConstraints gbc_usernameTextField = new GridBagConstraints();
		gbc_usernameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameTextField.gridx = 2;
		gbc_usernameTextField.gridy = 1;
		add(usernameTextField, gbc_usernameTextField);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 2;
		add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 2;
		add(passwordField, gbc_passwordField);
		
		JButton updateButton = new JButton("Update Display Name");
		GridBagConstraints gbc_updateButton = new GridBagConstraints();
		gbc_updateButton.anchor = GridBagConstraints.NORTH;
		gbc_updateButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_updateButton.insets = new Insets(0, 0, 5, 0);
		gbc_updateButton.gridwidth = 3;
		gbc_updateButton.gridx = 0;
		gbc_updateButton.gridy = 4;
		add(updateButton, gbc_updateButton);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.anchor = GridBagConstraints.NORTH;
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 3;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 5;
		add(separator, gbc_separator);
		
		JLabel label_3 = new JLabel("Game Statistics");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.NORTH;
		gbc_label_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_3.insets = new Insets(0, 0, 5, 0);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 6;
		add(label_3, gbc_label_3);
		
		JLabel label_2 = new JLabel("Games Played");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridwidth = 2;
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 7;
		add(label_2, gbc_label_2);
		
		JLabel gamesPlayedDisplay = new JLabel("0");
		GridBagConstraints gbc_gamesPlayedDisplay = new GridBagConstraints();
		gbc_gamesPlayedDisplay.fill = GridBagConstraints.HORIZONTAL;
		gbc_gamesPlayedDisplay.insets = new Insets(0, 0, 5, 0);
		gbc_gamesPlayedDisplay.gridx = 2;
		gbc_gamesPlayedDisplay.gridy = 7;
		add(gamesPlayedDisplay, gbc_gamesPlayedDisplay);
		
		JLabel label_1 = new JLabel("Games Won");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridwidth = 2;
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 8;
		add(label_1, gbc_label_1);
		
		JLabel gamesWonDisplay = new JLabel("0");
		GridBagConstraints gbc_gamesWonDisplay = new GridBagConstraints();
		gbc_gamesWonDisplay.fill = GridBagConstraints.HORIZONTAL;
		gbc_gamesWonDisplay.insets = new Insets(0, 0, 5, 0);
		gbc_gamesWonDisplay.gridx = 2;
		gbc_gamesWonDisplay.gridy = 8;
		add(gamesWonDisplay, gbc_gamesWonDisplay);
		
		JLabel lblWinRate = new JLabel("Win Rate");
		GridBagConstraints gbc_lblWinRate = new GridBagConstraints();
		gbc_lblWinRate.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblWinRate.insets = new Insets(0, 0, 5, 5);
		gbc_lblWinRate.gridwidth = 2;
		gbc_lblWinRate.gridx = 0;
		gbc_lblWinRate.gridy = 9;
		add(lblWinRate, gbc_lblWinRate);
		
		JLabel winRateDisplay = new JLabel("0%");
		GridBagConstraints gbc_winRateDisplay = new GridBagConstraints();
		gbc_winRateDisplay.fill = GridBagConstraints.HORIZONTAL;
		gbc_winRateDisplay.insets = new Insets(0, 0, 5, 0);
		gbc_winRateDisplay.gridx = 2;
		gbc_winRateDisplay.gridy = 9;
		add(winRateDisplay, gbc_winRateDisplay);
		
		JButton backButton = new JButton("Back to Lobby");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.anchor = GridBagConstraints.NORTH;
		gbc_backButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_backButton.gridwidth = 3;
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 10;
		add(backButton, gbc_backButton);
		
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String username = usernameTextField.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				if (username.contains(" ") || password.contains(" ")) {
					JOptionPane.showMessageDialog(null, "Space is not allowed in Display name or Password");
					return;
				}	
				passwordField.setText("");
				swingUI.send(CommunicationHandler.UPDATE_DISPLAYNAME + " " + username + " " + password);
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.setInterface(Interface.LOBBY);
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
}
