package project5;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class is the program and contains the main method
 * Responsible for running the Mountain Hike simulation
 * 
 * The program has to calculate all possible paths down the mountain 
 * that lead all the way down that the hiker can take and survive.
 * 
 * The program output should consist of number of lines equal to the number of 
 * safe paths. Each safe path should consist of a space separated list of the 
 * rest-stop labels in order in which they are followed 
 * from top of the mountain to the bottom. 
 * 
 * The paths should be printed in order from left to right in 
 * the tree that represents the mountain.
 * @author Eugene Chang
 * @version 12/10/21
 */
public class MountainHike {
	
	/**
	 * Helper method: checks to see if a string represents 
	 * a supply: food, axe or raft
	 * 
	 * @param s - string value which represents a supply
	 * @return - true if the string is a supply
	 */
	private static boolean isSupply(String s) {
		if (s.equals("food") || s.equals("axe") || s.equals("raft")) {
			return true;
		}
		return false;
	}

	/**
	 * Main method
	 * Responsible for parsing and validating command line arguments
	 * Reading and parsing inpput file
	 * Producing error messages and handling exceptions
	 * Producing output
	 * 
	 * @param args - file 
	 */
	public static void main(String[] args) {
		
		//checks if command line argument is there
		if (args.length == 0 ) {
			System.err.println("The program expects file name as an argument.\n");
			System.exit(1);	
		}
			
		//verify command line argument is the name of an existing file
		File file = new File(args[0]); 
		if (!file.exists()){
			System.err.println("The file "+file.getAbsolutePath()+" does not exist.\n");
			System.exit(1);
		}
		if (!file.canRead()){	
			System.err.println("The file "+file.getAbsolutePath()+" cannot be read.\n");
			System.exit(1);
		}
			
		//check file for reading
		Scanner in = null;
		try {
			in = new Scanner (file);
		} 
		catch (FileNotFoundException e) {
			System.err.println("The file "+file.getAbsolutePath()+
				" cannot be read.\n");
			System.exit(1);
		}
		
		String line = null;
		String label;
		boolean supplyDone;
		
		BSTMountain AVL = new BSTMountain();
		ArrayList<String> pastLabels = new ArrayList<String>();
		
		//validating and reading file inputs
		while (in.hasNextLine()) {
			
			line = in.nextLine();
			String [] inputs = line.split(" ");
			
			label = inputs[0];
			
			//checks if label is used twice, ignores duplicated labels
			if (pastLabels.contains(label)) {
				continue;
			}
			
			//adds into used labels list
			pastLabels.add(label);
			
			ArrayList<String> supplies = new ArrayList<String>();
			ArrayList<String> obstacles = new ArrayList<String>();
			
			//checks if all the supplies have been read
			supplyDone = false;
			
			for (int i = 1; i < inputs.length; i++) {
				
				inputs[i] = inputs[i].trim();
				
				if (isSupply(inputs[i]) && supplyDone == false) {
					supplies.add(inputs[i]);
				}
				
				else if (inputs[i].equals("fallen") && inputs[i + 1].equals("tree")) {
						obstacles.add("tree");
						supplyDone = true;
						i++;			
				}
				else if (inputs[i].equals("river")) {
					obstacles.add("river");
					supplyDone = true;	
				}	
			}
			
			RestStop r = new RestStop(supplies, obstacles, label);
			
			//add rest stop to the AVL tree
			AVL.add(r);
		}
		
		//find paths
		AVL.findPath();
	}

}
