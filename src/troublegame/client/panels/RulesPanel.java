package troublegame.client.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import troublegame.client.Interface;
import troublegame.client.SwingUI;

/**
 * 
 * The {@link RulesPanel} class handles the display of the "how to play" screen of the client.
 * 
 * @author Jeffrey Ung
 *
 */
@SuppressWarnings("serial")
public class RulesPanel extends JPanel {
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The image of the rules.
	 */
	private BufferedImage image;
	
	/**
	 * The constructor of the how to play panel screen handler.
	 * @param swingUI is the swing user interface.
	 */
	public RulesPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.init();
	}
	
	/**
	 * Initializes the rules panel.
	 */
	public void init() {
		JButton exit = new JButton("Return to menu");
		exit.setFont(new Font("Arial", Font.PLAIN, 15));
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.swingUI.requestFocusInWindow();
		this.setVisible(true);
		try {
			image = ImageIO.read(new File("how-to-play.jpg"));
		} catch (IOException e) {
			System.out.println("Error loading ./how-to-play.jpg");
		}
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.resizeFrame();
				swingUI.setInterface(Interface.START);
			}
		});
		this.add(exit);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
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
