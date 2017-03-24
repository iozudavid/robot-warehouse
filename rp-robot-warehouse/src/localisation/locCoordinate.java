package localisation;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class locCoordinate {

	private Integer x, y, North, East, South, West;

	public locCoordinate(Integer x, Integer y, Integer North, Integer East,
			Integer South, Integer West) {
		this.x = x;
		this.y = y;
		this.North = North;
		this.East = East;
		this.South = South;
		this.West = West;
	}

	public String returnXY() {
		String s = x + " " + y;
		return s;
	}

	public boolean chckN(Integer n, Integer e, Integer s, Integer w) {
		Boolean o = false;
		if (North.equals(n) && East.equals(e) && South.equals(s)
				&& West.equals(w)) {
			o = true;
		}
		return o;
	}

	public boolean chckE(Integer n, Integer e, Integer s, Integer w) {
		Boolean o = false;
		if (North.equals(w) && East.equals(n) && South.equals(e)
				&& West.equals(s)) {
			o = true;
		}
		return o;
	}

	public boolean chckS(Integer n, Integer e, Integer s, Integer w) {
		Boolean o = false;
		if (North.equals(s) && East.equals(w) && South.equals(n)
				&& West.equals(e)) {
			o = true;
		}
		return o;
	}

	public boolean chckW(Integer n, Integer e, Integer s, Integer w) {
		Boolean o = false;
		if (North.equals(e) && East.equals(s) && South.equals(w)
				&& West.equals(n)) {
			o = true;
		}
		return o;
	}

}
