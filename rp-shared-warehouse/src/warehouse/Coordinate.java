package warehouse;

public class Coordinate {
	
	private int x;
	private int y;
	
	public Coordinate(int _x, int _y){
		this.x=_x;
		this.y=_y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	@Override
	public String toString(){
		return "(" + x + "," + y + ")";
	}
}
