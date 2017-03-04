package networking;

import warehouse.Coordinate;

public class Message {
	private String sender;
	private String msg;
	private Coordinate coord;
	private boolean isCoordinate;
	
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

	public String getSender() {
		return sender;
	}

	public String getMsg() {
		return msg;
	}

	public Coordinate getCoord() {
		return coord;
	}
	
	public boolean isCoordinate(){
		return isCoordinate;
	}
}
