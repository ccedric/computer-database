package com.excilys.formation.java.computerdb.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.dto.mapper.CompanyDtoMapper;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;

import org.junit.Test;

/**
 * Test the class CompanyMapper.
 * 
 * @author Cédric Cousseran
 *
 */
public class CompanyMapperTest {

  @Test
  public void testCompanyToDto() {
    CompanyDto dto = new CompanyDto(666, "test");
    Company company = new Company(666, "test");
    assertEquals(dto, CompanyMapper.toDto(company));
  }

  @Test
  public void testCompanyToDtoFalse() {
    CompanyDto dto = new CompanyDto(666, "test");
    Company company = new Company(6656, "test");
    assertNotEquals(dto, CompanyMapper.toDto(company));
  }

  @Test
  public void testDtoToCompany() {
    CompanyDto dto = new CompanyDto(666, "test");
    Company company = new Company(666, "test");
    assertEquals(company, CompanyDtoMapper.toCompany(dto));
  }

  @Test
  public void testDtoToCompanyFalse() {
    CompanyDto dto = new CompanyDto(666, "test");
    Company company = new Company(666, "test faux");
    assertNotEquals(company, CompanyDtoMapper.toCompany(dto));
  }
}
