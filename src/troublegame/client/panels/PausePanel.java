package troublegame.client.panels;

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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import troublegame.client.Interface;
import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

/**
 * 
 * The {@link PausePanel} class handles the display of the start screen of the client.
 *
 */
public class PausePanel extends JPanel {
	
	private static final long serialVersionUID = -300797057331156270L;
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	private Image backgroundImage;
	
	/**
	 * The constructor for the pause panel.
	 * @param swingUI is the swing user interface.
	 */
	public PausePanel(SwingUI swingUI) {
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
	 * Initializes the start panel.
	 */
	public void init() {
		setLayout(null);
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1s.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_2s.png");
		Image newimg1 = image1.getScaledInstance(250, 55, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(250, 55, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		JButton returnGame = new JButton("Return to game");
		returnGame.setFont(new Font("Tahoma", Font.PLAIN, 18));
		returnGame.setBounds(400, 200, 250, 55);
		add(returnGame);
		
		JButton instructionsButton = new JButton("Instructions");
		instructionsButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		instructionsButton.setBounds(400, 268, 250, 55);
		add(instructionsButton);
		
		JButton exitButton = new JButton("Logout");
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		exitButton.setBounds(400, 336, 250, 55);
		add(exitButton);
		
		returnGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.setInterface(Interface.IN_GAME);
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
				int confirmed = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to exit the game? You will leave the game and disrupt play for others", "Exit Trouble Message",
						JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION)
						swingUI.send(CommunicationHandler.LOGOUT_REQUEST);
			}
		});
		
		returnGame.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				returnGame.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				returnGame.setIcon(imgIcon2);
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
		
		returnGame.setIcon(imgIcon1);
		returnGame.setHorizontalTextPosition(SwingConstants.CENTER);
		returnGame.setBorderPainted(false);
		returnGame.setFont(new Font("Arial", Font.PLAIN, 30));
		
		instructionsButton.setIcon(imgIcon1);
		instructionsButton.setHorizontalTextPosition(SwingConstants.CENTER);
		instructionsButton.setBorderPainted(false);
		instructionsButton.setFont(new Font("Arial", Font.PLAIN, 30));
		
		exitButton.setIcon(imgIcon1);
		exitButton.setHorizontalTextPosition(SwingConstants.CENTER);
		exitButton.setBorderPainted(false);
		exitButton.setFont(new Font("Arial", Font.PLAIN, 30));
		
		this.add(returnGame);
		this.add(instructionsButton);
		this.add(exitButton);
		this.validate();
		this.repaint();
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
