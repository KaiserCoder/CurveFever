package player;

import java.awt.Color;
import java.awt.Graphics2D;

public class Circle {

	private int radius;
	private Color color;
	
	private static final short DEFAULT_SIZE = 4;

	public Circle(Color color) {
		this.radius = DEFAULT_SIZE;
		this.color = color;
	}

	public int getRadius() {
		return this.radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void drawCircle(Graphics2D graphics, int x, int y) {
		graphics.setColor(this.color);
		graphics.fillOval(x - this.radius, y - this.radius, 2 * this.radius, 2 * this.radius);
	}

}
