package project2;
/**
 * This class represents the Location of each feature and implements the Comparable interface. Each Location objects stores the 
 * feature's state, county, latitude, longitude, and elevation.
 * 
 * @author Eugene Chang
 * @version 10/12/2021
 */
public class Location implements Comparable<Location>{
	
	private String state;
	private String county;
	
	private double latitude =0;
	private double longitude= 0; 
	private int elevation=0;
	

	/**
	 * Constructs a new Location object with specified state and county values
	 * @param state; should be a non-empty String
	 * @param county; should be a non-empty String
	 * @throws IllegalArgumentException if parameters are null or empty
	 */
	public Location(String state, String county) throws IllegalArgumentException{ 
		
		//validates the inputs
		if (state == null || county == null) {
			throw new IllegalArgumentException("Null Argument Error in Location Class");
		}
		else if (state.equals("") || county.equals("")) {
			throw new IllegalArgumentException("Empty String Argument Error in Location Class");
		}
	
		this.state = state;
		this.county = county;
	}
	
	/**
	 * Returns the state of this Location object
	 * @return state this Location object
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Returns the county of this Location object
	 * @return county of this Location object 
	 */
	public String getCounty() {
		return county;
	}
	
	/**
	 * Returns the latitude of this Location Object
	 * @return the latitude of this Location Object
	 */
	public double getLatitude() {
		return latitude;	
	}
	
	/**
	 * Returns the longitude of this Location Object
	 * @return the longitude of this Location Object
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Returns the elevation of this Location Object
	 * @return the elevation of this Location Object
	 */
	public int getElevation() {
		return elevation;
	}
	
	/**
	 * Validates and sets the latitude for this Location object.
	 * @param latitude decimal value
	 * @throws IllegalArgumentException if latitude is invalid
	 */
	public void setLatitude (double latitude) throws IllegalArgumentException{
		// a valid latitude is in the range of from -90 to +90 (inclusive)
		if (latitude > 90 || latitude < -90) {
			throw new IllegalArgumentException("Invalid value for latitude. Valid range is -90 to +90 (inclusive)");
		}
		
		this.latitude = latitude;
	
	}
	
	/**
	 * Validates and sets the longitude for this Location object.
	 * @param longitude decimal value
	 * @throws IllegalArgumentException if longitude is invalid
	 */
	public void setLongitude (double longitude) throws IllegalArgumentException{
		// a valid longitude is in the range of from -180 to +180 (inclusive)
		if (longitude > 180 || longitude < -180) {
			throw new IllegalArgumentException("Invalid value for longitude. Valid range is -180 to +180 (inclusive)");
		}
		
		this.longitude = longitude;
	}
	
	/**
	 * Sets the elevation for this Location Object.
	 * @param elevation integer value
	 */
	public void setElevation (int elevation) {
		
		this.elevation = elevation;
	}
	
	/**
	 * Compares this object with a Location object for order.
	 * @param o, the object to be compared
	 * @return a negative integer, zero, or a positive integer if the object is less than, equal to, or greater than specified object
	 */
	@Override
	public int compareTo(Location o) {
		
		if (!this.state.equalsIgnoreCase(o.getState())) {
			return this.state.compareToIgnoreCase(o.getState());
		}
		
		if (!this.county.equalsIgnoreCase(o.getCounty())) {
			return this.county.compareToIgnoreCase(o.getCounty());
		}
		
		if (!(this.latitude == o.getLatitude())) {
			return Double.compare(this.latitude, o.getLatitude());
		}
		
		if (!(this.longitude == o.getLongitude())) {
			return Double.compare(this.longitude, o.getLongitude());
		}
		
		if (!(this.elevation == o.getElevation())) {
			return Integer.compare(this.elevation, o.getElevation());
		}
		
		return 0;
	}
	
	/**
	 * Checks to see if some object obj is equal to this object.
	 * Two Location objects are equal if they represent the same state, county, latitude, longitude and elevation
	 * @return true if this object is the same as obj in argument and false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Location)) {
			return false;
		}
		Location l = (Location) obj;
	
		if (!state.equalsIgnoreCase(l.getState()) ) {
			return false;
		}
		if (!county.equalsIgnoreCase(l.getCounty()) ) {
			return false;
		}
		
		//creates temporary integer values for latitude and longitude
		int lat1, long1;
		int lat2, long2;
		
		//truncates the values for longitude and latitude 6 places after the decimal point
		lat1 = ((int) (latitude*1000000))/1000000;
		long1 = ((int) (longitude*1000000))/1000000;
		lat2 = ((int) (l.getLatitude()*1000000))/1000000;
		long2 = ((int) (l.getLongitude()*10000000))/1000000;
		
		if(!(lat1 == lat2)) {
			return false;
		}
		if (!(long1 == long2)) {
			return false;
		}
		if(!(elevation == l.getElevation())) {
			return false;
		}
		
	return true;
	
	}
	
	/**
	 * Returns the state, county, latitude, longitude and elevation in a readable fashion
	 * county, state
	 * latitude, longitude, elevation
	 * @returns the state, county, latitude, longitude and elevation in the format:
	 * 			county, state
	 *			 latitude, longitude, elevation
	 */
	@Override
	public String toString() {
		return county + ", " + state + "\n" + getLatitude() + ", " + getLongitude() + ", " + getElevation();
	}
}
