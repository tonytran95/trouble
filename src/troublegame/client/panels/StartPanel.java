package troublegame.client.panels;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import troublegame.client.Interface;
import troublegame.client.SwingUI;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;

public class StartPanel extends JPanel {

	/**
	 * Serial ID for object serialisation
	 */
	private static final long serialVersionUID = -3316901513367466105L;
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	private Image backgroundImage;
	
	/**
	 * The constructor for the start panel.
	 * @param swingUI is the swing user interface.
	 */
	public StartPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		
		/**
		 * set background image
		 */
		try {
			backgroundImage = ImageIO.read(new File("./data/img/background3.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.init();
	}
	
	/**
	 * Create the panel.
	 */
	public void init() {
		setLayout(null);
		
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1s.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_2s.png");
		Image newimg1 = image1.getScaledInstance(197, 55, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(197, 55, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		JButton loginButton = new JButton("Play Now");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loginButton.setBounds(416, 200, 197, 55);
		add(loginButton);
		
		JButton instructionsButton = new JButton("Instructions");
		instructionsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		instructionsButton.setBounds(416, 268, 197, 55);
		add(instructionsButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		exitButton.setBounds(416, 336, 197, 55);
		add(exitButton);
		
		
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.setInterface(Interface.LOGIN);
			}
		});
		instructionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.setInterface(Interface.RULES);
			}
		});
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				System.exit(0);
			}
		});
		
		loginButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				loginButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				loginButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		instructionsButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				instructionsButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				instructionsButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		exitButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				exitButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				exitButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		loginButton.setIcon(imgIcon1);
		loginButton.setHorizontalTextPosition(SwingConstants.CENTER);
		loginButton.setBorderPainted(false);
		loginButton.setFont(new Font("Arial", Font.PLAIN, 30));
		
		instructionsButton.setIcon(imgIcon1);
		instructionsButton.setHorizontalTextPosition(SwingConstants.CENTER);
		instructionsButton.setBorderPainted(false);
		instructionsButton.setFont(new Font("Arial", Font.PLAIN, 30));
		
		exitButton.setIcon(imgIcon1);
		exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
		exitButton.setBorderPainted(false);
		exitButton.setFont(new Font("Arial", Font.PLAIN, 30));
		
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
