package com.excilys.formation.java.computerdb.validator;

import java.lang.annotation.Documented;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

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
  String message() default "Date invalid, date format: yyyy-mm-dd";

  /**
   * Default groups.
   */
  Class<?>[] groups() default {};

  /**
   * Default payload.
   */
  Class<? extends Payload>[] payload() default {};
}
