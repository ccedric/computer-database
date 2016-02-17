/**
 * 
 */
package com.excilys.formation.java.computerdb.mapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import java.sql.ResultSet;

/**
 * @author Cédric Cousseran
 * Map the objects Computer, ComputerDTO and ResultSet of Computer toa nother
 */
public interface ComputerMapper {
	static final Logger LOGGER = LoggerFactory.getLogger(ComputerMapper.class);

	/**
	 * Map a computer with his company from a resultset
	 * @param result resultSet of the query, containing the computer
	 * @return Object Computer
	 * @throws SQLException
	 */
	static Computer map(ResultSet result) throws SQLException{
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
		return new  Computer.ComputerBuilder(result.getString("name"))
				.id(result.getInt("id"))
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
	static ComputerDTO mapComputerToDTO(Computer computer){
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
	 * Map a ComputerDTo to a Computer
	 * @param dto
	 * @return
	 */
	static Computer mapDTOToComputer(ComputerDTO dto){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate introduced = null;
		LocalDate discontinued = null;

		if (null!=dto.getIntroduced() && !dto.getIntroduced().isEmpty()){
			introduced = LocalDate.parse(dto.getIntroduced(),formatter);
		}
		if (null != dto.getDiscontinued() && !dto.getDiscontinued().isEmpty()){
			discontinued = LocalDate.parse(dto.getDiscontinued(),formatter);
		}
		Company company = null;
		if (dto.getCompanyId()!=0){
			company = new Company(dto.getCompanyId(),dto.getCompanyName());
		}

		return new Computer.ComputerBuilder(dto.getName())
				.id(dto.getId())
				.company(company)
				.introduced(introduced)
				.discontinued(discontinued).build();
	}
	
	/**
	 * Map a list of Computer to a list of ComputerDTO
	 * @param computers
	 * @return
	 */
	static List<ComputerDTO> mapListComputerToDTO(List<Computer> computers){
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
