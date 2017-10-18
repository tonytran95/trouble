package troublegame.client.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

public class BoardPanel extends JPanel {

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
	 * The main swing ui
	 */
	private SwingUI swingUI;
	
	/**
	 * Background image for the board
	 */
	private Image boardBackground;
	
	/**
	 * The rolling dice gif
	 */
	private Image rollingDie;
	
	/**
	 * The image of the die showing the number rolled
	 */
	private Image die;
	
	/**
	 * Each slots contains one die number
	 */
	private Image[] dieNumbers;
	
	/**
	 * Boundary of the die bubble to detect button clicks
	 */
	private Ellipse2D dieHolder;
	
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
	
	/**
	 * JPanel containing the board
	 */
	public BoardPanel(SwingUI mainFrame) {
		
		try {
			this.boardBackground = ImageIO.read(new File("data/img/board.png"));
			this.dieNumbers = new Image[6];
			for(int i = 0; i < dieNumbers.length; i++) {
				this.dieNumbers[i] = 
						ImageIO.read(new File("data/img/roll_" + (i + 1) + ".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.rollingDie = Toolkit.getDefaultToolkit().createImage("data/img/die.gif");
		generateMainSlots();
		generateHomeZones();
		generateEndZones();
		
		// TODO Request this number for all users so that the initial die roll is the same for everyone
		this.die = dieNumbers[new Random().nextInt(6)];
		this.swingUI = mainFrame;
		this.dieHolder = new Ellipse2D.Double(232, 232, 113, 113);
		this.addMouseListener(setMouseListener());
		
	}
	
	/**
	 * Shows the number rolled on the die on the board
	 * @param rolled The number that the player rolled
	 */
	public void rollAndShow(int rolled) {
		
		TimerTask showTimer = new TimerTask() {
			
			@Override
			public void run() {
				die = dieNumbers[rolled - 1];
				repaint();
			}
		};
		
		new Timer().schedule(showTimer, 1500);
		die = rollingDie;
		repaint();
		
	}
	
	public MouseListener setMouseListener() {
		return new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(dieHolder.contains(e.getPoint())) {
					rollAndShow(3);
					swingUI.send(CommunicationHandler.GAME_ROLL);
				} else {
					System.out.println("MOUSE AT X: " + e.getX() + ", Y:" + e.getY());
				}
			}
		};
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// Draw the background image and die
		g.drawImage(boardBackground, 13, 13, this);
		g.drawImage(die, 251, 251, this);
		
		// Paint the three zones
		Graphics2D artist = (Graphics2D) g;
		paintSlots(artist, mainSlots);
		paintSlots(artist, redHomeZone);
		paintSlots(artist, redEndZone);
		paintSlots(artist, greenHomeZone);
		paintSlots(artist, greenEndZone);
		paintSlots(artist, yellowHomeZone);
		paintSlots(artist, yellowEndZone);
		paintSlots(artist, blueHomeZone);
		paintSlots(artist, blueEndZone);
		
		// Paint the die holder
		artist.setColor(new Color(0, 0, 0, Color.OPAQUE));
		artist.draw(dieHolder);
		
	}
	
	/**
	 * Paints the given slots
	 * @param artist A wonderful artist who can also paint main zones
	 * @param slots The set of slots to paint
	 */
	public void paintSlots(Graphics2D artist, ArrayList<Ellipse2D> slots) {
		
		for(Ellipse2D slot : slots) {
			artist.setColor(Color.WHITE);
			artist.fill(slot);
			artist.setColor(Color.BLACK);
			artist.draw(slot);
		}
		
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
		
		// Left Quarter
		mainSlots.add(new Ellipse2D.Double(64, sideMoveable, 30, 30));
		mainSlots.add(new Ellipse2D.Double(35, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(27, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(27, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(27, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(35, sideMoveable += diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(64, sideMoveable += diff, 30, 30));
		
		// Top Quarter
		mainSlots.add(new Ellipse2D.Double(topMoveable, 65, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 36, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 36, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable += diff, 65, 30, 30));
		
		// Right Quarter
		mainSlots.add(new Ellipse2D.Double(482, sideMoveable, 30, 30));
		mainSlots.add(new Ellipse2D.Double(511, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(519, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(519, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(519, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(511, sideMoveable -= diff, 30, 30));
		mainSlots.add(new Ellipse2D.Double(482, sideMoveable -= diff, 30, 30));
		
		// Bottom Quarter
		mainSlots.add(new Ellipse2D.Double(topMoveable, 482, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 511, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 511, 30, 30));
		mainSlots.add(new Ellipse2D.Double(topMoveable -= diff, 482, 30, 30));
		
	}
	
	/**
	 * Paints user home zones
	 * @param artist A wonderful artist who paints home zones
	 */
	public void generateHomeZones() {
		
		// Red End
		generateDiagonalZone(2, 110, 435, 30, redHomeZone = new ArrayList<>());
		
		// Green End
		generateDiagonalZone(1, 200, 200, 30, greenHomeZone = new ArrayList<>());
				
		// Yellow End
		generateDiagonalZone(2, 345, 200, 30, yellowHomeZone = new ArrayList<>());
		
		// Blue End
		generateDiagonalZone(1, 435, 435, 30, blueHomeZone = new ArrayList<>());
		
	}
	
	/**
	 * Creates Ellipse2D to use for game endzones
	 * @param artist A wonderful artist who also paints endzones
	 */
	public void generateEndZones() {
		
		// Red End
		generateDiagonalZone(1, 110, 525, 30, redEndZone = new ArrayList<>());
		
		// Green End
		generateDiagonalZone(2, 20, 110, 30, greenEndZone = new ArrayList<>());
				
		// Yellow End
		generateDiagonalZone(1, 525, 110, 30, yellowEndZone = new ArrayList<>());
		
		// Blue End
		generateDiagonalZone(2, 435, 525, 30, blueEndZone = new ArrayList<>());
		
	}
	
	/**
	 * Adds Ellipse2D to the given arraylist in the given direction (1 = left, 2 = right)
	 */
	public void generateDiagonalZone(int direction, int x, int y, int spacing, ArrayList<Ellipse2D> slots) {
		
		int diametre = 30;
		
		for(int i = 0; i < 4; i++) {
			slots.add(new Ellipse2D.Double(x, y, diametre, diametre));
			if(direction == 1) {
				y -= spacing;
				x -= spacing;
			} else if(direction == 2) {
				y -= spacing;
				x += spacing;
			}
		}
		
	}
	
}
