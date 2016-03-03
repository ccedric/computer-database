package com.excilys.formation.java.computerdb.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.dto.mapper.CompanyDtoMapper;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test the class CompanyMapper.
 * 
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
public class CompanyMapperTest {
  @Autowired
  CompanyMapper companyMapper;
  @Autowired
  CompanyDtoMapper companyDtoMapper;

  @Test
  public void testCompanyToDto() {
    CompanyDto dto = new CompanyDto(666, "test");
    Company company = new Company(666, "test");
    assertEquals(dto, companyMapper.toDto(company));
  }

  @Test
  public void testCompanyToDtoFalse() {
    CompanyDto dto = new CompanyDto(666, "test");
    Company company = new Company(6656, "test");
    assertNotEquals(dto, companyMapper.toDto(company));
  }

  @Test
  public void testDtoToCompany() {
    CompanyDto dto = new CompanyDto(666, "test");
    Company company = new Company(666, "test");
    assertEquals(company, companyDtoMapper.toCompany(dto));
  }

  @Test
  public void testDtoToCompanyFalse() {
    CompanyDto dto = new CompanyDto(666, "test");
    Company company = new Company(666, "test faux");
    assertNotEquals(company, companyDtoMapper.toCompany(dto));
  }
}
