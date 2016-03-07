package com.excilys.formation.java.computerdb.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for a Date.
 * @author Cédric Cousseran
 *
 */
public class DateValidator implements ConstraintValidator<Date, String> {
  
  @Override
  public void initialize(Date arg0) {
    
  }


  @Override
  public boolean isValid(String date, ConstraintValidatorContext ctx) {
    if (date == null) {
      return true;
    }
    if (date.matches("^[1-2][0-9][0-9][0-9]-[0-2][0-9]-[0-3][0-9]$")) {
      return true;
    }
    return false;
  }

}
