package troublegame.client.panels;


import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GameChatPanel extends JPanel {

	private static final long serialVersionUID = 3532807987664167900L;
	
	/**
	 * The chat text area.
	 */
	private JTextArea chatMessages;
	
	public GameChatPanel() {
		this.setBackground(Color.RED);
	}
	
	/**
	 * Handles the chat message by reading the text field
	 * @param message is the string being sent to that chat
	 */
	public void sendChatMessage(String message) {
		if (message.equals(""))
			return;
		chatMessages.append(message+"\n");
	}

}
