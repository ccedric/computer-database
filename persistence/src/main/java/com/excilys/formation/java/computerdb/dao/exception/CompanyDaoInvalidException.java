package com.excilys.formation.java.computerdb.dao.exception;

/**
 * This exception can be thrown if the DAO detects a company is invalid.
 * @author Cédric Cousseran
 *
 */
public class CompanyDaoInvalidException extends RuntimeException {
  
  private static final long serialVersionUID = -6327441827031367214L;

  public CompanyDaoInvalidException() {
    super();
  }

  public CompanyDaoInvalidException(String message) {
    super(message);
  }

  public CompanyDaoInvalidException(String message, Throwable cause) {
    super(message, cause);
  }

}
