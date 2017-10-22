package troublegame.client.model;

import java.awt.Color;
import java.awt.Shape;

/**
 * The Slot class contains data of a slot such as the color and the the shape.
 */
public class Slot {

	/**
	 * The tile zone.
	 */
	private int zone;
	
	/**
	 * The tile index.
	 */
	private int index;
	
	/**
	 * The tile shape.
	 */
	private Shape shape;
	
	/**
	 * The tile color.
	 */
	private Color color;
	
	/**
	 * Constructs a new tile.
	 * @param zone is the tile zone.
	 * @param index is the tile index.
	 */
	public Slot(int zone, int index) {
		this.zone = zone;
		this.index = index;
		shape = null;
		color = null;
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
	 * @return the tile shape.
	 */
	public Shape getShape() {
		return shape;
	}
	
	/**
	 * @return the tile color.
	 */
	public Color getColor() {
		return color;
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
	 * Sets the tile's shape.
	 * @param shape is the tile shape.
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	
	/**
	 * Sets the tile's color.
	 * @param color is the tile color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Sets the slot's shape and color.
	 * @param shape is the slot shape.
	 * @param color is the slot color.
	 */
	public void setSlot(Shape shape, Color color) {
		this.shape = shape;
		this.color = color;
	}
}