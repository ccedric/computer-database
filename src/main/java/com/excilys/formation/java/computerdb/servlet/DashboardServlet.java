package com.excilys.formation.java.computerdb.servlet;

import com.excilys.formation.java.computerdb.dto.ComputerDto;
import com.excilys.formation.java.computerdb.model.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.order.Column;
import com.excilys.formation.java.computerdb.order.Order;
import com.excilys.formation.java.computerdb.order.OrderSearch;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.service.implementation.ComputerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DashboardServlet, servlet of the jsp dashboard.jsp, main page of the
 * web application, where you can see the list of computers.
 * 
 * @author Cédric Cousseran
 *
 */
@SuppressWarnings("serial")
@WebServlet({ "/dashboard-servlet", "/dashboard" })
public class DashboardServlet extends HttpServlet {
  private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);
  private Page pageComputer = new Page(1, 50, "");
  private ComputerService computerService = new ComputerService();


  public DashboardServlet() {
    super();
  }


  protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

    List<ComputerDto> computers = ComputerMapper
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
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    if (null != request.getParameter("selection") && !request.getParameter("selection").isEmpty()) {
      String[] idsDelete = request.getParameter("selection").split(",");
      ComputerService computerService = new ComputerService();
      int idDeleteInt;
      boolean resultDelete;
      for (String idDelete : idsDelete) {
        try {
          idDeleteInt = Integer.parseInt(idDelete);
          resultDelete = computerService.delete(computerService.find(idDeleteInt));
          if (resultDelete) {
            LOGGER.info("Deletion of the computer with id {} successful", idDelete);
          } else {
            LOGGER.info("Deletion of the computer with id {} unsuccessful", idDelete);
          }
        } catch (Exception e) {
          LOGGER.info("Exception occured during the deletion of the computer {}, stack trace: ",
              idDelete);
          e.printStackTrace();
        }
      }
      request.setAttribute("computerDelete", idsDelete.length);

    }
    doGet(request, response);
  }

}
