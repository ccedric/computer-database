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

import com.excilys.formation.java.computerdb.dto.CompanyDTO;
import com.excilys.formation.java.computerdb.dto.ComputerDTO;
import com.excilys.formation.java.computerdb.dto.exception.DateTimeInvalidException;
import com.excilys.formation.java.computerdb.dto.exception.DiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.dto.exception.NameRequiredException;
import com.excilys.formation.java.computerdb.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.java.computerdb.dto.validation.ComputerDTOValidator;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;
import com.excilys.formation.java.computerdb.service.implementation.CompanyService;
import com.excilys.formation.java.computerdb.service.implementation.ComputerService;


/**
 * Servlet for the jsp page addComputer.jsp
 * @author Cédric Cousseran
 */

@SuppressWarnings("serial")
@WebServlet({ "/add-computer-servlet", "/add-computer" })
public class AddComputerServlet extends HttpServlet{
	private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompanyService companyService = new CompanyService();
		List<CompanyDTO> companies= CompanyMapper.listToDTO(companyService.list());

		request.setAttribute("companies", companies);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");


		ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder(name)
				.introduced(introduced)
				.discontinued(discontinued)
				.companyId(Integer.parseInt(companyId))
				.build();
		try {
			ComputerDTOValidator.validate(computerDTO);
			Computer computer = ComputerDTOMapper.toComputer(computerDTO);
			ComputerService computerService = new ComputerService();
			computerService.create(computer);
			LOGGER.info("creation of a new computer : {}",computerDTO);
			request.setAttribute("newComputer", computerDTO);
			RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard");
			dispatcher.forward(request, response);
		} catch (DiscontinuedBeforeIntroducedException | NameRequiredException | DateTimeInvalidException e) {
			request.setAttribute("errors", e.getMessage());
			doGet(request,response);
		}
	}

}
