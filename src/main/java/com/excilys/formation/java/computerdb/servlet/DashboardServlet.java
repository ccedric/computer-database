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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
  private Page pageComputer = new Page(1, 50, "");

  @Autowired
  private ComputerService computerService;
  @Autowired
  private ComputerMapper computerMapper;

  /**
   * List of computers with no specific search.
   */
  @RequestMapping(method = RequestMethod.GET)
  public String doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String searchByName = request.getParameter("search");
    String orderColumn = request.getParameter("order-column");
    String orderOrder = request.getParameter("order-order");

    int numberResultsPage = 50;
    int page = 1;

    try {
      page = Integer.parseInt(request.getParameter("page"));
    } catch (Exception e) {
      LOGGER.info("page argument in the dashboard servlet was not an int");
    }

    try {
      numberResultsPage = Integer.parseInt(request.getParameter("number-results"));
    } catch (Exception e) {
      LOGGER.info("number-results argument in the dashboard servlet was not an int");
    }

    if (null == searchByName) {
      searchByName = "";
    }
    LOGGER.info("New search with the name : {}, page number: {}", searchByName, page);

    pageComputer.setSearch(searchByName);
    pageComputer.setPage(page);
    pageComputer.setPageSize(numberResultsPage);

    OrderSearch order = new OrderSearch();
    try {
      order = new OrderSearch(Column.valueOf(orderColumn), Order.valueOf(orderOrder));
    } catch (Exception e) {
      LOGGER.info("The order arguments in the dashboard servlet were invalid");
    }

    pageComputer.setOrderSearch(order);

    List<ComputerDto> computers = computerMapper
        .listToDto(computerService.listPageByName(pageComputer));
    int maxPage = (computerService.selectCount(searchByName) + numberResultsPage - 1)
        / numberResultsPage;
    int pageActuelle = pageComputer.getPage();
    int numberResults = computerService.selectCount(searchByName);

    request.setAttribute("maxPage", maxPage);
    request.setAttribute("pageActuelle", pageActuelle);
    request.setAttribute("computers", computers);
    request.setAttribute("nbResults", numberResults);
    request.setAttribute("searchByName", searchByName);
    request.setAttribute("numberResults", numberResultsPage);
    request.setAttribute("page", page);
    request.setAttribute("orderColumn", orderColumn);
    request.setAttribute("orderOrder", orderOrder);

    LOGGER.info("number of pages of the result: {}", maxPage);
    
    return "dashboard";
  }

  /**
   * Search of a computer or company.
   */
  @RequestMapping(method = RequestMethod.POST)
  public String doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (null != request.getParameter("selection") && !request.getParameter("selection").isEmpty()) {
      String[] idsDelete = request.getParameter("selection").split(",");
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
      request.setAttribute("computerDelete", idsDelete.length);
    }
    return "redirect:/dashboard";
  }

}
