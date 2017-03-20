package networking;

import warehouse.Coordinate;

public class Message {
	private Coordinate coordinate;
	private String msg;
	private boolean isMessage;
	
	public Message(Coordinate c){
		this.coordinate = c;
		this.isMessage = false;
	}
	
	public Message(String msg){
		this.msg = msg;
		this.isMessage = true;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public boolean isMessage(){
		return isMessage;
	}

}
