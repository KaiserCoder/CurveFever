package gameplay;

import player.PlayerPosition;

public class Wall {
	private int playerID;
	private long timestamp;
	private Wall[][] walls;
	
	//Every wall we want to possibly exist.
	public Wall( int maxX, int maxY ) {
		walls = new Wall[maxX][];
		for(int i=0;i<maxX;i++) {
			walls[i] = new Wall[maxY];
			for(int j=0;j<maxY;j++) {
				walls[i][j] = null;
			}
		}
	}
	
	//For a single wall - not to be accessed from outside.
	private Wall( int id, long timeStamp ) {
		this.playerID = id;
		this.timestamp = timeStamp;
	}
	
	public void add( int id, PlayerPosition pos, long timeStamp ) {
		 walls[(int)pos.getX()][(int)pos.getY()] = new Wall(id, timeStamp);
	}
	
	public Wall get( int posX, int posY ) {
		if(posX < 0 || posY < 0 || posX>walls.length || posY>walls[0].length)
			return null;
		return walls[posX][posY];
	}
	
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
