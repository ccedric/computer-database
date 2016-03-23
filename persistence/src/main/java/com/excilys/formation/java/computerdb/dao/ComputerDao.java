package com.excilys.formation.java.computerdb.dao;

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
   * Get the number of results of the search query on the name attribute Return the number of
   * elements in the table.
   * 
   * @return the count
   */
  int selectCount(String name);

  /**
   * Fetch in the database a list of T who have a corresponding name, null if no match. The size of
   * the list is limited by pageSize
   * 
   * @param indexBegin the beginning index of the table
   * @param pageSize the page size
   * @param name the name of the search
   * @return the page as a list
   */
  List<Computer> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order);
  
  /**
   * Delete all computers associated to the company.
   * 
   * @param obj
   *          the company
   */
  void deleteByCompany(Company obj);

}
