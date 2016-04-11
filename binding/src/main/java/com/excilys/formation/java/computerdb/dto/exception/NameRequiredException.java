package com.excilys.formation.java.computerdb.dto.exception;

/**
 * This exception can be thrown if a ComputerDTO doesn't have a name.
 * 
 * @author Cédric Cousseran
 */
public class NameRequiredException extends RuntimeException {
  private static final long serialVersionUID = 6837529176750355077L;

  public NameRequiredException() {
    super();
  }

  public NameRequiredException(String message) {
    super(message);
  }

}
