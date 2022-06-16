package project2;

import java.util.ArrayList;

/**
 * The FeatureList class is used to store all Feature Objects and inherits from the ArrayList<Feature> class
 * Provides methods for searching through lists by keywords: name, class and state
 * 
 * @author Eugene Chang
 * @version 10/12/21
 */

@SuppressWarnings("serial")
public class FeatureList extends ArrayList<Feature> {
	
	/**
	 * This method adds an element to FeatureList by natural ordering of Feature elements
	 * Utilizes the CompareTo methods defined in respective classes
	 * @author Eugene Chang
	 * @param Feature e
	 */
	public void sortAdd(Feature e) {
		
		if (size() == 0) {
			add(e);
		}
		else {
		 for (int i = 0; i < size(); i++) {
			
			 if (e.compareTo(get(i)) > 0) { 
				 continue; 
			 }
			 
			 add(i,e);	
			 return;
		} 
		 add(e);
	}
}
	
	/**
	 * Default constructor for the FeatureList
	 * Constructs a FeatureList with no parameters.
	 */
	public FeatureList() {	
	}
	
	/**
	 * Searches through this FeatureList for an object which contains the given name.
	 * @param keyword; the name of the feature for which to search
	 * @return the reference to the FeatureList containing all of the elements which contain the keyword,
	 * or null if the name is not found
	 * @throws IllegalArgumentException if keyword parameter is invalid.
	 */
	public FeatureList getByName(String keyword) throws IllegalArgumentException{
		
		if (keyword == null || keyword.equals("")) {
			throw new IllegalArgumentException("Keyword Input is Illegal");
		}
		
		FeatureList byName = new FeatureList();
		
		for (Feature f : this) {
			String name = f.getFeatureName();
			if (name == null)
				continue;
			if (name.toLowerCase().contains(keyword.toLowerCase()) ) 
				byName.sortAdd(f);
		}
		if (this.size() == 0) {
			return null;
		} 
		if (byName.size() == 0) {
			return null;
		}
		
		return byName;
	}
	
	/**
	 * Searches through this FeatureList for an object which contains the given class.
	 * @param keyword; the class of the feature for which to search
	 * @return the reference to the FeatureList containing all of the elements which contain the keyword.
	 * or null if the class is not found
	 * @throws IllegalArgumentException if keyword parameter is invalid.
	 */
	public FeatureList getByClass(String keyword) throws IllegalArgumentException{
		
		if (keyword == null || keyword.equals("")) {
			throw new IllegalArgumentException("Keyword Input is Illegal");
		}
		
		FeatureList byClass = new FeatureList();

		for (Feature f : this) {
			String fclass = f.getFeatureClass();
			if (fclass == null)
				continue;
			if (fclass.toLowerCase().contains(keyword.toLowerCase()) ) 
				byClass.sortAdd(f);
		}
		if (this.size() == 0) {
			return null;
		}
		if (byClass.size() == 0) {
			return null;
		}
		
		return byClass;	
	}
	
	/**
	 * Searches through this FeatureList for an object which matches the given state.
	 * @param keyword; the state of the feature for which to search
	 * @return the reference to the FeatureList containing all of the elements which match the keyword, 
	 * or null if the matching state is not found
	 * @throws IllegalArgumentException if keyword parameter is invalid.
	 */
	public FeatureList getByState (String keyword) throws IllegalArgumentException{
		
		if (keyword == null || keyword.equals("")) {
			throw new IllegalArgumentException("Keyword Input is Illegal");
		}
		
		FeatureList byState = new FeatureList();
		
		for (Feature f : this) {
			String state = f.getFeatureLocation().getState();
			if (state == null)
				continue;
			if (state.equalsIgnoreCase(keyword))  
				byState.sortAdd(f);
		}
		if (this.size() == 0) {
			return null;
		}
		if (byState.size() == 0) {
			return null;
		}
		
		return byState;
	}
}
