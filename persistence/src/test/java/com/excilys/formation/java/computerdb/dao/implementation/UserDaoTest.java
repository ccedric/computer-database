package com.excilys.formation.java.computerdb.dao.implementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Test the User Dao.
 * @author Cédric Cousseran
 *
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-persistence-context.xml" })
@Rollback(true)
public class UserDaoTest {
  @Autowired
  UserDaoImpl dao;
  
  @Test
  public void testFindException() {
    try {
      dao.find(1);
      fail("An exception should be thrown");
    } catch (NotImplementedException e) {
      assertTrue(true);
    }
  }
  
  @Test
  public void testFind() {
    User user = dao.find("admin");
    assertEquals("admin",user.getUsername());
  }

}
