package com.excilys.formation.java.computerdb.validation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.dto.exception.NameRequiredException;
import com.excilys.formation.java.computerdb.dto.validation.ComputerDtoValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Validate the class ComputerDtoValidator.
 * 
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-binding-context.xml" })
public class ComputerDtoValidatorTest {
  @Test
  public void testNameRequired() {
    ComputerDto computer = new ComputerDto.ComputerDtoBuilder("").build();
    try {
      ComputerDtoValidator.validate(computer);
      fail("An exception should be thrown");
    } catch (NameRequiredException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testValid() {
    ComputerDto computer = new ComputerDto.ComputerDtoBuilder("test dto").introduced("")
        .discontinued("").build();
    ComputerDtoValidator.validate(computer);
  }
}
