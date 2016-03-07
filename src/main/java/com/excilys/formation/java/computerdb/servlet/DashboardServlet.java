package com.excilys.formation.java.computerdb.servlet;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.model.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.order.Column;
import com.excilys.formation.java.computerdb.order.Order;
import com.excilys.formation.java.computerdb.order.OrderSearch;
import com.excilys.formation.java.computerdb.service.ComputerService;
import com.excilys.formation.java.computerdb.service.Page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

/**
 * Servlet implementation class DashboardServlet, servlet of the jsp dashboard.jsp, main page of the
 * web application, where you can see the list of computers.
 * 
 * @author Cédric Cousseran
 *
 */
@Controller
@RequestMapping({ "/dashboard-servlet", "/dashboard" })
public class DashboardServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);
  // private Page pageComputer = new Page(1, 50, "");

  @Autowired
  private ComputerService computerService;
  @Autowired
  private ComputerMapper computerMapper;

  /**
   * List of computers with no specific search.
   */
  @RequestMapping(method = RequestMethod.GET)
  public String doGet(Page pageComputer, ModelMap modelMap,
      @RequestParam(value = "column", required = false) Column column,
      @RequestParam(value = "order", required = false) Order order)
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

    List<ComputerDto> computers = computerMapper
        .listToDto(computerService.listPageByName(pageComputer));
    
    int maxPage = (computerService.selectCount(searchByName) + numberResultsPage - 1)
        / numberResultsPage;
    int numberResults = computerService.selectCount(searchByName);
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
  @RequestMapping(method = RequestMethod.POST)
  public String doPost(String selection, ModelMap modelMap) throws ServletException, IOException {
    if (null != selection && !selection.isEmpty()) {
      String[] idsDelete = selection.split(",");
      int idDeleteInt;
      for (String idDelete : idsDelete) {
        try {
          idDeleteInt = Integer.parseInt(idDelete);
          computerService.delete(computerService.find(idDeleteInt));
          LOGGER.info("Deletion of the computer with id {} successful", idDelete);

        } catch (Exception e) {
          LOGGER.info("Exception occured during the deletion of the computer {}, stack trace: ",
              idDelete);
          e.printStackTrace();
        }
      }
    }
    return "redirect:/dashboard";
  }

}
