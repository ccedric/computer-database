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
 * Map the object Company, CompanyDTO and ResultSet of Company to another
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
	
	/**
	 * Map a Company to a CompanyDTO
	 * @param company
	 * @return
	 */
	static CompanyDTO mapCompanyToDTO(Company company){
		return new CompanyDTO(company.getId(),company.getName());
	}
	
	/**
	 * Map a CompanyDTO to a Company
	 * @param dto
	 * @return
	 */
	static Company mapDTOTOCompany(CompanyDTO dto){
		return new Company(dto.getId(),dto.getName());
	}

	/**
	 * Map a list of Company to a list of CompanyDTO
	 * @param companies
	 * @return
	 */
	static List<CompanyDTO> mapListCompanyToDTO(List<Company> companies){
		List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	
		for(Company company : companies){			
			companiesDTO.add(new CompanyDTO(company.getId(),company.getName()));
		}
		return companiesDTO;
	}

}
