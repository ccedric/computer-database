package com.excilys.formation.java.computerdb.service.exception;

/**
 * This exception can be thrown if the discontinued date is before the introduced date.
 * 
 * @author Cédric Cousseran
 */
@SuppressWarnings("serial")
public class TimestampDiscontinuedBeforeIntroducedException extends RuntimeException {
  public TimestampDiscontinuedBeforeIntroducedException() {
    super();
  }

  public TimestampDiscontinuedBeforeIntroducedException(String message) {
    super(message);
  }
}
