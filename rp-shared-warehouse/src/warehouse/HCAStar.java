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
			PF path = new PF(new SearchCell(e.getKey()),
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
		
			if(reserved.size()>paths.get(paths.size()-1).size()){
				int nr=paths.get(paths.size()-1).size();
				while(nr!=reserved.size()){
					reserved.get(nr).add(paths.get(paths.size()-1).get(paths.get(paths.size()-1).size()-1));
					nr++;
				}
			}
			i = 0;

		}

		ArrayList<Path> a=new ArrayList<Path>();
		i = 1;

		for (ArrayList<Coordinate> p : paths) {
			a.add(new Path(p,0));
			
			i++;
		}
		
		return a;
	}

}
