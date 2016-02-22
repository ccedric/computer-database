package com.excilys.formation.java.computerdb.dto.exception;

/**
 * This exception can be thrown if a ComputerDTO has a discontinued date before his introduced date
 * @author Cédric Cousseran
 */
@SuppressWarnings("serial")
public class DiscontinuedBeforeIntroducedException extends RuntimeException {
	public DiscontinuedBeforeIntroducedException() { 
		super(); 
	}
	public DiscontinuedBeforeIntroducedException(String message) { 
		super(message); 
	}

}
