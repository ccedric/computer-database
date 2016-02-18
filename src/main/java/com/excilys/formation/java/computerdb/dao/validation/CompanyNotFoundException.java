/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.validation;

/**
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
public class CompanyNotFoundException extends Exception {
	public CompanyNotFoundException() { 
		super(); 
	}
	public CompanyNotFoundException(String message) { 
		super(message); 
	}

}
