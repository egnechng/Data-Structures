package project5;

/**
 * The Hiker class represents the hiker traveling down the mountain. 
 * Keeps track of how many supplies the hiker has.
 * @author Eugene CHang
 * @version 12/10/21
 */
public class Hiker {
	
	//supplies
	private int food;
	private int axe;
	private int raft;

	/**
	 * Constructor for the Hiker Class
	 * Constructs hiker object with no supplies.
	 */
	public Hiker() {
		this.food = 0;
		this.axe = 0;
		this.raft = 0;
	}
	
	/**
	 * Constructor for the Hiker Class
	 * Constructs hiker object with defined number of supplies
	 * @param food - number of food rations hiker holds
	 * @param axe - number of axes hiker holds
	 * @param raft - number of ranks hiker holds
	 */
	public Hiker(int food, int axe, int raft) {
		this.food = food;
		this.axe = axe;
		this.raft = raft;
	}
	
	/**
	 * Getter method to return the hiker's food supply
	 * @return the number of food rations hiker holds
	 */
	public int foodCount() {
		return food;
	}
	
	/**
	 * Updates the hiker's food supply after collecting food
	 * @param n - the number of food rations added to the hiker's supply
	 */
	public void collectFood(int n) {
		this.food += n;
	}
	
	/**
	 * Updates food supply after consuming food
	 * @param n - the number of food rations the hiker consumes
	 */
	public void eat(int n) {
		this.food -= n;
	}
	
	/**
	 * Returns the hiker's axe count
	 * @return - the number of axes the hiker holds
	 */
	public int axeCount() {
		return axe;
	}
	
	/**
	 * Updates the axe count after collecting axe(s)
	 * @param n - the number of axes added to hiker's supply
	 */
	public void collectAxe(int n) {
		this.axe += n;
	}
	
	/**
	 * Updates axe count after using axe
	 * @param n - the number of axes used
	 */
	public void useAxe(int n) {
		this.axe -= n;
	}
	
	/**
	 * Returns the hiker's raft count
	 * @return - the number of rafts the hiker holds
	 */
	public int raftCount() {
		return raft;
	}
	
	/**
	 * Updates the raft count after collecting raft(s)
	 * @param n - the number of rafts added to hiker's supply
	 */
	public void collectRaft(int n) {
		this.raft += n;
	}
	
	/**
	 * Updates raft count after using raft
	 * @param n - the number of rafts used
	 */
	public void useRaft(int n) {
		this.raft -= n;
	}
	
}
