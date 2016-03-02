package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDao;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Computer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;



/**
 * Test the class ComputerDAO.
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
public class ComputerDaoTest {
  @Autowired
  ComputerDao dao;

  @Test
  public void testCreateFind() throws DatabaseConnectionException, ComputerDaoInvalidException,
      DaoSqlException, ComputerNotFoundException {
    Computer computer = new Computer.ComputerBuilder("test").build();
    int id = dao.create(computer);
    computer.setId(id);
    assertEquals(computer, dao.find(id));

    dao.delete(computer);
  }

  @Test
  public void testCreateFalse() throws DatabaseConnectionException, DaoSqlException {
    try {
      dao.create(null);
      fail("An exception should be thrown");
    } catch (ComputerDaoInvalidException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testCreateDelete() throws DatabaseConnectionException, ComputerDaoInvalidException,
      DaoSqlException, ComputerNotFoundException {
    Computer computer = new Computer.ComputerBuilder("test").build();
    int id = dao.create(computer);
    computer.setId(id);
    dao.delete(computer);
    try {
      dao.find(id);
      fail("An exception should be thrown");
    } catch (ComputerNotFoundException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testFindUnknown() throws DatabaseConnectionException, DaoSqlException {
    try {
      dao.find(999999999);
      fail("An exception should be thrown");
    } catch (ComputerNotFoundException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testDeleteNotInDb() throws DatabaseConnectionException, DaoSqlException {
    Computer computer = new Computer.ComputerBuilder("test").build();
    try {
      dao.delete(computer);
      fail("An exception should be thrown");
    } catch (ComputerNotFoundException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testUpdate() throws DatabaseConnectionException, ComputerNotFoundException,
      DaoSqlException, ComputerDaoInvalidException {
    Computer computer = new Computer.ComputerBuilder("test").build();
    int id = dao.create(computer);
    computer.setId(id);
    assertEquals(computer, dao.find(id));
    computer.setName("autre test");
    dao.update(computer);
    assertEquals(computer, dao.find(id));

    dao.delete(computer);
  }

  @Test
  public void testUpdateNotInDb() throws DatabaseConnectionException, DaoSqlException {
    Computer computer = new Computer.ComputerBuilder("test update false").build();
    try {
      dao.update(computer);
      fail("An exception should be thrown");
    } catch (ComputerNotFoundException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testList() throws DatabaseConnectionException, ComputerDaoInvalidException,
      DaoSqlException, ComputerNotFoundException {
    Computer computer = new Computer.ComputerBuilder("test list").build();
    computer.setId(dao.create(computer));
    List<Computer> computers = dao.list();
    boolean isInList = false;
    for (Computer computerInList : computers) {
      if (computerInList.equals(computer)) {
        isInList = true;
      }
    }
    assertTrue(isInList);

    dao.delete(computer);

    computers = dao.list();
    isInList = false;
    for (Computer computerInList : computers) {
      if (computerInList.equals(computer)) {
        isInList = true;
      }
    }
    assertFalse(isInList);

  }

  @Test
  public void testSelectCount() throws DatabaseConnectionException, DaoSqlException,
      ComputerDaoInvalidException, ComputerNotFoundException {
    Computer computer = new Computer.ComputerBuilder("test count").build();
    int count = dao.selectCount("test");

    computer.setId(dao.create(computer));
    int countAfter = dao.selectCount("test");
    assertEquals(count + 1, countAfter);

    dao.delete(computer);

    assertEquals(count, dao.selectCount("test"));
  }

  @Test
  public void testFindByName() throws DatabaseConnectionException, ComputerDaoInvalidException,
      DaoSqlException, ComputerNotFoundException {
    Computer computer = new Computer.ComputerBuilder("test find by name").build();
    int id = dao.create(computer);
    computer.setId(id);
    List<Computer> computers = dao.findByName("test find by name");
    boolean isInList = false;
    for (Computer computerInList : computers) {
      if (computerInList.equals(computer)) {
        isInList = true;
      }
    }
    assertTrue(isInList);

    dao.delete(computer);
    computers = dao.findByName("test find by name");
    isInList = false;
    for (Computer computerInList : computers) {
      if (computerInList.equals(computer)) {
        isInList = true;
      }
    }
    assertFalse(isInList);
  }

}
