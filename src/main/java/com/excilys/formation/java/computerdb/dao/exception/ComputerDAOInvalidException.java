/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.exception;

/**
 * @author Cédric Cousseran
 * This exception can be thrown if the DAO detect that a computer is invalid
 */
@SuppressWarnings("serial")
public class ComputerDAOInvalidException extends RuntimeException {
	public ComputerDAOInvalidException() { 
		super(); 
	}
	public ComputerDAOInvalidException(String message) { 
		super(message); 
	}

}
