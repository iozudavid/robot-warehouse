package warehouse.jobInput;

import java.io.*;
import java.net.*;

import warehouse.Coordinate;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class Item {

	private Float w, v;
	private Coordinate xy;

	Item(Float w, Float v, Coordinate xy) {
		this.w = w;
		this.v = v;
		this.xy = xy;
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

}
