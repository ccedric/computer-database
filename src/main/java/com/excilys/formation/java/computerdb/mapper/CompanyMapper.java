/**
 * 
 */
package com.excilys.formation.java.computerdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.computerdb.dto.CompanyDTO;
import com.excilys.formation.java.computerdb.model.Company;

/**
 * @author Cédric Cousseran
 *
 */
public interface CompanyMapper {
	/**
	 * Map a resultSet in an object
	 * @param result the result of the query
	 * @return the company object
	 * @throws SQLException
	 */
	static Company map(ResultSet result) throws SQLException{
		return new  Company(result.getInt("id"),result.getString("name"));
	}
	
	static CompanyDTO mapCompanyToDTO(Company company){
		return new CompanyDTO(company.getId(),company.getName());
	}
	
	static Company mapDTOTOCompany(CompanyDTO dto){
		return new Company(dto.getId(),dto.getName());
	}

	static List<CompanyDTO> mapListCompanyToDTO(List<Company> companies){
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	
		for(Company company : companies){			
			companiesDTO.add(new CompanyDTO(company.getId(),company.getName()));
		}
		return companiesDTO;
	}

}
