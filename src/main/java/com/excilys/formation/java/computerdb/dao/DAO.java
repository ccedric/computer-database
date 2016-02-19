package com.excilys.formation.java.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerDAOInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.order.OrderSearch;


/**
 * Data Access Object for the operations on the database of a class
 * @author Cédric Cousseran
 *
 * @param <T> the class used in the DAO
 */
public interface DAO<T> {	   

	/**
	 * Create an object in the database
	 * @param obj the object to create in the database
	 * @return the id of the element created
	 * @throws TimestampDisontinuedBeforeIntroducedException 
	 * @throws SQLException 
	 */
	int create(T obj) throws DatabaseConnectionException,ComputerDAOInvalidException, DAOSqlException;

	/**
	 * Delete an object in the database
	 * @param obj the object to delete in the database
	 * @return true the object was successfully deleted, false otherwise
	 * @throws SQLException 
	 */
	 void delete(T obj) throws DatabaseConnectionException,ComputerNotFoundException, DAOSqlException,CompanyNotFoundException;

	/**
	 * Update an object in the database
	 * @param obj the object to update in the database
	 * @return true is the object was updated, false otherwise
	 * @throws TimestampDiscontinuedBeforeIntroducedException 
	 * @throws SQLException 
	 */
	void update(T obj) throws DatabaseConnectionException, ComputerNotFoundException, DAOSqlException;

	/**
	 * Find an object in the database with his id
	 * @param id the id of the object
	 * @return the object found, null if it was not found
	 * @throws SQLException 
	 */
	T find(int id) throws DatabaseConnectionException, DAOSqlException, ComputerNotFoundException, CompanyNotFoundException;

	/**
	 * Return the list of object available in the database
	 * @return the list of objects
	 * @throws SQLException 
	 */
	List<T> list() throws DatabaseConnectionException,DAOSqlException;
	
	/**
	 * Get a page of the list of all objects
	 * @return the list paged 
	 * @throws SQLException 
	 */
	List<T> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException, DAOSqlException;
	
	/**
	 * Return a list of objects who have a corresponding name
	 * @param name search criteria
	 * @return list of objects
	 * @throws DatabaseConnectionException
	 */
	List<T> findByName(String name) throws DatabaseConnectionException, DAOSqlException;
	
	/**
	 * Get the number of results of the search query on the name attribute
	 * Return the number of elements in the table
	 * @return
	 * @throws DatabaseConnectionException
	 */
	int selectCount(String name) throws DatabaseConnectionException, DAOSqlException;

	/**
	 * Fetch in the database a list of T who have a corresponding name, null if no match. The size of the list is limited by pageSize
	 * @param indexBegin
	 * @param pageSize
	 * @param name
	 * @return
	 * @throws DatabaseConnectionException
	 */
	List<T> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order) throws DatabaseConnectionException, DAOSqlException;
}
