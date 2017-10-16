package troublegame.client.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import troublegame.client.Interface;
import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

/**
 * 
 * The LoginPanel class handles the display of the Login screen of the client.
 * 
 * @author Jeffrey Ung
 *
 */
public class LoginPanel extends JPanel {
	
	/**
	 * Serial ID for the serialisation of this class
	 */
	private static final long serialVersionUID = -7619006812649770179L;

	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The email field.
	 */
	private JTextField emailField;
	
	/**
	 * The password field.
	 */
	private JPasswordField passwordField;

	/**
	 * The constructor for the Login panel.
	 * @param swingUI is the swing user interface.
	 */
	public LoginPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.init();
	}
	
	/**
	 * Initializes the Login panel.
	 */
	private void init() {
		this.setLayout(null);
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(271, 178, 62, 14);
		this.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(271, 206, 62, 14);
		this.add(lblPassword);
		
		emailField = new JTextField();
		emailField.setBounds(343, 175, 143, 20);
		this.add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(343, 203, 143, 20);
		this.add(passwordField);
		passwordField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.send(CommunicationHandler.LOGIN_REQUEST + " " + emailField.getText() + " " + String.valueOf(passwordField.getPassword()));
			}
		});
		
		JCheckBox btnRememberMe = new JCheckBox("Remember email");
		btnRememberMe.setBounds(353, 230, 108, 23);
		this.add(btnRememberMe);
		// TODO remember email
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(271, 275, 89, 23);
		this.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.send(CommunicationHandler.LOGIN_REQUEST + " " + emailField.getText() + " " + String.valueOf(passwordField.getPassword()));
			}
		});
		
		JButton btnReturnToMain = new JButton("Return to main menu");
		btnReturnToMain.setBounds(370, 275, 143, 23);
		this.add(btnReturnToMain);
		btnReturnToMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.setInterface(Interface.START);
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
