package networking;

import warehouse.Coordinate;
import warehouse.Path;

public class Message {
	//Class that just contains information about a received message, no
	//complex calculations just getters and setters
	
	private String msg;
	private Coordinate coord;
	
	private boolean isCoordinate;
	private Path path;
	private boolean isPath;
	
	public Message(String msg){
		this.msg = msg;
		this.isCoordinate = false;
	}
	
	public Message(Coordinate c){
		this.coord = c;
		this.isCoordinate = true;
	}

	public Message(Path p){
		this.path = p;
		this.isPath = true;
	}


	public String getMsg() {
		return msg;
	}

	public Coordinate getCoord() {
		return coord;
	}
	
	public Path getPath(){
		return path;
	}
	
	public boolean isCoordinate(){
		return isCoordinate;
	}

	public boolean isPath() {
		return isPath;
	}

}
