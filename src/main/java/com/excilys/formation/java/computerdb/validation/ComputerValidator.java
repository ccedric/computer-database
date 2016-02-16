/**
 * @author Cédric Cousseran
 *
 */
package com.excilys.formation.java.computerdb.validation;

import java.time.LocalDateTime;
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
	 * @return
	 */
	public static String validateComputer(String name, String introduced, String discontinued){
		StringBuilder errors = new StringBuilder();
		validateRequired(name,errors,"Please enter the name of the computer. ");
		validateDateTimeFormat(introduced, errors,"The introduced date doesn't have the right format: yyyy-mm-dd hh:mm. " );
		validateDateTimeFormat(discontinued, errors,"The discontinued date doesn't have the right format: yyyy-mm-dd hh:mm. " );
		if (errors.length() ==0 ){
			validateDiscontinuedAfterIntroduced(introduced, discontinued, errors, "The discontinued date must be after the introduced date. ");
		}
		return errors.toString();
	}
	
	private static void validateRequired(String input, StringBuilder errors, String message){
		if ((input.trim()).equals("")){
			errors.append(message);
		}
	}
	
	private static void validateDateTimeFormat(String input, StringBuilder errors, String message){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		try{
			LocalDateTime.parse(input,formatter);
		} catch(Exception e){
			errors.append(message);
		}

	}
	
	private static void validateDiscontinuedAfterIntroduced(String introduced, String discontinued, StringBuilder errors, String message){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		LocalDateTime introducedDate = LocalDateTime.parse(introduced,formatter);
		LocalDateTime discontinuedDate = LocalDateTime.parse(discontinued,formatter);
		if (introducedDate.isAfter(discontinuedDate)){
			errors.append(message);
		}
	}
}