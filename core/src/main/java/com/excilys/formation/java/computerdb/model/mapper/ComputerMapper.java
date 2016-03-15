package com.excilys.formation.java.computerdb.model.mapper;

import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Map the model Computer to a DTO or from a ResultSet.
 * 
 * @author Cédric Cousseran
 */
@Component
public class ComputerMapper {
  /**
   * Map a computer with his company from a resultset.
   * 
   * @param result
   *          resultSet of the query, containing the computer
   * @return Object Computer
   * @throws SQLException
   *           Thrown if there is an sql error
   */
  public Computer fromResultSet(ResultSet result) throws SQLException {
    LocalDate introduced = null;
    LocalDate discontinued = null;

    if (null != result.getTimestamp("introduced")) {
      introduced = result.getTimestamp("introduced").toLocalDateTime().toLocalDate();
    }
    if (null != result.getTimestamp("discontinued")) {
      discontinued = result.getTimestamp("discontinued").toLocalDateTime().toLocalDate();
    }
    Company company = null;
    if (null != result.getString("companyName")) {
      company = new Company(result.getInt("companyId"), result.getString("companyName"));
    }
    return new Computer.ComputerBuilder(result.getString("computerName"))
        .id(result.getInt("computerId")).company(company).introduced(introduced)
        .discontinued(discontinued).build();
  }

}
