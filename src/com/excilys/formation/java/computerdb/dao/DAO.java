package com.excilys.formation.java.computerdb.dao;

import java.util.List;


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
	 * Return the list of object available in the database
	 * @return the list of objects
	 */
	List<T> list();
	
	/**
	 * Get a page of the list of all objects
	 * @return the list paged 
	 */
	List<T> listPage(int indexBegin, int pageSize);
}
