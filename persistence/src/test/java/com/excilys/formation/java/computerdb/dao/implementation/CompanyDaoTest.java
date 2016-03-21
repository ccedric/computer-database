package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.CompanyDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.model.Company;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * test for the DAO Company.
 * @author Cédric Cousseran
 *
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-persistence-context.xml" })
@Rollback(true)
public class CompanyDaoTest {
  @Autowired
  CompanyDaoImpl dao;
  
  @Test
  public void testDeleteException() {
    try {
      dao.delete(null);
      fail("An exception should be thrown");
    } catch (CompanyDaoInvalidException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testFindUnknown() {
    try {
      dao.find(999999999);
      fail("An exception should be thrown");
    } catch (CompanyNotFoundException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testDeleteValid() {
    Company company = new Company(13,"IBM");
    dao.delete(company);
    try {
      dao.find(13);
      fail("An exception should be thrown");
    } catch (CompanyNotFoundException e) {
      assertTrue(true);
    }
  }
  
}
