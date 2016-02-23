package com.excilys.formation.java.computerdb.dao.exception;

/**
 * This exception can be thrown if a Computer was not found in the DAO.
 * 
 * @author Cédric Cousseran
 */
@SuppressWarnings("serial")
public class ComputerNotFoundException extends RuntimeException {
  public ComputerNotFoundException() {
    super();
  }

  public ComputerNotFoundException(String message) {
    super(message);
  }

}
