package gui;

import gameplay.Wall;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
	private Wall allWalls;
	private boolean[] keyStates;

	private final Rectangle gameField = new Rectangle(275, 10, 725, 550);
	private final float ANGLE_DIFF = 0.04f;
	private long lastTime;

	private boolean start;
	private Graphics graphics;
	
	private Timer timer;
	private JButton startButton;

	public CurveFeverPanel() {

		allWalls = new Wall(gameField.x+gameField.width, gameField.y+gameField.height);
		startButton = new JButton("Start Game");
		startButton.setBounds(76, 520, 117, 25);
		
		keyStates = new boolean[256];
		for(int i=0;i<keyStates.length;i++) {
			keyStates[i]=false;
		}
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int inGame = 0;
				//Check every player who's currently playing.
				if(players.size()>0) {
					for(Player player : players) {
						if(!player.isOut()) {
							inGame++;
						}
					}
				}
				//If max 1 player's still alive -> allow rematch.
				if(inGame<=1) {
					drawGameField(graphics);
					initPlayers();
					allWalls = new Wall(gameField.x+gameField.width, gameField.y+gameField.height);
					start = true;
				}
			}
		});

		//Just to make sure the button doesn't get focused, otherwise KeyListener doesn't work.
		startButton.setFocusable(false);
		
		//Focus the panel to ensure KeyListener's functionality.
		this.setFocusable(true);		
		this.requestFocus();
		this.add(startButton);

		//Init everything.
		initPlayers();
		timer = new Timer(25, this);
		timer.start();
		
		//Start drawing, mang.
		start = true;

	}
	/**
	 * Initilize/reset the player-base.
	 */
	private void initPlayers() {
		//re-add players.
		players.clear();
		Player p1 = new Player(Color.red, "DJ Klostermann", 0);
		Player p2 = new Player(Color.blue, "Sebastian", 1);

		players.add(p1);
		players.add(p2);
		this.addKeyListener(p1);
		this.addKeyListener(p2);
	}

	private void drawPlayerRoom(Graphics g) {
		Color tmpCol = g.getColor();
		g.setColor(Color.white);
		g.fillRect(25, 10, Math.max(g.getClipBounds().width - 800, 200),
				Math.max(g.getClipBounds().height - 300, 200));
		for (int i = 0; i < players.size(); i++) {
			players.get(i).drawPlayerInChat(g);
		}
		g.setColor(tmpCol);
	}

	private void drawChatBox(Graphics g) {
		Color tmpCol = g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(25, Math.max(g.getClipBounds().height - 300, 200) + 15,
				Math.max(g.getClipBounds().width - 800, 200), 250);
		g.setColor(tmpCol);
	}

	private void drawGameField(Graphics g) {
		Color tmpCol = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(gameField.x, gameField.y, gameField.width, gameField.height);
		g.setColor(tmpCol);
	}

	private void drawPlayerCircles(Graphics g) {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).drawPlayerInGame(g);
			PlayerPosition[] pBoundries = players.get(i).getBoundries();
			for(int j=0;j<pBoundries.length;j++) {
				if(pBoundries[j]==null)
					continue;
				g.drawOval((int) pBoundries[j].getX(), (int) pBoundries[j].getY(), 1, 1);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		graphics = g;
		// super.paint(g);
		drawPlayerRoom(g);
		drawChatBox(g);
		drawPlayerCircles(g);
		if(start) {
			//First time drawing the field.
			start = false;
			drawGameField(g);
		}
	}
	/**
	 * @param currentPlayer Player who creates the wall.
	 */
	public void addWalls(Player currentPlayer) {
		PlayerPosition[] pBoundries = currentPlayer.getBoundries();
		for(int i=0;i<pBoundries.length;i++) {
			allWalls.add(currentPlayer.getPosition(), pBoundries[i], System.currentTimeMillis());
		}
	}
	/**
	 * 
	 * @param player Current Player.
	 * @param angleModifier Angle modifier for every time step (e.g. 25ms)
	 */
	private void handleKeys(Player player, float angleModifier) {
		
		//First player --> LEFT_ARROW/RIGHT_ARROW
		if(player.getPosition()==0) {
			keyStates[KeyEvent.VK_LEFT] = player.getKeyStates()[KeyEvent.VK_LEFT];
			keyStates[KeyEvent.VK_RIGHT] = player.getKeyStates()[KeyEvent.VK_RIGHT];
			if(keyStates[KeyEvent.VK_LEFT]){
				player.setAngle(player.getAngle() + angleModifier);
			} else if(keyStates[KeyEvent.VK_RIGHT]){
				player.setAngle(player.getAngle() - angleModifier);
			}
		//Second Player --> A / D
		} else if(player.getPosition()==1) {
			keyStates[KeyEvent.VK_A] = player.getKeyStates()[KeyEvent.VK_A];
			keyStates[KeyEvent.VK_D] = player.getKeyStates()[KeyEvent.VK_D];
				
			if(keyStates[KeyEvent.VK_A]){
				player.setAngle(player.getAngle() + angleModifier);
			}
			else if(keyStates[KeyEvent.VK_D]){
				player.setAngle(player.getAngle() - angleModifier);
			}
		}	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		boolean allOut = true;

		//
		float angleModifier = ANGLE_DIFF;
		long timeDiff = (System.currentTimeMillis() - lastTime);
		if (timeDiff > 100) {
			angleModifier *= 5;
		}
		
		//Handle all polled keys.
		for( Player player : players ) {
			handleKeys(player,angleModifier);
		}
		
		//Check whether all players are out of the game.
		//If so, stop everything.
		for( Player player : players ) {
			if(!player.isOut()) {
				allOut=false;
				break;
			}
		}
		this.lastTime = System.currentTimeMillis();
		if(allOut) {
			//timer.stop();
			return;
		}
		
		//After going though everything, move the current player
		//and add walls. Once done, repaint the entire window.
		for (Player player : players) {
			player.move(gameField, allWalls);
			addWalls(player);
		}
		this.repaint();
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void removePlayer(Player player) {
		players.remove(player);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}
