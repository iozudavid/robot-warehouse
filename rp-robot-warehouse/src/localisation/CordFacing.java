package localisation;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

public class CordFacing {

	TreeNode itself;
	Integer orientation = 0;
	CordFacing Nr, Ea, So, We;
	boolean cn = false;
	boolean ce = false;
	boolean cs = false;
	boolean cw = false;
	List<CordFacing> children = new LinkedList<CordFacing>();
	locCoordinate root;

	public CordFacing(TreeNode t, Integer orientation) {
		itself = t;
		this.orientation = orientation;
	}

	public void rotateO() {
		if (orientation.equals(3)) {
			orientation = 0;
		} else {
			orientation = orientation + 1;
		}
	}

	public Integer returnO() {
		return orientation;
	}

	public TreeNode returnT() {
		return itself;
	}

	public boolean chckOrientationExp() {
		boolean chck = false;
		if (orientation.equals(0)) {
			chck = itself.chckChildN();
		} else if (orientation.equals(1)) {
			chck = itself.chckChildE();
		} else if (orientation.equals(2)) {
			chck = itself.chckChildS();
		} else {
			chck = itself.chckChildW();
		}
		return chck;
	}

	public CordFacing returnExpandedOrientation() {

		if (orientation.equals(0)) {
			return new CordFacing(itself.rChildN(), orientation);
		} else if (orientation.equals(1)) {
			return new CordFacing(itself.rChildE(), orientation);
		} else if (orientation.equals(2)) {
			return new CordFacing(itself.rChildS(), orientation);
		} else {
			return new CordFacing(itself.rChildW(), orientation);
		}

	}

}
