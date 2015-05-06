package gui;

import gameplay.Wall;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import player.Player;
import player.PlayerPosition;

public class CurveFeverPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -7908762394799897754L;
	private ArrayList<Player> players = new ArrayList<Player>();
	private boolean[] keyStates;
	private Wall allWalls;

	private final Rectangle gameField = new Rectangle(275, 10, 725, 550);
	private final float ANGLE_DIFF = 0.04f;
	private long lastTime;

	private Graphics2D graphics;
	private boolean start;

	private JButton startButton;
	private Timer timer;

	public CurveFeverPanel() {
		this.allWalls = new Wall(this.gameField.x + this.gameField.width, this.gameField.y + this.gameField.height);
		this.startButton = new JButton("Start Game");
		this.startButton.setBounds(76, 520, 117, 25);
		
		this.keyStates = new boolean[256];
		
		for (int i = 0; i < this.keyStates.length; i++) {
			this.keyStates[i]=false;
		}
		
		this.startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int inGame = 0;
				if (players.size() > 0) {
					for (Player player : players) {
						if (!player.isOut()) {
							inGame++;
						}
					}
				}

				if (inGame <= 1) {
					drawGameField();
					initPlayers();
					allWalls = new Wall(gameField.x + gameField.width, gameField.y + gameField.height);
					start = true;
				}
			}
		});

		this.startButton.setFocusable(false);
		
		this.setFocusable(true);		
		this.requestFocus();
		this.add(this.startButton);

		this.initPlayers();
		this.timer = new Timer(25, this);
		this.timer.start();
		
		this.start = true;
	}

	private void initPlayers() {
		this.players.clear();
		
		Player firstPlayer = new Player(Color.RED, "First Player", 0);
		Player secondPlayer = new Player(Color.BLUE, "Second Player", 1);

		this.players.add(firstPlayer);
		this.players.add(secondPlayer);
		
		this.addKeyListener(firstPlayer);
		this.addKeyListener(secondPlayer);
	}

	private void drawPlayerRoom() {
		this.graphics.setColor(Color.WHITE);
		this.graphics.fillRect(
			25, 10,
			Math.max(this.graphics.getClipBounds().width - 800, 200),
			Math.max(this.graphics.getClipBounds().height - 300, 200)
		);
		
		for (int i = 0; i < this.players.size(); i++) {
			this.players.get(i).drawPlayerInChat(this.graphics);
		}
	}

	private void drawChatBox() {
		this.graphics.setColor(Color.WHITE);
		this.graphics.fillRect(
			25,
			Math.max(this.graphics.getClipBounds().height - 300, 200) + 15,
			Math.max(this.graphics.getClipBounds().width - 800, 200), 250
		);
	}

	private void drawGameField() {
		this.graphics.setColor(Color.BLACK);
		this.graphics.fillRect(this.gameField.x, this.gameField.y, this.gameField.width, this.gameField.height);
	}

	private void drawPlayerCircles() {
		for (int i = 0; i < this.players.size(); i++) {
			this.players.get(i).drawPlayerInGame(this.graphics);
		}
	}

	@Override
	public void paintComponent(Graphics graphics) {
		this.graphics = (Graphics2D) graphics;
		this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		this.drawChatBox();
		this.drawPlayerRoom();
		this.drawPlayerCircles();

		if (this.start) {
			this.start = false;
			this.drawGameField();
		}
	}

	public void addWalls(Player currentPlayer) {
		PlayerPosition[] boundries = currentPlayer.getBoundries();
		for (int i = 0; i < boundries.length;i++) {
			this.allWalls.add(currentPlayer.getPosition(), boundries[i], System.currentTimeMillis());
		}
	}

	private void handleKeys(Player player, float angleModifier) {
		boolean[] keyStates = player.getKeyStates();
		
		if (player.getPosition() == 0) {
			this.keyStates[KeyEvent.VK_LEFT] = keyStates[KeyEvent.VK_LEFT];
			this.keyStates[KeyEvent.VK_RIGHT] = keyStates[KeyEvent.VK_RIGHT];

			if (this.keyStates[KeyEvent.VK_LEFT]) {
				player.setAngle(player.getAngle() + angleModifier);
			} else if(keyStates[KeyEvent.VK_RIGHT]) {
				player.setAngle(player.getAngle() - angleModifier);
			}
		} else if (player.getPosition() == 1) {
			this.keyStates[KeyEvent.VK_A] = keyStates[KeyEvent.VK_A];
			this.keyStates[KeyEvent.VK_D] = keyStates[KeyEvent.VK_D];
				
			if (this.keyStates[KeyEvent.VK_A]) {
				player.setAngle(player.getAngle() + angleModifier);
			} else if (this.keyStates[KeyEvent.VK_D]) {
				player.setAngle(player.getAngle() - angleModifier);
			}
		}	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		boolean allOut = true;

		float angleModifier = ANGLE_DIFF;
		long timeDiff = (System.currentTimeMillis() - lastTime);
		
		if (timeDiff > 100) {
			angleModifier *= 5;
		}
		
		for (Player player : this.players) {
			this.handleKeys(player, angleModifier);
		}

		for (Player player : this.players) {
			if (!player.isOut()) {
				allOut = false;
				break;
			}
		}
		
		this.lastTime = System.currentTimeMillis();

		if (!allOut) {
			for (Player player : this.players) {
				player.move(this.gameField, this.allWalls);
				addWalls(player);
			}
			this.repaint();
		}
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public ArrayList<Player> getPlayers() {
		return this.players;
	}

}
