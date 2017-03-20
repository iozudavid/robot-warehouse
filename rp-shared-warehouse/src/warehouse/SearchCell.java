package warehouse;

import java.util.ArrayList;
import java.util.HashMap;
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
	
	public int heuristicsSingleAStar(Coordinate _goal,ArrayList<SearchCell> obstacles){
		PathFindingDinamic a=new PathFindingDinamic(this, new SearchCell(_goal), obstacles);
		return a.aStar().size();
	}
	
	
	
}
