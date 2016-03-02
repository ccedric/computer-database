package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Test the class CompanyDao.
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
public class CompanyDaoTest {

  @Autowired (required = true)
  CompanyDao dao;

  
  @Test
  public void testFindUnknown() throws DatabaseConnectionException, DaoSqlException {
    try {
      dao.find(999999999);
      fail("An exception should be thrown");
    } catch (CompanyNotFoundException e) {
      assertTrue(true);
    }
  }

}
