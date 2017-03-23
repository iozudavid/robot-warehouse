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

public class PF {
	SearchCell start;
	SearchCell goal;
	float mntDistance;
	protected ArrayList<SearchCell> obstacles;
	protected ArrayList<SearchCell> openList;
	protected LinkedHashMap<SearchCell, ArrayList<SearchCell>> graph;
	protected Map<SearchCell, Integer> level;
	protected Map<SearchCell, SearchCell> predecessor;
	protected GridMap map = MapUtils.createRealWarehouse();
	LinkedHashMap<Integer, ArrayList<Coordinate>> reserved;
	int obstaclesNumber;
	boolean equal;

	public PF(SearchCell start, SearchCell goal, LinkedHashMap<Integer, ArrayList<Coordinate>> reserved) {
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
		level = new HashMap<>();
		graph = new LinkedHashMap<>();
		predecessor = new HashMap<>();
		this.reserved = reserved;
		obstaclesNumber = obstacles.size();
		equal=true;
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
	
	public LinkedHashMap<Integer, ArrayList<Coordinate>> getMap(int i){
		LinkedHashMap<Integer, ArrayList<Coordinate>> result=new LinkedHashMap<>();
		if(i>=reserved.size()){
			result.put(0, reserved.get(reserved.size()-1));
		}
		else{
		for(Entry<Integer,ArrayList<Coordinate>> a:reserved.entrySet()){
			if(a.getKey()>=i){
				result.put(a.getKey()-i, a.getValue());
			}
		}
		}
		return result;
	}
	
	public void isEqual(int i){
		if(reserved.get(i-1).size()>reserved.get(i).size()){
			equal=false;
		}
	}

	public ArrayList<Coordinate> aStar() {
		ArrayList<Coordinate> lastPositions=new ArrayList<>();
		
		if(reserved.size()>0){
			int positions=0;
			positions=reserved.get(0).size();
			System.out.println(positions);
		for(Entry<Integer,ArrayList<Coordinate>> e: reserved.entrySet()){
			if(e.getValue().size()<positions){
				System.out.println(reserved.get(e.getKey()-1).get(0).getX()+" "+reserved.get(e.getKey()-1).get(0).getY());
				lastPositions.add(reserved.get(e.getKey()-1).get(0));
				break;
			}
		}
		
		for(Coordinate c:reserved.get(reserved.size()-1)){
			lastPositions.add(c);
		}
		
		for(Coordinate a:lastPositions){
			if(a.getX()==goal.xcoord && a.getY()==goal.ycoord){
				ArrayList<Coordinate> finalPath = new ArrayList<>();
				finalPath.add(new Coordinate(start.xcoord,start.ycoord));
				return finalPath;
			}
		}
		}
		ArrayList<Coordinate> finalPath = new ArrayList<>();
		start.setG(0);
		if(reserved.size()>0){
		if(reserved.size()>1)
			
			start.setH(start.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord), getMap(0),1));
		}
		else
			start.setH(start.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord), new LinkedHashMap<Integer, ArrayList<Coordinate>>(), 1));

		SearchCell currentCell = start;
		int expandingLevel = 0;
		level.put(currentCell, expandingLevel);
		ArrayList<SearchCell> l = new ArrayList<SearchCell>();
		l.add(start);
	
		predecessor.put(start, start);
		int i = 0;
		int count=0;
		openList.add(currentCell);
		boolean repeat=false;
				
		while (openList.isEmpty()==false) {
		
			
			ArrayList<SearchCell> l2 = new ArrayList<SearchCell>();
			for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
				if (e.getKey().xcoord == predecessor.get(openList.get(0)).xcoord
						&& e.getKey().ycoord == predecessor.get(openList.get(0)).ycoord) {
					for (SearchCell c : e.getValue())
						l2.add(c);
				}
			}
			
			if(l2.size()<level.get(openList.get(0))){
			int number=l2.size();
			while(number!=level.get(openList.get(0))){
			l2.add(openList.get(0));
			number++;
			}
			}
			
			for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
				if(e.getKey().xcoord==openList.get(0).xcoord && e.getKey().ycoord==openList.get(0).ycoord){
			
					graph.remove(e.getKey());		
					break;
				}
			}
			
			if(count>0){
				l2.add(openList.get(0));
			}
			graph.put(currentCell, l2);
			
			
			
			if(isGoal(currentCell)==true && isInReserved(currentCell, level.get(currentCell))==false){
				break;
			}
			
			if(level.get(currentCell)>20){
				//break;
			}
			
			i++;

			
			if(count>0){
				level.replace(currentCell, level.get(currentCell)+1);
				count--;
				
			}
		count=0;
		
		
			if(reserved.size()==1 && obstaclesNumber==obstacles.size()){
				for(Coordinate a:reserved.get(0)){
					obstacles.add(new SearchCell(new Coordinate(a.getX(),a.getY())));
				}
			}
			if (level.get(currentCell)+1+count < reserved.size()) {
				
				isEqual(level.get(currentCell)+1);

				if (level.get(currentCell)+count != 0 && equal==true) {
					while (obstacles.size() != obstaclesNumber) {
						
						obstacles.remove(obstacles.size() - 1);
						
					}
				}
				else if(level.get(currentCell) !=0 && equal==false){
					obstacles.remove(obstacles.size()-1);
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
			
			for(SearchCell a:obstacles){
				for(SearchCell b:openList){
					if(a.xcoord==b.xcoord && a.ycoord==b.ycoord){
						openList.remove(b);
						break;
					}
				}
			}
	
		
			
			ArrayList<SearchCell> successors = new ArrayList<>();
			successors = getSuccessors(currentCell);
			for (SearchCell cell : successors) {
					if (isInOpenList(cell) == true) {

					expandingLevel = level.get(currentCell) + 1;
					SearchCell a2=null;
					for(SearchCell a:openList){
						if(a.xcoord==cell.xcoord && a.ycoord==cell.ycoord){
							a2=a;
						}
					}
					openList.remove(a2);
			
					int n=0;
				
						expandingLevel = level.get(currentCell) + 1;
						a2.setG(expandingLevel);
				
						if(reserved.size()>expandingLevel){
						a2.setH(cell.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord)
								, getMap(expandingLevel),expandingLevel));
						}
						else
							a2.setH(cell.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord), getMap(expandingLevel), reserved.size()-1));
		
						level.replace(a2, expandingLevel);			
						
						openList.add(a2);
						
						
					
				} else {					
					expandingLevel = level.get(currentCell) + 1;
					cell.setG(expandingLevel);
					if(reserved.size()>expandingLevel){
					cell.setH(cell.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord), getMap(expandingLevel),expandingLevel));
				
					}
		
					else
						cell.setH(cell.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord), getMap(expandingLevel), reserved.size()-1));
				
					openList.add(cell);
					level.put(cell, expandingLevel);
					predecessor.put(cell, currentCell);
				
				}
			}

			currentCell.G+=1;
			if(reserved.size()>expandingLevel){
				currentCell.setH(currentCell.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord), getMap(expandingLevel), expandingLevel));
				}
			
				else
					currentCell.setH(currentCell.heuristicsSingleAStar(new Coordinate(goal.xcoord, goal.ycoord), getMap(expandingLevel), reserved.size()-1));

		
			selectionSortOpenList(openList);	
			
			if(openList.isEmpty()==true)
				break;
			
			
			boolean ok1=false;
			boolean ok2=false;
			
			
			
			for (Entry<Integer, ArrayList<Coordinate>> e : reserved.entrySet()) {
				if (e.getKey() == level.get(predecessor.get(openList.get(0)))+count) {
					for (Coordinate c : e.getValue()) {
						if(c.getX()==openList.get(0).xcoord && c.getY()==openList.get(0).ycoord){
							ok1=true;
						}
					}
				}
				if (e.getKey() == level.get(predecessor.get(openList.get(0)))+count+1) {
					for (Coordinate c : e.getValue()) {
						if(c.getX()==predecessor.get(openList.get(0)).xcoord && c.getY()==predecessor.get(openList.get(0)).ycoord){
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
			
			
			
			repeat=false;
			SearchCell cop=currentCell;
			currentCell = openList.get(0);
			
		}
		
		finalPath.add(new Coordinate(start.xcoord,start.ycoord));
		for (Entry<SearchCell, ArrayList<SearchCell>> e : graph.entrySet()) {
			if (e.getKey().xcoord == currentCell.xcoord && e.getKey().ycoord == currentCell.ycoord) {
				for (SearchCell c : e.getValue()){
					
					finalPath.add(new Coordinate(c.xcoord, c.ycoord));
					
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
