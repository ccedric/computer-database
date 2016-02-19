/**
 * 
 */
package com.excilys.formation.java.computerdb.dto.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class NameRequiredException extends RuntimeException {
	public NameRequiredException() { 
		super(); 
	}
	public NameRequiredException(String message) { 
		super(message); 
	}

}
