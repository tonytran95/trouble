package troublegame.client.panels;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 4335278227443750057L;
	
	private JLabel currTurnLabel;
	private JLabel currRollLabel;
	private JLabel timeElapsedLabel;
	
	private int secondsTime;
	private int minutesTime;
	
	public InfoPanel() {
		
		secondsTime = 0;
		minutesTime = 0;
		
		init();
		doTime();
	}
	
	private void init() {
		
		setLayout(null);
		
		timeElapsedLabel = new JLabel();
		timeElapsedLabel.setBounds(0, 0, 410, 79);
		
		currRollLabel = new JLabel("PUT CURR ROLL NUM IN HERE");
		currRollLabel.setBounds(0, 158, 410, 79);
		
		currTurnLabel = new JLabel("PUT CURR COLOR TURN IN HERE");
		currTurnLabel.setBounds(0, 237, 410, 79);
		
		this.add(timeElapsedLabel);
		this.add(currTurnLabel);
		this.add(currRollLabel);
		
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

}
