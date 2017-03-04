package warehouse;

import java.util.ArrayList;

public class Path{
	
	private ArrayList<Coordinate> list;
	
	public Path( ArrayList<Coordinate> _coordlist){
		
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

}
