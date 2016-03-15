package com.excilys.formation.java.computerdb.dao.exception;

/**
 * This exception can be thrown if a Company was not found in a DAO.
 * 
 * @author Cédric Cousseran
 */
@SuppressWarnings("serial")
public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException() {
    super();
  }

  public CompanyNotFoundException(String message) {
    super(message);
  }

  public CompanyNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}
