/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.implementation;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class TimestampDiscontinuedBeforeIntroducedException extends Exception {
	public TimestampDiscontinuedBeforeIntroducedException() { 
		super(); 
	}
	public TimestampDiscontinuedBeforeIntroducedException(String message) { 
		super(message); 
	}
}
