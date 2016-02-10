/**
 * 
 */
package com.excilys.formation.java.computerdb.db;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception {
	public DatabaseConnectionException() { 
		super(); 
	}
	public DatabaseConnectionException(String message) { 
		super(message); 
	}
}
