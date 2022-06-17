package project5;
import java.util.*;

/**
 * Represents the BSTMountain that the hiker must traverse
 * The mountain is an implementation of an AVL Tree
 * 
 * Each node of the Mountain contains RestStop data
 * 
 * @author Eugene Chang + credit goes to lecture notes, 
 * recitations and Ed workspace for all BST and AVL implementation
 * 
 * @version 12/10/21
 */
public class BSTMountain{
	
	private Node root;
	private boolean added;
	
	public BSTMountain(){
		root = null;
	}
	
	/**
	 * Helper Method used to find the larger of two integers
	 * @param a - first integer
	 * @param b - second integer
	 * @return - the largest integer
	 */
	private static int findMax(int a, int b) {
		if (a > b) {
			return a;
		}
		else {
			return b;
		}
	}
	
	/**
	 * Used to get the depth of a node
	 * @param node - a node
	 * @param data - the rest stop data stored in node
	 * @param level - the level at the current node of traversal
	 * @return - depth of the node
	 */
	private static int getDepth(Node node, RestStop data, int level) {
		if (node == null) {
			return 0;
		}
		if (node.data == data) {
			return level;
		}
		int x = getDepth(node.left, data, level + 1);
		if (x != 0) {
			return x;
		}
		x = getDepth(node.right, data, level + 1);
		return x;
	}

	/**
	 * Used to get the height of a node
	 * @param n - a node
	 * @return - the height of the node
	 */
	private int getHeight(Node n) {
		if (n == null) {
			return -1;
		}
		return n.height;
	}
	
	/**
	 * Used to update the height of a node
	 * @param n - a node
	 */
	private void updateHeight(Node n) {
		n.height = findMax(getHeight(n.left), getHeight(n.right)) + 1;
	}
	
	/**
	 * Used to find the balance factor for a node for AVL balancing
	 * @param n - a node
	 * @return - the balance factor of the node
	 */
	private int balanceFactor(Node n) {
		if (n == null) return 0;
		
		return getHeight(n.right) - getHeight(n.left);
	}
	
	/**
	 * Right rotation method for AVL balancing
	 * @param n - node for rotation
	 * @return - new node reference after rotation
	 */
	private Node rotateRight(Node n) {
		
		Node x = n.left;
		Node y = x.right;
		
		x.right = n;
		n.left = y;
		
		updateHeight(n);
		updateHeight(x);
		
		return x;
		
	}
	
	/**
	 * Left rotation method for AVL balancing
	 * @param n - node for rotation
	 * @return - new node reference after rotation
	 */
	private Node rotateLeft(Node n) {
		Node x = n.right;
		Node y = x.left;
		
		x.left = n;
		n.right = y;
		
		updateHeight(n);
		updateHeight(x);
		
		return x;
		
	}
	
	/**
	 * Rebalances tree in accordance to AVL specifications
	 * @param n - the node to be rebalanced
	 * @return - new reference to the rebalanced node
	 */
	private Node rebalance(Node n) {
		updateHeight(n);
		
		int balFactor = balanceFactor(n);
		
		if (balFactor > 1) {
			//LL
			if (getHeight(n.right.right) > getHeight(n.right.left)) {
				n = rotateLeft(n);
			}
			//RL
			else {
				n.right = rotateRight(n.right);
				n = rotateLeft(n);
			}
		}			
		
		else if (balFactor < -1) {
			//RR
			if (getHeight(n.left.left) > getHeight(n.left.right)) {
				n = rotateRight(n);
			}
			//LR
			else {
				n.left = rotateLeft(n.left);
				n = rotateRight(n);
			}
		}
		return n;
	}
	
	/**
	 * Adds specified element to this tree if not already present
	 * If tree contains element already, call leaves
	 * tree unchanged and returns false
	 * @param data - RestStop element to be added to the tree
	 * @return true if tree does not already contain element
	 * @throws NullPointerException if element is null
	 */
	public boolean add(RestStop data) {
		added = false;
		if (data == null) 
			throw new NullPointerException("Null value for rest stop");
		root = add(root, data);
		return added;
	}
	
