package com.excilys.formation.java.computerdb.dao.exception;

/**
 * This exception can be thrown if the DAO detect that a computer is invalid.
 * 
 * @author Cédric Cousseran
 */
public class ComputerDaoInvalidException extends RuntimeException {
  private static final long serialVersionUID = -6184560629348111262L;

  public ComputerDaoInvalidException() {
    super();
  }

  public ComputerDaoInvalidException(String message) {
    super(message);
  }

  public ComputerDaoInvalidException(String message, Throwable cause) {
    super(message, cause);
  }
  
}
