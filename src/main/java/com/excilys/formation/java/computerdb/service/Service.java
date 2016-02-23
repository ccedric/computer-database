package com.excilys.formation.java.computerdb.service;

import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.service.exception.TimestampDiscontinuedBeforeIntroducedException;

import java.util.List;

/**
 * Service layer between the DAO and the ui.
 * 
 * @author Cédric Cousseran
 *
 */
public interface Service<T> {

  /**
   * Create an object in the database.
   * 
   * @param obj
   *          the object to create in the database
   * @return the id of the element created
   */
  int create(T obj)
      throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException;

  /**
   * Delete an object in the database.
   * 
   * @param obj
   *          the object to delete in the database
   * @return true the object was successfully deleted, false otherwise
   */
  boolean delete(T obj) throws DatabaseConnectionException;

  /**
   * Update an object in the database.
   * 
   * @param obj
   *          the object to update in the database
   * @return true is the object was updated, false otherwise
   */
  boolean update(T obj)
      throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException;

  /**
   * Find an object in the database with his id.
   * 
   * @param id
   *          the id of the object
   * @return the object found, null if it was not found
   */
  T find(int id) throws DatabaseConnectionException;

  /**
   * Call the list method in the dao.
   * 
   * @return the list of objects
   */
  List<T> list() throws DatabaseConnectionException;

  /**
   * Get a page of the select * query.
   * 
   * @param indexBegin
   *          beginning index of the page
   * @param indexEnd
   *          ending index of the page
   * @return the page as a list
   */
  List<T> listPage(Page page) throws DatabaseConnectionException;

  /**
   * Return a list of objects who have a corresponding name, empty list if not found.
   * 
   * @param name the search
   * @return the list of results
   */
  List<T> findByName(String name) throws DatabaseConnectionException;

  /**
   * Return a list of objects who have a corresponding name, empty lsit if not found. The list has a
   * limited size by pagSize
   * 
   * @param indexBegin beginning index of the page
   * @param pageSize size of the page
   * @param name search
   * @return a list containing the results of a page of the research by name
   */
  List<T> listPageByName(Page page) throws DatabaseConnectionException;

  /**
   * Get the number of results there will be in the sql query.
   * 
   * @param name
   *          name in the search query
   * @return the number of results in the select query
   */
  int selectCount(String name);
}
