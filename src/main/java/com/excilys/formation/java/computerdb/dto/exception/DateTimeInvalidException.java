package com.excilys.formation.java.computerdb.dto.exception;

/**
 * This exception can be thrown if the introduced or discontinued date is invalid
 * @author Cédric Cousseran
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
