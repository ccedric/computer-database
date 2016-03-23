package com.excilys.formation.java.computerdb.webservice;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.dto.mapper.CompanyDtoMapper;
import com.excilys.formation.java.computerdb.service.CompanyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.ws.rs.core.Response;

/**
 * WebService for the service Company.
 * 
 * @author Cédric Cousseran
 *
 */
@RestController
@RequestMapping("/api/company")
public class CompanyWebService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyWebService.class);

  @Autowired
  private CompanyService companyService;

  @Autowired
  private CompanyDtoMapper companyDtoMapper;

  /**
   * List all computers.
   */
  @RequestMapping(value = "list", method = RequestMethod.GET)
  public List<CompanyDto> list() {

    List<CompanyDto> companies = companyDtoMapper.listFromModel(companyService.list());

    return companies;
  }

  /**
   * List a company and all his computers.
   */
  @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
  public Response delete(@PathVariable("id") long id) {

    companyService.delete(companyService.find(id));
    LOGGER.info("Company {} and all his computers deleted",id);
    return Response.ok("ok").build();
  }

}
