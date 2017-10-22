package troublegame.client.panels;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

import troublegame.client.SwingUI;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 4335278227443750057L;
	
	private SwingUI swingUI;
	
	private JLabel currTurnLabel;
	private JLabel timeElapsedLabel;
	
	private JLabel playersLabel;
	private JLabel redPlayerLabel;
	private JLabel greenPlayerLabel;
	private JLabel yellowPlayerLabel;
	private JLabel bluePlayerLabel;
	
	private int secondsTime;
	private int minutesTime;
	
	public InfoPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		
		secondsTime = 0;
		minutesTime = 0;

		init();
		doTime();
	}
	
	private void init() {
		setLayout(null);
		
		Font titleFont = new Font("Tahoma", Font.BOLD, 18);
		Font playerFont = new Font("Tahoma", Font.PLAIN, 12);
		
		timeElapsedLabel = new JLabel();
		timeElapsedLabel.setFont(titleFont);
		timeElapsedLabel.setBounds(0, 40, 410, 20);
		
		playersLabel = new JLabel("Players");
		playersLabel.setFont(titleFont);
		playersLabel.setBounds(0, 80, 410, 20);
		redPlayerLabel = new JLabel("");
		redPlayerLabel.setFont(playerFont);
		redPlayerLabel.setBounds(0, 100, 410, 20);
		greenPlayerLabel = new JLabel("");
		greenPlayerLabel.setFont(playerFont);
		greenPlayerLabel.setBounds(0, 120, 410, 20);
		yellowPlayerLabel = new JLabel("");
		yellowPlayerLabel.setFont(playerFont);
		yellowPlayerLabel.setBounds(0, 140, 410, 20);
		bluePlayerLabel = new JLabel("");
		bluePlayerLabel.setFont(playerFont);
		bluePlayerLabel.setBounds(0, 160, 410, 20);
		
		currTurnLabel = new JLabel("Current turn: ");
		currTurnLabel.setFont(titleFont);
		currTurnLabel.setBounds(0, 200, 410, 20);
		
		this.add(timeElapsedLabel);
		this.add(playersLabel);
		this.add(redPlayerLabel);
		this.add(greenPlayerLabel);
		this.add(yellowPlayerLabel);
		this.add(bluePlayerLabel);
		this.add(currTurnLabel);
	}
	
	private void doTime() {
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				secondsTime++;
				if(secondsTime == 60) {
					secondsTime = 0;
					minutesTime++;
				}
				String seconds = "";
				if(secondsTime < 10) {
					seconds = "0" + secondsTime;
				} else {
					seconds = String.valueOf(secondsTime);
				}
				String minutes = "";
				if(minutesTime < 10) {
					minutes = "0" + minutesTime;
				} else {
					minutes = String.valueOf(minutesTime);
				}
				
				timeElapsedLabel.setText("Time in game: " + minutes + ":" + seconds);
				
			}
		}, 0, 1000);
		
	}
	
	public void updateTurn(String username) {
		currTurnLabel.setText("Current turn: " + username);
	}
	
	public void setupPlayer(String username, String color) {
		if (swingUI.getUser().getUsername().equals(username)) username = username + " (YOU)";
		if (color.equals("red")) {
			redPlayerLabel.setText(username);
			redPlayerLabel.setForeground(Color.RED);
		} else if (color.equals("green")) {
			greenPlayerLabel.setText(username);
			greenPlayerLabel.setForeground(Color.GREEN);
		} else if (color.equals("yellow")) {
			yellowPlayerLabel.setText(username);
			yellowPlayerLabel.setForeground(Color.YELLOW);
		} else {
			bluePlayerLabel.setText(username);
			bluePlayerLabel.setForeground(Color.BLUE);
		}
	}

}
