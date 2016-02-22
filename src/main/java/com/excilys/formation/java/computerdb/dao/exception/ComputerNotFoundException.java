/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.exception;

/**
 * @author Cédric Cousseran
 * This exception can be thrown if a Computer was not found in the DAO
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
