/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class ComputerNotFoundException extends RuntimeException {
	public ComputerNotFoundException() { 
		super(); 
	}
	public ComputerNotFoundException(String message) { 
		super(message); 
	}

}
