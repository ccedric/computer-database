package com.excilys.formation.java.computerdb;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.exception.ComputerInvalidException;
import com.excilys.formation.java.computerdb.model.validation.ComputerValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Test the computer validator.
 * 
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:core-context.xml" })
public class ComputerValidatorTest {

  @Test
  public void testComputerNull() {
    try {
      ComputerValidator.validate(null);
      fail("An exception should be thrown.");
    } catch (ComputerInvalidException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testComputerNameNull() {
    Computer computer = new Computer.ComputerBuilder("").build();
    try {
      ComputerValidator.validate(computer);
      fail("An exception should be thrown.");
    } catch (ComputerInvalidException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testDiscontinuedBeforeIntroduced() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate introduced = LocalDate.parse("1995-10-10", formatter);
    LocalDate discontinued = LocalDate.parse("1990-10-10", formatter);

    Computer computer = new Computer.ComputerBuilder("bonjour").introduced(introduced)
        .discontinued(discontinued).build();
    try {
      ComputerValidator.validate(computer);
      fail("An exception should be thrown.");
    } catch (ComputerInvalidException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testComputerValid() {
    Company company = new Company(11, "testCompany");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate introduced = LocalDate.parse("1995-10-10", formatter);
    LocalDate discontinued = LocalDate.parse("2000-10-10", formatter);

    Computer computer = new Computer.ComputerBuilder("valid computer").company(company)
        .introduced(introduced).discontinued(discontinued).build();
    
    ComputerValidator.validate(computer);
  }
}
