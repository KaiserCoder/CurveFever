package gameplay;

public class Wall {
	private int playerID;
	private int radius;
	private long timestamp;
	public Wall( int id, long time ) {
		playerID = id;
		setTimestamp(time);
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
