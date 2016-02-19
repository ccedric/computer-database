/**
 * 
 */
package com.excilys.formation.java.computerdb.dto.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class DateTimeInvalidException extends RuntimeException {
	public DateTimeInvalidException() { 
		super(); 
	}
	public DateTimeInvalidException(String message) { 
		super(message); 
	}

}
