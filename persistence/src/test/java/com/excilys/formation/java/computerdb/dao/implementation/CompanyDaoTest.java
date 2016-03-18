package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.model.User;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * test for the DAO Company.
 * @author Cédric Cousseran
 *
 */
public class CompanyDaoTest {
  @Autowired
  CompanyDaoImpl dao;
  
  @Test
  public void testDeleteException() {
    try {
      dao.delete(null);
      fail("An exception should be thrown");
    } catch (NotImplementedException e) {
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

}
