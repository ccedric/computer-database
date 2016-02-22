package com.excilys.formation.java.computerdb.dao.exception;

/**
 * This exception can be thrown if the DAO detect that a computer is invalid
 * @author Cédric Cousseran
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
