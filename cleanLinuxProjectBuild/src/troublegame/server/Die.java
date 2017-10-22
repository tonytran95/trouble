package troublegame.server;
import java.util.Random;

/**
 * A Die (Dice) object. Can be rolled to generate numbers for classical board games
 * @author Nick, Jeffrey, Michael, Mark, Tony
 */
public class Die {
	
	// The last rolled value
	private int value;
	
	/**
	 * Creates a default die which is rolled upon creation to generate an initial value
	 */
	public Die() {
		value = rollDie();
	}
	
	/**
	 * Rolls the die and updates the value of the previous roll to be this roll
	 * @return The value that was rolled
	 */
	public int rollDie() {
		value = new Random().nextInt(6) + 1;
		return value;
	}
	
	/**
	 * Get the value last rolled on this die without re-rolling
	 * @return The last value rolled on this die
	 */
	public int getLastRolledValue() {
		return value;
	}
	
}
