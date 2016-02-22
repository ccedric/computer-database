/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class NotImplementedException extends RuntimeException {
	public NotImplementedException() { 
		super(); 
	}
	public NotImplementedException(String message) { 
		super(message); 
	}

}
