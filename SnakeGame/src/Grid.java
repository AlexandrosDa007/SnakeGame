import java.awt.Color;
import java.awt.Graphics;

import java.awt.Point;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * This class represent the Game Panel where the drawing occurs 
 */

public class Grid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 600; //Game's width ( not window height )
	public static final int HEIGHT = 600; //Game's height ( not window height )

	public static final int space = 30; //The pixels of the cells in the grid

	private ArrayList<Point> Cells; //This list references the cells of the grid

	private Point head; //This point represents the position of the head of the snake
	private int facing = 1; //The direction of the snake (0 - UP) (1 - RIGHT) (2 - DOWN) (3 - LEFT)
	public boolean showGrid = false; //If true displays the grid lines
	public Color backGroundColor = Color.GRAY; //BackGround Color 

	//Images to be loaded
	private ImageIcon snakeHeadRight; 
	private ImageIcon snakeHeadUp;
	private ImageIcon snakeHeadDown;
	private ImageIcon snakeHeadLeft;
	private ImageIcon snakePart;
	private ImageIcon apple;
	
	//2 random numbers for the apple location
	public int RnumberX;
	public int RnumberY;

	public Grid() {
		RnumberX = 1 + (int) (Math.random() * 19);
		RnumberY = 1 + (int) (Math.random() * 19);
		Cells = new ArrayList<Point>(400);

		try {
			
			snakeHeadRight = new ImageIcon(getClass().getClassLoader().getResource("snakeHeadRight.png"));
			snakeHeadUp = new ImageIcon(getClass().getClassLoader().getResource("snakeHeadUp.png"));
			snakeHeadDown = new ImageIcon(getClass().getClassLoader().getResource("snakeHeadDown.png"));
			snakeHeadLeft = new ImageIcon(getClass().getClassLoader().getResource("snakeHeadLeft.png"));
			snakePart = new ImageIcon(getClass().getClassLoader().getResource("snakePart.png"));
			apple = new ImageIcon(getClass().getClassLoader().getResource("apple.png"));

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Loading images");
		}

		
		repaint();
	}

	
	
	//Override to paint custom components 
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Background drawing
		g.setColor(backGroundColor);
		g.fillRect(space, space, WIDTH, HEIGHT);

		
		//For snake tail draw with the image
		for (Point cell : Cells) {
			//Calculate tail position
			int cellX = space + (cell.x * space);
			int cellY = space + (cell.y * space);
			snakePart.paintIcon(this, g, cellX, cellY);
			
		}

		//Draw the apple
		apple.paintIcon(this, g, space * RnumberX + space, space * RnumberY + space);
		

		//Depending from the direction of the snake paint the correct pictures
		switch (facing) {
		case 0: {
			snakeHeadUp.paintIcon(this, g, space + (head.x * space), space + (head.y * space));
			
			break;
		}
		case 1: {
			snakeHeadRight.paintIcon(this, g, space + (head.x * space), space + (head.y * space));
			
			break;
		}
		case 2: {
			snakeHeadDown.paintIcon(this, g, space + (head.x * space), space + (head.y * space));
			
			break;
		}
		case 3: {
			snakeHeadLeft.paintIcon(this, g, space + (head.x * space), space + (head.y * space));
			
			break;
		}
		}

		//Draw a rectangle around the game panel
		g.setColor(Color.BLACK);
		g.drawRect(space, space, WIDTH, HEIGHT);
		
		//Draw grid lines if showGrid is true
		if (showGrid) {
			for (int i = space; i <= WIDTH; i += space) {
				g.drawLine(i, space, i, HEIGHT + space);
			}

			for (int i = space; i <= HEIGHT; i += space) {
				g.drawLine(space, i, WIDTH + space, i);
			}
		}
	}

	//This function is adding a new cell in the grid and repainting
	public void fillCell(int x, int y) {
		Cells.add(new Point(x, y));
		repaint();
	}

	//This function is removing a new cell in the grid and repainting
	public void removeCell(int x, int y) {
		Cells.remove(new Point(x, y));
		repaint();
	}

	//Drawing the head
	public void paintSnakeHead(int x, int y) {
		head = new Point(x, y);
		repaint();
	}

	public int getFacing() {
		return facing;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

}
