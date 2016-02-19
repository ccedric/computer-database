/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.implementation;

import org.junit.Test;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;

import static org.junit.Assert.*;


/**
 * @author Cédric Cousseran
 *
 */
public class CompanyDaoTest {
	private CompanyDAO dao = new CompanyDAO();
	
	@Test
	public void testFindUnknown() throws DatabaseConnectionException, DAOSqlException{
		try {
			dao.find(999999999);
			fail("An exception should be thrown");
		} catch (CompanyNotFoundException e) {
			assertTrue(true);
		}
	}

}
