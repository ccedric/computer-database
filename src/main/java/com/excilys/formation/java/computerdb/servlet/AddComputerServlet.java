package com.excilys.formation.java.computerdb.servlet;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.dto.exception.DateTimeInvalidException;
import com.excilys.formation.java.computerdb.dto.exception.DiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.dto.exception.NameRequiredException;
import com.excilys.formation.java.computerdb.dto.mapper.ComputerDtoMapper;
import com.excilys.formation.java.computerdb.dto.validation.ComputerDtoValidator;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;
import com.excilys.formation.java.computerdb.service.CompanyService;
import com.excilys.formation.java.computerdb.service.ComputerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for the jsp page addComputer.jsp
 * 
 * @author Cédric Cousseran
 */

@Controller
@RequestMapping({ "/add-computer-servlet", "/add-computer" })
public class AddComputerServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);

  @Autowired
  private CompanyService companyService;
  @Autowired
  private ComputerService computerService;
  @Autowired
  private CompanyMapper companyMapper;
  @Autowired
  private ComputerDtoMapper computerDtoMapper;

  /**
   * Display the apge dor adding a computer.
   */
  @RequestMapping(method = RequestMethod.GET)
  public String doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<CompanyDto> companies = companyMapper.listToDto(companyService.list());

    request.setAttribute("companies", companies);
    return "addComputer";
  }

  /**
   * Add a computer and redirect to the dashboard.
   */
  @RequestMapping(method = RequestMethod.POST)
  public String doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String name = request.getParameter("computerName");
    String introduced = request.getParameter("introduced");
    String discontinued = request.getParameter("discontinued");
    String companyId = request.getParameter("companyId");

    ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder(name).introduced(introduced)
        .discontinued(discontinued).companyId(Integer.parseInt(companyId)).build();
    try {
      ComputerDtoValidator.validate(computerDto);
      Computer computer = computerDtoMapper.toComputer(computerDto);
      computerService.create(computer);
      LOGGER.info("creation of a new computer : {}", computerDto);
      request.setAttribute("newComputer", computerDto);
    } catch (DiscontinuedBeforeIntroducedException | NameRequiredException
        | DateTimeInvalidException e) {
      request.setAttribute("errors", e.getMessage());
      doGet(request, response);
    }
    return "redirect:/dashboard";
  }

}
