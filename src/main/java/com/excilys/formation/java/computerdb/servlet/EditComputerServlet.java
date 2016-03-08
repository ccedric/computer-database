package com.excilys.formation.java.computerdb.servlet;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.dto.ComputerDto;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.validation.Valid;

/**
 * Servlet for the edit computer page, in the web-ui.
 * 
 * @author Cédric Cousseran
 */
@Controller
@RequestMapping({ "/edit-computer" ,"/editComputer"})
public class EditComputerServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);

  @Autowired
  private CompanyService companyService;
  @Autowired
  private ComputerService computerService;
  @Autowired
  private CompanyMapper companyMapper;
  @Autowired
  private ComputerMapper computerMapper;
  @Autowired
  private ComputerDtoMapper computerDtoMapper;

  /**
   * Show the view editComputer, get request.
   */
  @RequestMapping(method = RequestMethod.GET)
  public String doGet(int id, ModelMap modelMap) throws ServletException, IOException {
    List<CompanyDto> companies = companyMapper.listToDto(companyService.list());

    try {
      ComputerDto computer = computerMapper.toDto(computerService.find(id));
      modelMap.addAttribute("computer", computer);
      modelMap.addAttribute("companies", companies);
      LOGGER.info("Edit page of the computer: {}", computer.toString());

      return "editComputer";
    } catch (NumberFormatException e) {
      return "dashboard";
    }
  }

  /**
   * Confirm the edit of a computer, then redirect to the dashboard.
   */
  @RequestMapping(method = RequestMethod.POST)
  public String doPost(@Valid @ModelAttribute ComputerDto computerDto,
      BindingResult result, ModelMap modelMap) throws ServletException, IOException {
    if (result.hasErrors()) {
      List<CompanyDto> companies = companyMapper.listToDto(companyService.list());
      modelMap.addAttribute("companies", companies);
      modelMap.addAttribute("computer", computerDto);
      return "editComputer";
    } else {
      // Validation of the ComputerDTO
      ComputerDtoValidator.validate(computerDto);

      // Creation of a Computer from a ComputerDTO
      Computer computer = computerDtoMapper.toComputer(computerDto);
      computerService.update(computer);
      LOGGER.info("update of a new computer : {}", computerDto);
      return "redirect:/dashboard";
    }
  }
}
