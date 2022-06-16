package project2;
/**
 * The Feature Class implements the Comparable interface. Each feature objects stores the feature's name, class,
 * and location (via the Location class)
 * 
 * @author Eugene Chang
 * @version 10/12/2021
 *
 */


public class Feature implements Comparable<Feature> {
	
	private String featureName;
	private String featureClass;
	private Location featureLocation;
	
	/**
	 * Constructs a new Feature object with specified feature name, feature class and Location object
	 * @param featureName, name of the feature
	 * @param featureClass, class of the feature
	 * @param featureLocation, Location object representing the location of the feature
	 * @throws IllegalArgumentException if parameters are invalid
	 */
	public Feature (String featureName, String featureClass, Location featureLocation) throws IllegalArgumentException {
		
		//validates whether inputs are null or empty strings
		if (featureName == null || featureClass == null || featureLocation == null) {
			throw new IllegalArgumentException("Null Agrgument Error in Feature Class");
		}
		if (featureName.equals("") || featureClass.equals("")){
			throw new IllegalArgumentException("Empty String Agrgument Error in Feature Class");
		}
		
		this.featureName = featureName;
		this.featureClass = featureClass;
		this.featureLocation = featureLocation;
	}
	
	/**
	 * Returns the feature name.
	 * @return the feature name.
	 */
	public String getFeatureName() {
		return featureName;
	}
	
	/**
	 * Returns the feature class.
	 * @return the feature class.
	 */
	public String getFeatureClass() {
		return featureClass;
	}
	
	/**
	 * Returns the feature Location
	 * @return the Location object associated with the feature.
	 */
	public Location getFeatureLocation() {
		return featureLocation;
	}

	/**
	 * Compares this object with a Feature object for order.
	 * @param o, the object to be compared
	 * @return a negative integer, zero, or a positive integer if the object is less than, equal to, or greater than specified object
	 */
	@Override
	public int compareTo(Feature o) {
	
		if (!(this.featureName.equalsIgnoreCase(o.featureName))) {
			return this.featureName.compareToIgnoreCase(o.featureName);
		}
		if (this.featureLocation.compareTo(o.featureLocation) != 0)	{
			return this.featureLocation.compareTo(o.featureLocation);	
		}
		if (!(this.featureClass.equalsIgnoreCase(o.featureClass))) {
			return this.featureClass.compareToIgnoreCase(o.featureClass);
		}
	return 0;
	}
	
	/**
	 * Checks to see if this objects is equal to Object obj
	 * Two Feature objects are the same if they have identical names, locations and classes
	 * @returns true if this object and obj have the same name, locations and class; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Feature)) {
			return false;
		}
		Feature f = (Feature) obj;
		if (!featureName.equalsIgnoreCase(f.featureName)) {
			return false;
		}
		if(!featureLocation.equals(f.featureLocation)) {
			return false;
		}
		if(!featureClass.equalsIgnoreCase(f.featureClass)) {
			return false;
		}
	return true;
	}
	
	/**
	 * Returns a string of feature name, feature class, feature Location in a readable fashion
	 * @return a string in the format:
	 * 			featureName, featureClass
	 * 			county, state
	 * 			latitude, longitude, elevation
	 */
	@Override
	public String toString() {
		return  featureName + ", " + featureClass + "\n" + featureLocation.getCounty() + ", " + featureLocation.getState() +"\n"+
				featureLocation.getLatitude() + ", " + featureLocation.getLongitude()+ ", "+ featureLocation.getElevation();
	}
}
