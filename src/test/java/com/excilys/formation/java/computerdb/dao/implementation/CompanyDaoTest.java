/**
 * 
 */
package com.excilys.formation.java.computerdb.dao.implementation;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @author Cédric Cousseran
 *
 */
public class CompanyDaoTest {
	private CompanyDAO dao = new CompanyDAO();
	
	@Test
	public void testFindUnknown(){
		assertNull(dao.find(999999999));
	}

}
