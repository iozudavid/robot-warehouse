package localisation;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

public class TreeNode {

	TreeNode Nr, Ea, So, We;
	boolean cn = false;
	boolean ce = false;
	boolean cs = false;
	boolean cw = false;
	List<TreeNode> children = new LinkedList<TreeNode>();
	locCoordinate root;

	public TreeNode(locCoordinate n) {
		root = n;
		this.children = new LinkedList<TreeNode>();
	}

	public void addChildN(TreeNode k) {
		Nr = k;
		cn = true;
	}

	public void addChildE(TreeNode k) {
		Ea = k;
		ce = true;
	}

	public void addChildS(TreeNode k) {
		So = k;
		cs = true;
	}

	public void addChildW(TreeNode k) {
		We = k;
		cw = true;
	}

	public boolean chckChildN() {
		return cn;
	}

	public boolean chckChildE() {
		return ce;
	}

	public boolean chckChildS() {
		return cs;
	}

	public boolean chckChildW() {
		return cw;
	}

	public TreeNode rChildN() {
		return Nr;
	}

	public TreeNode rChildE() {
		return Ea;
	}

	public TreeNode rChildS() {
		return So;
	}

	public TreeNode rChildW() {
		return We;
	}

	public void addChild(TreeNode k) {
		children.add(k);
	}

	public List<TreeNode> returnT() {
		return children;
	}

	public locCoordinate returnV() {
		return root;
	}

}
