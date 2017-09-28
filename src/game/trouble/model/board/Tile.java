package game.trouble.model.board;

import java.awt.Color;
import java.awt.Shape;

/**
 * The Tile class contains data of a tile such as the color and the the shape.
 */
public class Tile {
	
	private Color color;
	private Shape shape;
	
	public Tile(Color color, Shape shape) {
		this.color = color;
		this.shape = shape;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Shape getShape() {
		return shape;
	}
	
}