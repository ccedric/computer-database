/**
 * 
 */
package com.excilys.formation.java.computerdb.model.validation;

import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.exception.ComputerInvalidException;

/**
 * @author Cédric Cousseran
 *
 */
public class ComputerValidator {

	public static void validate(Computer computer) throws ComputerInvalidException {
		if (null == computer){
			throw new ComputerInvalidException("The computer can't be null");
		}
		validateNameNotNull(computer);
		validatePositionDate(computer);
	}
	
	private static void validateNameNotNull(Computer computer) throws ComputerInvalidException {
		if (null == computer.getName() || computer.getName().equals("")){
			throw new ComputerInvalidException("The name of the computer can't be null");
		}	
	}
	
	private static void validatePositionDate(Computer computer) throws ComputerInvalidException {
		if (null != computer.getDiscontinued()){
			if (null == computer.getIntroduced()){
				throw new ComputerInvalidException("You need an introduced date if you have a discontinued date");
			}
			if (computer.getDiscontinued().isBefore(computer.getIntroduced())){
				throw new ComputerInvalidException("The discontinued date must be after the introduced date");
			}
		}

	}
}
