package player;

import gameplay.Wall;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Player implements KeyListener {
	
	private Color color;
	private String name;
	private int chatPosition;
	private boolean isReady;
	private Circle circle;
	private PlayerPosition[] boundries;	
	private float angle;
	private PlayerPosition position;
	private boolean isOut;
	private boolean[] keyStates;
	private final float velocity = 1.5f;

	
	public Player( Color newCol, String newName, int newPos ) {
		setColor(newCol);
		setPlayerName(newName);
		setPosition(newPos);
		isReady=true;
		isOut=false;
		boundries = new PlayerPosition[8];
		circle = new Circle(4,newCol);
		Random rnd = new Random(System.nanoTime());
		position = new PlayerPosition(rnd.nextFloat() * 280 + 400, rnd.nextFloat() * 400 + 100 );
		angle = rnd.nextInt(360);
		
		//In order to assure our keys are still pressed, even when polling for new ones.
		keyStates = new boolean[256];
		for(int i=0;i<keyStates.length;i++) {
			keyStates[i]=false;
		}
	}
	
	public void drawPlayerInChat(Graphics g) {
		Color tmpCol = g.getColor();
		g.setFont(new Font("Helvetica", Font.BOLD, 13));
		g.setColor(color);
		g.fillRect(30, 21 * (chatPosition) + 20, 15, 15);
		if(this.isOut) {
			g.drawString(name + " (OUT!)", 55, 21 * (chatPosition) + 32);
		} else {
			g.drawString(name + (isReady?" (READY)":""), 55, 21 * (chatPosition) + 32);
		}
		g.setColor(tmpCol);
	}
	
	public void drawPlayerInGame(Graphics g) {
		circle.drawCircle(g, (int)position.getX(), (int)position.getY());
	}

	private boolean isExceedingBoundries( Rectangle boundries ) {
		//Boundries-check.
		if(position.getX() - this.circle.getRadius() < boundries.x ||
			(position.getX() + this.circle.getRadius()) > (boundries.x+boundries.width) || 
			(position.getY() - this.circle.getRadius()) < boundries.y ||
			(position.getY() + this.circle.getRadius()) > (boundries.y+boundries.height)) {
				this.isOut=true;
				return true;
		}
		return false;
	}
	
	private void checkWalls(Wall allWalls) {
		
		//Calculate a few points around the player as wall.
		for(int i=0;i<this.boundries.length;i++) {
			
			//Calculate a single one of the wall points.
			this.boundries[i] = new PlayerPosition((float) (this.position.getX() + this.circle.getRadius()
					* Math.sin(this.getAngle()+((360/this.boundries.length)*i*2))),
			(float) (this.position.getY() + this.circle.getRadius()
					* Math.cos(this.getAngle()+((360/this.boundries.length)*i*2))));
					
			//If a wall already exists, check whether it's freshly added and ours.
			//If not, make him crash.
			Wall currentWall = allWalls.get((int)this.boundries[i].getX(), (int)this.boundries[i].getY());
			if(currentWall!=null) {
				if(currentWall.getPlayerID() == this.getPosition() && (System.currentTimeMillis() - currentWall.getTimestamp())<2000) {
					continue;
				} else {
					isOut=true;
				}
			}
			//Else: wall doesn't exist at this point, yet.
		}
	}
	
	public void move(Rectangle levelBoundries, Wall walls) {
		if(this.isOut) {
			return;
		}
		
		//Set the new position.
		position.setX(position.getX() + velocity * (float)(Math.sin(angle)));
		position.setY(position.getY() + velocity * (float)(Math.cos(angle)));	

		//Check for bumps after we calculated the newest position.
		if(this.isExceedingBoundries(levelBoundries)) {
			return;
		}
		this.checkWalls(walls);
	}

/*
 * ============GETTER/SETTER===============
 */
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getPlayerName() {
		return name;
	}

	public void setPlayerName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return chatPosition;
	}

	public void setPosition(int position) {
		this.chatPosition = position;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		if(angle<0)
			angle += 360;
		if(angle>360)
			angle -= 360;
		this.angle = angle;
	}
	
	public PlayerPosition getPlayerPosition() {
		return position;
	}
	
	public Circle getCircle() {
		return circle;
	}
	
	public PlayerPosition[] getBoundries() {
		return boundries;
	}
	
	public boolean isOut() {
		return isOut;
	}
	public void setOut( boolean out) {
		isOut = out;
	}
	
	public boolean[] getKeyStates() {
		return keyStates;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		keyStates[arg0.getKeyCode()]=true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keyStates[arg0.getKeyCode()]=false;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
