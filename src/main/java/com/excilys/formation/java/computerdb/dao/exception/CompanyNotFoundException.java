/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.exception;

/**
 * @author Cédric Cousseran
 * This exception can be thrown if a Company was not found in a DAO
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
