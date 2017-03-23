package networking;

import warehouse.Coordinate;
import warehouse.Path;

public class Message {
	//Butt
	//Class that just contains information about a received message, no
	//complex calculations just getters and setters
	private String sender;
	private String msg;
	private Coordinate coord;
	
	private boolean isCoordinate;
	private Path path;
	private boolean isPath;
	
	public Message(String sender,String msg){
		this.sender = sender;
		this.msg = msg;
		this.isCoordinate = false;
	}
	
	public Message(String sender,Coordinate c){
		this.sender = sender;
		this.coord = c;
		this.isCoordinate = true;
	}

	public Message(String sender,Path p){
		this.sender = sender;
		this.path = p;
		this.isPath = true;
	}

	public String getSender(){
		return sender;
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
