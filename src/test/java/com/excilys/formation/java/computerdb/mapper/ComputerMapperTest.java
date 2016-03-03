package com.excilys.formation.java.computerdb.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.dto.mapper.ComputerDtoMapper;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.mapper.ComputerMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Test the class ComputerMapper.
 * 
 * @author Cédric Cousseran
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
public class ComputerMapperTest {
  @Autowired
  ComputerMapper computerMapper;
  @Autowired
  ComputerDtoMapper computerDtoMapper;
  
  @Test
  public void testMapComputerToComputerDto() {
    ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder("test").id(666).companyId(999)
        .companyName("test company").build();

    Computer computer = new Computer.ComputerBuilder("test").id(666)
        .company(new Company(999, "test company")).build();

    assertEquals(computerDto, computerMapper.toDto(computer));
  }

  @Test
  public void testMapComputerToComputerDtoFalse() {
    ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder("test").id(666).companyId(999)
        .companyName("test company").build();

    Computer computer = new Computer.ComputerBuilder("test faux").id(666)
        .company(new Company(999, "test company")).build();

    assertNotEquals(computerDto, computerMapper.toDto(computer));
  }

  @Test
  public void testMapComputerDtoToComputer() {
    ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder("test").id(666).companyId(999)
        .companyName("test company").build();

    Computer computer = new Computer.ComputerBuilder("test").id(666)
        .company(new Company(999, "test company")).build();

    assertEquals(computer, computerDtoMapper.toComputer(computerDto));
  }

  @Test
  public void testMapComputerDtoToComputerFalse() {
    ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder("test").id(666).companyId(999)
        .companyName("test company").build();

    Computer computer = new Computer.ComputerBuilder("test faux").id(666)
        .company(new Company(999, "test company")).build();

    assertNotEquals(computer, computerDtoMapper.toComputer(computerDto));
  }
}
