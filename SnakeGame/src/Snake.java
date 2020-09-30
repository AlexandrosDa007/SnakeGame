import java.awt.Point;

public class Snake {

	private int facing = 1; //Direction the snake is facing (0 - UP) (1 - RIGHT) (2 - DOWN) (3 - LEFT)
	private int size = 0; //Size of the snake
	private Point[] parts; //A point array for the positions of the snake parts 
	
	public Snake(){
		parts = new Point[400]; //limit on the snake parts
		parts[0] = new Point(7,5); //Starting position of the snake head
		size++;
		for(int i = 1;i<6;i++){
			parts[i] = new Point(7-i,5); //Initialize the parts one by one after head
			size++;
		}
		
	}
	
	//Adding a part in the snake array
	public void addPart(){
		parts[size] = new Point(parts[0].x-size,parts[size-1].y);
		size++;
	}
	
	public Point getSnakeHead(){
		return parts[0];
	}
	
	public int getSnakeSize(){
		return size;
	}
	
	public Point[] getSnake(){
		return parts;
	}
	
	public void setFacing(int facing){
		this.facing = facing;
	}
	
	public int getFacing(){
		return facing;
	}
	
	public void setSnakeSize(int size){
		this.size = size;
	}
}
