package troublegame.communication;

/**
 * Class holding all constants required for server/client communication
 * @author Nick
 */
public final class CommunicationHandler {
	
	// Login constants
	public static final String LOGIN_REQUEST = "[LOGIN]";
	public static final String LOGIN_SUCCESS = "[LOGIN_SUCCESS]";
	public static final String LOGIN_ERROR = "[LOGIN_ERROR]";
	
	// Logout constants
	public static final String LOGOUT_REQUEST = "[LOGOUT]";
	public static final String LOGOUT_SUCCESS = "[LOGOUT_SUCCESS]";
	public static final String LOGOUT_ERROR = "[LOGOUT_ERROR]";
	
	// Game room constants
	public static final String GAME_ROOM_OPEN = "[GAME_ROOM_OPEN]";
	public static final String GAME_ROOM_NEW = "[GAME_ROOM_NEW]";
	public static final String GAME_ROOM_CHAT = "[GAME_ROOM_CHAT]";
	public static final String GAME_ROOM_INFO = "[GAME_ROOM_INFO]";
	public static final String GAME_ROOM_JOIN = "[GAME_ROOM_JOIN]";
	public static final String GAME_ROOM_LEAVE = "[GAME_ROOM_LEAVE]";
	public static final String GAME_ROOM_CLOSE = "[GAME_ROOM_CLOSE]";
	
	// Game constants
	public static final String GAME_ROLL = "[GAME_ROLL]";
	public static final String GAME_START = "[GAME_START]";
	public static final String GAME_TURN = "[GAME_TURN]";
	public static final String GAME_COLORS = "[GAME_COLORS]";
	public static final String GAME_ROLL_SUCCESS = "[GAME_ROLL_SUCCESS]";
	public static final String GAME_ROLL_AGAIN = "[GAME_ROLL_AGAIN]";
	public static final String GAME_ROLL_FAIL = "[GAME_ROLL_FAIL]";
	public static final String GAME_EAT_TOKEN = "[GAME_EAT_TOKEN]";
	
	
}
