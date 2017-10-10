package troublegame.server;

import java.io.File;

public class UserList extends DataList<User> {

	private static final long serialVersionUID = -5460531975773475433L;

	/**
	 * @return The User list. If no list exists a new list is generated and
	 *         returned
	 */
	public static UserList getUserList() {

		File dataList = new File(GameServer.USER_LIST);
		
		if (dataList.exists()) {
			DataList<?> list = (new UserList()).loadDataList();
			return (UserList) list;
		} else {
			UserList list = new UserList();
			list.saveList();
			return list;
		}
	}

	/**
	 * Get the user from this list with the given email
	 *
	 * @param email
	 *            The email of the user to get
	 * @return The user if they were found, null if they were not
	 */
	public User getUser(String email) {

		User toGet = null;
		email = email.toLowerCase();

		for (User user : this) {

			if (user.getEmail().equals(email)) {
				toGet = user;
				break;
			}

		}

		return toGet;

	}

	@Override
	protected String getFilePath() {
		return GameServer.USER_LIST;
	}

}
