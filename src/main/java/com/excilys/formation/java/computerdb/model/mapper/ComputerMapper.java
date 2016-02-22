package com.excilys.formation.java.computerdb.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;

/**
 * @author Cédric Cousseran
 * Map the model Computer to a DTO or from a ResultSet
 */
public interface ComputerMapper {
	/**
	 * Map a computer with his company from a resultset
	 * @param result resultSet of the query, containing the computer
	 * @return Object Computer
	 * @throws SQLException
	 */
	static Computer fromResultSet(ResultSet result) throws SQLException{
		LocalDate introduced = null;
		LocalDate discontinued = null;

		if (null!=result.getTimestamp("introduced")){
			introduced =result.getTimestamp("introduced").toLocalDateTime().toLocalDate();
		}
		if (null!=result.getTimestamp("discontinued")){
			discontinued =result.getTimestamp("discontinued").toLocalDateTime().toLocalDate();
		}
		Company company = null;
		if (null!=result.getString("companyName")){
			company = new Company(result.getInt("companyId"), result.getString("companyName"));
		}
		return new  Computer.ComputerBuilder(result.getString("computerName"))
				.id(result.getInt("computerId"))
				.company(company)
				.introduced(introduced)
				.discontinued(discontinued)
				.build();
	}
	
	/**
	 * map a computer to a ComputerDTO
	 * @param computer
	 * @return 
	 */
	static ComputerDTO toDTO(Computer computer){
		String companyName=null;
		int companyId=0;
		String introduced = null;
		String discontinued = null;

		if (null!=computer.getCompany()){
			companyId = computer.getCompany().getId();
			companyName= computer.getCompany().getName();
		}

		if (null!=computer.getIntroduced()){
			introduced = computer.getIntroduced().toString();
		}
		if(null!=computer.getDiscontinued()){
			discontinued = computer.getDiscontinued().toString();
		}
		
		return new ComputerDTO.ComputerDTOBuilder(computer.getName())
				.id(computer.getId())
				.companyId(companyId)
				.companyName(companyName)
				.introduced(introduced)
				.discontinued(discontinued).build();
	}

	/**
	 * Map a list of Computer to a list of ComputerDTO
	 * @param computers
	 * @return
	 */
	static List<ComputerDTO> listToDTO(List<Computer> computers){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
				
		for(Computer computer : computers){
			String introduced = null;
			String discontinued = null;
			int companyId = 0;
			String companyName=null;
			if (null!=computer.getIntroduced()){
				introduced =computer.getIntroduced().format(formatter);
			}
			if (null!=computer.getDiscontinued()){
				discontinued =computer.getDiscontinued().format(formatter);
			}
			if (null!=computer.getCompany()){
				companyId=computer.getCompany().getId();
				companyName= computer.getCompany().getName();
			}

			
			computersDTO.add(new ComputerDTO.ComputerDTOBuilder(computer.getName())
					.id(computer.getId())
					.companyId(companyId)
					.companyName(companyName)
					.introduced(introduced)
					.discontinued(discontinued).build());
		}
		return computersDTO;
	}

}
