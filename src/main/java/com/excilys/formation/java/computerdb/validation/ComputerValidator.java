/**
 * @author Cédric Cousseran
 *
 */
package com.excilys.formation.java.computerdb.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



/**
 * @author Cédric Cousseran
 * Check if a Computer is valid or not
 */
public class ComputerValidator {

	/**
	 * Check if a Computer is valid or not, with his name, introduced and discontinued date
	 * @param name name of the Computer
	 * @param introduced Introduced date of the Computer
	 * @param discontinued Discontinued date of the Computer
	 * @return an empty string if no errors, a String containing the errors message otherwisee
	 */
	public static String validateComputer(String name, String introduced, String discontinued){
		StringBuilder errors = new StringBuilder();
		validateRequired(name,errors,"Please enter the name of the computer. ");
		validateDateTimeFormat(introduced, errors,"The introduced date doesn't have the right format: yyyy-mm-dd. " );
		validateDateTimeFormat(discontinued, errors,"The discontinued date doesn't have the right format: yyyy-mm-dd. " );
		if (errors.length() ==0 ){
			validateIntroducedExistIfDiscontinuedDo(introduced, discontinued, errors, "Please enter an introduced date if you have a discontinued date. ");
			validateDiscontinuedAfterIntroduced(introduced, discontinued, errors, "The discontinued date must be after the introduced date. ");
		}
		return errors.toString();
	}

	private static void validateIntroducedExistIfDiscontinuedDo(String introduced, String discontinued,
			StringBuilder errors, String message) {
		if (introduced.equals("") && !discontinued.equals("")){
			errors.append(message);
		}
		
	}

	private static void validateRequired(String input, StringBuilder errors, String message){
		if ((input.trim()).equals("")){
			errors.append(message);
		}
	}

	private static void validateDateTimeFormat(String input, StringBuilder errors, String message){
		if (!input.equals("")){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			try{
				LocalDate.parse(input,formatter);
			} catch(Exception e){
				errors.append(message);
			}		
		}
	}

	private static void validateDiscontinuedAfterIntroduced(String introduced, String discontinued, StringBuilder errors, String message){
		if (!introduced.equals("") && !discontinued.equals("")){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate introducedDate = LocalDate.parse(introduced,formatter);
			LocalDate discontinuedDate = LocalDate.parse(discontinued,formatter);
			if (introducedDate.isAfter(discontinuedDate)){
				errors.append(message);
			}
		}
	}
}