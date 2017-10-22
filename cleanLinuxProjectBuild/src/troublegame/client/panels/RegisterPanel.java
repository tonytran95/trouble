package troublegame.client.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import troublegame.client.Interface;
import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

public class RegisterPanel extends JPanel {
	
	private static final long serialVersionUID = -6169128406502978993L;
	private JTextField nameField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField quoteField;
	private JRadioButton rdbtnRed;
	private JRadioButton rdbtnBlue;
	private JRadioButton rdbtnGreen;
	private JRadioButton rdbtnYellow;
	private String preferredColor;
	private Image backgroundImage;
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	
	/**
	 * The constructor of the how to play panel screen handler.
	 * @param swingUI is the swing user interface.
	 */
	public RegisterPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.rdbtnRed = new JRadioButton("Red");
		this.rdbtnBlue = new JRadioButton("Blue");
		this.rdbtnGreen = new JRadioButton("Green");
		this.rdbtnYellow = new JRadioButton("Yellow");
		
		/**
		 * set background image
		 */
		try {
			backgroundImage = ImageIO.read(new File("./data/img/background5.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.init();
	}
	
	/**
	 * Initializes the rules panel.
	 */
	public void init() {
		this.setLayout(null);
		
		JLabel lblDisplayName = new JLabel("Display name:");
		lblDisplayName.setBounds(260, 173, 124, 14);
		this.add(lblDisplayName);
		
		JLabel lblEmailAddress = new JLabel("Email address:");
		lblEmailAddress.setBounds(260, 198, 124, 14);
		this.add(lblEmailAddress);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(260, 223, 124, 14);
		this.add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password:");
		lblConfirmPassword.setBounds(260, 248, 124, 14);
		this.add(lblConfirmPassword);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(171, 275, 690, 2);
		this.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(176, 159, 690, 2);
		this.add(separator_1);
		
		JLabel lblAccountRegistration = new JLabel("Account Registration");
		lblAccountRegistration.setBounds(189, 132, 117, 14);
		this.add(lblAccountRegistration);
		
		JLabel lblWhatIsYour = new JLabel("What is your preferred color?");
		lblWhatIsYour.setBounds(284, 296, 145, 14);
		this.add(lblWhatIsYour);
		
		nameField = new JTextField();
		nameField.setBounds(413, 170, 158, 20);
		this.add(nameField);
		nameField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setBounds(413, 195, 158, 20);
		this.add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(413, 220, 158, 20);
		this.add(passwordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(413, 245, 158, 20);
		this.add(confirmPasswordField);
		
		rdbtnRed.setBounds(320, 317, 109, 23);
		rdbtnRed.setBackground(new Color(195, 195, 195));
		rdbtnRed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				rdbtnBlue.setSelected(false);
				rdbtnGreen.setSelected(false);
				rdbtnYellow.setSelected(false);
				preferredColor = "RED";
			}
		});
		this.add(rdbtnRed);
		
		rdbtnBlue.setBounds(320, 343, 109, 23);
		rdbtnBlue.setBackground(new Color(195, 195, 195));
		rdbtnBlue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				rdbtnRed.setSelected(false);
				rdbtnGreen.setSelected(false);
				rdbtnYellow.setSelected(false);
				preferredColor = "BLUE";
			}
		});
		this.add(rdbtnBlue);
		
		rdbtnGreen.setBounds(320, 369, 109, 23);
		rdbtnGreen.setBackground(new Color(195, 195, 195));
		rdbtnGreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				rdbtnRed.setSelected(false);
				rdbtnBlue.setSelected(false);
				rdbtnYellow.setSelected(false);
				preferredColor = "GREEN";
			}
		});
		this.add(rdbtnGreen);
		
		rdbtnYellow.setBounds(320, 395, 109, 23);
		rdbtnYellow.setBackground(new Color(195, 195, 195));
		rdbtnYellow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				rdbtnRed.setSelected(false);
				rdbtnBlue.setSelected(false);
				rdbtnGreen.setSelected(false);
				preferredColor = "YELLOW";
			}
		});
		this.add(rdbtnYellow);
		
		quoteField = new JTextField();
		quoteField.setBounds(284, 450, 476, 20);
		this.add(quoteField);
		quoteField.setColumns(10);
		
		JLabel lblTellUsAbout = new JLabel("Tell us about yourself. (Optional)");
		lblTellUsAbout.setBounds(284, 425, 349, 14);
		this.add(lblTellUsAbout);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(171, 485, 690, 13);
		this.add(separator_2);
		
		
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1x.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_3.png");
		Image newimg1 = image1.getScaledInstance(89, 23, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(89, 23, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.setBounds(390, 499, 89, 23);
		this.add(btnSignUp);
		
		btnSignUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.playButtonSound();
				sendSignUp();
			}
		});
		btnSignUp.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				btnSignUp.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnSignUp.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		btnSignUp.setIcon(imgIcon1);
		btnSignUp.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSignUp.setBorderPainted(false);
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(503, 499, 89, 23);
		this.add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.playButtonSound();
				swingUI.setInterface(Interface.LOGIN);
			}
		});
		btnCancel.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				btnCancel.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnCancel.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		btnCancel.setIcon(imgIcon1);
		btnCancel.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCancel.setBorderPainted(false);
	}
	
	/**
	 * Handles the sign up here.
	 */
	private void sendSignUp() {
		nameField.setBackground(Color.WHITE);
		String proposedName = nameField.getText();
		proposedName.replaceAll(" ", "");
		if (proposedName.length() == 0) {
			JOptionPane.showMessageDialog(null, "Display name cannot be empty!", "Please Try again", JOptionPane.ERROR_MESSAGE);
			nameField.setBackground(Color.RED);
			return;
		}
		if (proposedName.contains(" ")) {
			JOptionPane.showMessageDialog(null, "Display name and password cannot have space(s)!", "Please Try again", JOptionPane.ERROR_MESSAGE);
			nameField.setBackground(Color.RED);
			return;
		}
		emailField.setBackground(Color.WHITE);
		if (!isValidEmailAddress(emailField.getText())) {
			JOptionPane.showMessageDialog(null, "Enter a valid email!", "Please Try again", JOptionPane.ERROR_MESSAGE);
			emailField.setBackground(Color.RED);
			return;
		}
		passwordField.setBackground(Color.WHITE);
		confirmPasswordField.setBackground(Color.WHITE);
		if (passwordField.getPassword().length != confirmPasswordField.getPassword().length) {
			JOptionPane.showMessageDialog(null, "Passwords do not match!", "Please Try again", JOptionPane.ERROR_MESSAGE);
			passwordField.setBackground(Color.RED);
			confirmPasswordField.setBackground(Color.RED);
			return;
		}
		if (passwordField.getPassword().length != confirmPasswordField.getPassword().length) {
			JOptionPane.showMessageDialog(null, "Passwords do not match!", "Please Try again", JOptionPane.ERROR_MESSAGE);
			passwordField.setBackground(Color.RED);
			confirmPasswordField.setBackground(Color.RED);
			return;
		}
		for (int i = 0; i < passwordField.getPassword().length; i++) {
			if (passwordField.getPassword()[i] == ' ') {
				JOptionPane.showMessageDialog(null, "Display name and password cannot have space(s)!", "Please Try again", JOptionPane.ERROR_MESSAGE);
				passwordField.setBackground(Color.RED);
				return;
			}
			if (passwordField.getPassword()[i] != confirmPasswordField.getPassword()[i]) {
				JOptionPane.showMessageDialog(null, "Passwords do not match!", "Please Try again", JOptionPane.ERROR_MESSAGE);
				passwordField.setBackground(Color.RED);
				confirmPasswordField.setBackground(Color.RED);
				return;
			}
		}
		if (!rdbtnRed.isSelected() && !rdbtnBlue.isSelected() && !rdbtnGreen.isSelected() && !rdbtnYellow.isSelected()) {
			JOptionPane.showMessageDialog(null, "Please select a preferred color!", "Please Try again", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (quoteField.getText().equals("")) quoteField.setText("-");
		this.swingUI.send(CommunicationHandler.REGISTER_REQUEST + "\t" +
				emailField.getText() + "\t" +
				nameField.getText() + "\t" +
				String.valueOf(passwordField.getPassword()) + "\t" +
				preferredColor + "\t" +
				quoteField.getText());
	}
	
	private boolean isValidEmailAddress(String email) {
    	String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    	Pattern p = java.util.regex.Pattern.compile(ePattern);
    	Matcher m = p.matcher(email);
        return m.matches();
    }
	
	/**
	 * @return the email field.
	 */
	public JTextField getEmailField() {
		return this.emailField;
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
