package com.excilys.formation.java.computerdb.dto.mapper;

import com.excilys.formation.java.computerdb.dto.CompanyDTO;
import com.excilys.formation.java.computerdb.model.Company;

/**
 * Class used to convert a CompanyDTO to or from a Company
 * @author Cédric Cousseran
 */
public interface CompanyDTOMapper {
	/**
	 * Map a CompanyDTO to a Company
	 * @param dto
	 * @return
	 */
	static Company toCompany(CompanyDTO dto){
		return new Company(dto.getId(),dto.getName());
	}
}
