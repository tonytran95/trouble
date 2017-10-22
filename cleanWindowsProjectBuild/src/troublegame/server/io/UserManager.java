package troublegame.server.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.regex.Pattern;

import troublegame.server.Color;
import troublegame.server.User;

/**
 * Player manager to load existing, and save new and existing user profiles using serialisation.
 * @author Nick
 *
 */
public class UserManager {
	
	/**
	 *  Directory containing all user data files
	 */
	public static final String USER_PATH = "./data/users/";
	
	/**
	 * Error codes
	 */
	public static final int SUCCESS = 0;
	public static final int EMAIL_EXISTS = 1;
	public static final int DIRECTORY_ERROR = 2;
	public static final int ACCOUNT_NOT_FOUND = 3;
	public static final int DELETION_ERROR = 4;
	public static final int RENAME_ERROR = 5;
	
	/**
	 * Load the existing user with the given email from the associated data file.
	 * @return null if user/file not found, otherwise the found user
	 */
	public static User loadUserByEmail(String email) {
		
		File[] profileList = new File(USER_PATH).listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(Pattern.matches("^" + email.toLowerCase().hashCode() + " .+\\.ser$", name)) return true;
				return false;
			}
		});
		
		if(profileList.length != 1) {
			return null;
		}
		
		File userProfile = profileList[0];
		
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
	 * Load the existing user with the given id from the associated data file.
	 * @return null if user/file not found, otherwise the found user
	 */
	public static User loadUserById(UUID id) {
		
		File[] profileList = new File(USER_PATH).listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(Pattern.matches("^.+ " + id + "\\.ser$", name)) return true;
				return false;
			}
		});

		if (profileList.length != 1) {
			return null;
		}
		
		File userProfile = profileList[0];
		
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
	 * Save the given, existing user to a data file
	 * @return PlayerManagerErrorCode relating to the status of this operation
	 */
	public static int saveExistingUser(User user) {
		
		String email = user.getEmail();
		int emailHash = email.hashCode();
		
		// Create file paths for the list
		File userDirectory = new File(USER_PATH);
		File userProfile = new File(USER_PATH + emailHash + " " + user.getId() + ".ser");
		
		// Check if the user directory exists or can be created
		if (userDirectory.isDirectory() == false) {
			if (userDirectory.mkdirs() == false) {
				return DIRECTORY_ERROR;
			}
		}
		
		// Check that user file already exists
		if(userProfile.exists() == false) {
			return ACCOUNT_NOT_FOUND;
		}
		
		try {

			// Write the this list object to the file
			FileOutputStream fileOutput = new FileOutputStream(userProfile);
			ObjectOutputStream outStream = new ObjectOutputStream(fileOutput);

			outStream.writeObject(user);
			outStream.close();
			fileOutput.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return SUCCESS;
		
	}
	
	/**
	 * Create a new user given the current properties received from the client, check that the
	 * a user doesn't already exist with this email address and then save the user to a data file
	 * @return PlayerManagerErrorCode relating to status of the operation
	 */
	public static int createAndSaveNewUser(String email, String username, String password, Color favColor, String pQuot) {
		
		// Create the new user
		email = email.toLowerCase();
		User tmp = new User(email, username, password, favColor, pQuot);
		
		int emailHash = email.hashCode();
		
		// Create file paths for the list
		File userDirectory = new File(USER_PATH);
		File userProfile = new File(USER_PATH + emailHash + " " + tmp.getId() + ".ser");
		
		// Check if the user directory exists or can be created
		if (userDirectory.isDirectory() == false) {
			if (userDirectory.mkdirs() == false) {
				return DIRECTORY_ERROR;
			}
		}
		
		// Check that a user with this email doesnt exist already
		File[] existingUsers = userDirectory.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(Pattern.matches("^" + emailHash + " .+\\.ser$", name)) return true;
				return false;
			}
		});
		
		if(existingUsers != null && existingUsers.length != 0) return EMAIL_EXISTS;
		
		try {

			// Create user file if it doesn't exist
			userProfile.createNewFile();

			// Write the this list object to the file
			FileOutputStream fileOutput = new FileOutputStream(userProfile);
			ObjectOutputStream outStream = new ObjectOutputStream(fileOutput);

			outStream.writeObject(tmp);
			outStream.close();
			fileOutput.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return SUCCESS;
		
	}
	
	/**
	 * Renames an existing user file to a new name provided the new name file does not already exist
	 * @param oldEmail The original email of the user to migrate
	 * @param u The user to migrate
	 * @return PlayerManagerErrorCode relating to the status of the operation
	 */
	public static int migrateUser(String currentEmail, User user) {
		
		Path currentFile = Paths.get(USER_PATH + currentEmail.toLowerCase().hashCode() + " " + user.getId() + ".ser");
		try {
			Files.move(currentFile, currentFile.resolveSibling(user.getEmail().hashCode() + " " + user.getId() + ".ser"));
		} catch (IOException e) {
			e.printStackTrace();
			return RENAME_ERROR;
		}
		
		return SUCCESS;
		
	}
	
	/**
	 * Delete the user data for the given email address 
	 * @param email The email of the user to delete
	 * @return PlayerManagerErrorCode relating to the status of the operation
	 */
	public static int deleteUserAccount(User toDelete) {
		
		String email = toDelete.getEmail();
		String profile = USER_PATH + email.hashCode() + " " + toDelete.getId() + ".ser";
		File userProfile = new File(profile);
		
		if (userProfile.exists() == false) {
			return ACCOUNT_NOT_FOUND;
		} else {
			
			// Remove connection from all friends
			for(UUID friendID : toDelete.getFriendList()) {
				User friend = loadUserById(friendID);
				friend.removeFriend(toDelete.getId());
			}
			
			if(userProfile.delete()) return SUCCESS;
			return DELETION_ERROR;
		}
		
	}
	
}
