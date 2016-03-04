package com.excilys.formation.java.computerdb.service.exception;

/**
 * Can be thrown by a Service when a computer is invalid.
 * 
 * @author Cédric Cousseran
 *
 */
public class ComputerServiceInvalidException extends RuntimeException {
  private static final long serialVersionUID = -4793495054247481976L;

  public ComputerServiceInvalidException() {
    super();
  }

  public ComputerServiceInvalidException(String message) {
    super(message);
  }

  public ComputerServiceInvalidException(String message, Throwable cause) {
    super(message, cause);
  }

}
