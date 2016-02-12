package com.excilys.formation.java.computerdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.implementation.ComputerService;
import com.excilys.formation.java.computerdb.ui.Page;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet({ "/DashboardServlet", "/dashboard" })
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService computerService = new ComputerService();
		Page<ComputerDTO> pageComputer = new Page<ComputerDTO>(10,computerService,"");
		
		pageComputer.setPage(1);
		List<ComputerDTO> computers= pageComputer.getListForPage();
		int maxPage = pageComputer.getMaxPages();
		int pageActuelle = pageComputer.getPage();

		request.setAttribute("maxPage", maxPage);
		request.setAttribute("pageActuelle", pageActuelle);
		request.setAttribute("computers", computers);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
