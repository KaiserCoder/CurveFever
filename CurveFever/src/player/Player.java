package player;

import gameplay.Wall;

import java.util.Random;

import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {

	private Color color;
	private String name;
	private Circle circle;

	private boolean isOut;
	private boolean isReady;

	private float angle;
	private int chatPosition;

	private boolean[] keyStates;
	private PlayerPosition position;
	private PlayerPosition[] boundries;

	private static final float DEFAULT_VELOCITY = 2.2f;

	public Player(Color color, String name, int position) {
		this.setColor(color);
		this.setPlayerName(name);
		this.setPosition(position);

		this.isOut = false;
		this.isReady = true;
		this.circle = new Circle(color);
		this.boundries = new PlayerPosition[8];

		Random random = new Random(System.nanoTime());

		this.angle = random.nextInt(360);
		this.position = new PlayerPosition(
			random.nextFloat() * 280 + 400,
			random.nextFloat() * 400 + 100
		);

		this.keyStates = new boolean[256];

		for (int i = 0; i < this.keyStates.length; i++) {
			this.keyStates[i] = false;
		}
	}

	public void drawPlayerInChat(Graphics2D graphics) {
		graphics.setColor(this.color);
		graphics.setFont(new Font("Helvetica", Font.BOLD, 13));
		graphics.fillRect(30, 21 * this.chatPosition + 20, 15, 15);

		if (this.isOut) {
			graphics.drawString(this.name + " " + "DEAD", 55, 21 * this.chatPosition + 32);
		} else {
			graphics.drawString(this.name + (this.isReady ? " " + "READY" : ""), 55, 21 * this.chatPosition + 32);
		}
	}

	public void drawPlayerInGame(Graphics2D graphics) {
		graphics.setColor(this.color);
		this.circle.drawCircle(graphics, (int) this.position.getX(), (int) this.position.getY());
	}

	private boolean isExceedingBoundries(Rectangle boundries) {
		if (this.position.getX() - this.circle.getRadius() < boundries.x ||
			this.position.getX() + this.circle.getRadius() > boundries.x + boundries.width || 
			this.position.getY() - this.circle.getRadius() < boundries.y ||
			this.position.getY() + this.circle.getRadius() > boundries.y + boundries.height) {
			this.isOut = true;
			return true;
		}
		return false;
	}

	private void checkWalls(Wall walls) {
		for (int i = 0; i < this.boundries.length; i++) {
			float x = (float) (this.position.getX() + this.circle.getRadius() * Math.sin(this.angle + (360 / this.boundries.length * i * 2)));
			float y = (float) (this.position.getY() + this.circle.getRadius() * Math.cos(this.angle + (360 / this.boundries.length * i * 2)));

			this.boundries[i] = new PlayerPosition(x, y);

			Wall currentWall = walls.get((int) this.boundries[i].getX(), (int) this.boundries[i].getY());

			if (currentWall != null) {
				if (currentWall.getPlayerID() == this.getPosition() && System.currentTimeMillis() - currentWall.getTimestamp() < 2000) {
					continue;
				} else {
					this.isOut = true;
				}
			}
		}
	}

	public void move(Rectangle levelBoundries, Wall walls) {
		if (!this.isOut) {
			this.position.setX(this.position.getX() + DEFAULT_VELOCITY * (float) Math.sin(this.angle));
			this.position.setY(this.position.getY() + DEFAULT_VELOCITY * (float) Math.cos(this.angle));	

			if (!this.isExceedingBoundries(levelBoundries)) {
				this.checkWalls(walls);
			}
		}
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getPlayerName() {
		return this.name;
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
		return this.angle;
	}

	public void setAngle(float angle) {
		if (angle < 0) {
			angle += 360;
		} else if (angle > 360) {
			angle -= 360;
		}

		this.angle = angle;
	}

	public PlayerPosition getPlayerPosition() {
		return this.position;
	}

	public Circle getCircle() {
		return this.circle;
	}

	public PlayerPosition[] getBoundries() {
		return this.boundries;
	}

	public boolean isOut() {
		return this.isOut;
	}
	public void setOut( boolean out) {
		isOut = out;
	}

	public boolean[] getKeyStates() {
		return this.keyStates;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		this.keyStates[event.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent event) {
		this.keyStates[event.getKeyCode()] = false;

	}

	@Override
	public void keyTyped(KeyEvent event) {}

}
