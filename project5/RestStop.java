package project5;
import java.util.ArrayList;

/**
 * The RestStop class represents each rest stop on the mountain.
 * 
 * Keeps track of the supplies and obstacles encountered 
 * by the hiker at each rest stop.
 * 
 * @author Eugene Chang
 * @version 12/10/21
 */
public class RestStop implements Comparable<RestStop>{
	
	private int tree = 0;
	private int river = 0;
	private int axe = 0;
	private int raft = 0;
	private int food = 0;
	
	String label;
	
	/**
	 * Constructor for the RestStop object
	 * @param supplies - ArrayList consisting of supplies at the rest stop
	 * @param obstacles - ArrayList consisting of obstacles at the rest stop
	 * @param label - String representing the label of the rest stop
	 */
	public RestStop(ArrayList<String> supplies, ArrayList<String> obstacles, 
			String label) {
		
		this.label = label;
		
		for (int i = 0; i < supplies.size(); i++) {
			if (supplies.get(i).equals("food")) {
				this.food++;
			}
			if (supplies.get(i).equals("raft")) {
				this.raft++;
			}
			if (supplies.get(i).equals("axe")) {
				this.axe++;
			}
		}
		
		for (int i = 0; i < obstacles.size(); i++) {
			if (obstacles.get(i).equals("tree")) {
				this.tree++;
			}
			if (obstacles.get(i).equals("river")) {
				this.river++;
			}
		}
	}
	
	/**
	 * Returns number of food rations at rest stop
	 * @return integer value for food
	 */
	public int getFood() {
		return this.food;
	}
	
	/**
	 * Returns number of rafts at rest stop
	 * @return integer value for raft
	 */
	public int getRaft() {
		return this.raft;
	}
	
	/**
	 * Returns number of rafts at rest stop
	 * @return integer value for raft
	 */
	public int getAxe() {
		return this.axe;
	}
	
	/**
	 * Returns number of trees at rest stop
	 * @return integer value for tree
	 */
	public int getTree() {
		return this.tree;
	}
	
	/**
	 * Returns number of rivers at rest stop
	 * @return integer value for river
	 */
	public int getRiver() {
		return this.river;
	}
	
	/**
	 * Returns the label of the rest stop
	 * @return string representation of label
	 */
	public String getLabel() {
		return label;
	}

	@Override
	/**
	 * Compares RestStop objects by natural ordering of labels
	 * @param o - RestStop object to be compared
	 * @return - a positive integer if this is greater than O
	 * 			 a negative integer if this is less than O
	 * 			 0 if the two objects have the same label
	 */
	public int compareTo(RestStop o) {
		return this.label.compareTo(o.label);
	}

}
