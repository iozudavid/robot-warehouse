package warehouse;

import java.util.ArrayList;

public class Path{
	
	private ArrayList<Coordinate> list;
	private int numberOfItems;
	
	public Path( ArrayList<Coordinate> _coordlist,int numberOfItems){
		this.numberOfItems = 0;
		setNumberOfItems(numberOfItems);
		list = _coordlist;
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
	
	public ArrayList<Coordinate> get(){
		return list;
	}
	
	public void setNumberOfItems(int num){
		if (num >= 0){
			this.numberOfItems = num;
		}
	}

	public int getNumberOFItems(){
		return numberOfItems;
	}
	
	//Should be removed
	public ArrayList<Coordinate> getList(){
		return list;
	}
}
