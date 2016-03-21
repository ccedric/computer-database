package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDaoImpl;
import com.excilys.formation.java.computerdb.model.Company;
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
@ContextConfiguration(locations = { "classpath:test-persistence-context.xml" })
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
  public void testCreateValid() {
    Computer computer = new Computer.ComputerBuilder("test junit").build();
    long id = dao.create(computer);
    assertEquals(id,dao.find(id).getId());
  }
  

  @Test
  public void testDeleteValid() {
    int before = dao.list().size();
    dao.deleteByCompany(new Company(13,"IBM"));
    int after = dao.list().size();
    assertTrue(before >= after);
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
  public void testUpdateFalse() {
    try {
      dao.update(null);
      fail("An exception should be thrown");
    } catch (ComputerDaoInvalidException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testUpdate() {
    Computer computer = dao.find(110);
    computer.setName(computer.getName() + "t");
    dao.update(computer);
    assertEquals(dao.find(110).getName(),computer.getName());
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
  public void testCount() {
    int before = dao.selectCount("");
    int searched = dao.selectCount("Apple");
    assertTrue(searched < before);
    Computer computer = new Computer.ComputerBuilder("test junit").build();
    dao.create(computer);
    int after = dao.selectCount("");
    assertEquals(before + 1, after );
  }
}
