/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class DAOSqlException extends RuntimeException {
	public DAOSqlException() { 
		super(); 
	}
	public DAOSqlException(String message) { 
		super(message); 
	}

}
