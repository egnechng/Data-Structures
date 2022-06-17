package project4;
import java.util.ArrayList;

/**
 * This program finds all solutions to a number puzzle. 
 * The solution uses a recursive algorithm to find all the ways through the puzzle.
 * The puzzle uses an array of positive integers. 
 * The objective is to find a path from index zero to the last index in the array. 
 * At each step we need to know the distance to be traveled and the direction. 
 * Each entry in the array is a number indicating the distance to be traveled on this particular leg of the path. 
 * The player (the program) needs to decide the direction (if the move should be made to the right or to the left).
 * @author Eugene Chang
 *
 */
public class WayFinder {
	
	//keeps track of # of solutions
	public static int counter = 0;	
	
	/**
	 * Recursive algorithm to find all possible solutions to the puzzle provided in args.
	 * Solutions that contain an unnecessary short loop are not considered valid.
	 * @param puzzle - the string array which contains the number puzzle
	 */
	public static void solve(String[] puzzle, int position, ArrayList<Integer> moves, ArrayList<String[]> solution) {

		//checks if puzzle found solution
		if (checkEnd(puzzle, position)) {
			printSolutions(solution);
			counter++;
			return;
		}
		
		//checks if position out of bounds
		if (position < 0 || position > puzzle.length-1) {
			return;
		}
		
		//how many steps we move at the spot we are at
		int steps = Integer.parseInt( puzzle[position].trim() );
		
		//checks if we already visited this index
		if (moves.contains(position)) {
			return;
		}

		ArrayList<Integer> clone = new ArrayList<Integer>(moves);	
		clone.add(position);
		
		ArrayList<String[]> pathR = new ArrayList<String[]>(solution);
		ArrayList<String[]> pathL = new ArrayList<String[]>(solution);
		
		String[] tempR = puzzle.clone();
		String[] tempL = puzzle.clone();
		
		tempR[position] = ( String.format("%2s%c", puzzle[position],'R') );
		tempL[position] = ( String.format("%2s%c", puzzle[position],'L') );
		
		pathR.add(tempR);
		pathL.add(tempL);
		
		//recursive call
		solve(puzzle, position + steps, clone, pathR);
		solve(puzzle, position - steps, clone, pathL);
	
	}
	
	/**
	 * Helper method for recursive solve method
	 * Considers the case where 0 is the only argument and calls the recursive solve method
	 * @param puzzle - the string array which contains the number puzzle
	 */
	public static void solveRec(String[] puzzle) {
		
		//solves case where only 0 is argument
		if(puzzle.length == 1 && puzzle[0].trim().equals("0")) {
			System.out.println("[ 0 ]");
			System.out.println("There is 1 way through the puzzle.");
			System.exit(1);
		}
		solve(puzzle, 0, new ArrayList<Integer>(), new ArrayList<String[]>());
	}
	
	/**
	 * Checks to see if position of puzzle is at the end
	 * @param array - puzzle 
	 * @param position - current index in puzzle
	 * @return true if position is at the end of the puzzle
	 */
	public static boolean checkEnd(String[] array, int position) {
		
		if (position == array.length-1 && array[position].trim().equals("0")) {
			return true;	
		}
		return false;
	}
	
	/**
	 * Prints the formatted solution to the number puzzle stored in ArrayList
	 * Example: The format of a 6-element array should be:

		[DDS,SDDC,SDDS,SDDS,SDDS,SDDS]

		where D stands for a digit, S stands for a space character, C stands for a character (either L or R) indicating the direction.
	 * @param solution - the ArrayList which contains a solution to the number puzzle
	 */
	public static void printSolutions(ArrayList<String[]> solution) {
		
		//formatted print of solution
		for (int i = 0; i < solution.size(); i++) {
			System.out.print("[");
			for (int j = 0; j < solution.get(i).length-1; j++) {
				if (isNumber(solution.get(i)[j])) {
					System.out.print(String.format("%2s%c", solution.get(i)[j], ' '));
					System.out.print(", ");
				}
				else {
					System.out.print(solution.get(i)[j] + ", ");
				}
			}
			System.out.print(" 0 ]\n");
			}
		System.out.println();
	}
	
	/**
	 * Checks to see if a string value can be represented as an integer
	 * @param s - string to be tested if it represent an integer
	 * @returns true if string value is an integer
	 */
	public static boolean isNumber(String s) {
		if (s == null) {
			return false;
		}
		try {
			int num = Integer.parseInt(s);
		}
		catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	/**
	 * Validates command line argument for number puzzle:
	 * Last digit must be a 0
	 * Number values must be 0-99 inclusive
	 * Solves number puzzle provided in command line arguments and prints solutions
	 * @param args - List of numbers that represent the number puzzle
	 */
	public static void main(String[] args) {
		
		//checks if argument is provided		
		if (args.length == 0 || args == null) {
			System.err.println("ERROR: Please provide a list of numbers");
			System.exit(1);
		}
		
		//validates inputs, checks whether values are integers and from 0-99 (inclusive)
		for (int i = 0; i < args.length; i++) {
			
			String s = args[i].trim();
			
			if (!isNumber(s)) {
				throw new IllegalArgumentException("ERROR: Please provide a list of valid integers");
			}
			
			int x = Integer.parseInt(s);
			if (x < 0 || x > 99) {
				throw new IllegalArgumentException("ERROR: All values have to be non-negative integers in the range of 0 to 99 inclusive");
			}			
			
		}
		
		//checks if last value is 0
		if (!args[args.length-1].trim().equals("0")) {
			throw new IllegalArgumentException("ERROR: Last Value Must be 0");
		}
		
		String[] puzzle = new String[args.length];
		
		//copies values from args to new array
		for (int i = 0; i < puzzle.length; i++) {
				puzzle[i] = args[i];
		}
		
		//solve puzzle
		solveRec(puzzle);
		
		//print number of solutions
		if (counter == 0) {
			System.out.println("No way through this puzzle.");
		}
		else {
			System.out.println("There are " + counter + " ways through the puzzle.");
		}
		
	}
	
}