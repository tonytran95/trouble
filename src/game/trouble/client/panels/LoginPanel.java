package game.trouble.client.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.trouble.client.ClientState;
import game.trouble.client.SwingUI;
import game.trouble.client.User;

/**
 * 
 * The LoginPanel class handles the display of the Login screen of the client.
 * 
 * @author Jeffrey Ung
 *
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel {
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The username text field.
	 */
	private JTextField username;
	
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
		this.username = new JTextField();
		this.enter = new JButton("Enter");
		this.exit = new JButton("Return to start");
		enter.setFont(new Font("Arial", Font.PLAIN, 15));
		exit.setFont(new Font("Arial", Font.PLAIN, 15));
		this.init();
	}
	
	/**
	 * Initializes the Login panel.
	 */
	public void init() {
		JLabel label = new JLabel("Enter your name: ");
		JPanel subPanel1 = new JPanel();
		JPanel subPanel2 = new JPanel();
		this.setLayout(new BorderLayout());
		this.username.setColumns(10);
		username.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				swingUI.send("CONNECTED " + username.getText());
				swingUI.setUser(new User(username.getText()));
				swingUI.setClientState(ClientState.IN_GAME); // change to lobby later
				//swingUI.send("COLORS");
			}
		});
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.send("CONNECTED " + username.getText());
				swingUI.setUser(new User(username.getText()));
				swingUI.setClientState(ClientState.IN_GAME); // change to lobby later
				//swingUI.send("COLORS");
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.setClientState(ClientState.START);
			}
		});
		subPanel1.add(label);
		subPanel1.add(username);
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
