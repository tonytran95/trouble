package game.trouble;
import java.util.Random;

public class Die {
	
	private int value;
	
	public Die() {
		value = 6;
	}
	
	/**
	 * Rolls the die
	 * @return The value that was rolled
	 */
	public int rollRie() {
		return new Random().nextInt(6) + 1;
	}
	
}
