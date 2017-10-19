package troublegame.communication;

/**
 * Class holding all constants required for server/client communication
 * @author Nick
 */
public final class CommunicationHandler {
	
	// Socket Listener
	public static final String SOCKET_LISTENER_INFO = "[SOCKET_LISTENER_INFO]";
	
	// Game Engine
	public static final String GAME_ENGINE_INFO = "[GAME_ENGINE_INFO]";
	
	// Game Server
	public static final String GAME_SERVER_INFO = "[GAME_SERVER_INFO]";
	
	// Lobby 
	public static final String LOBBY_CHAT = "[LOBBY_CHAT]";
	public static final String LOBBY_ONLINE_LIST = "[LOBBY_ONLINE_LIST]";
	
	// Login constants
	public static final String LOGIN_REQUEST = "[LOGIN]";
	public static final String LOGIN_SUCCESS = "[LOGIN_SUCCESS]";
	public static final String LOGIN_ERROR = "[LOGIN_ERROR]";
	public static final String LOGIN_GUEST = "[LOGIN_GUEST]";
	
	// Logout constants
	public static final String LOGOUT_REQUEST = "[LOGOUT]";
	public static final String LOGOUT_SUCCESS = "[LOGOUT_SUCCESS]";
	public static final String LOGOUT_ERROR = "[LOGOUT_ERROR]";
	
	// Game room constants
	public static final String GAME_ROOM_OPEN = "[GAME_ROOM_OPEN]";
	public static final String GAME_ROOM_QUERY = "[GAME_ROOM_QUERY]";
	public static final String GAME_ROOM_NEW = "[GAME_ROOM_NEW]";
	public static final String GAME_ROOM_CHAT = "[GAME_ROOM_CHAT]";
	public static final String GAME_ROOM_INFO = "[GAME_ROOM_INFO]";
	public static final String GAME_ROOM_JOIN = "[GAME_ROOM_JOIN]";
	public static final String GAME_ROOM_LEAVE = "[GAME_ROOM_LEAVE]";
	public static final String GAME_ROOM_CLOSE = "[GAME_ROOM_CLOSE]";
	public static final String GAME_ROOM_MEMBER = "[GAME_ROOM_MEMBER]";
	
	// Game constants
	public static final String GAME_INFO = "[GAME_INFO]";
	public static final String GAME_ROLL = "[GAME_ROLL]";
	public static final String GAME_START = "[GAME_START]";
	public static final String GAME_START_FAIL = "[GAME_START_FAIL]";
	public static final String GAME_TURN = "[GAME_TURN]";
	public static final String GAME_COLORS = "[GAME_COLORS]";
	public static final String GAME_SETUP = "[GAME_SETUP]";
	public static final String GAME_ROLL_SUCCESS = "[GAME_ROLL_SUCCESS]";
	public static final String GAME_ROLL_AGAIN = "[GAME_ROLL_AGAIN]";
	public static final String GAME_ROLL_FAIL = "[GAME_ROLL_FAIL]";
	public static final String GAME_EAT_TOKEN = "[GAME_EAT_TOKEN]";
	public static final String GAME_CHAT= "[GAME_CHAT]";
	
	// user profile
	public static final String UPDATE_DISPLAYNAME= "[UPDATE_DISPLAYNAME]";
	public static final String UPDATE_FAIL = "[UPDATE_FAIL]";
	public static final String UPDATE_SUCCESS = "[UPDATE_SUCCESS]";
	public static final String GET_STATISTICS = "[GET_STATISTICS]";
	
	// Registration constants
	public static final String REGISTER_REQUEST = "[REGISTER_REQUEST]";
	public static final String REGISTER_ERROR = "[REGISTER_ERROR]";
	public static final String REGISTER_SUCCESS = "[REGISTER_SUCCESS]";
}
