package warehouse;

import java.util.ArrayList;

public class Path{
	
	private int numberOfItems;
	private ArrayList<Coordinate> list;
	
	public Path( ArrayList<Coordinate> _coordlist,int numberOfItems){
			this.numberOfItems = 0;
			this.numberOfItems = numberOfItems;
		list = _coordlist;
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
	
	//Should be removed
	public ArrayList<Coordinate> getList(){
		return list;
	}
	
}
