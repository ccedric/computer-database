package com.excilys.formation.java.computerdb.dto.mapper;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Mapper used to convert a ComputerDTO to or from a Computer.
 * 
 * @author Cédric Cousseran
 */
@Component
public class ComputerDtoMapper {
  @Autowired
  private MessageSource messageSource;

  /**
   * Map a ComputerDTo to a Computer.
   * 
   * @param dto
   *          the ComputerDTO to map
   * @return the dto as a computer
   */
  public Computer toComputer(ComputerDto dto) {
    
    DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern(messageSource.getMessage("app.formatDate", null, Locale.getDefault()));
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


  /**
   * map a computer to a ComputerDTO.
   * 
   * @param computer
   *          the computer to map
   * @return the computer as a dto
   */
  public ComputerDto fromModel(Computer computer) {
    String companyName = null;
    long companyId = 0;
    String introduced = null;
    String discontinued = null;

    if (null != computer.getCompany()) {
      companyId = computer.getCompany().getId();
      companyName = computer.getCompany().getName();
    }

    if (null != computer.getIntroduced()) {
      introduced = computer.getIntroduced().toString();
    }
    if (null != computer.getDiscontinued()) {
      discontinued = computer.getDiscontinued().toString();
    }

    return new ComputerDto.ComputerDtoBuilder(computer.getName()).id(computer.getId())
        .companyId(companyId).companyName(companyName).introduced(introduced)
        .discontinued(discontinued).build();
  }

  /**
   * Map a list of Computer to a list of ComputerDTO.
   * 
   * @param computers
   *          the computers to map
   * @return the computers as dto
   */
  public List<ComputerDto> listFromModel(List<Computer> computers) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
        messageSource.getMessage("app.formatDate", null, LocaleContextHolder.getLocale()));
    List<ComputerDto> computersDto = new ArrayList<ComputerDto>();

    for (Computer computer : computers) {
      String introduced = null;
      String discontinued = null;
      long companyId = 0;
      String companyName = null;
      if (null != computer.getIntroduced()) {
        introduced = computer.getIntroduced().format(formatter);
      }
      if (null != computer.getDiscontinued()) {
        discontinued = computer.getDiscontinued().format(formatter);
      }
      if (null != computer.getCompany()) {
        companyId = computer.getCompany().getId();
        companyName = computer.getCompany().getName();
      }

      computersDto.add(new ComputerDto.ComputerDtoBuilder(computer.getName()).id(computer.getId())
          .companyId(companyId).companyName(companyName).introduced(introduced)
          .discontinued(discontinued).build());
    }
    return computersDto;
  }

}
