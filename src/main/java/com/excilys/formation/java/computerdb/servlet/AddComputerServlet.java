package com.excilys.formation.java.computerdb.servlet;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.dto.mapper.ComputerDtoMapper;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;
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
   * Display the page for adding a computer.
   */
  @RequestMapping(method = RequestMethod.GET)
  public String doGet(ModelMap modelMap)
      throws ServletException, IOException {
    List<CompanyDto> companies = companyMapper.listToDto(companyService.list());
    modelMap.addAttribute("companies", companies);
    return "addComputer";
  }

  /**
   * Add a computer and redirect to the dashboard.
   */
  @RequestMapping(method = RequestMethod.POST)
  public String doPost(@Valid @ModelAttribute ComputerDto computerDto, BindingResult result, 
      ModelMap modelMap) throws ServletException, IOException {
    if (result.hasErrors()) {
      List<CompanyDto> companies = companyMapper.listToDto(companyService.list());
      modelMap.addAttribute("companies", companies);
      modelMap.addAttribute("computer", computerDto);
      return "addComputer";
    } else {
      Computer computer = computerDtoMapper.toComputer(computerDto);
      computerService.create(computer);
      LOGGER.info("creation of a new computer : {}", computerDto);
      return "redirect:/dashboard";
    }
    
  }

}
