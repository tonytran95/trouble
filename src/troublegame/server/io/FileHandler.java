package troublegame.server.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FileHandler {

	/**
	 * The map of savables.
	 */
	public static Map<Savable, FileHandler> files = new HashMap<Savable, FileHandler>();

	/**
	 * The file.
	 */
	private File file;
	
	/**
	 * The attributes are stored in the map.
	 */
	private Map<String, String> attributes;
	
	/**
	 * Constructs a new file save class.
	 * @param path is the location of the file.
	 */
	public FileHandler(File file) {
		this.file = file;
		this.attributes = new HashMap<String, String>();
	}
	
	/**
	 * Saves the file in a the path that is given.
	 */
	public void save(Savable savable) {
		savable.save(this);
		FileWriter out = null;
		BufferedWriter bw = null;
		try {
			out = new FileWriter(file);
			bw = new BufferedWriter(out);
			for (Entry<String, String> entry : attributes.entrySet()) {
				bw.write(entry.getKey() + ":" + entry.getValue());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the file in a path that is given.
	 */
	@SuppressWarnings("resource")
	public boolean load(Savable savable) {
		FileReader in = null;
		BufferedReader br = null;
		try {
			in = new FileReader(file);
			br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				String[] lineSplit = line.split(":");
				this.attributes.put(lineSplit[0], lineSplit[1]);
			}
		} catch (IOException e) {
			return false;
		} finally {

			try {

				if (br != null)
					br.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Adds a new object to the map.
	 * @param attribute is the object name.
	 * @param value is the value of the object.
	 */
	public void set(String attribute, String value) {
		this.attributes.put(attribute, value);
	}
	
	/**
	 * @param attribute is the object that is being read.
	 * @return the value of the object as a string.
	 */
	public String get(String attribute) {
		return this.attributes.get(attribute);
	}

}
