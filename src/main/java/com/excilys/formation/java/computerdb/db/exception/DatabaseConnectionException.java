package com.excilys.formation.java.computerdb.db.exception;

/**
 * This exception can be thrown if there is an error while connecting to the database
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class DatabaseConnectionException extends RuntimeException {
	public DatabaseConnectionException() { 
		super(); 
	}
	public DatabaseConnectionException(String message) { 
		super(message); 
	}
}
