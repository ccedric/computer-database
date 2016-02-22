package com.excilys.formation.java.computerdb.model.exception;

/**
 * This exception can be thrown by a mapper if the Computer is invalid
 * @author Cédric Cousseran
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
