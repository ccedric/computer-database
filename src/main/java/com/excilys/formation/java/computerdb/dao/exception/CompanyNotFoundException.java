/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.exception;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class CompanyNotFoundException extends RuntimeException {
	public CompanyNotFoundException() { 
		super(); 
	}
	public CompanyNotFoundException(String message) { 
		super(message); 
	}

}
