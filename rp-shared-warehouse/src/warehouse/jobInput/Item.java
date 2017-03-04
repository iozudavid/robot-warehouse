package warehouse.jobInput;

import java.io.*;
import java.net.*;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class Item {

	private Float w, v;
	private int x, y;

	Item(Float w, Float v, int x, int y) {
		this.w = w;
		this.v = v;
		this.x = x;
		this.y = y;
	}

	public float rWeight() {
		return w;
	}

	public float rValue() {
		return v;
	}

	public float rX() {
		return x;
	}

	public float rY() {
		return y;
	}

}
