package com.excilys.formation.java.computerdb.service.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.ComputerService;
import com.excilys.formation.java.computerdb.service.exception.ComputerServiceInvalidException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test the class ComputerServiceImpl.
 * @author Cédric Cousseran
 *
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-service-context.xml" })
@Rollback(true)
public class ComputerServiceImplTest {
  @Autowired
  ComputerService service;
  
  @Test
  public void testCreateComputerInvalid() {
    Computer computer = new Computer.ComputerBuilder("").build();
    try {
      service.create(computer);
      fail("An exception should be thrown.");
    } catch (ComputerServiceInvalidException e) {
      assertTrue(true);
    }
  }
}
