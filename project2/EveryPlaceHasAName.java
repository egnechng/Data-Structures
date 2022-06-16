package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The EveryPlaceHasAName class contains the main method.
 * This program is interactive
 * When the program is executed, the name of input file containing the list of all features 
 * is provided as the program's command line argument
 * It is responsible for opening and reading the data file.
 * The user is required to enter a name for the feature and is given the option to
 * provide a class and state keyword to narrow down searches 
 * This program is also responsible for performing some data validation and handling all errors that may occur
 * 
 * @author Eugene Chang
 * @version 10/12/2021
 */

public class EveryPlaceHasAName{
	
	/**
	 * The main() method of the program.
	 * @param args array of Strings provided in the command line when program is executed
	 * the first string should be name of input file containing the list of features
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
		
		//necessary data to extract from each line in file
		FeatureList list = new FeatureList();
		String line = null; 
		String featureID;
		String stateName, countyName = null;
		String fName, fClass = null;
		
		double lat=0; 
		double lon = 0;
		int elev = 0;
		
		Feature current = null;
		Location loc = null;
	
		//read the file and save the necessary data in a list of features (FeatureList)
		while(in.hasNextLine()) {
			
			line = in.nextLine();
			//splits each Line by the delimiter (|) and saves each string into an array
			String [] f = splitInputLine(line);
			
			if (line.startsWith("FEATURE_ID")) { 
				continue;
				}
			
			try {
				
				featureID = f[0];
				if (!("0123456789").contains(featureID.substring(0,1))) {
					continue;
				}
				fName = f[1];
				fClass = f[2];
				stateName = f[3];
				countyName = f[5];	
			}
			catch (NoSuchElementException | StringIndexOutOfBoundsException e) { 
				continue;
			}
			
			/**
			 * Checks to see if there are valid latitude, longitude, and elevation values. 
			 * @throws NumberFormatException or IndexOutOfBoundsException if these values are not provided or valid
			 * and a value of 0 is assigned to these values.
			 */
			
			try {
				lat = Double.parseDouble(f[9]);
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				lat =0; //sets default value to 0
			}
			try {
				lon = Double.parseDouble(f[10]);
			} catch(NumberFormatException | IndexOutOfBoundsException e) {
				lon =0; //sets default value to 0
			}
			try {
				elev = Integer.parseInt(f[16]);
			} catch(NumberFormatException | IndexOutOfBoundsException e) {
				elev = 0; //sets default value to 0
			}
			
			//saves the data in list of Feature objects
			try {
				 loc = new Location (stateName.trim(),countyName.trim());
				 loc.setLatitude(lat);
				 loc.setLongitude(lon);
				 loc.setElevation(elev);				 
				 current = new Feature(fName.trim(),fClass.trim(), loc);
				 list.add(current);
				
			} catch (IllegalArgumentException e) {
				//Ignores this exception, and keeps going
			}
		}
		
		//start of user interactive mode
		
		Scanner userInput = new Scanner(System.in);
		String input = "";
		String inputName= null;
		String id1 = null;
		FeatureList search = new FeatureList();
		
		while(!input.equalsIgnoreCase("quit")) {
			System.out.println("Search the dataset by using one of the following queries. \n"
					+ " To search for features by keyword in their name, enter \n"
					+ "\t name KEYWORD \n"
					+ " To limit the search to a particular class of features , enter \n"
					+ "\t name KEYWORD class FEATURE_CLASS \n"
					+ " To limit the search to a particular state, enter \n"
					+ "\t name KEYWORD state STATE \n"
					+ " Or combine both restrictions by entering \n"
					+ "\t name KEYWORD class CLASS state STATE \n"
					+ "   or \n"
					+ "\t name KEYWORD state STATE class CLASS \n"
					+ "   To terminate the program, enter \n"
					+ "\t quit \n");
			
			//gets value from user
			input = userInput.nextLine();
			
			//terminate program if user inputs 'quit'
			if (input.equalsIgnoreCase("quit")) {
				System.out.println("Program Terminated");
				break;
			}
			
			//splits user input into individual strings
			String [] inputList = splitLine(input);
		
			//validates user input
			if (checkInput(inputList) == false) {
				System.out.println("Invalid query, please try again. \n");
				continue;
			}
			
			//searches through list of Feature objects by name
			inputName = inputList[1];
			search = list.getByName(inputName);
			
			//If search finds nothing, prompts user to try again.
			if (search == null || search.size() == 0) {
				System.out.println("No Matches Found. Try Again. \n");
				continue;
			}
			
			//search case when user gives only two identifiers name and (state/class)
			if (inputList.length == 4) {
				id1 = inputList[2];
				
				if (id1.equals("class")) {
					search = search.getByClass(inputList[3]);
				}
				else if(id1.equals("state")) {
					search = search.getByState(inputList[3]);	
				}
			}
			
			//search case when user gives all possible identifiers name, class and state
			if (inputList.length == 6) {
				id1 = inputList[2];
				
				if (id1.equals("class")) {
					search = search.getByClass(inputList[3]);
					search = search.getByState(inputList[5]);
				}
				else if(id1.equals("state")) {
					search = search.getByState(inputList[3]);
					search = search.getByClass(inputList[5]);
				}
			}
			
			//If search finds nothing, prompts user to try again.
			if (search == null || search.size() == 0) {
				System.out.println("No Matches Found. Try Again. \n");
				continue;
			}
			
			//prints all the Feature objects in the list
			for (Feature f : search) {
				System.out.println(f.toString());
				System.out.println();
				System.out.println("-----------");
			}
			
		}
		userInput.close();
	}
	
	/**
	 * checks whether the user input follows the name KEYWORD, state/class KEYWORD, state/class KEYWORD query format
	 * @author Eugene Chang
	 * @param input, array of input line Strings split by spaces and trimmed
	 * @return boolean value indicating if the identifier words are match "class" or "state" and if they are in the correct location and if "name" is the first word in query
	 */
	public static boolean checkInput(String []	input) {
		int length = input.length;
		
		if (length > 6 || length <2) {
			return false;
		}
		
		if ( !(input[0].equals("name")) )  {
			return false;
		}
		
		if (length == 4) {
			if ( !(input[2]).equals("class") && !(input[2]).equals("state") ){
				return false;
			}
		}
		
		if (length == 6) {
			if ( (!(input[2]).equals("class") && !(input[4]).equals("state") ) && ( !(input[2]).equals("state") && !(input[4]).equals("class") ) ) {
				return false;
			}	
		}
		return true;
	}
	
	/**
	 * Splits the given line of a pipe-delimited file according to | characters.
	 * @author Joanna Klukowska
	 * @param textLine	a line of text to be parsed
	 * @return the array containing words (or empty strings) from between | characters
	 */
	public static String [] splitInputLine(String textLine){

		if (textLine == null ) return null;

		String [] entries = null;

		entries = textLine.split("\\|");

		return entries;
	}
	
	/**
	 * Splits the given line of space-delimited input
	 * @author Eugene Chang
	 * @param textLine, a line of text to be parsed
	 * @return array containing words between spaces (" ")
	 */
	public static String [] splitLine(String textLine){

		if (textLine == null ) return null;
		
		//regex escape sequence gets rid of multiple spaces
		textLine = textLine.trim().replaceAll("\\s+", " ");

		String [] entries = null;

		entries = textLine.split(" ");
	
		return entries;
	}

	
}
