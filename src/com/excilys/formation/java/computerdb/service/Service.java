/**
 * 
 */
package com.excilys.formation.java.computerdb.service;

import java.util.List;

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
	 */
	int create(T obj);

	/**
	 * Delete an object in the database
	 * @param obj the object to delete in the database
	 * @return true the object was successfully deleted, false otherwise
	 */
	 boolean delete(T obj);

	/**
	 * Update an object in the database
	 * @param obj the object to update in the database
	 * @return true is the object was updated, false otherwise
	 */
	boolean update(T obj);

	/**
	 * Find an object in the database with his id
	 * @param id the id of the object
	 * @return the object found, null if it was not found
	 */
	T find(int id);

	/**
	 * Call the list method in the dao
	 * @return the list of objects
	 */
	List<T> list();
}
