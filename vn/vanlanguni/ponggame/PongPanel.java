/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */
package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;
	private int interval = 1000 / 60;

	/** Background. */
	private Color backgroundColor = Color.BLACK;

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */
	private int ballX = 240;
	private int ballY = 240;
	private int diameter = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;

	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 230;
	private int playerOneWidth = 10;
	private int playerOneHeight = 60;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 480;
	private int playerTwoY = 230;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 60;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;
	
	/** Icon game*/
	private ImageIcon imPlus = new ImageIcon("./icon/Plus.png");
	private	int IconPlusY;
	private	int IconPlusX;
	private int IconPlusW_H = 30;
	boolean blicon=false;
	int timeToDisplay;

	/** Construct a PongPanel. */
	public PongPanel() {
		setBackground(backgroundColor);

		// listen to key presses
		setFocusable(true);
		addKeyListener(this);

		// call step() 60 fps
		Timer timer = new Timer(interval, this);
		timer.start();
		// draw icon
		timeToDisplay = ThreadLocalRandom.current().nextInt(5,15+1)*1000;
	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (wPressed && playerOneY - paddleSpeed > 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed && playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (upPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed && playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;

			// IconPlus
			int IconPlusLeft = IconPlusX;
			int IconPlusRight = IconPlusX+30;
			int IconPlusTop = IconPlusY;
			int IconPlusButtom = IconPlusY+30;
			
			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = getWidth()-10;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1;
				Sound.play("sound/pongsound.wav");
				
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

					playerTwoScore++;

					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
						playing = false;
						gameOver = true;
						Sound.play("sound/you-lose.wav");
					}
					ballX = getWidth()/2;
					ballY = getHeight()/2;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					Sound.play("sound/pongsound.wav");
					
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
						playing = false;
						gameOver = true;
						Sound.play("sound/fatality.wav");
					}
					ballX = getWidth()/2;
					ballY = getHeight()/2;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					Sound.play("sound/pongsound.wav");
				}
			}
			
			// Ball hit icon;
			if (nextBallLeft < IconPlusRight || nextBallRight > IconPlusLeft) {
				if (nextBallTop > IconPlusButtom && nextBallBottom < IconPlusTop) {
					IconPlusW_H = 0;
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {

			/* Show welcome screen */

			// Draw game title and start message
			g.setColor(Color.CYAN);
			g.setFont(new Font("Colonna MT", 3, getWidth()/7));
			g.drawString("Pong Game", getWidth()/8, getHeight()/2-40);

			// FIXME Wellcome message below show smaller than game title
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
			g.drawString("Press 'P' to play.", getWidth()/2-75, getHeight()/2+80);
			ballX = getWidth()/2-10;
			ballY = getHeight()/2-10;
			playerTwoY = getHeight()/2-30;
			playerOneY = getHeight()/2-30;
			IconPlusX = ThreadLocalRandom.current().nextInt(getWidth()-100) + 100;
			IconPlusY = ThreadLocalRandom.current().nextInt(getHeight()-30) + 0;
		} else if (playing) {

			/* Game is playing */

			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			g.setColor(Color.GREEN);
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(getWidth()/2, lineY, getWidth()/2, lineY + 25);
			}

			// draw "goal lines" on each side
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(getWidth()-10, 0, getWidth()-10, getHeight());

			// draw the scores
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(playerOneScore), getWidth()/4-10, 100); // Player 1
																	// score
			g.drawString(String.valueOf(playerTwoScore), getWidth()*3/4-10, 100); // Player 2
																	// score

			// draw the ball
			g.setColor(Color.RED);
			g.fillOval(ballX, ballY, diameter, diameter);

			// draw the paddles
			g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
			g.fillRect(getWidth()-10, playerTwoY, playerTwoWidth, playerTwoHeight);
			
			// draw icon
			timeToDisplay -= interval;
		
			if (timeToDisplay < 0) {
				blicon=true;
				if (blicon==true) {
					IconPlusX = ThreadLocalRandom.current().nextInt(getWidth()-100) + 100;
					IconPlusY = ThreadLocalRandom.current().nextInt(getHeight()-30) + 0;
					timeToDisplay = ThreadLocalRandom.current().nextInt(5,15+1)*1000;
				}
			}
			if (timeToDisplay < timeToDisplay-4000) {
				blicon=false;
			}
			if (blicon==true) {
				IconPlusW_H = 30;
				g.drawImage(imPlus.getImage() , IconPlusX, IconPlusY, IconPlusW_H, IconPlusW_H, null);		
			} 

		} else if (gameOver) {

			/* Show End game screen with winner name and score */

			// Draw scores
			// TODO Set Blue color
			g.setFont(new Font(Font.DIALOG, Font.BOLD, getWidth()/13));
			g.setColor(Color.BLUE);
			g.drawString(String.valueOf(playerOneScore), getWidth()/4-10, getHeight()/2);
			g.drawString(String.valueOf(playerTwoScore), getWidth()*3/4-10, getHeight()/2);

			// Draw the winner name
			g.setFont(new Font(Font.DIALOG, Font.BOLD, getWidth()/13));
			if (playerOneScore > playerTwoScore) {
				g.drawString("Player 1 Wins!", getWidth()/4, getHeight()/5);
			} else {
				g.drawString("Player 2 Wins!", getWidth()/4, getHeight()/5);
			}
			// Draw Restart message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, getWidth()/22));
			g.drawString("Press 'SPACE' to Restart the game.", getWidth()/7, getHeight()*4/5);
			// TODO Draw a restart message
			blicon=false;
		}
	}

	
	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyCode() == KeyEvent.VK_P) {
				Sound.play("sound/fight.wav");
				showTitleScreen = false;
				playing = true;
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				downPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				sPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = false;
			playing = true;
			Sound.play("sound/fight.wav");
			playerOneScore = 0;
			playerTwoScore = 0;
			playerOneY = 220;
			playerTwoY = 220;
			ballX = 240;
			ballY = 240;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			sPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
