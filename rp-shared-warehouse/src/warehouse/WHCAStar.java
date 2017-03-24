package warehouse;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import lejos.util.Delay;
import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;

public class WHCAStar {

	LinkedHashMap<Coordinate, Coordinate> map;
	LinkedHashMap<Integer, ArrayList<Coordinate>> reserved;
	ArrayList<ArrayList<Coordinate>> paths;
	LinkedHashMap<Coordinate, ArrayList<Coordinate>> eachPath;

	public WHCAStar(LinkedHashMap<Coordinate, Coordinate> map) {
		this.map = map;
		reserved = new LinkedHashMap<Integer, ArrayList<Coordinate>>();
		paths = new ArrayList<ArrayList<Coordinate>>();
		eachPath = new LinkedHashMap<Coordinate, ArrayList<Coordinate>>();
		for (Entry<Coordinate, Coordinate> a : map.entrySet()) {
			eachPath.put(a.getKey(), new ArrayList<Coordinate>());
		}
	}
	
	public static int getHeuristics(SearchCell start, SearchCell goal, ArrayList<Coordinate> o){
		ArrayList<Coordinate> obstacles = new ArrayList<>();
		for(Coordinate a:o){
			obstacles.add(new Coordinate(a.getX(),a.getY()));
		//	System.out.println("nice: "+a.getX()+" "+a.getY());
		}
		GridMap map = MapUtils.createRealWarehouse();
		SearchCell a=new SearchCell(new Coordinate(start.xcoord,start.ycoord));
		
		return a.heuristicsSingleAStar(new Coordinate(goal.xcoord,goal.ycoord), obstacles);
	}

	public void orderMap(HashMap<Coordinate, Coordinate> a, int i) {

		LinkedHashMap<Coordinate, Coordinate> b = new LinkedHashMap<Coordinate, Coordinate>();
		for (int j = i; j < a.size(); j++) {
			int count = 0;
			for (Entry<Coordinate, Coordinate> e : a.entrySet()) {
				if (count == j) {
					b.put(e.getKey(), e.getValue());
				} else
					count++;
			}
		}
		for (int j = 0; j < i; j++) {
			int count = 0;
			for (Entry<Coordinate, Coordinate> e : a.entrySet()) {
				if (count == j) {
					b.put(e.getKey(), e.getValue());
				} else
					count++;
			}
		}
		a.clear();
		for (Entry<Coordinate, Coordinate> e : b.entrySet()) {
			a.put(e.getKey(), e.getValue());
		}

	}

	public void fillPaths(LinkedHashMap<Coordinate, ArrayList<Coordinate>> p, int w, int count) {
		
		LinkedHashMap<Coordinate,Boolean> verify=new LinkedHashMap<Coordinate, Boolean>();
		for (Entry<Coordinate, Coordinate> a : map.entrySet()) {
			verify.put(a.getKey(), false);
		}
		
	/*	int max=0;
		for (ArrayList<Coordinate> path : paths) {
			max=0;
			if(Math.min(w/2,path.size())>max)
				max=Math.min(w/2,path.size());
		}*/
		
		for (ArrayList<Coordinate> path : paths) {
		
		//	System.out.println(path.size());
			for (Entry<Coordinate, ArrayList<Coordinate>> e : eachPath.entrySet()) {
				
				
				if (verify.get(e.getKey())==false && e.getValue().size()==0 && e.getKey().getX() == path.get(0).getX()
						&& e.getKey().getY() == path.get(0).getY()) {
					
					int size=0;
					for (int i = 0; i < Math.min(w/2,path.size()) ; i++) {
						e.getValue().add(path.get(i));
						size++;
					}
				/*	while(size!=max){
						e.getValue().add(path.get(Math.min(w/2,path.size())-1));
						size++;
					}*/
					verify.replace(e.getKey(), false, true);
					
				}
				else if(verify.get(e.getKey())==false && e.getValue().size()>0 && path.get(0).getX()==e.getValue().get(e.getValue().size()-1).getX()
						&& path.get(0).getY()==e.getValue().get(e.getValue().size()-1).getY()){
					
					int size=0;
					for (int i = 1; i < Math.min(w/2+1,path.size()) ; i++) {
						e.getValue().add(path.get(i));
						size++;
					}
				/*	while(size!=max){
						e.getValue().add(path.get(Math.min(w/2,path.size())-1));
						size++;
					}*/
				
					verify.replace(e.getKey(), false, true);
				}
				
			}
			
	

		}
	
		for (Entry<Coordinate, ArrayList<Coordinate>> e : eachPath.entrySet()) {
		
			for(Coordinate c:e.getValue()){
			System.out.println(e.getKey().getX()+" "+e.getKey().getY()+" -> "+c.getX()+" "+c.getY());
			}
		
		}
	//	System.exit(0);

	}

	public boolean isValid() {
		for (Entry<Coordinate, ArrayList<Coordinate>> e : eachPath.entrySet()) {
			if(e.getValue().size()==0)
				return false;
			if (e.getValue().get(e.getValue().size() - 1).getX() != map.get(e.getKey()).getX()
					|| e.getValue().get(e.getValue().size() - 1).getY() != map.get(e.getKey()).getY()) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<Path> startFindingPaths() {
		int i = 0;
		int count = 1;
		int window = 7;

		while (isValid() == false) {
			
			i=0;
			for (Entry<Coordinate, Coordinate> e : map.entrySet()) {
				System.out.println("here: "+reserved.size());
				PF path;
				if(eachPath.get(e.getKey()).size()-1<=0){
					path = new PF(
							new SearchCell(e.getKey()),
							new SearchCell(e.getValue()), reserved);
				}
				else{
					path = new PF(
						new SearchCell(eachPath.get(e.getKey()).get(eachPath.get(e.getKey()).size()-1)),
						new SearchCell(e.getValue()), reserved);
				}
		
				paths.add(path.aStar());
				
				//System.out.println(paths.get(paths.size()-1).get(0).getX()+" "+paths.get(paths.size()-1).get(0).getY());
			
				int cop=window;
				for (Coordinate c : paths.get(paths.size()-1)) {

					if (reserved.containsKey(i) == true) {
						reserved.get(i).add(c);
					} else {
						ArrayList<Coordinate> array = new ArrayList<Coordinate>();
						array.add(c);
						reserved.put(i, array);
					}
					i++;
					cop--;
					if(cop==0)
						break;
				}
			//	System.out.println("size: "+reserved.size());
				
				i = 0;
			}	
			
			if(count>map.size()){
				count=0;
			}
			
ArrayList<Path> a=new ArrayList<Path>();
			
			
			
		//	i = 1;
			fillPaths(eachPath, window, count);
			orderMap(map, count++);
			paths.clear();
			reserved.clear();
			
		}

		ArrayList<Path> a = new ArrayList<Path>();
		i = 1;
		
		for (Entry<Coordinate, ArrayList<Coordinate>> e : eachPath.entrySet()) {
			a.add(new Path(e.getValue(),0));
			for (Coordinate c : e.getValue()) {
			System.out.println(i + ": " + c.getX() + " " + c.getY());
			}
			i++;
		}

		return a;
	}

}
