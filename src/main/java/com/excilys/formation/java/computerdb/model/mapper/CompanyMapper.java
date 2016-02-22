package com.excilys.formation.java.computerdb.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.computerdb.dto.CompanyDTO;
import com.excilys.formation.java.computerdb.model.Company;

/**
 * Map the model Company from a ResultSet or to a DTO
 * @author Cédric Cousseran
 */
public interface CompanyMapper {
	/**
	 * Map a resultSet in an object
	 * @param result the result of the query
	 * @return the company object
	 * @throws SQLException
	 */
	static Company fromResultSet(ResultSet result) throws SQLException{
		return new  Company(result.getInt("id"),result.getString("name"));
	}

	/**
	 * Map a Company to a CompanyDTO
	 * @param company
	 * @return
	 */
	static CompanyDTO toDTO(Company company){
		return new CompanyDTO(company.getId(),company.getName());
	}
	

	/**
	 * Map a list of Company to a list of CompanyDTO
	 * @param companies
	 * @return
	 */
	static List<CompanyDTO> listToDTO(List<Company> companies){
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	
		for(Company company : companies){			
			companiesDTO.add(new CompanyDTO(company.getId(),company.getName()));
		}
		return companiesDTO;
	}
}
