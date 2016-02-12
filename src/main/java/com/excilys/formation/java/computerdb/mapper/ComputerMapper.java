/**
 * 
 */
package com.excilys.formation.java.computerdb.mapper;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
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

		if (null!=result.getTimestamp("introduced")){
			introduced =result.getTimestamp("introduced").toLocalDateTime();
		}
		if (null!=result.getTimestamp("discontinued")){
			discontinued =result.getTimestamp("discontinued").toLocalDateTime();
		}
		Company company = null;
		if (null!=result.getString("companyName")){
			company = new Company(result.getInt("companyId"), result.getString("companyName"));
		}
		return new  Computer.ComputerBuilder(result.getString("name"))
				.id(result.getInt("id"))
				.company(company)
				.introduced(introduced)
				.discontinued(discontinued)
				.build();
	}
	
	static ComputerDTO mapComputerToDTO(Computer computer){
		return new ComputerDTO.ComputerDTOBuilder(computer.getName())
				.id(computer.getId())
				.companyId(computer.getCompany().getId())
				.companyName(computer.getCompany().getName())
				.introduced(computer.getIntroduced().toString())
				.discontinued(computer.getDiscontinued().toString()).build();
	}

	static Computer mapDTOToComputer(ComputerDTO dto){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime introduced = LocalDateTime.parse(dto.getIntroduced(),formatter);
		LocalDateTime discontinued = LocalDateTime.parse(dto.getIntroduced(),formatter);

		return new Computer.ComputerBuilder(dto.getName())
				.id(dto.getId())
				.company(new Company(dto.getCompanyId(),dto.getCompanyName()))
				.introduced(introduced)
				.discontinued(discontinued).build();
	}

	
}
