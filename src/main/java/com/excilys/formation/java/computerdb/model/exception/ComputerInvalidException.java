/**
 * 
 */
package com.excilys.formation.java.computerdb.model.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class ComputerInvalidException extends RuntimeException {
	public ComputerInvalidException() { 
		super(); 
	}
	public ComputerInvalidException(String message) { 
		super(message); 
	}

}
