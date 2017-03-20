package warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HCAStar {

	LinkedHashMap<Coordinate, Coordinate> map;
	LinkedHashMap<Integer, ArrayList<Coordinate>> reserved;
	ArrayList<ArrayList<Coordinate>> paths;

	public HCAStar(LinkedHashMap<Coordinate, Coordinate> map) {
		this.map = map;
		reserved = new LinkedHashMap<Integer, ArrayList<Coordinate>>();
		paths = new ArrayList<ArrayList<Coordinate>>();
	}

	public ArrayList<Path> startFindingPaths() {
		int i = 0;
		int j = 0;
		for (Entry<Coordinate, Coordinate> e : map.entrySet()) {
			j++;
			PathFindingMultiRobot path = new PathFindingMultiRobot(new SearchCell(e.getKey()),
					new SearchCell(e.getValue()), reserved);
			paths.add(path.aStar());
		

				for(Coordinate c:paths.get(paths.size()-1)){
				if (reserved.containsKey(i) == true) {
					reserved.get(i).add(c);
				} else {
					ArrayList<Coordinate> array = new ArrayList<Coordinate>();
					array.add(c);
					reserved.put(i, array);
				}
				i++;
				}
		
			
			i = 0;

		}

		ArrayList<Path> a=new ArrayList<Path>();
		i = 1;
		
		for (ArrayList<Coordinate> p : paths) {
			a.add(new Path(p,1));
			for (Coordinate c : p) {
		//		System.out.println(i + ": " + c.getX() + " " + c.getY());
			}
			i++;
		}
		//System.out.println(a.size());
		
		return a;
	}
	
	public static void main(String[] args){
		LinkedHashMap a=new LinkedHashMap();
		a.put(new Coordinate(0,0), new Coordinate(2,0));
		a.put(new Coordinate(3,0), new Coordinate(3,1));
		HCAStar b=new HCAStar(a);
		for(Path c:b.startFindingPaths()){
	//		System.out.println(c.getNextCoord());
			
		}
	}

}
