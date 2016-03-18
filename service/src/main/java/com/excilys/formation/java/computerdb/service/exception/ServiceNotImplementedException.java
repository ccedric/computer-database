package com.excilys.formation.java.computerdb.service.exception;

/**
 * This exception can be thrown when a method of a service is not implemented.
 * @author Cédric Cousseran
 *
 */
public class ServiceNotImplementedException extends RuntimeException {

  private static final long serialVersionUID = 5059539568088939114L;

  public ServiceNotImplementedException() {
    super();
  }

  public ServiceNotImplementedException(String message) {
    super(message);
  }

  public ServiceNotImplementedException(String message, Throwable cause) {
    super(message, cause);
  }

}
