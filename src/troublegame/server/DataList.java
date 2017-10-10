package troublegame.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class DataList<E extends DataObject> extends ArrayList<E> {

	// Unique serial ID used for serialization
	private static final long serialVersionUID = -1746106713227820469L;

	/**
	 * @return Path for the file of the current accessing object
	 */
	abstract protected String getFilePath();

	/**
	 * Add a new element to this data list at the index specified by the id
	 * number. Id number will correlate to the item number in the list
	 * 
	 * @param e
	 *            The element to add to the list
	 * @param id
	 *            The id of the item being inserted
	 * @return true if the item was added to the list, false otherwise
	 */
	public boolean addAndSave(E e) {

		boolean success = false;
		int id = e.getId();

		if (id == size()) {
			add(e);
			success = true;
		} else if (id >= 0 && id < size()) {
			if (get(id) == null) {
				set(id, e);
				success = true;
			}
		}

		if (success)
			saveList();
		return success;
	}

	/**
	 * Remove the given element from the list and replace its value with null
	 * This space will be reassigned later to new objects as they are created
	 * 
	 * @param e
	 *            The element to remove
	 * @return true if the element was removed successfully, false otherwise
	 */
	public boolean removeAndSave(E e) {
		boolean success = false;

		int index = indexOf(e);
		if (index != e.getId())
			return success;

		if (index != -1) {
			set(index, null);
			success = true;
		}

		if (success)
			saveList();
		return true;
	}

	/**
	 * Updates old element to be the new element
	 * 
	 * @param oldElement
	 *            The element to update
	 * @param updatedElement
	 *            The updated element
	 * @return true if the update was successful
	 */
	public boolean updateElement(E oldElement, E updatedElement) {
		boolean success = false;

		updatedElement.setId(oldElement);

		int index = indexOf(oldElement);

		if (index != oldElement.getId() || index != updatedElement.getId())
			return success;

		if (index != -1) {
			set(index, updatedElement);
			success = true;
		}

		if (success)
			saveList();
		return success;
	}

	/**
	 * Gets the item at the given index in the array
	 * 
	 * @param id
	 *            Id of the item to get
	 * @return The item at the given index in the data list
	 */
	public DataObject getItemById(int id) {
		if (id < size())
			return get(id);
		else
			return null;
	}

	/**
	 * Saves the datalist to a file
	 *
	 * @return true if the list was saved successfully. false otherwise
	 */
	protected boolean saveList() {

		// Create file paths for the list
		File dir = new File(GameServer.DATA_PATH);
		File file = new File(getFilePath());

		// Check if the offer directory exists or can be created
		if (dir.isDirectory() == false) {
			if (dir.mkdirs() == false) {
				return false;
			}
		}

		try {

			// Create list file if it doesn't exist
			file.createNewFile();

			// Write the this list object to the file
			FileOutputStream fileOutput = new FileOutputStream(file);
			ObjectOutputStream outStream = new ObjectOutputStream(fileOutput);

			outStream.writeObject(this);
			outStream.close();
			fileOutput.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}

		return true;

	}

	/**
	 * Loads the data list at FILE_PATH
	 *
	 * @return The data list if it exists. null if it doesnt
	 */
	protected DataList<?> loadDataList() {

		File file = new File(getFilePath());
		if (file.exists() == false) {
			return null;
		}

		DataList<?> tmp = null;

		try {

			// Try read in and open the requested list
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			// Read in object and cast to the specified object
			tmp = (DataList<?>) in.readObject();
			in.close();
			fileIn.close();

		} catch (IOException e) {
			// Catch input output error
			System.out.println(e.toString());

		} catch (ClassNotFoundException e) {
			// Catch class not found error, display class not found error
			// message
			System.out.println(e.toString());
		}

		return tmp;
	}

}
