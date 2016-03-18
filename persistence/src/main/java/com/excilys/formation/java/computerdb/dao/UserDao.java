package com.excilys.formation.java.computerdb.dao;

import com.excilys.formation.java.computerdb.model.User;

/**
 * Interface for the Dao User.
 * @author Cédric Cousseran
 *
 */
public interface UserDao extends Dao<User> {

  /**
   * Find a User by his username.
   */
  User find(String username);
  
  
}
