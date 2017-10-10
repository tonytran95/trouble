package troublegame.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * The main class for the trouble game server.
 *
 */
public class GameServer {
	
	/**
	 *  Uppermost directory containing all data files used with the project
	 */
	public static final String DATA_PATH = "./data/";
	
	public static final String USR_PATH = DATA_PATH + "users/";
	
	private static final int TIME_PERIOD = 500;
	
	/**
	 * The port.
	 */
	public final static int PORT = 4321;
	
	/**
	 * The main function.
	 * @param args are the arguments.
	 */
	public static void main(String args[]) {
		GameServer GS = new GameServer();
		Runner gameRunner = GS.new Runner();
		Timer serverTimer = new Timer();
		
		// Test user
		User u = new User("tsmex@hotmail.com", "Tilly", "Smexy", Color.RANDOM);
		GameServer.saveUser(u);
		
		User asdf = GameServer.loadUser("tsmex@hotmail.com");
		System.out.println("This is a test for the user Tilly");
		System.out.println("Loaded user tilly has username: " + asdf.getUsername());
		System.out.println("password: " + asdf.getPassword());
		System.out.println("and email: " + asdf.getEmail());
		
		serverTimer.schedule(gameRunner, 0, TIME_PERIOD);
	}
	
	/**
	 * Load the existing user with the given email from the associated data file.
	 * @return null if user/file not found, otherwise the found user
	 */
	public static User loadUser(String email) {
		
		email = email.toLowerCase();
		File userProfile = new File(USR_PATH + "/" + email.hashCode() + ".ser");
		if (userProfile.exists() == false) {
			return null;
		}
		
		User tmp = null;
		
		try {

			// Try read in and open the requested list
			FileInputStream fileIn = new FileInputStream(userProfile);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			// Read in object and cast to the specified object
			tmp = (User) in.readObject();
			in.close();
			fileIn.close();

		} catch (IOException e) {
			// Catch input output error
			System.out.println(e.toString());

		} catch (ClassNotFoundException e) {
			// Catch class not found error, display class not found error
			// message
			System.out.println(e.toString());
		}

		return tmp;
		
	}
	
	/**
	 * Save the given user to a data file
	 * @return false if user/file not saved successfully, otherwise true
	 */
	public static boolean saveUser(User user) {
		
		// Create file paths for the list
		File userDirectory = new File(USR_PATH);
		File userProfile = new File(USR_PATH + "/" + user.getEmail().hashCode() + ".ser");
		
		// Check if the user directory exists or can be created
		if (userDirectory.isDirectory() == false) {
			if (userDirectory.mkdirs() == false) {
				return false;
			}
		}
		
		try {

			// Create user file if it doesn't exist
			userProfile.createNewFile();

			// Write the this list object to the file
			FileOutputStream fileOutput = new FileOutputStream(userProfile);
			ObjectOutputStream outStream = new ObjectOutputStream(fileOutput);

			outStream.writeObject(user);
			outStream.close();
			fileOutput.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return true;
		
	}
	
	/**
	 * The socket listener.
	 */
	private SocketListener socketListener;
	
	/**
	 * The game engine.
	 */
	private GameEngine gameEngine;
	
	/**
	 * The login handler.
	 */
	private LoginHandler loginHandler;
	
	/**
	 * Constructs a new server.
	 */
	public GameServer() {
		System.out.println("[Game Server] Starting game server...");
		
		/**
		 * Initializes the game engine.
		 */
		this.gameEngine = new GameEngine();
		this.gameEngine.init();
		
		/**
		 * Initializes the socket listener and login handler.
		 */
		this.socketListener = new SocketListener(GameServer.PORT);
		this.loginHandler = new LoginHandler(gameEngine);
		this.socketListener.setLoginHandler(loginHandler);
		this.socketListener.init();
		
		/**
		 * Add the gameengine to the socketListener (temporary)
		 */
		this.socketListener.addGameEngine(this.gameEngine);
	}

	/**
	 * @return the socket listener.
	 */
	public SocketListener getSocketListener() {
		return socketListener;
	}
	
	class Runner extends TimerTask {
		public void run() {
			gameEngine.process();
			loginHandler.process();
		}
	}
}

