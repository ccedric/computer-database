package com.excilys.formation.java.computerdb.dto.validation;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.dto.exception.DateTimeInvalidException;
import com.excilys.formation.java.computerdb.dto.exception.DiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.dto.exception.NameRequiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Class used to validate a ComputerDTO.
 * 
 * @author Cédric Cousseran
 */
@Component
public class ComputerDtoValidator {
  @Autowired
  private static MessageSource messageSource;

  /**
   * Check if a Computer is valid or not, with his name, introduced and discontinued date.
   */
  public static void validate(ComputerDto dto) throws DiscontinuedBeforeIntroducedException,
      NameRequiredException, DateTimeInvalidException {
    validateRequired(dto.getName());
    validateDateTimeFormat(dto.getIntroduced());
    validateDateTimeFormat(dto.getDiscontinued());
    validateIntroducedExistIfDiscontinuedDo(dto.getIntroduced(), dto.getDiscontinued());
    validateDiscontinuedAfterIntroduced(dto.getIntroduced(), dto.getDiscontinued());
  }

  private static void validateIntroducedExistIfDiscontinuedDo(String introduced,
      String discontinued) throws DiscontinuedBeforeIntroducedException {
    if (introduced.equals("") && !discontinued.equals("")) {
      throw new DiscontinuedBeforeIntroducedException(
          "A discontinued date exists, but not an introduced date");
    }

  }

  private static void validateRequired(String input) throws NameRequiredException {
    if ((null == input) || (input.equals("") || (input.trim()).equals(""))) {
      throw new NameRequiredException("The name of the ComputerDTO is required");
    }
  }

  private static void validateDateTimeFormat(String input) throws DateTimeInvalidException {
    if (!input.equals("")) {
      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(messageSource.getMessage("app.formatDate", null, Locale.getDefault()));

      try {
        LocalDate.parse(input, formatter);
      } catch (Exception e) {
        throw new DateTimeInvalidException("The format of hte DateTime is invalid");
      }
    }
  }

  private static void validateDiscontinuedAfterIntroduced(String introduced, String discontinued)
      throws DiscontinuedBeforeIntroducedException {
    if (!introduced.equals("") && !discontinued.equals("")) {
      DateTimeFormatter formatter = DateTimeFormatter
          .ofPattern(messageSource.getMessage("app.formatDate", null, Locale.getDefault()));

      LocalDate introducedDate = LocalDate.parse(introduced, formatter);
      LocalDate discontinuedDate = LocalDate.parse(discontinued, formatter);
      if (introducedDate.isAfter(discontinuedDate)) {
        throw new DiscontinuedBeforeIntroducedException(
            "The discontinued date must be adter the introduced date");
      }
    }
  }

}
