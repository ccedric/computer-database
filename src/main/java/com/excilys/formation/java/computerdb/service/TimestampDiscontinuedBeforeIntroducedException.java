/**
 * 
 */
package com.excilys.formation.java.computerdb.service;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class TimestampDiscontinuedBeforeIntroducedException extends RuntimeException {
	public TimestampDiscontinuedBeforeIntroducedException() { 
		super(); 
	}
	public TimestampDiscontinuedBeforeIntroducedException(String message) { 
		super(message); 
	}
}
