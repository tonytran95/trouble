package troublegame.client.panels;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	
	/**
	 * The constructor for the pause panel.
	 * @param swingUI is the swing user interface.
	 */
	public PausePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.init();
	}
	
	/**
	 * Initializes the start panel.
	 */
	public void init() {
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_2.png");
		Image newimg1 = image1.getScaledInstance(SwingUI.WIDTH, 200, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(SwingUI.WIDTH, 200, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		JButton returnGame = new JButton("Return to game");
		JButton rules = new JButton("How to play");
		JButton exit = new JButton("Leave game and logout");
		this.setLayout(new GridLayout(3, 0));
		returnGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.setInterface(Interface.IN_GAME);
			}
		});
		rules.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.setInterface(Interface.RULES);
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				int confirmed = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to exit the game?", "Exit Trouble Message",
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
		
		rules.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				rules.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				rules.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		exit.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				exit.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				exit.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		returnGame.setIcon(imgIcon1);
		returnGame.setHorizontalTextPosition(SwingConstants.CENTER);
		returnGame.setBorderPainted(false);
		returnGame.setFont(new Font("Arial", Font.PLAIN, 30));
		
		rules.setIcon(imgIcon1);
		rules.setHorizontalTextPosition(SwingConstants.CENTER);
		rules.setBorderPainted(false);
		rules.setFont(new Font("Arial", Font.PLAIN, 30));
		
		exit.setIcon(imgIcon1);
		exit.setHorizontalTextPosition(SwingConstants.CENTER);
		exit.setBorderPainted(false);
		exit.setFont(new Font("Arial", Font.PLAIN, 30));
		
		this.add(returnGame);
		this.add(rules);
		this.add(exit);
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

}
