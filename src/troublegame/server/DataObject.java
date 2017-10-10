package troublegame.server;

import java.io.Serializable;

public abstract class DataObject implements Serializable {

	private static final long serialVersionUID = 8981774097362532018L;

	// Unique id number. Corresponds to the items place in the data list
	private int id;

	/**
	 * @return The data objects id number
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Set the id of the data object
	 * 
	 * @param id
	 *            The id for this data object
	 */
	private void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets a unique id for the current data object. If an existing data object
	 * of the same type is given, the id is set to be that of the object
	 * Otherwise the id is set to be equal to the objects place in its data list
	 * 
	 * @param existing
	 *            An existing data object. If null will generate a new unique id
	 */
	public boolean setId(DataObject existing) {
		DataList<?> list = null;

		if (this instanceof User)
			list = UserList.getUserList();

		if (list == null)
			return false;

		if (list.size() == 0) {
			setId(0);
		} else {
			int index = list.indexOf(existing);
			if (index == -1)
				setId(list.size());
			else
				setId(index);
		}

		return true;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DataObject))
			return false;
		DataObject other = (DataObject) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
