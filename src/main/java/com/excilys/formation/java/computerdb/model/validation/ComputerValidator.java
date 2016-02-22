/**
 * 
 */
package com.excilys.formation.java.computerdb.model.validation;

import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.exception.ComputerInvalidException;

/**
 * @author Cédric Cousseran
 * This class validate a Computer
 */
public class ComputerValidator {

	/**
	 * Method to use when validating a computer. Throws an ComputerInvalidException exception if the computer is not valid
	 * @param computer the computer to validate
	 * @throws ComputerInvalidException
	 */
	public static void validate(Computer computer) throws ComputerInvalidException {
		if (null == computer){
			throw new ComputerInvalidException("The computer can't be null");
		}
		validateNameNotNull(computer);
		validatePositionDate(computer);
	}
	
	/**
	 * Validate if the name of a Computer is not null
	 * @param computer
	 * @throws ComputerInvalidException
	 */
	private static void validateNameNotNull(Computer computer) throws ComputerInvalidException {
		if (null == computer.getName() || computer.getName().equals("")){
			throw new ComputerInvalidException("The name of the computer can't be null");
		}	
	}
	
	/**
	 * Validate if the discontinued timestamp is after the introduced timestamp
	 * @param computer
	 * @throws ComputerInvalidException
	 */
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
