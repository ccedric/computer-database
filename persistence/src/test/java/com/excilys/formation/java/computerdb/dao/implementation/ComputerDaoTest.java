package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDaoImpl;
import com.excilys.formation.java.computerdb.model.Computer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test the class ComputerDAO.
 * 
 * @author Cédric Cousseran
 *
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:persistence-context.xml" })
@Rollback(true)
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
  
  @Test
  public void testUpdateFalse() {
    try {
      dao.update(null);
      fail("An exception should be thrown");
    } catch (ComputerDaoInvalidException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testDeleteFalse() {
    try {
      dao.delete(null);
      fail("An exception should be thrown");
    } catch (ComputerDaoInvalidException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testFindFalse() {
    try {
      dao.find(999999999);
      fail("An exception should be thrown");
    }  catch (ComputerNotFoundException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testFind() {
    Computer computer = dao.find(110);
    assertEquals(110,computer.getId());
  }
  
  @Test
  public void testUpdate() {
    Computer computer = dao.find(110);
    computer.setName(computer.getName() + "t");
    dao.update(computer);
    assertEquals(dao.find(110).getName(),computer.getName());
  }
}
