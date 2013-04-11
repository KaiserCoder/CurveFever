package player;

public class PlayerPosition {
	private float x;
	private float y;
	
	public PlayerPosition(float xx, float yy) {
		setX(xx);
		setY(yy);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "X: " + x + ", Y: " + y;
	}
}