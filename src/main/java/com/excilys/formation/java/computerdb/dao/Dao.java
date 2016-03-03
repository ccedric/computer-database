package com.excilys.formation.java.computerdb.dao;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;

import java.sql.SQLException;
import java.util.List;


/**
 * Data Access Object for the operations on the database of a class.
 * 
 * @author Cédric Cousseran
 *
 * @param <T>
 *          the class used in the DAO
 */
public interface Dao<T> {

  /**
   * Create an object in the database.
   * 
   * @param obj
   *          the object to create in the database
   * @return the id of the element created
   * @throws SQLException Thrown if an sql error occur
   */
  int create(T obj)
      throws DatabaseConnectionException, ComputerDaoInvalidException, DaoSqlException;

  /**
   * Delete an object in the database.
   * 
   * @param obj
   *          the object to delete in the database
   * @return true the object was successfully deleted, false otherwise
   */
  void delete(T obj) throws DatabaseConnectionException, ComputerNotFoundException, DaoSqlException,
      CompanyNotFoundException;

  /**
   * Update an object in the database.
   * 
   * @param obj
   *          the object to update in the database
   * @return true is the object was updated, false otherwise
   */
  void update(T obj) throws DatabaseConnectionException, ComputerNotFoundException, DaoSqlException;

  /**
   * Find an object in the database with his id.
   * 
   * @param id
   *          the id of the object
   * @return the object found, null if it was not found
   */
  T find(long id) throws DatabaseConnectionException, DaoSqlException, ComputerNotFoundException,
      CompanyNotFoundException;

  /**
   * Return the list of object available in the database.
   * 
   * @return the list of objects
   */
  List<T> list() throws DatabaseConnectionException, DaoSqlException;

}
