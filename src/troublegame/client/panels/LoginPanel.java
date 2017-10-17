package troublegame.client.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
		lblEmail.setBounds(386, 178, 62, 14);
		this.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(386, 206, 62, 14);
		this.add(lblPassword);
		
		String email = getLastEmail();
		
		emailField = new JTextField(email);
		emailField.setBounds(458, 175, 143, 20);
		this.add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(458, 203, 143, 20);
		this.add(passwordField);
		
		JCheckBox btnRememberMe = new JCheckBox("Remember email");
		btnRememberMe.setBounds(468, 230, 108, 23);
		if (email.length() > 0)
			btnRememberMe.setSelected(true);
		this.add(btnRememberMe);
		// TODO remember email
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(386, 275, 89, 23);
		this.add(btnLogin);
		
		passwordField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.send(CommunicationHandler.LOGIN_REQUEST + " " + emailField.getText() + " " + String.valueOf(passwordField.getPassword()));
				if (btnRememberMe.isSelected())
					setLastEmail(emailField.getText());
				else if (email.length() > 0)
					setLastEmail("");
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(passwordField.getPassword().length == 0 || emailField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Email and password cannot be blank");
					return;
				}
				swingUI.send(CommunicationHandler.LOGIN_REQUEST + " " + emailField.getText() + " " + String.valueOf(passwordField.getPassword()));
				if (btnRememberMe.isSelected())
					setLastEmail(emailField.getText());
				else if (email.length() > 0)
					setLastEmail("");
			}
		});
		
		JButton btnReturnToMain = new JButton("Return to main menu");
		btnReturnToMain.setBounds(485, 275, 143, 23);
		this.add(btnReturnToMain);
		btnReturnToMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.setInterface(Interface.START);
			}
		});
	}

	/**
	 * Writes the last email inside the cache.
	 * @param email is the email
	 */
	private void setLastEmail(String email) {

		File f = new File("./data/client");
		FileOutputStream outputStream = null;
		try {
			if (!f.exists())
				f.createNewFile();
			outputStream = new FileOutputStream(f);
			byte[] contentInBytes = email.getBytes(Charset.forName("UTF-8"));
			outputStream.write(contentInBytes);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Reads the last email inside the cache.
	 * @param email is the email
	 */
	private String getLastEmail() {
		File f = new File("./data/client");
		FileInputStream inputStream = null;
		byte fileContent[] = new byte[(int)f.length()];
		try {
			if (!f.exists())
				f.createNewFile();
			inputStream = new FileInputStream(f);
			inputStream.read(fileContent);
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(fileContent, StandardCharsets.UTF_8);
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
