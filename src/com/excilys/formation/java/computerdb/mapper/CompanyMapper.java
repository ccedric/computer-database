/**
 * 
 */
package com.excilys.formation.java.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.computerdb.model.Company;

/**
 * @author Cédric Cousseran
 *
 */
public class CompanyMapper {
	/**
	 * Map a resultSet in an object
	 * @param result the result of the query
	 * @return the company object
	 * @throws SQLException
	 */
	public static Company map(ResultSet result) throws SQLException{
		return new  Company(result.getInt("id"),result.getString("name"));
	}

}
