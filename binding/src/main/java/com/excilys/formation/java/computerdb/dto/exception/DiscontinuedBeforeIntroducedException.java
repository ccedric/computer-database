package com.excilys.formation.java.computerdb.dto.exception;

/**
 * This exception can be thrown if a ComputerDTO has a discontinued date before his introduced date.
 * 
 * @author Cédric Cousseran
 */
public class DiscontinuedBeforeIntroducedException extends RuntimeException {
  private static final long serialVersionUID = -8912723862409003853L;

  public DiscontinuedBeforeIntroducedException() {
    super();
  }

  public DiscontinuedBeforeIntroducedException(String message) {
    super(message);
  }

}
