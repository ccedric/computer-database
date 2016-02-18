/**
 * 
 */
package com.excilys.formation.java.computerdb.dto.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.dto.exception.DateTimeInvalidException;
import com.excilys.formation.java.computerdb.dto.exception.DiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.dto.exception.NameRequiredException;

/**
 * @author Cédric Cousseran
 *
 */
public class ComputerDTOValidator {

	/**
	 * Check if a Computer is valid or not, with his name, introduced and discontinued date
	 * @param name name of the Computer
	 * @param introduced Introduced date of the Computer
	 * @param discontinued Discontinued date of the Computer
	 * @return an empty string if no errors, a String containing the errors message otherwise
	 * @throws DiscontinuedBeforeIntroducedException 
	 * @throws NameRequiredException 
	 * @throws DateTimeInvalidException 
	 */
	public static void validate(ComputerDTO dto) throws DiscontinuedBeforeIntroducedException, NameRequiredException, DateTimeInvalidException{
		StringBuilder errors = new StringBuilder();
		validateRequired(dto.getName());
		validateDateTimeFormat(dto.getIntroduced() );
		validateDateTimeFormat(dto.getDiscontinued());
		if (errors.length() ==0 ){
			validateIntroducedExistIfDiscontinuedDo(dto.getIntroduced(),dto.getDiscontinued());
			validateDiscontinuedAfterIntroduced(dto.getIntroduced(),dto.getDiscontinued());
		}
	}

	private static void validateIntroducedExistIfDiscontinuedDo(String introduced, String discontinued
			) throws DiscontinuedBeforeIntroducedException {
		if (introduced.equals("") && !discontinued.equals("")){
			throw new DiscontinuedBeforeIntroducedException("A discontinued date exists, but not an introduced date");
		}

	}

	private static void validateRequired(String input) throws NameRequiredException{
		if ((null==input) || (input.equals("") || (input.trim()).equals("")) ){
			throw new NameRequiredException("The name of the ComputerDTO is required");
		}
	}

	private static void validateDateTimeFormat(String input) throws DateTimeInvalidException{
		if (!input.equals("")){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			try{
				LocalDate.parse(input,formatter);
			} catch(Exception e){
				throw new DateTimeInvalidException("The format of hte DateTime is invalid");
			}		
		}
	}

	private static void validateDiscontinuedAfterIntroduced(String introduced, String discontinued) throws DiscontinuedBeforeIntroducedException{
		if (!introduced.equals("") && !discontinued.equals("")){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate introducedDate = LocalDate.parse(introduced,formatter);
			LocalDate discontinuedDate = LocalDate.parse(discontinued,formatter);
			if (introducedDate.isAfter(discontinuedDate)){
				throw new DiscontinuedBeforeIntroducedException("The discontinued date must be adter the introduced date");
			}
		}
	}

}
