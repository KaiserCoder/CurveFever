package gameplay;

import player.PlayerPosition;

public class Wall {

	private int playerID;
	private long timestamp;
	private Wall[][] walls;

	public Wall(int maxX, int maxY) {
		this.walls = new Wall[maxX][];
		for (int i = 0; i < maxX; i++) {
			this.walls[i] = new Wall[maxY];
			for (int j = 0; j < maxY; j++) {
				this.walls[i][j] = null;
			}
		}
	}

	private Wall(int id, long timeStamp) {
		this.playerID = id;
		this.timestamp = timeStamp;
	}

	public void add(int id, PlayerPosition position, long timeStamp) {
		this.walls[(int) position.getX()][(int) position.getY()] = new Wall(id, timeStamp);
	}

	public Wall get(int posX, int posY) {
		if (posX < 0 || posY < 0 || posX > this.walls.length || posY > this.walls[0].length) {
			return null;
		}

		return this.walls[posX][posY];
	}

	public int getPlayerID() {
		return this.playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString() {
		String toReturn = "";
		for (int i = 0; i < this.walls.length; i++) {
			for (int j = 0; j < this.walls[0].length; j++) {
				toReturn += this.walls[i][j];
			}
			toReturn += "\n";
		}
		return toReturn;
	}

}