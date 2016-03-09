package com.excilys.formation.java.computerdb.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Validator for a Date.
 * 
 * @author Cédric Cousseran
 *
 */
public class DateValidator implements ConstraintValidator<Date, String> {
  @Autowired
  private MessageSource messageSource;

  @Override
  public void initialize(Date arg0) {

  }

  @Override
  public boolean isValid(String date, ConstraintValidatorContext ctx) {
    if ((date == null) || (date.equals(""))
        || (date.matches(messageSource.getMessage("app.regexDate", null, Locale.getDefault())))) {
      return true;
    }
    return false;
  }

}
