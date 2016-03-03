package com.excilys.formation.java.computerdb.service;

import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Computer;

import java.util.List;

/**
 * Interface for the service Computer.
 * @author Cédric Cousseran
 *
 */
public interface ComputerService extends Service<Computer> {

  /**
   * Get a page of the select * query.
   * 
   * @param indexBegin
   *          beginning index of the page
   * @param indexEnd
   *          ending index of the page
   * @return the page as a list
   */
  List<Computer> listPage(Page page) throws DatabaseConnectionException;

  /**
   * Return a list of objects who have a corresponding name, empty list if not found.
   * 
   * @param name the search
   * @return the list of results
   */
  List<Computer> findByName(String name) throws DatabaseConnectionException;

  /**
   * Return a list of objects who have a corresponding name, empty lsit if not found. The list has a
   * limited size by pagSize
   * 
   * @param indexBegin beginning index of the page
   * @param pageSize size of the page
   * @param name search
   * @return a list containing the results of a page of the research by name
   */
  List<Computer> listPageByName(Page page) throws DatabaseConnectionException;

  /**
   * Get the number of results there will be in the sql query.
   * 
   * @param name
   *          name in the search query
   * @return the number of results in the select query
   */
  int selectCount(String name);
}
