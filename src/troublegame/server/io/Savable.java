package troublegame.server.io;

public interface Savable {

	/**
	 * The file saving is handled in this method.
	 */
	public void save(FileHandler fileHandler);
	
	/**
	 * The file loading is handled in this method.
	 * @param file is the file to be loaded.
	 */
	public void load(FileHandler fileHandler);
	
}
