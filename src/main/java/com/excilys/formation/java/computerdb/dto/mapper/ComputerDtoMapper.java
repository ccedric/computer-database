package com.excilys.formation.java.computerdb.dto.mapper;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Mapper used to convert a ComputerDTO to or from a Computer.
 * 
 * @author Cédric Cousseran
 */
public interface ComputerDtoMapper {
  /**
   * Map a ComputerDTo to a Computer.
   * 
   * @param dto the ComputerDTO to map
   * @return the dto as a computer
   */
  static Computer toComputer(ComputerDto dto) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate introduced = null;
    LocalDate discontinued = null;

    if (null != dto.getIntroduced() && !dto.getIntroduced().isEmpty()) {
      introduced = LocalDate.parse(dto.getIntroduced(), formatter);
    }
    if (null != dto.getDiscontinued() && !dto.getDiscontinued().isEmpty()) {
      discontinued = LocalDate.parse(dto.getDiscontinued(), formatter);
    }
    Company company = null;
    if (dto.getCompanyId() != 0) {
      company = new Company(dto.getCompanyId(), dto.getCompanyName());
    }

    return new Computer.ComputerBuilder(dto.getName()).id(dto.getId()).company(company)
        .introduced(introduced).discontinued(discontinued).build();
  }
}
