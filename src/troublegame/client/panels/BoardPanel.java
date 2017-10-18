package troublegame.client.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import troublegame.server.Die;
import troublegame.server.Slot;

public class BoardPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 997446065983298178L;
	
	public final static Color TRANSPARENT_RED = new Color(1f, 0f, 0f, 0.4f);
	public final static Color TRANSPARENT_BLUE = new Color(0f, 0f, 1f, 0.4f);
	public final static Color TRANSPARENT_GREEN = new Color(0f, 1f, 0f, 0.4f);
	public final static Color TRANSPARENT_YELLOW = new Color(1f, 1f, 0f, 0.3f);
	
	// Number of slot constants
	public static final int NUM_MAIN_SLOTS = 28;
	public static final int NUM_HOME_SLOTS = 4;
	public static final int NUM_END_SLOTS = 4;
	
	// Starting positions
	public static final int RED_START = 0;
	public static final int GREEN_START = 7;
	public static final int YELLOW_START = 14;
	public static final int BLUE_START = 21;
	
	// Ending positions
	public static final int RED_END = NUM_MAIN_SLOTS - 1;
	public static final int BLUE_END = BLUE_START - 1;
	public static final int YELLOW_END = YELLOW_START - 1;
	public static final int GREEN_END = GREEN_START - 1;
	
	public static final int SLOT_HOME = 0;
	public static final int SLOT_MAIN = 1;
	public static final int SLOT_END = 2;	
	
	/**
	 * Background image for the board
	 */
	private BufferedImage boardBackground;
	
	/**
	 * The rolling dice gif
	 */
	private Image rollingDie;
	
	/**
	 * Each slots contains one die number
	 */
	private BufferedImage[] dieNumbers;
	
	/**
	 * The roll die button
	 */
	private BufferedImage rolledNumber;
	
	// The main board
	private ArrayList<Ellipse2D> mainSlots;
	
	// Each players home base where the tokens spawn
	private ArrayList<Ellipse2D> redHomeZone;
	private ArrayList<Ellipse2D> blueHomeZone;
	private ArrayList<Ellipse2D> yellowHomeZone;
	private ArrayList<Ellipse2D> greenHomeZone;
	
	// Each players end zone where the tokens are safe
	private ArrayList<Ellipse2D> redEndZone;
	private ArrayList<Ellipse2D> blueEndZone;
	private ArrayList<Ellipse2D> yellowEndZone;
	private ArrayList<Ellipse2D> greenEndZone;
	
	// The die
	private Die die;
	
	/**
	 * JPanel containing the board
	 */
	public BoardPanel() {
		
		try {
			this.boardBackground = ImageIO.read(new File("C:/Users/Nick/Documents/UNSW Current Semester/COMP4920/Software Project/SoftwareTrouble/data/img/board.png"));
			this.dieNumbers = new BufferedImage[6];
			for(int i = 0; i < dieNumbers.length; i++) {
				this.dieNumbers[i] = 
						ImageIO.read(new File("C:/Users/Nick/Documents/UNSW Current Semester/COMP4920/Software Project/SoftwareTrouble/data/img/roll_" + i + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.rollingDie = Toolkit.getDefaultToolkit().createImage("C:/Users/Nick/Documents/UNSW Current Semester/COMP4920/Software Project/SoftwareTrouble/data/img/die.gif");
		generateMainSlots();
		generateHomeZones();
		generateEndZones();
		
	}
	
	/**
	 * Shows the number rolled on the die on the board
	 * @param rolled The number that the player rolled
	 */
	public void showRollNumber(int rolled) {
		
	}
	
	/**
	 * Generates an arraylist of Ellipse2D which contains the exact same digit representation for slot numbers
	 * as are found in the server construct of board
	 * @return The filled arraylist of slot shapes for the main zone
	 */
	public void generateMainSlots() {
		
		mainSlots = new ArrayList<>();
		
		int topMoveable, sideMoveable;
		topMoveable = sideMoveable = 131;
		int diff = 47;
		
		mainSlots.add(new Ellipse2D.Double(64, sideMoveable, 30, 30));
		mainSlots.add(new Ellipse2D.Double(35, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(27, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(27, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(27, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(35, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(64, sideMoveable -= diff, 30, 30));
		
		mainSlots.add(new Ellipse2D.Double(topMoveable, 65, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 36, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 36, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 65, 30, 30));
		
		mainSlots.add(new Ellipse2D.Double(482, sideMoveable, 30, 30));
		mainSlots.add(new Ellipse2D.Double(511, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(519, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(519, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(519, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(511, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(482, sideMoveable += diff, 30, 30));
		
		mainSlots.add(new Ellipse2D.Double(topMoveable, 482, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 511, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 511, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 482, 30, 30));
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// Draw the background image
		g.drawImage(boardBackground, 13, 13, this);
		
		// Paint the three zones
		Graphics2D artist = (Graphics2D) g;
		paintHomeZones(artist);
		paintEndZones(artist);
		paintMainZone(artist);
		
	}
	
	/**
	 * Paints user home zones
	 * @param artist A wonderful artist who paints home zones
	 */
	public void paintHomeZones(Graphics2D artist) {
		
		// Green Home
		paintDiagonalZones(2, 20, 110, 30, artist);
		
		// Yellow Home
		paintDiagonalZones(1, 525, 110, 30, artist);
		
		// Blue Home
		paintDiagonalZones(2, 435, 525, 30, artist);
		
		// Red Home
		paintDiagonalZones(1, 110, 525, 30, artist);
		
	}
	
	/**
	 * Paints user endzones
	 * @param artist A wonderful artist who also paints endzones
	 */
	public void paintEndZones(Graphics2D artist) {
		
		// Green End
		paintDiagonalZones(1, 200, 200, 30, artist);
				
		// Yellow End
		paintDiagonalZones(2, 345, 200, 30, artist);
		
		// Blue End
		paintDiagonalZones(1, 435, 435, 30, artist);
		
		// Red End
		paintDiagonalZones(2, 110, 435, 30, artist);
		
	}
	
	/**
	 * Paints the main zone
	 * @param artist A wonderful artist who can also paint main zones
	 */
	public void paintMainZone(Graphics2D artist) {
		
		
		
		
		
	}
	
	/**
	 * Paints slot from bottom to top in the given direction (1 = left, 2 = right)
	 */
	public void paintDiagonalZones(int direction, int x, int y, int spacing, Graphics2D artist) {
		
		int diametre = 30;
		
		for(int i = 0; i < 4; i++) {
			artist.draw(new Ellipse2D.Double(x, y, diametre, diametre));
			if(direction == 1) {
				y -= spacing;
				x -= spacing;
			} else if(direction == 2) {
				y -= spacing;
				x += spacing;
			}
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
