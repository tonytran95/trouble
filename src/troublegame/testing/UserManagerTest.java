package troublegame.testing;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import troublegame.server.Color;
import troublegame.server.User;
import troublegame.server.io.UserManager;

/**
 * Tester class to remove all information from the user directory and 
 * create new test users to test the program from a clean slate 
 * @author Nick
 */
public class UserManagerTest {
	
	private ArrayList<UUID> testArray;
	
	public static void main(String[] args) {
		
		UserManagerTest tum = new UserManagerTest();
		tum.removeExistingUsers();
		tum.addNewUsers();
		tum.addFriendships();
		tum.printUserDetails();
	}
	
	/**
	 * Removes all files within the user directory
	 */
	public void removeExistingUsers() {
		
		File usrDir = new File(UserManager.USER_PATH);
		for(File f : usrDir.listFiles()) {
			f.delete();
		}
		
	}
	
	/**
	 * Create new users and save them in the user directory
	 */
	public void addNewUsers() {
		
		testArray = new ArrayList<>();
		
		UserManager.createAndSaveNewUser("test@test.com", "Victor", "test", Color.RANDOM, "Life is a highway");
		testArray.add(UserManager.loadUserByEmail("test@test.com").getId());
		
		UserManager.createAndSaveNewUser("test2@test.com", "Victor2", "test", Color.RANDOM, "Life is a");
		testArray.add(UserManager.loadUserByEmail("test2@test.com").getId());
		
		UserManager.createAndSaveNewUser("test3@test.com", "Victor3", "test", Color.RANDOM, "Life is");
		testArray.add(UserManager.loadUserByEmail("test3@test.com").getId());
		
		UserManager.createAndSaveNewUser("test4@test.com", "Victor4", "test", Color.RANDOM, "Life");
		testArray.add(UserManager.loadUserByEmail("test4@test.com").getId());
		
		UserManager.createAndSaveNewUser("test5@test.com", "Victor5", "test", Color.RANDOM, "L");
		testArray.add(UserManager.loadUserByEmail("test5@test.com").getId());
		
		UserManager.createAndSaveNewUser("bob@bob.com", "Pirate103", "bob", null, null);
		testArray.add(UserManager.loadUserByEmail("bob@bob.com").getId());
		
		UserManager.createAndSaveNewUser("asdf@asdf.com", "ASS-DUFF-MAN", "asdf", Color.BLUE, "Pizza rocks");
		testArray.add(UserManager.loadUserByEmail("asdf@asdf.com").getId());
		
		UserManager.createAndSaveNewUser("qwerty@qwerty.com", "KeyboardPlayer", "qwerty", Color.GREEN, "#UNSW4Lyf");
		testArray.add(UserManager.loadUserByEmail("qwerty@qwerty.com").getId());
		
		UserManager.createAndSaveNewUser("nicholas.everton@hotmail.com", "Invindicator", "nick", Color.RED, "I love the beatles");
		testArray.add(UserManager.loadUserByEmail("nicholas.everton@hotmail.com").getId());
		
	}
	
	public void addFriendships() {
		
		User tilly = UserManager.loadUserByEmail("test@test.com");
		
		User bobby = UserManager.loadUserByEmail("bob@bob.com");
		
		User asdf = UserManager.loadUserByEmail("asdf@asdf.com");
		bobby.addFriend(tilly);
		bobby.addFriend(asdf);
		bobby.addFriend(bobby);
		bobby.updateEmail("itsAPiratesLifeForMe@gmail.com");
		bobby.updateUsername("Jack Sparrow");
		
	}
	
	public void printUserDetails() {
		
		for(UUID userId : testArray) {
			User tmp = UserManager.loadUserById(userId);
			System.out.println(tmp.toString());
			System.out.println("\n");
		}
		
	}
	
}
