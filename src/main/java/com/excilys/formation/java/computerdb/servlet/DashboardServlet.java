package com.excilys.formation.java.computerdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.implementation.ComputerService;
import com.excilys.formation.java.computerdb.ui.Page;

/**
 * Servlet implementation class DashboardServlet, servlet of the jsp dashboard.jsp, main page of the web application, where you can see the list of computers
 */
@SuppressWarnings("serial")
@WebServlet({ "/DashboardServlet", "/dashboard" })
public class DashboardServlet extends HttpServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

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
		String searchByName = request.getParameter("search");
		int numberResultsPage = 50;
		int page =1;

		try{
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e){}

		try{
			numberResultsPage = Integer.parseInt(request.getParameter("numberResults"));
		} catch (Exception e){}

		if (null==searchByName){
			searchByName="";
		}
		LOGGER.info("New search with the name : {}", searchByName);

		Page<Computer> pageComputer = new Page<Computer>(numberResultsPage,computerService,searchByName);

		pageComputer.setPage(page);
		List<ComputerDTO> computers= ComputerMapper.mapListComputerToDTO(pageComputer.getListForPage());
		int maxPage = pageComputer.getMaxPages();
		int pageActuelle = pageComputer.getPage();
		int numberResults = pageComputer.getNbResults();

		request.setAttribute("maxPage", maxPage);
		request.setAttribute("pageActuelle", pageActuelle);
		request.setAttribute("computers", computers);
		request.setAttribute("nbResults", numberResults);
		request.setAttribute("searchByName", searchByName);
		request.setAttribute("numberResults", numberResultsPage);
		request.setAttribute("page", page);
		LOGGER.info("number of pages of the result: {}",maxPage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (null!=request.getParameter("selection") &&  !request.getParameter("selection").isEmpty()){
			String[] idsDelete =  request.getParameter("selection").split(",");
			ComputerService computerService = new ComputerService();
			int idDeleteInt;
			boolean resultDelete;
			for(String idDelete : idsDelete){
				try{
					idDeleteInt = Integer.parseInt(idDelete);
					resultDelete = computerService.delete(computerService.find(idDeleteInt));
					if (resultDelete){
						LOGGER.info("Deletion of the computer with id {} successful",idDelete);
					} else{
						LOGGER.info("Deletion of the computer with id {} unsuccessful",idDelete);
					}
				} catch (Exception e){
					LOGGER.info("Exception occured during the deletion of the computer {}, stack trace: ",idDelete);
					e.printStackTrace();
				}
			}

		}
		doGet(request, response);
	}

}
