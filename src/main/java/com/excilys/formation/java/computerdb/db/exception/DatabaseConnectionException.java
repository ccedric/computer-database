/**
 * 
 */
package com.excilys.formation.java.computerdb.db.exception;

/**
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
