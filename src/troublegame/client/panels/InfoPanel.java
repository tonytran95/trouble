package troublegame.client.panels;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import troublegame.client.SwingUI;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 4335278227443750057L;
	
	private SwingUI swingUI;
	private JLabel timeElapsedLabel;
	
	private JLabel playersLabel;
	private JLabel redPlayerLabel;
	private JLabel greenPlayerLabel;
	private JLabel yellowPlayerLabel;
	private JLabel bluePlayerLabel;
	private DefaultListModel<String> playerListModel;
	private JList<String> playerList;
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
		
		timeElapsedLabel = new JLabel();
		timeElapsedLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		timeElapsedLabel.setBounds(0, 40, 410, 20);
		
		playersLabel = new JLabel("Players");
		playersLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		playersLabel.setBounds(0, 98, 84, 20);
		redPlayerLabel = new JLabel("@");
		redPlayerLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		redPlayerLabel.setBounds(86, 102, 19, 19);
		redPlayerLabel.setForeground(Color.RED);
		greenPlayerLabel = new JLabel("@");
		greenPlayerLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		greenPlayerLabel.setBounds(86, 122, 19, 19);
		greenPlayerLabel.setForeground(Color.GREEN);
		yellowPlayerLabel = new JLabel("@");
		yellowPlayerLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		yellowPlayerLabel.setBounds(86, 142, 19, 19);
		yellowPlayerLabel.setForeground(new Color(255, 215, 0));
		bluePlayerLabel = new JLabel("@");
		bluePlayerLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		bluePlayerLabel.setBounds(86, 162, 19, 19);
		bluePlayerLabel.setForeground(Color.BLUE);
		
		
		playerListModel = new DefaultListModel<String>();
		
		playerList = new JList<String>(playerListModel);
		playerList.setEnabled(false);
		playerList.setBounds(117, 101, 132, 79);
		add(playerList);
		
		this.add(timeElapsedLabel);
		this.add(playersLabel);
		this.add(redPlayerLabel);
		this.add(greenPlayerLabel);
		this.add(yellowPlayerLabel);
		this.add(bluePlayerLabel);
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
				
				timeElapsedLabel.setText("Game Time: " + minutes + ":" + seconds);
				
			}
		}, 0, 1000);
		
	}
	
	public void updateTurn(String username) {
		//currTurnLabel.setText("Current turn: " + username);
		if (swingUI.getUser().getUsername().equals(username)) username = username + " (YOU)";
		ListSelectionModel sm = playerList.getSelectionModel();
		sm.clearSelection();
		playerList.setSelectedIndex(getIndex(username));
	}
	
	public void setupPlayer(String username, String color) {
		if (swingUI.getUser().getUsername().equals(username)) username = username + " (YOU)";
		addUser(username);
	}

	public int getIndex(String username) {
		return playerListModel.indexOf(username);
	}
	/**
	 * Adds the user to our user list.
	 * @param username the username to add.
	 */
	public void addUser(String username) {
		playerListModel.addElement(username);
	}
	
	/**
	 * Removes the users from our user list.
	 * @param username is the name to remove.
	 */
	public void removeUser(String username) {
		playerListModel.removeElement(username);
	}
	
}
