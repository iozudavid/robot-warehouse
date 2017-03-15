package warehouse.jobInput;

import java.io.*;
import java.net.*;

import warehouse.Coordinate;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class Item {

	private Float w, v;
	private Coordinate xy;
	private String name;

	public Item(Float w, Float v, Coordinate xy, String name) {
		this.w = w;
		this.v = v;
		this.xy = xy;
		this.name = name;
	}
	

	public float rWeight() {
		return w;
	}

	public float rValue() {
		return v;
	}

	public Coordinate rCoordinate(){
		return xy;
	}
	public String rName(){
		return name;
	}
	
	@Override
	public String toString(){
		return name;
		
	}

}
