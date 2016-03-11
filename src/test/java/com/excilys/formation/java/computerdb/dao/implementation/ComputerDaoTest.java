package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDaoImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Test the class ComputerDAO.
 * 
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
public class ComputerDaoTest {
  @Autowired
  ComputerDaoImpl dao;

  @Test
  public void testCreateFalse() {
    try {
      dao.create(null);
      fail("An exception should be thrown");
    } catch (ComputerDaoInvalidException e) {
      assertTrue(true);
    }
  }
}
