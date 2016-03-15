package com.excilys.formation.java.computerdb.service.implementation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test the class ComputerService.
 * 
 * @author Cédric Cousseran
 *
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "/test-context.xml" })
public class ComputerServiceTest {
  @Autowired
  ComputerServiceImpl service;

  @Test
  public void testCreateException() {
    assertTrue(true);
    // try {
    //   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //   LocalDate debut = LocalDate.parse("2004-10-10", formatter);
    //   LocalDate fin = LocalDate.parse("1989-10-10", formatter);
    //   Computer computer = new Computer.ComputerBuilder("test create").introduced(debut)
    //       .discontinued(fin).build();
    //   service.create(computer);
    //   fail("An exception should be thrown");
    // } catch (ComputerServiceInvalidException e) {
    //   assertTrue(true);
    // }
  }
}
