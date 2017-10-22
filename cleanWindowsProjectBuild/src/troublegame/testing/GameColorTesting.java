package troublegame.testing;

import java.util.ArrayList;

import troublegame.server.Connection;
import troublegame.server.GameEngine;
import troublegame.server.io.UserManager;

public class GameColorTesting {

	public static void main(String[] args) {
		
		GameEngine ge = new GameEngine();
		Connection t = new Connection(null, null, null);
		Connection t1 = new Connection(null, null, null);
		Connection a = new Connection(null, null, null);
		t.setUser(UserManager.loadUserByEmail("test@test.com"));
		t1.setUser(UserManager.loadUserByEmail("test2@test.com"));
		a.setUser(UserManager.loadUserByEmail("asdf@asdf.com"));
		
		ArrayList<Connection> conns = new ArrayList<>();
		conns.add(t);
		conns.add(t1);
		conns.add(a);
		
		ge.createGame(conns, 5);
		
	}

}
