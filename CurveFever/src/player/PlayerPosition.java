package player;

public class PlayerPosition {

	private float x;
	private float y;

	public PlayerPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return this.x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return this.y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "X: " + this.x + ", Y: " + this.y;
	}

}