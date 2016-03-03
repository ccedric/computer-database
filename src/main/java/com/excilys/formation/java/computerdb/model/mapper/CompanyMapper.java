package com.excilys.formation.java.computerdb.model.mapper;


import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.model.Company;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Map the model Company from a ResultSet or to a DTO.
 * 
 * @author Cédric Cousseran
 */
@Component
public class CompanyMapper {
  /**
   * Map a resultSet in an object.
   * 
   * @param result
   *          the result of the query
   * @return the company object
   * @throws SQLException TH
   */
  public Company fromResultSet(ResultSet result) throws SQLException {
    return new Company(result.getInt("id"), result.getString("name"));
  }

  /**
   * Map a Company to a CompanyDTO.
   * 
   * @param company the company to map
   * @return the company as a dto
   */
  public CompanyDto toDto(Company company) {
    return new CompanyDto(company.getId(), company.getName());
  }

  /**
   * Map a list of Company to a list of CompanyDTO.
   * 
   * @param companies the companies to map
   * @return the companies as a dto
   */
  public List<CompanyDto> listToDto(List<Company> companies) {
    List<CompanyDto> companiesDto = new ArrayList<CompanyDto>();

    for (Company company : companies) {
      companiesDto.add(new CompanyDto(company.getId(), company.getName()));
    }
    return companiesDto;
  }
}
