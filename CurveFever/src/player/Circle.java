package player;

import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	
	private int radius;
	private Color color;
	public Circle(int newRad, Color newCol) {
		setRadius(newRad);
		setColor(newCol);
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void drawCircle(Graphics g, int x, int y) {
		Color tmpCol = g.getColor();
		g.setColor(color);
		g.fillOval(x-radius, y-radius, 2*radius, 2*radius);
		g.setColor(tmpCol);
	}
}
