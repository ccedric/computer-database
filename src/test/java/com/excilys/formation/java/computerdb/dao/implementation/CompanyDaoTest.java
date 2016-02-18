/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.implementation;

import org.junit.Test;

import com.excilys.formation.java.computerdb.dao.validation.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.validation.DAOSqlException;
import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;

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
