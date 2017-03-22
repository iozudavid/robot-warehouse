

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import rp.robotics.mapping.GridMap;
import rp.robotics.mapping.MapUtils;

public class Test {

	public static boolean compareLists(ArrayList<Coordinate> arrayList, ArrayList<Coordinate> arrayList2) {
		int min = Math.min(arrayList.size(), arrayList2.size());
		for (int i = 0; i < min; i++) {
			if (arrayList.get(i).getX() == arrayList2.get(i).getX()
					&& arrayList.get(i).getY() == arrayList2.get(i).getY())
				return false;
		}
		return true;
	}

	public static boolean isValid(ArrayList<Path> a) {

		Path first;
		Path second;

		for (int i = 0; i < a.size(); i++) {
			first = a.get(i);
			for (int j = 0; j < a.size(); j++) {
				if (j == i) {

				} else {
					if (compareLists(a.get(i).getAllCoordinates(), a.get(j).getAllCoordinates()) == false) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		LinkedHashMap<Coordinate, Coordinate> h = new LinkedHashMap<Coordinate, Coordinate>();
		
		h.put(new Coordinate(2, 6), new Coordinate(2, 5));
		h.put(new Coordinate(2, 7), new Coordinate(2, 1));
		h.put(new Coordinate(2, 4), new Coordinate(2, 5));
		

		// h.put(new Coordinate(0, 0), new Coordinate(0, 5));
		// h.put(new Coordinate(0, 4), new Coordinate(2,0));

		 HCAStar cooperative=new HCAStar(h);
		// cooperative.startFindingPaths();
		// System.out.println(isValid(cooperative.startFindingPaths()));

//		WHCAStar cooperative = new WHCAStar(h);

		 int nr=1;
		for(Path a:cooperative.startFindingPaths()){
			for(Coordinate b:a.getAllCoordinates()){
				System.out.println(nr+" "+b.getX()+" "+b.getY());
			}
			nr++;
		}
		
		/*
		 * PathFindingMultiRobot a=new PathFindingMultiRobot(new SearchCell(new
		 * Coordinate(0, 0)), new SearchCell(new Coordinate(2,5)), new
		 * LinkedHashMap<>()); for(Coordinate a2:a.aStar()){
		 * System.out.println(a2.getX()+" "+a2.getY()); }
		 */
		// PathFinding a=new PathFinding(new SearchCell(new Coordinate(0,1)),new
		// SearchCell(new Coordinate(2,1)));
		// PathFinding b=new PathFinding(new SearchCell(new Coordinate(1,0)),new
		// SearchCell(new Coordinate(2,1)));
		// System.out.println(a.aStar().size());
		// System.out.println(b.aStar().size());

	/*	ArrayList<Coordinate> obstacles = new ArrayList<>();
		GridMap map = MapUtils.createRealWarehouse();
		SearchCell a = new SearchCell(new Coordinate(0, 2));
		for (int i = 0; i < map.getXSize(); i++) {
			for (int j = 0; j < map.getYSize(); j++) {
				if (map.isObstructed(i, j)) {
					obstacles.add(new Coordinate(i, j));

				}
			}
		}
		obstacles.add(new Coordinate(1, 0));*/
		
	/*	ArrayList<Coordinate> obstacles = new ArrayList<>();
		GridMap map = MapUtils.createRealWarehouse();
		SearchCell a = new SearchCell(new Coordinate(2, 4));
		LinkedHashMap<Integer, ArrayList<Coordinate>> b=new LinkedHashMap<>();
		ArrayList<Coordinate> asd=new ArrayList<>();
		
		asd.add(new Coordinate(2,7));
		int c=0;
		b.put(c, asd);
		c++;
		
		//int c=0;
		asd.add(new Coordinate(2,6));
		//int c=0;
		b.put(c, asd);
		c++;
		
		asd=new ArrayList<>();
		asd.add(new Coordinate(2,5));
		b.put(c, asd);
		c++;
	
		asd=new ArrayList<>();
		asd.add(new Coordinate(2,4));
		b.put(c, asd);
		c++;
	
		
		asd=new ArrayList<>();
		asd.add(new Coordinate(2,3));
		b.put(c, asd);
		c++;
		
		asd=new ArrayList<>();
		asd.add(new Coordinate(2,2));
		b.put(c, asd);
		c++;
		
		
		for(Entry<Integer, ArrayList<Coordinate>> bbb:b.entrySet()){
		//	System.out.println(bbb.getKey());
		}
		
	//	obstacles.add(new Coordinate(1, 0));

		System.out.println(a.heuristicsHCAStar(new Coordinate(2, 2), b, 0));*/
	//	System.out.println(b.size());
	//	System.out.println(a.heuristicsSingleAStar(new Coordinate(5, 3), b,0));
	//	System.out.println(a.heuristicsSingleAStar(new Coordinate(0,2), b ,1 ));
		// System.out.println(a.heuristicsSingleAStar(new Coordinate(2,0),
		// obstacles));

		// System.out.println(WHCAStar.getHeuristics(a, new SearchCell(new
		// Coordinate(0,0)), obstacles));

	}
}
