package com.excilys.formation.java.computerdb.model.exception;

/**
 * This exception can be thrown by a mapper if the Computer is invalid.
 * 
 * @author Cédric Cousseran
 */
public class ComputerInvalidException extends RuntimeException {
  private static final long serialVersionUID = -8806683165398682225L;

  public ComputerInvalidException() {
    super();
  }

  public ComputerInvalidException(String message) {
    super(message);
  }

}
