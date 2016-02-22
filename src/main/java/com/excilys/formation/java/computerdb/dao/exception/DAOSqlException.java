package com.excilys.formation.java.computerdb.dao.exception;

/**
 * This exception can be thrown if a sql exception was catch in a DAO
 * @author Cédric Cousseran
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
