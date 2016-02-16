/**
 * 
 */
package com.excilys.formation.java.computerdb.validation;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Cédric Cousseran
 *
 */
public class ComputerValidatorTest {
	@Test
	public void testValidateComputerGood(){
		assertEquals("",ComputerValidator.validateComputer("test", "1999-10-10 10:10", "2005-10-10 10:10"));
	}
	
	@Test
	public void testValidateComputerNameRequired(){
		assertNotEquals("",ComputerValidator.validateComputer("", "1999-10-10 10:10", "2005-10-10 10:10"));
	}
	
	@Test
	public void testValidateComputerIntroducedDate(){
		assertNotEquals("",ComputerValidator.validateComputer("", "1999-10-10 10:10cds", "2005-10-10 10:10"));
	}
	
	@Test
	public void testValidateComputerDiscontinuedDate(){
		assertNotEquals("",ComputerValidator.validateComputer("", "1999-10-10 10:10", "2005-10-10"));
	}
	
	@Test
	public void testValidateComputerDiscontinuedAfterIntroduced(){
		assertNotEquals("",ComputerValidator.validateComputer("", "1999-10-10 10:10", "1990-10-10 10:10"));
	}
}
