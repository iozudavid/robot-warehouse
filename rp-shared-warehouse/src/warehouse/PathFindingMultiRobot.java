package warehouse;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import lejos.util.Delay;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;

public class PathFindingMultiRobot {
	SearchCell start;
	SearchCell goal;
	float mntDistance;
	protected ArrayList<SearchCell> obstacles;
	protected ArrayList<SearchCell> openList;
	protected ArrayList<SearchCell> closedList;
	protected LinkedHashMap<SearchCell, ArrayList<SearchCell>> graph;
	protected Map<SearchCell, Integer> level;
	protected Map<SearchCell, SearchCell> predecessor;
	protected GridMap map = MapUtils.createRealWarehouse();
	LinkedHashMap<Integer, ArrayList<Coordinate>> reserved;
	int obstaclesNumber;

	public PathFindingMultiRobot(SearchCell start, SearchCell goal, LinkedHashMap<Integer, ArrayList<Coordinate>> reserved) {
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
		graph = new LinkedHashMap<>();
		predecessor = new HashMap<>();
		this.reserved = reserved;
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

	public boolean isInReserved(SearchCell a, int i){
		for(Entry<Integer,ArrayList<Coordinate>> b:reserved.entrySet()){
			if(b.getKey()>=i){
			for(Coordinate d:b.getValue()){
				if(d.getX()==a.xcoord && d.getY()==a.ycoord){
					return true;
				}
			}
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
		start.setG(1);
		if(reserved.size()>1)
		start.setH(start.heuristicsHCAStar(new Coordinate(goal.xcoord, goal.ycoord), reserved, 0));
		else
			start.setH(start.heuristicsHCAStar(new Coordinate(goal.xcoord, goal.ycoord), new LinkedHashMap<>(),0));
		SearchCell currentCell = start;
		int expandingLevel = 0;
		level.put(currentCell, expandingLevel);
		ArrayList<SearchCell> l = new ArrayList<SearchCell>();
		l.add(start);
	//	graph.put(start, l);
		predecessor.put(start, start);
		int i = 0;
		int count=0;
		openList.add(currentCell);
		boolean isgoal=false;
		if(isGoal(start)){
			isgoal=true;
		}
		

		
		while (openList.isEmpty()==false) {
			
		//	Delay.msDelay(5000);
		//	System.out.println("---------------current:"+currentCell.xcoord+" "+currentCell.ycoord);
			
			ArrayList<SearchCell> l2 = new ArrayList<SearchCell>();
			for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
				if (e.getKey().xcoord == predecessor.get(openList.get(0)).xcoord
						&& e.getKey().ycoord == predecessor.get(openList.get(0)).ycoord) {
					for (SearchCell c : e.getValue())
						l2.add(c);
				}
			}
			l2.add(openList.get(0));

			
			for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
				if(e.getKey().xcoord==openList.get(0).xcoord && e.getKey().ycoord==openList.get(0).ycoord){
				//	System.out.println("exista:"+e.getKey().xcoord+" "+e.getKey().ycoord);
					graph.remove(e.getKey());		
					break;
				}
			}
			
			if(count>0){
				l2.add(openList.get(0));
			}
			graph.put(currentCell, l2);
			
			
			
			if(isGoal(currentCell)==true && isInReserved(currentCell, level.get(currentCell))==false){
		//	if(isGoal(currentCell)){
			break;
			}
			
			if(level.get(currentCell)>20){
				break;
			}
			
	//		System.out.println("count:"+i);
			i++;
	//		System.out.println("current: "+currentCell.xcoord+" "+currentCell.ycoord);
			
			if(count>0){
				level.replace(currentCell, level.get(currentCell)+1);
				count--;
				
			}
		
			//Delay.msDelay(5000);
			

			if (level.get(currentCell)+1+count < reserved.size()) {

				if (level.get(currentCell)+count != 0) {
					while (obstacles.size() != obstaclesNumber) {
					
						obstacles.remove(obstacles.size() - 1);
						
					}
				}
				for (Entry<Integer, ArrayList<Coordinate>> e : reserved.entrySet()) {
					if (e.getKey() == level.get(currentCell)+1+count) {
						for (Coordinate c : e.getValue()) {
							obstacles.add(new SearchCell(c));
						//	System.out.println("added: "+c.getX()+" "+c.getY());
						}
						break;
					}
					

				}

				i++;
			}
			
		

			ArrayList<SearchCell> successors = new ArrayList<>();
			successors = getSuccessors(currentCell);
		/*	if(reserved.size()>level.get(currentCell)+1)
			System.out.println("obstacle: "+ reserved.get(level.get(currentCell)+1).get(0).getX()+" "+reserved.get(level.get(currentCell)+1).get(0).getY());*/
			for (SearchCell cell : successors) {
				if (isInClosedList(cell) == true) {
				} else if (isInOpenList(cell) == true) {					
				} else {					
					expandingLevel = level.get(currentCell) + 1;
					cell.setG(expandingLevel);
					if(reserved.size()>expandingLevel){
					cell.setH(cell.heuristicsHCAStar(new Coordinate(goal.xcoord, goal.ycoord)
							, reserved,expandingLevel));
					}
			//		else if(reserved.size()>0)
				//		cell.setH(WHCAStar.getHeuristics(new SearchCell(new Coordinate(cell.xcoord,cell.ycoord)), new SearchCell(new Coordinate(goal.xcoord, goal.ycoord))
					//			, reserved.get(reserved.size()-1)));
				//	System.out.println("heuristic: "+cell.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord), reserved.get(expandingLevel+1)));
					
					else
						cell.setH(cell.heuristicsHCAStar(new Coordinate(goal.xcoord, goal.ycoord), new LinkedHashMap<>(),0));
					openList.add(cell);
					level.put(cell, expandingLevel);
					predecessor.put(cell, currentCell);
				}
			}

			currentCell.G+=1;

			selectionSortOpenList(openList);
			if(openList.isEmpty()==true)
				break;
			
		//	System.out.println("-------------");
		//	System.out.println("current "+currentCell.xcoord+" "+currentCell.ycoord);
		/*	for(SearchCell a:openList){
				System.out.println(a.xcoord+" "+a.ycoord+" "+a.getF());
			}*/
	
			
			if(isGoal(currentCell) && isInReserved(currentCell, level.get(currentCell))){
				closedList.remove(currentCell);
			}
			
			isgoal=false;
			
			boolean ok1=false;
			boolean ok2=false;
			
			
			
			for (Entry<Integer, ArrayList<Coordinate>> e : reserved.entrySet()) {
				if (e.getKey() == level.get(currentCell)+count) {
					for (Coordinate c : e.getValue()) {
						if(c.getX()==openList.get(0).xcoord && c.getY()==openList.get(0).ycoord){
							ok1=true;
						}
					}
				}
				if (e.getKey() == level.get(currentCell)+1+count) {
					for (Coordinate c : e.getValue()) {
						if(c.getX()==currentCell.xcoord && c.getY()==currentCell.ycoord){
							ok2=true;
						}
					}
				}
				

			}

			if(ok1==true && ok2==true){
				openList.remove(0);
			}
			
			if(openList.isEmpty()==true)
				break;
			
			
			
			
			SearchCell cop=currentCell;
			currentCell = openList.get(0);
			if(cop!=currentCell){
				openList.remove(cop);
				closedList.add(cop);
				count=0;
			//	System.out.println("-------------");
			//	System.out.println("current "+currentCell.xcoord+" "+currentCell.ycoord);
			/*	for(SearchCell a:openList){
					System.out.println(a.xcoord+" "+a.ycoord+" "+a.getF());
				}*/
			}
			else{
				boolean ok3=false;
				for (Entry<Integer, ArrayList<Coordinate>> e : reserved.entrySet()) {
				if (e.getKey() == level.get(currentCell)+1) {
					for (Coordinate c : e.getValue()) {
						if(c.getX()==currentCell.xcoord && c.getY()==currentCell.ycoord){
							ok3=true;
						}
					}
				}
				}
				if(ok3==false){
					
					count++;
				}
				else{
					count=0;
					openList.remove(currentCell);
					closedList.add(currentCell);
					currentCell=openList.get(0);
				}
			}
			
		//	System.out.println("-------------");
		//	System.out.println("current "+currentCell.xcoord+" "+currentCell.ycoord);
		/*	for(SearchCell a:openList){
				System.out.println(a.xcoord+" "+a.ycoord+" -> "+a.H);
			}*/
			
		//	System.out.println("current "+currentCell.xcoord+" "+currentCell.ycoord);
		//	for(SearchCell a:openList){
			//	System.out.println(a.xcoord+" "+a.ycoord+" "+a.getF());
			//}
			
		//	Delay.msDelay(5000);
		
		
		}

	//	System.out.println("escape");
		
		for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
			if (e.getKey().xcoord == currentCell.xcoord && e.getKey().ycoord == currentCell.ycoord) {
				for (SearchCell c : e.getValue()){
					finalPath.add(new Coordinate(c.xcoord, c.ycoord));
			//		System.out.println(c.xcoord+" "+c.ycoord);
				}
		}
		}
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
