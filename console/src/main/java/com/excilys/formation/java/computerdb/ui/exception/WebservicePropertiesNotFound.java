package com.excilys.formation.java.computerdb.ui.exception;

/**
 * This exception can be thrown if the properties file cotnaining the webservice url is not found.
 * @author Cédric Cousseran
 *
 */
public class WebservicePropertiesNotFound extends RuntimeException {
  private static final long serialVersionUID = 4835058183083968691L;

  public WebservicePropertiesNotFound() {
    super();
  }

  public WebservicePropertiesNotFound(String message) {
    super(message);
  }

  public WebservicePropertiesNotFound(String message, Throwable cause) {
    super(message, cause);
  }

}
