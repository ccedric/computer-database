package com.excilys.formation.java.computerdb.validator;

import java.lang.annotation.Documented;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

/**
 * Annotation for describing a date.
 * 
 * @author Cédric Cousseran
 *
 */
@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {

  /**
   * Default message.
   */
  String message() default "{Date invalid}";
}
