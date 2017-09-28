package game.trouble.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TroubleClient {

	private static BufferedReader in;
    private static PrintWriter out;
	
	public static void main(String[] args) {		
		try {
			Socket socket = new Socket("localhost", 4321);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    out = new PrintWriter(socket.getOutputStream(), true);
			SwingUI ui = new SwingUI(in , out);
		    while (true) {
		    	
		    }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
