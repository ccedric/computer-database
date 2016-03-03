package com.excilys.formation.java.computerdb.dto.mapper;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.model.Company;

import org.springframework.stereotype.Component;

/**
 * Class used to convert a CompanyDTO to or from a Company.
 * 
 * @author Cédric Cousseran
 */
@Component
public class CompanyDtoMapper {
  /**
   * Map a CompanyDTO to a Company.
   * 
   * @param dto the CompanyDTo to map
   * @return the dto as a Company
   */
  public Company toCompany(CompanyDto dto) {
    return new Company(dto.getId(), dto.getName());
  }
}
