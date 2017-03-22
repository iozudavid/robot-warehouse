package warehouse;

import java.util.ArrayList;

public class Path{
	
	private int numberOfItems;
	private ArrayList<Coordinate> list;
	private boolean isWait;
	
	public Path( ArrayList<Coordinate> _coordlist,int numberOfItems){
			this.numberOfItems = 0;
			this.numberOfItems = numberOfItems;
			this.list = _coordlist;
			this.isWait = (this.list.size() == 1);
	}
	
	public void setNumberOfItems(int num){
		if (num >= 0){
			this.numberOfItems = num;
		}
	}
	
	public ArrayList<Coordinate> getAllCoordinates(){
		return list;
	}
	
	public Coordinate getNextCoord(){
		
		Coordinate next = list.get(0);
		list.remove(0);
		
		return next;
	}
	
	public boolean reachedEnd(){
		
		if(list.isEmpty())
			return true;
		return false;
	}

	public int getNumberOFItems(){
		return numberOfItems;
	}
	
	public boolean getIsWait(){
		return isWait;
	}
	
	//Should be removed
	public ArrayList<Coordinate> getList(){
		return list;
	}
	
}
