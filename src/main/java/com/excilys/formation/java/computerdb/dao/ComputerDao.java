package com.excilys.formation.java.computerdb.dao;

import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.order.OrderSearch;


import java.util.List;

/**
 * Interface for the Dao Computer.
 * @author Cédric Cousseran
 *
 */
public interface ComputerDao extends Dao<Computer> {

  /**
   * Return a list of objects who have a corresponding name.
   * 
   * @param name
   *          search criteria
   * @return list of objects
   */
  List<Computer> findByName(String name) throws DatabaseConnectionException, DaoSqlException;

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
  List<Computer> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order)
      throws DatabaseConnectionException, DaoSqlException;
  
  /**
   * Delete all computers associated to the company.
   * 
   * @param obj
   *          the company
   */
  public void deleteByCompany(Company obj);

}
