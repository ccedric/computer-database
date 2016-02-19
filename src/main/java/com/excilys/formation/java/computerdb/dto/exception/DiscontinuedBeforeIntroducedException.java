/**
 * 
 */
package com.excilys.formation.java.computerdb.dto.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class DiscontinuedBeforeIntroducedException extends RuntimeException {
	public DiscontinuedBeforeIntroducedException() { 
		super(); 
	}
	public DiscontinuedBeforeIntroducedException(String message) { 
		super(message); 
	}

}
