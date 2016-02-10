package com.excilys.formation.java.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.java.computerdb.dao.implementation.TimestampDiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;


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
	int create(T obj) throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException;

	/**
	 * Delete an object in the database
	 * @param obj the object to delete in the database
	 * @return true the object was successfully deleted, false otherwise
	 * @throws SQLException 
	 */
	 boolean delete(T obj) throws DatabaseConnectionException;

	/**
	 * Update an object in the database
	 * @param obj the object to update in the database
	 * @return true is the object was updated, false otherwise
	 * @throws TimestampDiscontinuedBeforeIntroducedException 
	 * @throws SQLException 
	 */
	boolean update(T obj) throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException;

	/**
	 * Find an object in the database with his id
	 * @param id the id of the object
	 * @return the object found, null if it was not found
	 * @throws SQLException 
	 */
	T find(int id) throws DatabaseConnectionException;

	/**
	 * Return the list of object available in the database
	 * @return the list of objects
	 * @throws SQLException 
	 */
	List<T> list() throws DatabaseConnectionException;
	
	/**
	 * Get a page of the list of all objects
	 * @return the list paged 
	 * @throws SQLException 
	 */
	List<T> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException;
}
