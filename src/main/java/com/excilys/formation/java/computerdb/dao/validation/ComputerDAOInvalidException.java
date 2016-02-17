/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.validation;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class ComputerDAOInvalidException extends Exception {
	public ComputerDAOInvalidException() { 
		super(); 
	}
	public ComputerDAOInvalidException(String message) { 
		super(message); 
	}

}
