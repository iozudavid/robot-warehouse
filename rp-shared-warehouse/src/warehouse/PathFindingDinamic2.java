package warehouse;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import lejos.util.Delay;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;
public class PathFindingDinamic2 {
	SearchCell start;
	SearchCell goal;
	float mntDistance;
	protected ArrayList<SearchCell> obstacles;
	protected ArrayList<SearchCell> openList;
	protected ArrayList<SearchCell> closedList;
	protected Map<SearchCell, ArrayList<SearchCell>> graph;
	protected Map<SearchCell, Integer> level;
	protected Map<SearchCell, SearchCell> predecessor;
	protected GridMap map = MapUtils.createRealWarehouse();
	LinkedHashMap<Integer, ArrayList<Coordinate>> reserved;
	
	
	int obstaclesNumber;
	public PathFindingDinamic2(SearchCell start, SearchCell goal, LinkedHashMap<Integer, ArrayList<Coordinate>> reserved) {
		this.start = start;
		this.goal = goal;
		obstacles = new ArrayList<>();
		for (int i = 0; i <= map.getXSize(); i++) {
			for (int j = 0; j <= map.getYSize(); j++) {
				if (map.isObstructed(i, j)) {
					obstacles.add(new SearchCell(new Coordinate(i, j)));
				}
			}
		}
		openList = new ArrayList<>();
		closedList = new ArrayList<>();
		level = new HashMap<>();
		graph = new HashMap<>();
		predecessor = new HashMap<>();
		this.reserved=new LinkedHashMap<>();
		for(Entry<Integer, ArrayList<Coordinate>> a:reserved.entrySet()){
			if(this.reserved.containsKey(a.getKey())){
				for(Coordinate c:a.getValue()){
				this.reserved.get(a.getKey()).add(new Coordinate(c.getX(),c.getY()));
				}
			}
			else{
				if(a.getValue()!=null){
				ArrayList<Coordinate> array=new ArrayList<>();
				for(Coordinate c:a.getValue()){
					array.add(new Coordinate(c.getX(),c.getY()));
				}
				this.reserved.put(a.getKey(), array);
				}
			}
		}
	//	System.out.println(reserved.size());
		obstaclesNumber = obstacles.size();
		
	}
	public static void selectionSortOpenList(ArrayList<SearchCell> list) {
		for (int i = 0; i < list.size(); i++) {
			int index = i;
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(j).getF() < list.get(index).getF()) {
					index = j;
				}
			}
			SearchCell aux = list.get(index);
			list.set(index, list.get(i));
			list.set(i, aux);
		}
	}
	public boolean isGoal(SearchCell state) {
		if (state.xcoord == goal.xcoord && state.ycoord == goal.ycoord)
			return true;
		return false;
	}
	public boolean isInOpenList(SearchCell state) {
		for (SearchCell c : openList) {
			if (c.xcoord == state.xcoord && c.ycoord == state.ycoord) {
				return true;
			}
		}
		return false;
	}
	public Coordinate getLocation(int time) {
		return aStar().get(time);
	}
	public boolean isInClosedList(SearchCell state) {
		for (SearchCell c : closedList) {
			if (c.xcoord == state.xcoord && c.ycoord == state.ycoord) {
				return true;
			}
		}
		return false;
	}
	public ArrayList<SearchCell> getSuccessors(SearchCell state) {
		ArrayList<SearchCell> successors = new ArrayList<>();
		if (map.isValidGridPosition(state.xcoord - 1, state.ycoord) == true) {
			SearchCell cell = new SearchCell(new Coordinate(state.xcoord - 1, state.ycoord));
			boolean ok = false;
			for (SearchCell c : obstacles) {
				if (c.xcoord == cell.xcoord && c.ycoord == cell.ycoord) {
					ok = true;
				}
			}
			if (ok == false) {
				successors.add(cell);
			}
		}
		if (map.isValidGridPosition(state.xcoord + 1, state.ycoord) == true) {
			SearchCell cell = new SearchCell(new Coordinate(state.xcoord + 1, state.ycoord));
			boolean ok = false;
			for (SearchCell c : obstacles) {
				if (c.xcoord == cell.xcoord && c.ycoord == cell.ycoord) {
					ok = true;
				}
			}
			if (ok == false) {
				successors.add(cell);
			}
		}
		if (map.isValidGridPosition(state.xcoord, state.ycoord - 1) == true) {
			SearchCell cell = new SearchCell(new Coordinate(state.xcoord, state.ycoord - 1));
			boolean ok = false;
			for (SearchCell c : obstacles) {
				if (c.xcoord == cell.xcoord && c.ycoord == cell.ycoord) {
					ok = true;
				}
			}
			if (ok == false) {
				successors.add(cell);
			}
		}
		if (map.isValidGridPosition(state.xcoord, state.ycoord + 1) == true) {
			SearchCell cell = new SearchCell(new Coordinate(state.xcoord, state.ycoord + 1));
			boolean ok = false;
			for (SearchCell c : obstacles) {
				if (c.xcoord == cell.xcoord && c.ycoord == cell.ycoord) {
					ok = true;
				}
			}
			if (ok == false) {
				successors.add(cell);
			}
		}
		return successors;
	}
	public ArrayList<Coordinate> aStar() {
		ArrayList<Coordinate> finalPath = new ArrayList<>();
		start.setG(0);
		start.setH(start.manhattanDistance(new Coordinate(goal.xcoord, goal.ycoord)));
		SearchCell currentCell = start;
		int expandingLevel = 0;
		level.put(currentCell, expandingLevel);
		ArrayList<SearchCell> l = new ArrayList<SearchCell>();
		l.add(start);
		graph.put(start, l);
		predecessor.put(start, start);
		int i = -1;
		int count=0;
		openList.add(currentCell);
		
		while (openList.isEmpty()==false) {
	
	//System.out.println(openList.get(0).xcoord+" "+openList.get(0).ycoord);
			//Delay.msDelay(1000);
			if(isGoal(currentCell)){
				boolean ok=false;
				for(Entry<Integer,ArrayList<Coordinate>> a:reserved.entrySet()){
					if(a.getKey()>level.get(currentCell)+count){
						for(Coordinate b:a.getValue()){
							if(b.getX()==currentCell.xcoord && b.getY()==currentCell.ycoord){
								ok=true;
							}
						}
					}
				}
				if(ok==true)
				break;
			}
			
		//	if(level.get(currentCell)>=val){
			
			if (level.get(currentCell)+count+1 < reserved.size()) {
				if (level.get(currentCell)+count != 0) {
					while (obstacles.size() != obstaclesNumber) {
						obstacles.remove(obstacles.size() - 1);
						
					}
				}
				for (Entry<Integer, ArrayList<Coordinate>> e : reserved.entrySet()) {
					if (e.getKey() == level.get(currentCell)+1+count) {
						for (Coordinate c : e.getValue()) {
							obstacles.add(new SearchCell(c));
						}
						break;
					}
					
				}
				i++;
			}
			else{
				if(reserved.size()>0){
					while (obstacles.size() != obstaclesNumber) {
						obstacles.remove(obstacles.size() - 1);
						
					}
		
					if(reserved.get(reserved.size()-1)!=null){
				for (Coordinate c : reserved.get(reserved.size()-1)) {
					
					obstacles.add(new SearchCell(new Coordinate(c.getX(),c.getY())));
				}
					}
				}
				
			}
		//	}
			
		//	System.out.println("OBSTACOOOOOOOOOOOL"+obstacles.get(obstacles.size()-1).xcoord+" "+obstacles.get(obstacles.size()-1).ycoord);
			
			
			ArrayList<SearchCell> successors = new ArrayList<>();
			successors = getSuccessors(currentCell);
			for (SearchCell cell : successors) {
				if (isInClosedList(cell) == true) {
				} else if (isInOpenList(cell) == true) {
				} else {
					expandingLevel = level.get(currentCell) + 1;
					cell.setG(expandingLevel);
					cell.setH(cell.manhattanDistance(new Coordinate(goal.xcoord, goal.ycoord)));
					openList.add(cell);
					level.put(cell, expandingLevel);
					predecessor.put(cell, currentCell);
				}
			}
			openList.remove(currentCell);
			closedList.add(currentCell);
			selectionSortOpenList(openList);
			if(openList.isEmpty()==true)
				break;
			
			
			
			boolean ok1=false;
			boolean ok2=false;
			
			
			
			for (Entry<Integer, ArrayList<Coordinate>> e : reserved.entrySet()) {
				if (e.getKey() == level.get(predecessor.get(openList.get(0)))+count) {
					
					if(e.getValue()!=null){
					for (Coordinate c : e.getValue()) {
						
						if(c.getX()==openList.get(0).xcoord && c.getY()==openList.get(0).ycoord){
							ok1=true;
					//		System.out.println("da2");
					//		System.out.println(openList.get(0).xcoord+" "+openList.get(0).ycoord);
					//		System.out.println("urmatorul: "+reserved.get(level.get(currentCell)+1+val).get(0).getX()+" "+reserved.get(level.get(currentCell)+1+val).get(0).getY());
						}
					}
					}
				}
				if (e.getKey() == level.get(predecessor.get(openList.get(0)))+1+count) {
					if(e.getValue()!=null){
					for (Coordinate c : e.getValue()) {
						if(c.getX()==predecessor.get(openList.get(0)).xcoord && c.getY()==predecessor.get(openList.get(0)).ycoord){
					//		System.out.println("daaaaaaaaaaaa");
				//			System.out.println(c.getX()+" "+c.getY());
						//	System.out.println(openList.get(0).xcoord+" "+openList.get(0).ycoord);
							ok2=true;
						}
					}
					}
				}
				
			}
			
			if(ok1==true && ok2==true){
		//		System.out.println("nuuuuuuuuuuuu");
			//	System.out.println(openList.get(0).xcoord+" "+openList.get(0).ycoord);
				openList.remove(0);
			//	System.out.println(openList.get(0).xcoord+" "+openList.get(0).ycoord);
			}
			
			if(openList.isEmpty()==true)
				break;
			
			ArrayList<SearchCell> l2 = new ArrayList<SearchCell>();
			for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
				if (e.getKey().xcoord == predecessor.get(openList.get(0)).xcoord
						&& e.getKey().ycoord == predecessor.get(openList.get(0)).ycoord) {
					for (SearchCell c : e.getValue())
						l2.add(c);
				}
			}
			
			//System.out.println(currentCell.xcoord+" "+currentCell.ycoord);
			//System.out.println("--------"+obstacles.get(obstacles.size()-1).xcoord+" "+obstacles.get(obstacles.size()-1).ycoord);
			
			
			l2.add(openList.get(0));
			
			graph.put(openList.get(0), l2);
			SearchCell cop=currentCell;
			currentCell = openList.get(0);
		
		}
		
		
		for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
			if (e.getKey().xcoord == goal.xcoord && e.getKey().ycoord == goal.ycoord) {
				for (SearchCell c : e.getValue()){
					finalPath.add(new Coordinate(c.xcoord, c.ycoord));
				//	System.out.println(c.xcoord+" "+c.ycoord);
				}
			}
		}
	//	System.out.println("AICIFMM");
		for(SearchCell a:obstacles){
			//System.out.println("o:"+a.xcoord+" "+a.ycoord);
		}
		for (Coordinate c : finalPath){
			
		//	finalPath.add(new Coordinate(c.xcoord, c.ycoord));
			//System.out.println(c.getX()+" "+c.getY());
		}
		//System.out.println("path size:"+finalPath.size());
		return finalPath;
	}
	public static void main(String[] args) {
		ArrayList<SearchCell> list = new ArrayList<>();
		SearchCell e1 = new SearchCell(new Coordinate(2, 1));
		SearchCell e2 = new SearchCell(new Coordinate(5, 5));
		PathFinding path = new PathFinding(e1, e2);
		System.out.println(path.aStar());
	}
}