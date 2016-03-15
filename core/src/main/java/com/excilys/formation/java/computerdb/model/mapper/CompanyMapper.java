package com.excilys.formation.java.computerdb.model.mapper;


import com.excilys.formation.java.computerdb.model.Company;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


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

}
