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
import com.excilys.formation.java.computerdb.model.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.service.CompanyService;
import com.excilys.formation.java.computerdb.service.ComputerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for the edit computer page, in the web-ui.
 * 
 * @author Cédric Cousseran
 */
@SuppressWarnings("serial")
@WebServlet({ "/edit-computer" })
public class EditComputerServlet extends HttpServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);

  @Autowired
  CompanyService companyService;
  @Autowired
  ComputerService computerService;
  @Autowired
  CompanyMapper companyMapper;
  @Autowired
  ComputerMapper computerMapper;
  @Autowired
  ComputerDtoMapper computerDtoMapper;
  
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        config.getServletContext());
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<CompanyDto> companies = companyMapper.listToDto(companyService.list());

    try {
      int id = Integer.parseInt(request.getParameter("id"));
      ComputerDto computer = computerMapper.toDto(computerService.find(id));
      request.setAttribute("computer", computer);
      request.setAttribute("companies", companies);
      LOGGER.info("Edit page of the computer: {}", computer.toString());

      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editComputer.jsp");
      dispatcher.forward(request, response);

    } catch (NumberFormatException e) {
      RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard");
      dispatcher.forward(request, response);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response) Called when
   *      the user has finished updating the computer.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String name = request.getParameter("computerName");
    String introduced = request.getParameter("introduced");
    String discontinued = request.getParameter("discontinued");
    String companyId = request.getParameter("companyId");
    int id = Integer.parseInt(request.getParameter("id"));

    ComputerDto computerDto = new ComputerDto.ComputerDtoBuilder(name).introduced(introduced)
        .discontinued(discontinued).id(id).companyId(Integer.parseInt(companyId)).build();
    try {
      // Validation of the ComputerDTO
      ComputerDtoValidator.validate(computerDto);

      // Creation of a Computer from a ComputerDTO
      Computer computer = computerDtoMapper.toComputer(computerDto);
      computerService.update(computer);
      LOGGER.info("update of a new computer : {}", computerDto);

      request.setAttribute("updateComputer", computerDto);
      RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard");
      dispatcher.forward(request, response);

    } catch (DiscontinuedBeforeIntroducedException | NameRequiredException
        | DateTimeInvalidException e) {
      request.setAttribute("errors", e.getMessage());
      doGet(request, response);
    }

  }

}
