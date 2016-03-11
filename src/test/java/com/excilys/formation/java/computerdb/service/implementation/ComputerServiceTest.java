package com.excilys.formation.java.computerdb.service.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.exception.ComputerServiceInvalidException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Test the class ComputerService.
 * 
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
public class ComputerServiceTest {
  @Autowired
  ComputerServiceImpl service;

  @Test
  public void testCreateException() {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate debut = LocalDate.parse("2004-10-10", formatter);
      LocalDate fin = LocalDate.parse("1989-10-10", formatter);
      Computer computer = new Computer.ComputerBuilder("test create").introduced(debut)
          .discontinued(fin).build();
      service.create(computer);
      fail("An exception should be thrown");
    } catch (ComputerServiceInvalidException e) {
      assertTrue(true);
    }
  }
}
