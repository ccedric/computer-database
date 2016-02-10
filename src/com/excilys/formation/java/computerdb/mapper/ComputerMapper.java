/**
 * 
 */
package com.excilys.formation.java.computerdb.mapper;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import java.sql.ResultSet;

/**
 * @author Cédric Cousseran
 *
 */
public interface ComputerMapper {

	/**
	 * Map a computer with his company
	 * @param result resultSet of the query, containing the computer
	 * @return Object Computer
	 * @throws SQLException
	 */
	static Computer map(ResultSet result) throws SQLException{
		LocalDateTime introduced = null;
		LocalDateTime discontinued = null;

		if (result.getTimestamp("introduced")!=null){
			introduced =result.getTimestamp("introduced").toLocalDateTime();
		}
		if (result.getTimestamp("discontinued")!=null){
			discontinued =result.getTimestamp("discontinued").toLocalDateTime();
		}
		return new  Computer.ComputerBuilder(result.getString("name"))
				.id(result.getInt("id"))
				.company(new Company(result.getInt("companyId"), result.getString("companyName")))
				.introduced(introduced)
				.discontinued(discontinued)
				.build();
	}

}
