package troublegame.client.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 997446065983298178L;
	
	/**
	 * Background image for the board
	 */
	private BufferedImage boardBackground;
	
	public BoardPanel() {
		
//		try {
//			this.boardBackground = ImageIO.read(new File("data/img/board.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		this.setBackground(Color.BLUE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
//		g.drawImage(boardBackground, 13, 13, this);
		
	}
	
	
}
