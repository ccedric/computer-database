package com.excilys.formation.java.computerdb.dto.exception;

/**
 * This exception can be thrown if the introduced or discontinued date is invalid.
 * 
 * @author Cédric Cousseran
 */
public class DateTimeInvalidException extends RuntimeException {
  private static final long serialVersionUID = -7326106829487534777L;

  public DateTimeInvalidException() {
    super();
  }

  public DateTimeInvalidException(String message) {
    super(message);
  }

}
