package com.excilys.formation.java.computerdb.servlet;

import com.excilys.formation.java.computerdb.dto.CompanyDto;
import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.dto.mapper.CompanyDtoMapper;
import com.excilys.formation.java.computerdb.dto.mapper.ComputerDtoMapper;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.order.Column;
import com.excilys.formation.java.computerdb.order.Order;
import com.excilys.formation.java.computerdb.order.OrderSearch;
import com.excilys.formation.java.computerdb.service.CompanyService;
import com.excilys.formation.java.computerdb.service.ComputerService;
import com.excilys.formation.java.computerdb.service.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.validation.Valid;

/**
 * Controller for the computers of the application Computer Database.
 * 
 * @author Cédric Cousseran
 *
 */
@Controller
public class ComputerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

  @Autowired
  private ComputerService computerService;
  @Autowired
  private CompanyService companyService;

  @Autowired
  private ComputerDtoMapper computerDtoMapper;
  @Autowired
  private CompanyDtoMapper companyDtoMapper;

  /**
   * List of computers with no specific search.
   */
  @RequestMapping(path = "computer", method = RequestMethod.GET)
  public String list(Page pageComputer, ModelMap modelMap, Column column, Order order)
      throws ServletException, IOException {

    if (null == pageComputer.getSearch()) {
      pageComputer.setSearch("");
    }
    if (column == null) {
      column = Column.computerId;
    }
    if (order == null) {
      order = Order.ASC;
    }

    OrderSearch orderSearch = new OrderSearch(column, order);
    pageComputer.setOrderSearch(orderSearch);

    if (pageComputer.getPageSize() == 0) {
      pageComputer.setPageSize(50);
    }
    if (pageComputer.getPage() == 0) {
      pageComputer.setPage(1);
    }
    String searchByName = pageComputer.getSearch();

    int page = pageComputer.getPage();

    int numberResultsPage = pageComputer.getPageSize();

    List<ComputerDto> computers = computerDtoMapper
        .listFromModel(computerService.listPageByName(pageComputer));
    int numberResults = computerService.selectCount(searchByName);
    
    int maxPage = ( numberResults + numberResultsPage - 1)
        / numberResultsPage;
    Column orderColumn = pageComputer.getOrderSearch().getColumn();
    Order orderOrder = pageComputer.getOrderSearch().getOrder();

    modelMap.addAttribute("maxPage", maxPage);
    modelMap.addAttribute("pageActuelle", page);
    modelMap.addAttribute("computers", computers);
    modelMap.addAttribute("nbResults", numberResults);
    modelMap.addAttribute("searchByName", searchByName);
    modelMap.addAttribute("numberResults", numberResultsPage);
    modelMap.addAttribute("page", page);
    modelMap.addAttribute("orderColumn", orderColumn.toString());
    modelMap.addAttribute("orderOrder", orderOrder.toString());

    return "dashboard";
  }

  /**
   * Deletion of computers.
   */
  @RequestMapping(path = "computer", method = RequestMethod.POST)
  public String deleteListComputer(String selection, ModelMap modelMap)
      throws ServletException, IOException {
    if (null != selection && !selection.isEmpty()) {
      String[] idsDelete = selection.split(",");
      int idDeleteInt;
      for (String idDelete : idsDelete) {
        idDeleteInt = Integer.parseInt(idDelete);
        computerService.delete(computerService.find(idDeleteInt));
        LOGGER.info("Deletion of the computer with id {} successful", idDelete);
      }
    }
    return "redirect:/computer";
  }

  /**
   * Display the page for adding a computer.
   */
  @RequestMapping(path = "computer/add", method = RequestMethod.GET)
  public String addPage(ModelMap modelMap) throws ServletException, IOException {
    
    List<CompanyDto> companies = companyDtoMapper.listFromModel(companyService.list());
    modelMap.addAttribute("companies", companies);
    return "addComputer";
  }

  /**
   * Add a computer and redirect to the dashboard.
   */
  @RequestMapping(path = "computer/add", method = RequestMethod.POST)
  public String addComputer(@Valid @ModelAttribute ComputerDto computerDto, BindingResult result,
      ModelMap modelMap) throws ServletException, IOException {

    if (result.hasErrors()) {
      LOGGER.warn("A Post to create a new dto : {} had errors: {}", computerDto,
          result.getAllErrors());

      List<CompanyDto> companies = companyDtoMapper.listFromModel(companyService.list());
      modelMap.addAttribute("companies", companies);
      modelMap.addAttribute("computer", computerDto);
      return "addComputer";
    } else {
      Computer computer = computerDtoMapper.toComputer(computerDto);
      computerService.create(computer);
      LOGGER.info("creation of a new computer : {}", computerDto);
      return "redirect:/computer";
    }

  }

  /**
   * Show the view editComputer, get request.
   */
  @RequestMapping(path = { "computer/edit/{id}" }, method = RequestMethod.GET)
  public String doGet(@PathVariable(value = "id") int id, ModelMap modelMap)
      throws ServletException, IOException {
    List<CompanyDto> companies = companyDtoMapper.listFromModel(companyService.list());

    try {
      ComputerDto computer = computerDtoMapper.fromModel(computerService.find(id));
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
  @RequestMapping(path = "computer/edit", method = RequestMethod.POST)
  public String doPost(@Valid @ModelAttribute ComputerDto computerDto, BindingResult result,
      ModelMap modelMap) throws ServletException, IOException {
    if (result.hasErrors()) {
      List<CompanyDto> companies = companyDtoMapper.listFromModel(companyService.list());
      modelMap.addAttribute("companies", companies);
      modelMap.addAttribute("computer", computerDto);
      return "editComputer";
    } else {
      // Creation of a Computer from a ComputerDTO
      Computer computer = computerDtoMapper.toComputer(computerDto);
      computerService.update(computer);
      LOGGER.info("update of a new computer : {}", computerDto);
      return "redirect:/computer";
    }
  }
}
