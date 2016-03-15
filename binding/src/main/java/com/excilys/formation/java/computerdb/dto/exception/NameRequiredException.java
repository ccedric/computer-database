package com.excilys.formation.java.computerdb.dto.exception;

/**
 * This exception can be thrown if a ComputerDTO doesn't have a name.
 * 
 * @author Cédric Cousseran
 */
@SuppressWarnings("serial")
public class NameRequiredException extends RuntimeException {
  public NameRequiredException() {
    super();
  }

  public NameRequiredException(String message) {
    super(message);
  }

}
