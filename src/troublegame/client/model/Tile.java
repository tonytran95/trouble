package troublegame.client.model;

import java.awt.Color;
import java.awt.Shape;

/**
 * The Tile class contains data of a tile such as the color and the the shape.
 */
public class Tile {

	/**
	 * The tile zone.
	 */
	private int zone;
	
	/**
	 * The tile index.
	 */
	private int index;
	
	/**
	 * The tile color.
	 */
	private Color color;
	
	/**
	 * The tile shape.
	 */
	private Shape shape;
	
	/**
	 * Constructs a new tile.
	 * @param zone is the tile zone.
	 * @param index is the tile index.
	 */
	public Tile(int zone, int index) {
		this.zone = zone;
		this.index = index;
		color = null;
		shape = null;
	}
	
	/**
	 * @return the tile zone.
	 */
	public int getZone() {
		return zone;
	}
	
	/**
	 * @return the tile index.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * @return the tile color.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @return the tile shape.
	 */
	public Shape getShape() {
		return shape;
	}
	
	/**
	 * Sets a new zone for the tile.
	 * @param zone is the tile zone.
	 */
	public void setZone(int zone) {
		this.zone = zone;
	}
	
	/**
	 * Sets the tile index.
	 * @param index is the tile index.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * Sets the tile as a new shape and color.
	 * @param shape is the tile shape.
	 * @param color is the tile color.
	 */
	public void setTile(Shape shape, Color color) {
		this.shape = shape;
		this.color = color;
	}
	
}