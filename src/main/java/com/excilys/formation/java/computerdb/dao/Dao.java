package com.excilys.formation.java.computerdb.dao;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.order.OrderSearch;

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
  T find(int id) throws DatabaseConnectionException, DaoSqlException, ComputerNotFoundException,
      CompanyNotFoundException;

  /**
   * Return the list of object available in the database.
   * 
   * @return the list of objects
   */
  List<T> list() throws DatabaseConnectionException, DaoSqlException;

  /**
   * Get a page of the list of all objects.
   * 
   * @return the list paged
   */
  List<T> listPage(int indexBegin, int pageSize)
      throws DatabaseConnectionException, DaoSqlException;

  /**
   * Return a list of objects who have a corresponding name.
   * 
   * @param name
   *          search criteria
   * @return list of objects
   */
  List<T> findByName(String name) throws DatabaseConnectionException, DaoSqlException;

  /**
   * Get the number of results of the search query on the name attribute Return the number of
   * elements in the table.
   * 
   * @return the count
   */
  int selectCount(String name) throws DatabaseConnectionException, DaoSqlException;

  /**
   * Fetch in the database a list of T who have a corresponding name, null if no match. The size of
   * the list is limited by pageSize
   * 
   * @param indexBegin the beginning index of the table
   * @param pageSize the page size
   * @param name the name of the search
   * @return the page as a list
   */
  List<T> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order)
      throws DatabaseConnectionException, DaoSqlException;
}
