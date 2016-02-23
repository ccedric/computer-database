package com.excilys.formation.java.computerdb.dao.exception;

/**
 * This exception can be thrown if a metod in a dao is not implemented.
 * 
 * @author Cédric Cousseran
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
