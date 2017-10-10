package troublegame.server.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import troublegame.server.Connection;

public class PlayerManager {
	
	public static final String DIRECTORY = "./data/users";
	public static final int LOAD_FAIL = 0;
	public static final int LOAD_SUCCESS = 1;
	public static final int LOAD_BADREAD = 2;
	public static final int LOAD_INVALIDPASS = 3;
	
	public int loadPlayer(Connection conn) {
		
		String line = "";
		String token = "";
		String token2 = "";
		int ReadMode = 0;
		BufferedReader userfile = null;
		boolean userFileFound = false;
		boolean EndOfFile = false;
		String playerName = conn.getUsername();
		String playerPass = conn.getPassword();
		
		try {
			userfile = new BufferedReader(new FileReader(DIRECTORY+playerName));
			userFileFound = true;
		} catch(FileNotFoundException f) {		
		}
		
		if (!userFileFound) {
			return LOAD_FAIL;
		}
		
		try {
			line = userfile.readLine();
		} catch(IOException ioexception) {
			System.out.println(playerName+": error loading file.");
			return LOAD_BADREAD;
		}
		
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				
				switch (ReadMode) {
					case 1:
						 if (token.equals("player-username")) {
							if (playerPass.equalsIgnoreCase(token2)) {
								playerPass = token2;
							} else {
								return LOAD_INVALIDPASS;
							}
						}
						break;
				}
			} else {
				if (line.equals("[ACCOUNT]")) {		ReadMode = 1;
				} else if (line.equals("[FRIENDS]")) {		ReadMode = 2;
				} else if (line.equals("[EOF]")) {		try { userfile.close(); } catch(IOException ioexception) { } return 1;
				}
			}
			try {
				line = userfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
			
		}
		try { userfile.close(); } catch(IOException ioexception) { }
		
		return LOAD_SUCCESS;
	}
}
