package com.excilys.formation.java.computerdb.dto.mapper;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.model.Company;

/**
 * Class used to convert a CompanyDTO to or from a Company.
 * 
 * @author Cédric Cousseran
 */
public interface CompanyDtoMapper {
  /**
   * Map a CompanyDTO to a Company.
   * 
   * @param dto the CompanyDTo to map
   * @return the dto as a Company
   */
  static Company toCompany(CompanyDto dto) {
    return new Company(dto.getId(), dto.getName());
  }
}
