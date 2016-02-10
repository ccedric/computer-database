/**
 * 
 */
package com.excilys.formation.java.computerdb.service;

import java.util.List;

import com.excilys.formation.java.computerdb.dao.implementation.TimestampDiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;

/**
 * Service layer between the DAO and the ui
 * @author Cédric Cousseran
 *
 */
public interface Service<T> {

	/**
	 * Create an object in the database
	 * @param obj the object to create in the database
	 * @return the id of the element created
	 * @throws TimestampDiscontinuedBeforeIntroducedException 
	 */
	int create(T obj) throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException;

	/**
	 * Delete an object in the database
	 * @param obj the object to delete in the database
	 * @return true the object was successfully deleted, false otherwise
	 */
	 boolean delete(T obj) throws DatabaseConnectionException;

	/**
	 * Update an object in the database
	 * @param obj the object to update in the database
	 * @return true is the object was updated, false otherwise
	 * @throws TimestampDiscontinuedBeforeIntroducedException 
	 */
	boolean update(T obj) throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException;

	/**
	 * Find an object in the database with his id
	 * @param id the id of the object
	 * @return the object found, null if it was not found
	 */
	T find(int id) throws DatabaseConnectionException;

	/**
	 * Call the list method in the dao
	 * @return the list of objects
	 */
	List<T> list() throws DatabaseConnectionException;
	

	/**
	 * Get a page of the select * query
	 * @param indexBegin beginning index of the page
	 * @param indexEnd ending index of the page
	 * @return
	 */
	List<T> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException;
}
