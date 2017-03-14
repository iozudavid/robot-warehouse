package warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;

public class PathFindingForTest {

	SearchCell start;
	SearchCell goal;
	float mntDistance;
	protected ArrayList<SearchCell> obstacles;
	protected ArrayList<SearchCell> openList;
	protected ArrayList<SearchCell> closedList;
	protected Map<SearchCell, ArrayList<SearchCell>> graph;
	protected Map<SearchCell, Integer> level;
	protected Map<SearchCell, SearchCell> predecessor;
	protected GridMap map; 

	public PathFindingForTest(SearchCell start, SearchCell goal) {
		map= MapUtils.createMarkingWarehouseMap();
		if(map.isValidGridPosition(start.xcoord, start.ycoord)==false){
			return;
		}
		if(map.isValidGridPosition(goal.xcoord, goal.ycoord)==false){
			return;
		}
		map= MapUtils.createMarkingWarehouseMap();
		this.start = start;
		this.goal = goal;
		obstacles = new ArrayList<>();
		for (int i = 0; i < map.getXSize(); i++) {
			for (int j = 0; j < map.getYSize(); j++) {
			//	if(map.isValidGridPosition(i, j)){
				if (map.isObstructed(i, j)) {
					obstacles.add(new SearchCell(new Coordinate(i, j)));
				}
				//}
			}
		}
		
		for(SearchCell da: obstacles){
			if(da.xcoord==start.xcoord && da.ycoord==start.ycoord){
			this.start=null;
			this.goal=null;
				return;
			}
			if(da.xcoord==goal.xcoord && da.ycoord==goal.ycoord){
				this.start=null;
				this.goal=null;
				return;
			}
		}
		
		openList = new ArrayList<>();
		closedList = new ArrayList<>();
		level = new HashMap<>();
		graph = new HashMap<>();
		predecessor = new HashMap<>();
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

		if (map.isValidGridPosition(state.xcoord - 1, state.ycoord)==true) {
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

		if (map.isValidGridPosition(state.xcoord + 1, state.ycoord)==true) {
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

		if (map.isValidGridPosition(state.xcoord, state.ycoord - 1)==true) {
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

		if (map.isValidGridPosition(state.xcoord, state.ycoord + 1)==true) {
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
		if(start!=null && goal!=null){
		ArrayList<Coordinate> finalPath=new ArrayList<>();
		start.setG(0);
		start.setH(start.manhattanDistance(new Coordinate(goal.xcoord, goal.ycoord)));
		SearchCell currentCell = start;
		int expandingLevel = 0;
		level.put(currentCell, expandingLevel);
		ArrayList<SearchCell> l = new ArrayList<SearchCell>();
		l.add(start);
		graph.put(start, l);
		predecessor.put(start, start);

		while (isGoal(currentCell) == false) {

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
		
			ArrayList<SearchCell> l2 = new ArrayList<SearchCell>();
			for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
				if (e.getKey().xcoord == predecessor.get(openList.get(0)).xcoord
						&& e.getKey().ycoord == predecessor.get(openList.get(0)).ycoord) {
					for (SearchCell c : e.getValue())
						l2.add(c);
				}
			}

			l2.add(openList.get(0));
			graph.put(openList.get(0), l2);
			currentCell = openList.get(0);

		}
		for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
			if (e.getKey().xcoord == goal.xcoord && e.getKey().ycoord == goal.ycoord) {
				for (SearchCell c : e.getValue())
					finalPath.add(new Coordinate(c.xcoord, c.ycoord));
					//System.out.println(c.xcoord + ", " + c.ycoord);
			}
		}
		finalPath.remove(0);
		return finalPath;
		}
		return new ArrayList<>();
	}
	
	public static void main(String[] args){
		ArrayList<SearchCell> list = new ArrayList<>();
		SearchCell e1 = new SearchCell(new Coordinate(0, 1));
		SearchCell e2 = new SearchCell(new Coordinate(2, 2));
		PathFinding path = new PathFinding(e1, e2);
		for(Coordinate c: path.aStar()){
			System.out.println(c.getX()+" "+c.getY());
		}
		
	}
}