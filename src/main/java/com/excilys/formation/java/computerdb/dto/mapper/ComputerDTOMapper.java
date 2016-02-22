package com.excilys.formation.java.computerdb.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;

/**
 * Mapper used to convert a ComputerDTO to or from a Computer
 * @author Cédric Cousseran
 */
public interface ComputerDTOMapper {
	/**
	 * Map a ComputerDTo to a Computer
	 * @param dto
	 * @return
	 */
	static Computer toComputer(ComputerDTO dto){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate introduced = null;
		LocalDate discontinued = null;

		if (null!=dto.getIntroduced() && !dto.getIntroduced().isEmpty()){
			introduced = LocalDate.parse(dto.getIntroduced(),formatter);
		}
		if (null != dto.getDiscontinued() && !dto.getDiscontinued().isEmpty()){
			discontinued = LocalDate.parse(dto.getDiscontinued(),formatter);
		}
		Company company = null;
		if (dto.getCompanyId()!=0){
			company = new Company(dto.getCompanyId(),dto.getCompanyName());
		}

		return new Computer.ComputerBuilder(dto.getName())
				.id(dto.getId())
				.company(company)
				.introduced(introduced)
				.discontinued(discontinued).build();
	}
}
