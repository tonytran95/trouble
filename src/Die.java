
public class Die {
	
	private static final int ROLLING = 1;
	private static final int ROLLED = 0;
	
	private int state;
	private int value;
	
	public Die() {
		state = ROLLED;
		value = 6;
	}
	
}
