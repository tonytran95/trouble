package game.trouble;
import java.util.Random;

/**
 * A Die (Dice) object. Can be rolled to generate numbers for classical board games
 * @author Nick, Jeffrey, Michael, Mark, Tony
 */
public class Die {
	
	// The last rolled value
	private int value;
	
	/**
	 * Creates a default die with the value set to -1
	 */
	public Die() {
		value = -1;
	}
	
	/**
	 * Rolls the die and updates the value of the previous roll to be this roll
	 * @return The value that was rolled
	 */
	public int rollRie() {
		value = new Random().nextInt(6) + 1;
		return value;
	}
	
	/**
	 * Get the value last rolled on this die without re-rolling
	 * @return The last value rolled on this die. If die has not yet been rolled, -1 will be returned
	 */
	public int getLastRolledValue() {
		return value;
	}
	
}
