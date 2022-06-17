package project3;

/**
 * The Number class is used to represent positive integers. 
 * It provides several operations to support number manipulation: addition, multiplication and comparisons.
 * There is no limit to the number of digits in a Number object.
 * The numbers are represented in the form of a doubly linked list. Each node in the list contains a single digit. 
 * For example,134 is represented as a three node list:

 *  head <--> | 4 | <--> | 3 | <--> | 1 | <--> null
 *  and 98765 is represented as a five node list
 *  head <--> | 5 | <--> | 6 | <--> | 7 | <--> | 8 | <--> | 9 | <--> null
 *  
 *  The least significant digit, or the ones digit, is stored in the very first node. 
 *  The tens digit is stored in the second node. And so on ....
 * 
 * @author Eugene Chang
 * @version 11/2/21
 */
public class Number extends Object implements Comparable<Number> {
	
	/**
	 * This defines the Node class for a doubly Linked List.
	 * Each node stores a positive integer value
	 * @author Eugene Chang
	 */
	private static class Node {
		 
		private int data;
	    private Node next;
	    private Node prev;
	    
	    Node(int data) {
	    	this.data = data;
	    }
	    
	}
	
	private Node head;
	private Node tail;
	private int length;
	
	/**
	 * Creates a Number object with value represented by the string argument number. 
	 * The number should consist of decimal digits only.
	 * @param number - a string representation of the number
	 * @throws IllegalArgumentException if number is null
	 * @throws NullPointerException if number contains any illegal characters
	 */
	public Number(String number) throws IllegalArgumentException,  NullPointerException {
		
		if (number == null) throw new NullPointerException("Number value is null");
		
		for (int i = 0; i < number.length(); i++) {
			if (!Character.isDigit(number.charAt(i))) {
				throw new IllegalArgumentException("String is not a number");
			}
		}
		
		head = null;
		tail = null;
		length = 0;
		
		for (int i = 0; i < number.length(); i++) {
			addFront(Integer.parseInt(number.substring(i, i+1)));
		}	
	}
	
	/**
	 * Adds to the front of a doubly linked list representing an integer
	 * @param data - integer value representing a digit of a number
	 */
	public void addFront(int data) {
		
		Node n = new Node(data);
		n.next = head;
		n.prev = null;
		
		if (head != null) {
			head.prev = n;
		}
		
		head = n;
		
		if (tail == null) {
			tail = n;
		}
		length++;	
		
	}
	
	/**
	 * Adds to the end of a doubly linked list representing an integer
	 * @param data - integer value representing a digit of a number
	 */
	public void addEnd(int data) {
		
		Node n = new Node(data);
		
		n.prev = tail;
		n.next = null;
		
		if (tail == null) {
			head = n;
		}
		else {
			tail.next = n;
		}
		tail = n;
		length++;	
	}
	
	/**
	 * Deletes the first node of the doubly linked list representing an integer, void method
	 */
	public void deleteFirst() {
		if (head != tail) {
			head = head.next;
			head.prev = null;
		}
	}
	
	/**
	 * Returns the number of digits in this object.
	 * @returns the number of digits in this object
	 */
	public int length() {
		return length;
	}
	
	/**
	 * Computes the sum of this object with other. Returns the result in a new object. 
	 * This object is not modified by call to add.
	 * @param other - the value to be added to this object
	 * @returns a Number object whose value is equal to the sum of this object and other
	 * @throws NullPointerException - if other is null
	 */
	public Number add(Number other) throws NullPointerException{
		
		if (other == null) throw new NullPointerException();
		
		
		int d = 0;
		
		//adds preceding 0's if the number lengths are not the same
		if (this.length > other.length) {
			d = this.length - other.length;
			for (int i = 0; i < d; i++) {
				other.addEnd(0);
			}
		}
		
		else if (this.length < other.length) {
			d = other.length - this.length;
			for (int i = 0; i < d; i++) {
				this.addEnd(0);
			}
		}
		
		Node c1 = this.head;
		Node c2 = other.head;
		
		Number s = new Number("0");
		
		int carry = 0;
		int sum = 0;
		
		while (c1 != null && c2 != null) {
			
			sum = c1.data + c2.data + carry;
			
			s.addEnd(sum%10);
			carry = sum/10;
			
			c1 = c1.next;
			c2 = c2.next;	
				
		}	
		if (carry > 0) {
			s.addEnd(1);
		}
		//removes "dummy" node initialized with 0 in the beginning
		s.deleteFirst();
		return s;
	}
	
