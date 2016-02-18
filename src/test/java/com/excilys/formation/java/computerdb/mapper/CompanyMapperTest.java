/**
 * 
 */
package com.excilys.formation.java.computerdb.mapper;

import org.junit.Test;

import com.excilys.formation.java.computerdb.dto.CompanyDTO;
import com.excilys.formation.java.computerdb.dto.mapper.CompanyDTOMapper;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;

import static org.junit.Assert.*;

/**
 * @author Cédric Cousseran
 *
 */
public class CompanyMapperTest {

	@Test
	public void testCompanyToDTO(){
		CompanyDTO dto= new CompanyDTO(666,"test");
		Company company = new Company(666,"test");
		assertEquals(dto,CompanyMapper.toDTO(company));
	}
	
	@Test
	public void testCompanyToDTOFalse(){
		CompanyDTO dto= new CompanyDTO(666,"test");
		Company company = new Company(6656,"test");
		assertNotEquals(dto,CompanyMapper.toDTO(company));
	}
	
	@Test
	public void testDTOToCompany(){
		CompanyDTO dto= new CompanyDTO(666,"test");
		Company company = new Company(666,"test");
		assertEquals(company,CompanyDTOMapper.toCompany(dto));
	}
	
	@Test
	public void testDTOToCompanyFalse(){
		CompanyDTO dto= new CompanyDTO(666,"test");
		Company company = new Company(666,"test faux");
		assertNotEquals(company,CompanyDTOMapper.toCompany(dto));
	}
}
