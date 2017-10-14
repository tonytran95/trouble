package troublegame.client.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
	 * The username text field.
	 */
	private JTextField email;
	
	/**
	 * The password text field.
	 */
	private JPasswordField password;
	
	/**
	 * The button to set the name.
	 */
	private JButton enter;
	
	/**
	 * The button to return to main menu.
	 */
	private JButton exit;
	
	/**
	 * The constructor for the Login panel.
	 * @param swingUI is the swing user interface.
	 */
	public LoginPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.swingUI = swingUI;
		this.email = new JTextField();
		this.password = new JPasswordField();
		this.enter = new JButton("Enter");
		this.exit = new JButton("Return to menu");
		enter.setFont(new Font("Arial", Font.PLAIN, 15));
		exit.setFont(new Font("Arial", Font.PLAIN, 15));
		this.init();
	}
	
	/**
	 * Initializes the Login panel.
	 */
	public void init() {
		JLabel usernameLabel = new JLabel("Enter your email: ");
		JLabel passwordLabel = new JLabel("Enter your password: ");
		JPanel subPanel1 = new JPanel();
		JPanel subPanel2 = new JPanel();
		this.setLayout(new BorderLayout());
		this.email.setColumns(10);
		this.password.setColumns(10);
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.send(CommunicationHandler.LOGIN_REQUEST + " " + email.getText() + " " + String.valueOf(password.getPassword()));
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.setInterface(Interface.START);
			}
		});
		subPanel1.add(usernameLabel);
		subPanel1.add(email);
		subPanel1.add(passwordLabel);
		subPanel1.add(password);
		subPanel2.add(enter);
		subPanel2.add(exit);
		this.add(subPanel1, BorderLayout.CENTER);
		this.add(subPanel2, BorderLayout.SOUTH);
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
