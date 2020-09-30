import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameContainer extends JFrame {

	/**
	 * This class represents the window of the game
	 */
	private static final long serialVersionUID = 1L;

	private Snake snake; //Reference to the Snake Object

	private Grid grid; //Grid Object extends from the JPanel
	
	private Point apple; //Apple Coordinates

	private JLabel scoreLabel;
	private JLabel partsLabel;
	private JLabel infoLabel;
	private JLabel state; //State label for pausing the game
	private JButton resetButton;
	

	private int score = 0; 
	private boolean sound; //True if sound is on
	private boolean moved = false; //If the snake moved
	
	//Flags for pausing and resuming game
	public boolean isRunning = false;
	public boolean paused = true;

	public GameContainer(boolean sound, boolean gridb, Color c) {
		this.sound = sound;
		snake = new Snake();

		grid = new Grid();
		grid.backGroundColor = c;
		grid.showGrid = gridb;
		grid.setLayout(null);
		super.setSize(980, 720);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		super.setLocationRelativeTo(null);

		infoLabel = new JLabel(
				"<html>PRESS \"SPACE\" TO START<br>PRESS \"ALT+R\" TO RESET<br>PRESS \"P\" TO PAUSE");
		resetButton = new JButton("Start");
		scoreLabel = new JLabel("Score: " + score);
		state = new JLabel("");
		scoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		partsLabel = new JLabel("Tail length: " + (snake.getSnakeSize() - 1));
		partsLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		partsLabel.setBounds(650, 80, 200, 30);
		scoreLabel.setBounds(650, 50, 200, 30);
		infoLabel.setFont(new Font("Times New Roman",Font.BOLD,22));
		infoLabel.setBounds(650, 400, 300, 200);
		state.setFont(new Font("Times New Roman", Font.BOLD, 68));
		state.setBounds(200, 200, 400, 200);

		resetButton.setBounds(850, 50, 100, 30);
		resetButton.setEnabled(true);
		resetButton.setMnemonic('r');

		
		//Listener for resetButton
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetButton.setText("Reset");
				if (isRunning) {
					isRunning = false;
					paused = true;
				} else {
					score = 0;
					scoreLabel.setText("Score: " + score);
					isRunning = true;
					paused = false;
				}
				state.setText("");
			}
		});

		grid.add(resetButton);
		grid.add(scoreLabel);
		grid.add(partsLabel);
		grid.add(infoLabel);
		grid.add(state);

		super.add(grid);
		
		super.setTitle("Snake game by Alex");
		super.setVisible(true);

		//Spawn the apple in a random position
		apple = new Point(grid.RnumberX, grid.RnumberY);

		
		getInput();
		drawSnake();

	}

	/**
	 * Draw the snake parts
	 */
	public void drawSnake() {
		grid.paintSnakeHead(snake.getSnakeHead().x, snake.getSnakeHead().y);
		for (int i = 1; i < snake.getSnakeSize(); i++) {
			grid.fillCell(snake.getSnake()[i].x, snake.getSnake()[i].y);
		}
	}

	//Listen for key strokes
	public void getInput() {
		super.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				//If game is running
				if (!paused && isRunning) {
					
					/*
					 * Get Key code and depending on the direction and the key code,
					 * change the direction of the snake
					 */
					
					if (e.getKeyCode() == KeyEvent.VK_UP && (snake.getFacing() == 1 || snake.getFacing() == 3)
							&& moved == true) {
						snake.setFacing(0);
						moved = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_RIGHT && (snake.getFacing() == 0 || snake.getFacing() == 2)
							&& moved == true) {
						snake.setFacing(1);
						moved = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN && (snake.getFacing() == 1 || snake.getFacing() == 3)
							&& moved == true) {
						snake.setFacing(2);
						moved = false;
					}
					if (e.getKeyCode() == KeyEvent.VK_LEFT && (snake.getFacing() == 0 || snake.getFacing() == 2)
							&& moved == true) {
						snake.setFacing(3);
						moved = false;
					}
				}

				
				//Pressing P pauses the game
				if (e.getKeyCode() == KeyEvent.VK_P && isRunning) {
					if (paused) {
						paused = false;
						state.setText("");
					} else {
						paused = true;
						state.setText("PAUSE");
					}
				}

			}
		});
	}

	/*
	 * Moving the snake by deleting its tail and moving it's parts one by one
	 */
	public void move() {
		for (int i = snake.getSnakeSize() - 1; i > 0; i--) {
			grid.removeCell(snake.getSnake()[i].x, snake.getSnake()[i].y);
			snake.getSnake()[i].setLocation(snake.getSnake()[i - 1].x, snake.getSnake()[i - 1].y);
		}

	}
	
	/*
	 * Eating an apple plays a sound and adding a part to tail of the snake
	 */
	public void eatApple(){
		if (snake.getSnakeHead().x == apple.x && snake.getSnakeHead().y == apple.y) {
			if (sound) {
				URL musicURL = getClass().getResource("gainSound.wav");
				Sound.PlaySound(musicURL);
			}
				

			snake.addPart();
			grid.RnumberX = 1 + (int) (Math.random() * 19);
			grid.RnumberY = 1 + (int) (Math.random() * 19);
			apple = new Point(grid.RnumberX, grid.RnumberY);
			score = score + 100;

			scoreLabel.setText("Score: " + score);

		}
	}

	/*
	 * Dying freezes the game and displays Game over scene
	 */
	public void die(){
		for (int i = 1; i < snake.getSnakeSize(); i++) {
			if (snake.getSnakeHead().x == snake.getSnake()[i].x && snake.getSnakeHead().y == snake.getSnake()[i].y) {
				isRunning = false;
				resetButton.setEnabled(true);
				grid.RnumberX = 1 + (int) (Math.random() * 19);
				grid.RnumberY = 1 + (int) (Math.random() * 19);
				apple = new Point(grid.RnumberX, grid.RnumberY);
				snake = new Snake();
				state.setText("Game over");
			}
		}
	}
	
	/*
	 * Updating snake behavior 
	 */
	public void moveSnake() {
		grid.setFacing(snake.getFacing());

		eatApple();
		move();
		checkCollision();

		
		/*
		 * Depending on the direction of the snake, setting the location of the head
		 */
		switch (snake.getFacing()) {
		case 0: {
			snake.getSnakeHead().setLocation(snake.getSnakeHead().x, snake.getSnakeHead().y - 1);
			break;
		}
		case 1: {

			snake.getSnakeHead().setLocation(snake.getSnakeHead().x + 1, snake.getSnakeHead().y);
			break;
		}
		case 2: {
			snake.getSnakeHead().setLocation(snake.getSnakeHead().x, snake.getSnakeHead().y + 1);
			break;
		}
		case 3: {
			snake.getSnakeHead().setLocation(snake.getSnakeHead().x - 1, snake.getSnakeHead().y);
			break;
		}

		}
		
		die();
		
		//if the game is running then draw
		if (isRunning) {
			drawSnake();
			partsLabel.setText("Tail length: " + (snake.getSnakeSize() - 1));
		}
		
		//When the snake moves then true
		moved = true;

	}

	/*
	 * Checking collisions
	 */
	public void checkCollision() {
		if (snake.getSnakeHead().x > 18 && snake.getFacing() == 1)
			snake.getSnakeHead().setLocation(-1, snake.getSnakeHead().y);
		if (snake.getSnakeHead().x < 1 && snake.getFacing() == 3)
			snake.getSnakeHead().setLocation(20, snake.getSnakeHead().y);
		if (snake.getSnakeHead().y < 1 && snake.getFacing() == 0)
			snake.getSnakeHead().setLocation(snake.getSnakeHead().x, 20);
		if (snake.getSnakeHead().y > 18 && snake.getFacing() == 2)
			snake.getSnakeHead().setLocation(snake.getSnakeHead().x, -1);

	}

	public JButton getResetButton() {
		return resetButton;
	}

}
