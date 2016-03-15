package com.excilys.formation.java.computerdb.dto.mapper;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.model.Company;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

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


  /**
   * Map a Company to a CompanyDTO.
   * 
   * @param company the company to map
   * @return the company as a dto
   */
  public CompanyDto fromModel(Company company) {
    return new CompanyDto(company.getId(), company.getName());
  }

  /**
   * Map a list of Company to a list of CompanyDTO.
   * 
   * @param companies the companies to map
   * @return the companies as a dto
   */
  public List<CompanyDto> listFromModel(List<Company> companies) {
    List<CompanyDto> companiesDto = new ArrayList<CompanyDto>();

    for (Company company : companies) {
      companiesDto.add(new CompanyDto(company.getId(), company.getName()));
    }
    return companiesDto;
  }
}
