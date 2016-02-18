/**
 * 
 */
package com.excilys.formation.java.computerdb.validation;

import org.junit.Test;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.dto.exception.DateTimeInvalidException;
import com.excilys.formation.java.computerdb.dto.exception.DiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.dto.exception.NameRequiredException;
import com.excilys.formation.java.computerdb.dto.validation.ComputerDTOValidator;

import static org.junit.Assert.*;


/**
 * @author Cédric Cousseran
 *
 */
public class ComputerValidatorTest {
	@Test
	public void testValidateComputerGood() throws DiscontinuedBeforeIntroducedException, NameRequiredException, DateTimeInvalidException{
		ComputerDTO dto = new ComputerDTO.ComputerDTOBuilder("test").introduced("1999-10-10").discontinued("2005-10-10").build();
		ComputerDTOValidator.validate(dto);
	}

	@Test
	public void testValidateComputerNameRequired() throws DiscontinuedBeforeIntroducedException, DateTimeInvalidException{
		ComputerDTO dto = new ComputerDTO.ComputerDTOBuilder("").introduced("1999-10-10").discontinued("2005-10-10").build();

		try {
			ComputerDTOValidator.validate(dto);
			fail("An exception should be thrown");
		} catch (NameRequiredException e) {
			assertTrue(true);
		}

	}

	@Test
	public void testValidateComputerIntroducedDate() throws DiscontinuedBeforeIntroducedException, NameRequiredException{
		ComputerDTO dto = new ComputerDTO.ComputerDTOBuilder("test").introduced("1999-10-10cds").discontinued("2005-10-10").build();
		try {
			ComputerDTOValidator.validate(dto);
			fail("An exception should be thrown");
		} catch (DateTimeInvalidException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testValidateComputerDiscontinuedDate() throws DiscontinuedBeforeIntroducedException, NameRequiredException{
		ComputerDTO dto = new ComputerDTO.ComputerDTOBuilder("test").introduced("1999-10-10").discontinued("2005-10-10 10:10").build();

		try {
			ComputerDTOValidator.validate(dto);
			fail("An exception should be thrown");
		} catch (DateTimeInvalidException e) {
			assertTrue(true);
		}	
	}

	@Test
	public void testValidateComputerDiscontinuedAfterIntroduced() throws NameRequiredException, DateTimeInvalidException{
		ComputerDTO dto = new ComputerDTO.ComputerDTOBuilder("test").introduced("1999-10-10").discontinued("1995-10-10").build();

		try {
			ComputerDTOValidator.validate(dto);
			fail("An exception should be thrown");
		} catch (DiscontinuedBeforeIntroducedException e) {
			assertTrue(true);
		}
	}
}