	/**
	 * Recursive implementation of add function with AVL balancing
	 * 
	 * @param node - node at which recursive call is called
	 * @param data - element to be added to the tree
	 * @return reference to subtree in which new value is added
	 */
	private Node add(Node node, RestStop data) {
		if (node == null) { 
			 added = true;
			 return new Node(data);
		}
		
		int comp = data.compareTo(node.data);
		
		if (comp < 0) {
			node.left = add(node.left, data);
		}
		else if (comp > 0) {
			node.right = add(node.right, data);
		}
		else {
			return node;
		}
	return rebalance(node);
	}
	
	/**
	 * Checks if a node is at the bottom of this mountain
	 * @param n - node to be checked
	 * @return true if the node is a leaf at the bottom of mountain
	 */
	private boolean isBottom(Node n) {
		if (getHeight(this.root) == getDepth(this.root, n.data, 0) 
				&& n.right == null && n.left == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if Hiker is stuck
	 * ie. hiker is unable to penetrate obstacles or have enough food
	 * @param h - hiker on this mountain
	 * @param n - node to be checked
	 * @return - true if hiker is unable to progress past this node
	 */
	private static boolean stuck(Hiker h, Node n) {
		
		RestStop r = n.data;
	
		//checks if not enough supplies to break obstacles
		if (r.getTree() > h.axeCount() || r.getRiver() > h.raftCount()) {
			return true;
		}
		
		//check if enough food
		else if (n.right != null && n.left != null && h.foodCount() == 0) {
			return true;
		}
		
		return false;
		
	}

	/**
	 * Finds all possible paths down this mountain
	 */
	public void findPath(){

		Stack<String> path = new Stack<>();
		Hiker h = new Hiker();
		findPathRec(this, h, root, path);
		
	}
	
	/**
	 * Recursive function to find all paths down the mountain
	 * Paths that result in the hiker being stuck are not considered
	 * @param mountain - the BSTmountain function is called by
	 * @param h - the hiker on the mountain
	 * @param node - the node at which hiker starts
	 * @param path - the path hiker has taken, represented by RestStop labels
	 */
	private static void findPathRec(BSTMountain mountain, Hiker h, Node node, 
			Stack<String> path) {
		
		//checks if node is null: base case
		if (node == null) {
			return;
		}	
		RestStop r = node.data;
		
		//collect supplies
		h.collectAxe(r.getAxe());
		h.collectRaft(r.getRaft());
		h.collectFood(r.getFood());
		
		//check to see if node is impossible to traverse
		if (stuck(h,node)) {
			return;
		}	
		
		//using necessary supplies, breaking obstacles and eating food ration
		h.useRaft(r.getRiver());
		h.useAxe(r.getTree());
		h.eat(1);

		path.add(r.getLabel());
		
		//checks for bottom of mountain
		if (mountain.isBottom(node)) {

			for (int i = 0; i < path.size(); i++) {
				System.out.print(path.get(i) + " ");
			}
			System.out.println();
		}
		
		Hiker h1 = new Hiker(h.foodCount(), h.axeCount(), h.raftCount());
		
		//recursive calls
		findPathRec(mountain, h, node.left, path);
		findPathRec(mountain, h1, node.right, path);
		
		//backtracking: removing last visited path
		path.pop();
		
	}
	
	/**
	 * Class representing the Node of the AVL Mountain
	 * @author Eugene Chang
	 * @version 12/10/21
	 */
	private class Node{
		
		private RestStop data;
		private Node left;
		private Node right;
		private int height;
		
		/**
		 * Constructor for Node
		 * @param d - RestStop object stored in node
		 */
		private Node(RestStop d){
			data = d;
		}
		
	}

}



