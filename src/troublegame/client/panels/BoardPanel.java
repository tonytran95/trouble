package troublegame.client.panels;

import java.awt.BasicStroke;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import troublegame.client.SwingUI;
import troublegame.client.model.Board;
import troublegame.client.model.Slot;
import troublegame.communication.CommunicationHandler;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 997446065983298178L;
	
	public final static Color TRANSPARENT_RED = new Color(1f, 0f, 0f, 0.4f);
	public final static Color TRANSPARENT_BLUE = new Color(0f, 0f, 1f, 0.4f);
	public final static Color TRANSPARENT_GREEN = new Color(0f, 1f, 0f, 0.4f);
	public final static Color TRANSPARENT_YELLOW = new Color(1f, 1f, 0f, 0.3f);
	
	/**
	 * The main swing ui
	 */
	private SwingUI swingUI;
	
	/**
	 * The Board model
	 */
	private Board board;
	
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
	
	/**
	 * The map of players <username, color>
	 */
	private Map<String, String> players;
	
	/**
	 * Boolean for whether or not it is this player's turn
	 */
	private boolean myTurn;
	
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
		
		this.swingUI = mainFrame;
		this.board = new Board();
		this.dieHolder = new Ellipse2D.Double(232, 232, 113, 113);
		this.players = new HashMap<String, String>();
		this.myTurn = false;
		this.addMouseListener(setMouseListener());
		
		generateMainSlots();
		generateHomeZones();
		generateEndZones();
		generateTokens();
	}
	
	/**
	 * Plays the rolling die sound when this method is called.
	 */
	public void playDieSound() {
		if (!swingUI.isSoundEffect())
			return;
		String soundName = "./data/sound/roll.wav";
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip;
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getPlayers() {
		return players;
	}
	
	/**
	 * Set the value shown on the die to the given number
	 * @param num Number to show on the die
	 */
	public void setDie(int num) {
		die = dieNumbers[num - 1];
	}
	
	/**
	 * Shows the number rolled on the die on the board
	 * @param rolled The number that the player rolled
	 */
	public void rollAndShow(int rolled) {
		
		TimerTask showTimer = new TimerTask() {
			
			@Override
			public void run() {
				setDie(rolled);
				repaint();
			}
		};
		this.playDieSound();
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
				for (Slot slot : swingUI.getUser().getTokens()) {
					if (slot.getShape().contains(e.getPoint())) {
						if (swingUI.getUser().isSelectedSlot(slot)) {
							swingUI.getUser().deselectSlot();
						} else if (getPlayers().get(
								swingUI.getUser().getUsername()).equals(getColor(slot.getColor()))) {
							swingUI.getUser().selectSlot(slot);
						}
						repaint();
					}
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (myTurn) {
					if (swingUI.getUser().isSelectedSlot()) {
						if (dieHolder.contains(e.getPoint())) {
							for (int i = 0; i < swingUI.getUser().getTokens().size(); i++) {
								if (swingUI.getUser().getTokens().get(i).equals(swingUI.getUser().getSelectedSlot())) {
									if (swingUI.getUser().getTokens().get(i).getZone() != Board.SLOT_END) {
										swingUI.send(CommunicationHandler.GAME_ROLL + " " + i);
										myTurn = false;
										break;
									}
								}
							}
						}
					}
				}
			}
		};
	}
	
	public String getColor(Color color) {
		if (color == Color.RED) {
			return "red";
		} else if (color == Color.BLUE) {
			return "blue";
		} else if (color == Color.YELLOW) {
			return "yellow";
		} else if (color == Color.GREEN) {
			return "green";
		}
		return "empty";
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// Draw the background image and die
		g.drawImage(boardBackground, 13, 13, this);
		g.drawImage(die, 251, 251, this);
		
		// Paint the three zones
		Graphics2D artist = (Graphics2D) g;
		paintSlots(artist, board.getMainZone());
		paintSlots(artist, board.getRedHomeZone());
		paintSlots(artist, board.getRedEndZone());
		paintSlots(artist, board.getGreenHomeZone());
		paintSlots(artist, board.getGreenEndZone());
		paintSlots(artist, board.getYellowHomeZone());
		paintSlots(artist, board.getYellowEndZone());
		paintSlots(artist, board.getBlueHomeZone());
		paintSlots(artist, board.getBlueEndZone());
		
		// Paint the tokens
		paintTokens(artist, board.getRedTokens());
		paintTokens(artist, board.getGreenTokens());
		paintTokens(artist, board.getYellowTokens());
		paintTokens(artist, board.getBlueTokens());
				
		// Paint the die holder
		artist.setColor(new Color(0, 0, 0, Color.OPAQUE));
		artist.draw(dieHolder);
		
	}
	
	/**
	 * Paints the given slots
	 * @param artist A wonderful artist who can also paint main zones
	 * @param slots The set of slots to paint
	 */
	public void paintSlots(Graphics2D artist, ArrayList<Slot> slots) {
		for (Slot slot : slots) {
			artist.setColor(slot.getColor());
			artist.fill(slot.getShape());
			artist.setColor(Color.DARK_GRAY);
			artist.setStroke(new BasicStroke(2));
			artist.draw(slot.getShape());
		}
		
	}
	
	public void paintTokens(Graphics2D artist, ArrayList<Slot> tokens) {
		for (Slot slot : tokens) {
			if (swingUI.getUser().getColor() == slot.getColor()) {
				if (swingUI.getUser().isSelectedSlot(slot)) {
					artist.setColor(slot.getColor());
				} else {
					if (slot.getColor() == Color.RED) {
						artist.setColor(TRANSPARENT_RED);
					} else if (slot.getColor() == Color.GREEN) {
						artist.setColor(TRANSPARENT_GREEN);
					} else if (slot.getColor() == Color.YELLOW) {
						artist.setColor(TRANSPARENT_YELLOW);
					} else {
						artist.setColor(TRANSPARENT_BLUE);
					}
				}
			} else {
				artist.setColor(slot.getColor());
			}
			
			Ellipse2D tmp = (Ellipse2D) slot.getShape();
			Ellipse2D token = new Ellipse2D.Double(tmp.getX(), tmp.getY(), 24, 24);
			token.setFrame(token.getX() + 3, token.getY() + 3, 24, 24);
			artist.fill(token);
		}
	}
	
	public void updateToken(String username, int tokenID, int destZone, int destIndex) {
		String color = players.get(username);
		Slot token = null;
		Color paintColor = null;
		ArrayList<Slot> homeZone = null;
		ArrayList<Slot> endZone = null;
		
		switch (color) {
			case "red":
				token = board.getRedTokens().get(tokenID);
				paintColor = Color.RED;
				homeZone = board.getRedHomeZone();
				endZone = board.getRedEndZone();
				break;
			case "green":
				token = board.getGreenTokens().get(tokenID);
				paintColor = Color.GREEN;
				homeZone = board.getGreenHomeZone();
				endZone = board.getGreenEndZone();
				break;
			case "yellow":
				token = board.getYellowTokens().get(tokenID);
				paintColor = Color.YELLOW;
				homeZone = board.getYellowHomeZone();
				endZone = board.getYellowEndZone();
				break;
			case "blue":
				token = board.getBlueTokens().get(tokenID);
				paintColor = Color.BLUE;
				homeZone = board.getBlueHomeZone();
				endZone = board.getBlueEndZone();
				break;
		}
		
		if (token.getZone() == Board.SLOT_HOME) {
			Slot newLoc = board.getMainZone().get(destIndex);
			token.setSlot(newLoc.getShape(), paintColor);
			token.setZone(Board.SLOT_MAIN);
			token.setIndex(destIndex);
		} else if (token.getZone() == Board.SLOT_MAIN) {
			if (destZone == Board.SLOT_HOME) {
				Slot newLoc = homeZone.get(destIndex);
				token.setSlot(newLoc.getShape(), paintColor);
				token.setZone(Board.SLOT_HOME);
				token.setIndex(destIndex);
			} else if (destZone == Board.SLOT_MAIN) {
				Slot newLoc = board.getMainZone().get(destIndex);
				token.setSlot(newLoc.getShape(), paintColor);
				token.setIndex(destIndex);
			} else if (destZone == Board.SLOT_END) {
				Slot newLoc = endZone.get(destIndex);
				token.setSlot(newLoc.getShape(), paintColor);
				token.setZone(Board.SLOT_END);
				token.setIndex(destIndex);
			}
		}
		
		this.repaint();
	}
	
	/**
	 * Generates an arraylist of Ellipse2D which contains the exact same digit representation for slot numbers
	 * as are found in the server construct of board
	 * @return The filled arraylist of slot shapes for the main zone
	 */
	public void generateMainSlots() {	
		
		int topMoveable, sideMoveable;
		topMoveable = sideMoveable = 131;
		int diff = 47;
		
		// Left Quarter
		board.getMainZone().get(6).setSlot(new Ellipse2D.Double(64, sideMoveable, 30, 30), Color.WHITE);
		board.getMainZone().get(5).setSlot(new Ellipse2D.Double(35, sideMoveable += diff, 30, 30), Color.WHITE);
		board.getMainZone().get(4).setSlot(new Ellipse2D.Double(27, sideMoveable += diff, 30, 30), Color.WHITE);
		board.getMainZone().get(3).setSlot(new Ellipse2D.Double(27, sideMoveable += diff, 30, 30), Color.WHITE);
		board.getMainZone().get(2).setSlot(new Ellipse2D.Double(27, sideMoveable += diff, 30, 30), Color.WHITE);
		board.getMainZone().get(1).setSlot(new Ellipse2D.Double(35, sideMoveable += diff, 30, 30), Color.WHITE);
		board.getMainZone().get(0).setSlot(new Ellipse2D.Double(64, sideMoveable += diff, 30, 30), Color.WHITE);
		
		// Top Quarter
		board.getMainZone().get(7).setSlot(new Ellipse2D.Double(topMoveable, 65, 30, 30), Color.WHITE);
		board.getMainZone().get(8).setSlot(new Ellipse2D.Double(topMoveable += diff, 36, 30, 30), Color.WHITE);
		board.getMainZone().get(9).setSlot(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30), Color.WHITE);
		board.getMainZone().get(10).setSlot(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30), Color.WHITE);
		board.getMainZone().get(11).setSlot(new Ellipse2D.Double(topMoveable += diff, 28, 30, 30), Color.WHITE);
		board.getMainZone().get(12).setSlot(new Ellipse2D.Double(topMoveable += diff, 36, 30, 30), Color.WHITE);
		board.getMainZone().get(13).setSlot(new Ellipse2D.Double(topMoveable += diff, 65, 30, 30), Color.WHITE);
		
		// Right Quarter
		board.getMainZone().get(20).setSlot(new Ellipse2D.Double(482, sideMoveable, 30, 30), Color.WHITE);
		board.getMainZone().get(19).setSlot(new Ellipse2D.Double(511, sideMoveable -= diff, 30, 30), Color.WHITE);
		board.getMainZone().get(18).setSlot(new Ellipse2D.Double(519, sideMoveable -= diff, 30, 30), Color.WHITE);
		board.getMainZone().get(17).setSlot(new Ellipse2D.Double(519, sideMoveable -= diff, 30, 30), Color.WHITE);
		board.getMainZone().get(16).setSlot(new Ellipse2D.Double(519, sideMoveable -= diff, 30, 30), Color.WHITE);
		board.getMainZone().get(15).setSlot(new Ellipse2D.Double(511, sideMoveable -= diff, 30, 30), Color.WHITE);
		board.getMainZone().get(14).setSlot(new Ellipse2D.Double(482, sideMoveable -= diff, 30, 30), Color.WHITE);
		
		// Bottom Quarter
		board.getMainZone().get(21).setSlot(new Ellipse2D.Double(topMoveable, 482, 30, 30), Color.WHITE);
		board.getMainZone().get(22).setSlot(new Ellipse2D.Double(topMoveable -= diff, 511, 30, 30), Color.WHITE);
		board.getMainZone().get(23).setSlot(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30), Color.WHITE);
		board.getMainZone().get(24).setSlot(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30), Color.WHITE);
		board.getMainZone().get(25).setSlot(new Ellipse2D.Double(topMoveable -= diff, 519, 30, 30), Color.WHITE);
		board.getMainZone().get(26).setSlot(new Ellipse2D.Double(topMoveable -= diff, 511, 30, 30), Color.WHITE);
		board.getMainZone().get(27).setSlot(new Ellipse2D.Double(topMoveable -= diff, 482, 30, 30), Color.WHITE);
		
	}
	
	/**
	 * Creates Ellipse2D to use for game endzones
	 * @param artist A wonderful artist who also paints endzones
	 */
	public void generateHomeZones() {
		
		// Red End
		generateDiagonalZone(1, 110, 525, 30, board.getRedHomeZone());
		
		// Green End
		generateDiagonalZone(2, 20, 110, 30, board.getGreenHomeZone());
				
		// Yellow End
		generateDiagonalZone(1, 525, 110, 30, board.getYellowHomeZone());
		
		// Blue End
		generateDiagonalZone(2, 435, 525, 30, board.getBlueHomeZone());
		
	}
	
	/**
	 * Paints user home zones
	 * @param artist A wonderful artist who paints home zones
	 */
	public void generateEndZones() {
		
		// Red Home
		generateDiagonalZone(2, 110, 435, 30, board.getRedEndZone());
		
		// Green Home
		generateDiagonalZone(1, 200, 200, 30, board.getGreenEndZone());
				
		// Yellow Home
		generateDiagonalZone(2, 345, 200, 30, board.getYellowEndZone());
		
		// Blue Home
		generateDiagonalZone(1, 435, 435, 30, board.getBlueEndZone());
		
	}
	
	/**
	 * Adds Ellipse2D to the given arraylist in the given direction (1 = left, 2 = right)
	 */
	public void generateDiagonalZone(int direction, int x, int y, int spacing, ArrayList<Slot> slots) {
		
		int diametre = 30;
		
		for(int i = 0; i < 4; i++) {
			slots.get(i).setSlot(new Ellipse2D.Double(x, y, diametre, diametre), Color.WHITE);
			if(direction == 1) {
				y -= spacing;
				x -= spacing;
			} else if(direction == 2) {
				y -= spacing;
				x += spacing;
			}
		}
		
	}
	
	public void generateTokens() {
		for (Slot token : board.getRedTokens()) {
			Slot slot;
			switch (token.getZone()) {
				case Board.SLOT_HOME:
					slot = board.getRedHomeZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.RED);
					break;
				case Board.SLOT_MAIN:
					slot = board.getMainZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.RED);
					break;
				case Board.SLOT_END:
					slot = board.getRedEndZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.RED);
					break;
			}
		}
		for (Slot token : board.getGreenTokens()) {
			Slot slot;
			switch (token.getZone()) {
				case Board.SLOT_HOME:
					slot = board.getGreenHomeZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.GREEN);
					break;
				case Board.SLOT_MAIN:
					slot = board.getMainZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.GREEN);
					break;
				case Board.SLOT_END:
					slot = board.getGreenEndZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.GREEN);
					break;
			}
		}
		for (Slot token : board.getYellowTokens()) {
			Slot slot;
			switch (token.getZone()) {
				case Board.SLOT_HOME:
					slot = board.getYellowHomeZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.YELLOW);
					break;
				case Board.SLOT_MAIN:
					slot = board.getMainZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.YELLOW);
					break;
				case Board.SLOT_END:
					slot = board.getYellowEndZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.YELLOW);
					break;
			}
		}
		for (Slot token : board.getBlueTokens()) {
			Slot slot;
			switch (token.getZone()) {
				case Board.SLOT_HOME:
					slot = board.getBlueHomeZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.BLUE);
					break;
				case Board.SLOT_MAIN:
					slot = board.getMainZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.BLUE);
					break;
				case Board.SLOT_END:
					slot = board.getBlueEndZone().get(token.getIndex());
					token.setSlot(slot.getShape(), Color.BLUE);
					break;
			}
		}	
	}
	
	public void updateTurn(String username) {
		if (username.equals(swingUI.getUser().getUsername())) {
			myTurn = true;
		} else {
			myTurn = false;
		}
	}
	
	public void setupPlayer(String username, String color) {
		players.put(username, color);
	}
	
	public void setupPanel() {
		String color = players.get(swingUI.getUser().getUsername());
		switch(color) {
			case "red":
				swingUI.getUser().setColor(Color.RED);
				swingUI.getUser().setTokens(board.getRedTokens());
				break;
			case "blue":
				swingUI.getUser().setColor(Color.BLUE);
				swingUI.getUser().setTokens(board.getBlueTokens());
				break;
			case "yellow":
				swingUI.getUser().setColor(Color.YELLOW);
				swingUI.getUser().setTokens(board.getYellowTokens());
				break;
			case "green":
				swingUI.getUser().setColor(Color.GREEN);
				swingUI.getUser().setTokens(board.getGreenTokens());
				break;
			default:
		}
	}
	
}