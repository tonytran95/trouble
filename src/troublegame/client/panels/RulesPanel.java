package troublegame.client.panels;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import troublegame.client.SwingUI;

/**
 * 
 * The {@link RulesPanel} class handles the display of the "how to play" screen of the client.
 * 
 * @author Jeffrey Ung
 *
 */
public class RulesPanel extends JPanel {
	
	private static final long serialVersionUID = -6129475232074458340L;
	
	private static final String INSTRUCTION_DIR = "./data/man/manual.jpg";
	
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
		JButton exit = new JButton("Return");
		exit.setFont(new Font("Arial", Font.PLAIN, 15));
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.swingUI.requestFocusInWindow();
		this.setVisible(true);
		
		try {
			image = ImageIO.read(new File(INSTRUCTION_DIR));
		} catch (IOException e) {
			JPanel subPanel = new JPanel();
			subPanel.add(new Label("Error loading instruction manual. Image not found"));
			this.add(subPanel);
		}
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.playButtonSound();
				swingUI.resizeFrame();
				swingUI.switchPanel(swingUI.getPreviousPanel());
			}
		});
		this.add(exit);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 89, 0, this);
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
