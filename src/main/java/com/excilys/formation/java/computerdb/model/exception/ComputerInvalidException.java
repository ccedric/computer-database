/**
 * 
 */
package com.excilys.formation.java.computerdb.model.exception;

/**
 * @author Cédric Cousseran
 * This exception can be thrown by a mapper if the Computer is invalid
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
