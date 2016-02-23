package com.excilys.formation.java.computerdb.dao.exception;

/**
 * This exception can be thrown if the DAO detect that a computer is invalid.
 * 
 * @author Cédric Cousseran
 */
@SuppressWarnings("serial")
public class ComputerDaoInvalidException extends RuntimeException {
  public ComputerDaoInvalidException() {
    super();
  }

  public ComputerDaoInvalidException(String message) {
    super(message);
  }

}
