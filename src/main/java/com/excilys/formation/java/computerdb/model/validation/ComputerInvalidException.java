/**
 * 
 */
package com.excilys.formation.java.computerdb.model.validation;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class ComputerInvalidException extends Exception {
	public ComputerInvalidException() { 
		super(); 
	}
	public ComputerInvalidException(String message) { 
		super(message); 
	}

}
