/**
 * 
 */
package com.excilys.formation.java.computerdb.mapper;

import org.junit.Test;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.mapper.ComputerMapper;

import static org.junit.Assert.*;


/**
 * @author Cédric Cousseran
 *
 */
public class ComputerMapperTest {

	@Test
	public void testMapComputerToComputerDTO(){
		ComputerDTO computerDTO= new ComputerDTO.ComputerDTOBuilder("test")
		.id(666)
		.companyId(999)
		.companyName("test company")
		.build();
		
		Computer computer = new Computer.ComputerBuilder("test")
				.id(666)
				.company(new Company(999,"test company"))
				.build();
		
		assertEquals(computerDTO,ComputerMapper.toDTO(computer));
	}
	
	@Test
	public void testMapComputerToComputerDTOFalse(){
		ComputerDTO computerDTO= new ComputerDTO.ComputerDTOBuilder("test")
		.id(666)
		.companyId(999)
		.companyName("test company")
		.build();
		
		Computer computer = new Computer.ComputerBuilder("test faux")
				.id(666)
				.company(new Company(999,"test company"))
				.build();
		
		assertNotEquals(computerDTO,ComputerMapper.toDTO(computer));
	}
	
	@Test
	public void testMapComputerDTOToComputer(){
		ComputerDTO computerDTO= new ComputerDTO.ComputerDTOBuilder("test")
		.id(666)
		.companyId(999)
		.companyName("test company")
		.build();
		
		Computer computer = new Computer.ComputerBuilder("test")
				.id(666)
				.company(new Company(999,"test company"))
				.build();
		
		assertEquals(computer,ComputerDTOMapper.toComputer(computerDTO));
	}
	
	@Test
	public void testMapComputerDTOToComputerFalse(){
		ComputerDTO computerDTO= new ComputerDTO.ComputerDTOBuilder("test")
		.id(666)
		.companyId(999)
		.companyName("test company")
		.build();
		
		Computer computer = new Computer.ComputerBuilder("test faux")
				.id(666)
				.company(new Company(999,"test company"))
				.build();
		
		assertNotEquals(computer,ComputerDTOMapper.toComputer(computerDTO));
	}
}
