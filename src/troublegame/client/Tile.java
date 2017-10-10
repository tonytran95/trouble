package troublegame.client;

import java.awt.Color;
import java.awt.Shape;

/**
 * The Tile class contains data of a tile such as the color and the the shape.
 */
public class Tile {
	
	private int zone;
	private int index;
	private Color color;
	private Shape shape;
	
	public Tile(int zone, int index) {
		this.zone = zone;
		this.index = index;
		color = null;
		shape = null;
	}
	
	public int getZone() {
		return zone;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public void setZone(int zone) {
		this.zone = zone;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setTile(Shape shape, Color color) {
		this.shape = shape;
		this.color = color;
	}
	
}