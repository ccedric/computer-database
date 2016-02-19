/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class DAOSqlException extends Exception {
	public DAOSqlException() { 
		super(); 
	}
	public DAOSqlException(String message) { 
		super(message); 
	}

}