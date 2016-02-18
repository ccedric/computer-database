/**
 * 
 */
package com.excilys.formation.java.computerdb.dto.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class DateTimeInvalidException extends Exception{
	public DateTimeInvalidException() { 
		super(); 
	}
	public DateTimeInvalidException(String message) { 
		super(message); 
	}

}
