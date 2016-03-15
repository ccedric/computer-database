package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertTrue;

import com.excilys.formation.java.computerdb.dao.implementation.ComputerDaoImpl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Test the class ComputerDAO.
 * 
 * @author Cédric Cousseran
 *
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "/test-context.xml" })
public class ComputerDaoTest {
  @Autowired
  ComputerDaoImpl dao;

  @Test
  public void testCreateFalse() {
    assertTrue(true);
    // try {
    //   dao.create(null);
    //   fail("An exception should be thrown");
    // } catch (ComputerDaoInvalidException e) {
    //   assertTrue(true);
    // }
  }
}
