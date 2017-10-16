package troublegame.testing;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import troublegame.client.Interface;
import troublegame.communication.CommunicationHandler;

import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class TestSwing {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSwing window = new TestSwing();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestSwing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JPanel panel = new JPanel();
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 550);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JList list = new JList();
		list.setBounds(218, 67, 135, 67);
		panel.add(list);
		
		JLabel lblMembers = new JLabel("Members");
		lblMembers.setBounds(218, 42, 46, 14);
		panel.add(lblMembers);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(402, 64, 89, 23);
		panel.add(btnStart);
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.setBounds(402, 98, 89, 23);
		panel.add(btnLeave);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(103, 242, 503, 129);
		panel.add(textArea);
		
		textField = new JTextField();
		textField.setBounds(103, 382, 503, 14);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(607, 386, 82, 23);
		panel.add(btnSend);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
