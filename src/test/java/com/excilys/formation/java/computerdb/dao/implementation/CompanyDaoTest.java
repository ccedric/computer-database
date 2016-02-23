package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;

import org.junit.Test;



/**
 * Test the class CompanyDao.
 * @author Cédric Cousseran
 *
 */
public class CompanyDaoTest {
  private CompanyDao dao = new CompanyDao();

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
