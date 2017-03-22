

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class SearchCell {
	protected int xcoord, ycoord;
	protected int G;
	protected int H;
	
	public SearchCell(Coordinate start){
		xcoord=start.getX();
		ycoord=start.getY();
		G=0;
		H=0;
	}
	
	public int getF(){
		return (G+H);
	}
	
	public void setG(int value){
		this.G=value;
	}
	
	public void setH(int value){
		this.H=value;
	}
	
	public int manhattanDistance(Coordinate _goal){
		int x= Math.abs(this.xcoord-_goal.getX());
		int y= Math.abs(this.ycoord-_goal.getY());
		return (x+y);
	}
	
	public int heuristicsSingleAStar(Coordinate _goal, ArrayList<Coordinate> arrayList){
		PathFindingDinamic a=new PathFindingDinamic(new SearchCell(new Coordinate(this.xcoord,this.ycoord)), new SearchCell(new Coordinate(_goal.getX(),_goal.getY())),arrayList);
	//	PathFinding a=new PathFinding(this,new SearchCell(new Coordinate(_goal.getX(),_goal.getY())));
		
		for(Coordinate n:a.aStar()){
		//	System.out.println(n.getX()+" "+n.getY());
		}
		
		return a.aStar().size();
	}
	
	public int heuristicsSingleAStar(Coordinate _goal, LinkedHashMap<Integer, ArrayList<Coordinate>> arrayList2, int val){
		LinkedHashMap<Integer, ArrayList<Coordinate>> asd=new LinkedHashMap<>();
//		System.out.println(arrayList2.size());
		for(java.util.Map.Entry<Integer, ArrayList<Coordinate>> e:arrayList2.entrySet()){
			if(asd.containsKey(e.getKey())){
				for(Coordinate c:e.getValue()){
				asd.get(e.getKey()).add(new Coordinate(c.getX(),c.getY()));
				}
			}
			else{
				if(e.getValue()!=null){
				ArrayList<Coordinate> array=new ArrayList<>();
				for(Coordinate c:e.getValue()){
					array.add(new Coordinate(c.getX(),c.getY()));
				}
				asd.put(e.getKey(), array);
				}
			}
		}
		PathFindingDinamic2 a=new PathFindingDinamic2(new SearchCell(new Coordinate(this.xcoord,this.ycoord)), new SearchCell(new Coordinate(_goal.getX(),_goal.getY())),arrayList2);
	//	PathFinding a=new PathFinding(this,new SearchCell(new Coordinate(_goal.getX(),_goal.getY())));
		
		for(Coordinate n:a.aStar()){
		//	System.out.println(n.getX()+" "+n.getY());
		}
		
		return a.aStar().size();
	}
	
	public int heuristicsHCAStar(Coordinate _goal, LinkedHashMap<Integer, ArrayList<Coordinate>> arrayList2, int i){
	//	PathFindingDinamic a=new PathFindingDinamic(new SearchCell(new Coordinate(this.xcoord,this.ycoord)), new SearchCell(new Coordinate(_goal.getX(),_goal.getY())),arrayList);
	//	PathFinding a=new PathFinding(this,new SearchCell(new Coordinate(_goal.getX(),_goal.getY())));
		LinkedHashMap<Integer, ArrayList<Coordinate>> a=new LinkedHashMap<Integer, ArrayList<Coordinate>>();
	
		PF b=new PF(new SearchCell(new Coordinate(this.xcoord,this.ycoord)), new SearchCell(new Coordinate(_goal.getX(),_goal.getY())),arrayList2);
		
		return b.aStar().size();
	}
	
	
}
