package com.excilys.formation.java.computerdb.validation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.dto.exception.DateTimeInvalidException;
import com.excilys.formation.java.computerdb.dto.exception.DiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.dto.exception.NameRequiredException;
import com.excilys.formation.java.computerdb.dto.validation.ComputerDtoValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test the class ComputerValidator.
 * 
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
public class ComputerValidatorTest {
  // @Test
  // public void testValidateComputerGood() throws DiscontinuedBeforeIntroducedException,
  // NameRequiredException, DateTimeInvalidException {
  // ComputerDto dto = new ComputerDto.ComputerDtoBuilder("test").introduced("1999-10-10")
  // .discontinued("2005-10-10").build();
  // ComputerDtoValidator.validate(dto);
  // }

  @Test
  public void testValidateComputerNameRequired()
      throws DiscontinuedBeforeIntroducedException, DateTimeInvalidException {
    ComputerDto dto = new ComputerDto.ComputerDtoBuilder("").introduced("1999-10-10")
        .discontinued("2005-10-10").build();

    try {
      ComputerDtoValidator.validate(dto);
      fail("An exception should be thrown");
    } catch (NameRequiredException e) {
      assertTrue(true);
    }

  }

  // @Test
  // public void testValidateComputerIntroducedDate()
  // throws DiscontinuedBeforeIntroducedException, NameRequiredException {
  // ComputerDto dto = new ComputerDto.ComputerDtoBuilder("test").introduced("1999-10-10cds")
  // .discontinued("2005-10-10").build();
  // try {
  // ComputerDtoValidator.validate(dto);
  // fail("An exception should be thrown");
  // } catch (DateTimeInvalidException e) {
  // assertTrue(true);
  // }
  // }
  //
  // @Test
  // public void testValidateComputerDiscontinuedDate()
  // throws DiscontinuedBeforeIntroducedException, NameRequiredException {
  // ComputerDto dto = new ComputerDto.ComputerDtoBuilder("test").introduced("1999-10-10")
  // .discontinued("2005-10-10 10:10").build();
  //
  // try {
  // ComputerDtoValidator.validate(dto);
  // fail("An exception should be thrown");
  // } catch (DateTimeInvalidException e) {
  // assertTrue(true);
  // }
  // }
  //
  // @Test
  // public void testValidateComputerDiscontinuedAfterIntroduced()
  // throws NameRequiredException, DateTimeInvalidException {
  // ComputerDto dto = new ComputerDto.ComputerDtoBuilder("test").introduced("1999-10-10")
  // .discontinued("1995-10-10").build();
  //
  // try {
  // ComputerDtoValidator.validate(dto);
  // fail("An exception should be thrown");
  // } catch (DiscontinuedBeforeIntroducedException e) {
  // assertTrue(true);
  // }
  // }
}