	/**
	 * Computes the product of this object and other. Returns the result in a new object. 
	 * This object is not modified by call to multiply.
	 * @param other - the value to be multiplied by this object
	 * @return a Number object whose value is equal to the product of this object and Other
	 * @throws NullPointerException - if other is null
	 */
	public Number multiply(Number other) throws NullPointerException {
		
		//input validation
		if (other==null) throw new NullPointerException("Cannot multiply by null value");
		
		if (this.toString().equals("0") || other.toString().equals("0")){
			return new Number("0"); 
		}
		
		Node c = other.head;
		Number p = new Number("0");
		Number temp = new Number("0");
		
		int place = 0;
		
		while (c != null) {
			
			temp = this.multiplyByDigit(c.data);
			
			for (int i = 0; i < place; i++) {
				temp.addFront(0);
			}
				
			p = p.add(temp);
			place++;
			c = c.next;	
		}
		return p;
	}
	
	/**
	 * Computes the product of this object and a single digit digit. Returns the result in a new object. 
	 * This object is not modified by call to multiplyByDigit.
	 * @param digit - a single positive digit to be used for multiplication
	 * @return a Number object whose value is equal to the product of this object and digit
	 * @throws IllegalArgumentException - when digit is invalid (i.e., not a single digit or negative)
	 */
	public Number multiplyByDigit(int digit) throws IllegalArgumentException {
		
		//input validation
		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Digit is invalid. Must be a nonnegative, single digit.");
		}
		
		if (digit == 0) {
			return new Number("0");
		}
		
		Node c1 = this.head;
		Number p = new Number("0");
		
		int carry = 0;
		int prod = 0;
		
		while (c1 != null) {
			
			prod = (c1.data * digit) + carry;
			
			p.addEnd(prod%10);
			carry = prod/10;
			
			c1 = c1.next;
			
		}
		if (carry > 0) {
			p.addEnd(carry);
		}
		
		//removes "dummy" node initialized with 0 in the beginning
		p.deleteFirst();	
		return p;
	}
	
	/**
	 * 
	 * @param other - the object to be compared with this object
	 * @return a negative integer, zero, or a positive integer as this object is 
	 * less than, equal to, or greater than other
	 * @throws NullPointerException - if other is null
	 * @see Comparable
	 */
	public int compareTo(Number other) throws NullPointerException{
		
		//input validation
		if (other == null) {
			throw new NullPointerException("Cannot compare to null pointer");
		}
		
		if (this.length > other.length) {
			return 1;
		}
		else if (this.length < other.length) {
			return -1;
		}
		
		Node x = this.head;
		Node y = other.head;
		
		while (x != null && y != null) {
			if (x.data > y.data) {
				return 1;
			}
			else if (x.data < y.data) {
				return -1;
			}
			x = x.next;
			y = y.next;	
		}
		return 0;
	}
	
	/**
	 * Determines if this object is equal to obj. 
	 * Two Number objects are equal if all of their digits are the same and in the same order, 
	 * and if they have the same number of digits. 
	 * In other words, if the values represented by the two objects are the same.
	 * @param obj - the object to be compared to this object
	 * @returns true if two objects are equal, false otherwise\
	 * @see Object.equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Number)) {
			return false;
		}
		
		Number n = (Number) obj;
		
		if(this.length != n.length) {
			return false;
		}
		
		Node x = this.head;
		Node y = n.head;
		
		while (x != null && y != null) {
			if (x.data > y.data) {
				return true;
			}
			else if (x.data < y.data) {
				return false;
			}
			x = x.next;
			y = y.next;	
		}
		return true;
	}
	
	/**
	 * Returns the string representation of this object.
	 * @returns string representation of this object
	 */
	@Override
	public String toString() {
		Node current = this.tail;
		
		//removes leading zeroes
		while (current != null && current.data == 0) {
			current = current.prev;
		}
	
		StringBuilder s  = new StringBuilder();
		
		while (current != null) {
			s.append(current.data);
			current = current.prev;
		}
		
		//returns "0" if s is empty
		if (s.length() == 0) {
			return "0";
		}
		return s.toString();
	}
		
	
}
