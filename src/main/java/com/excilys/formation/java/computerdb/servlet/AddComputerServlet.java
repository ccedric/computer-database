/**
 * 
 */
package com.excilys.formation.java.computerdb.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.excilys.formation.java.computerdb.mapper.CompanyMapper;
import com.excilys.formation.java.computerdb.mapper.ComputerMapper;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.implementation.CompanyService;
import com.excilys.formation.java.computerdb.service.implementation.ComputerService;


/**
 * @author Cédric Cousseran
 * Servlet for the jsp page addComputer.jsp
 */

@SuppressWarnings("serial")
@WebServlet({ "/AddComputerServlet", "/addComputer" })
public class AddComputerServlet extends HttpServlet{
	private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CompanyService companyService = new CompanyService();
		List<CompanyDTO> companies= CompanyMapper.mapListCompanyToDTO(companyService.list());
		
		request.setAttribute("companies", companies);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errors = new String();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		if (null == name){
			errors += "Please enter the name of the computer. ";
		}
		if(!introduced.equals("")){
			try{
				LocalDateTime.parse(introduced,formatter);
			} catch(Exception e){
				errors += "The introduced date doesn't have the right format: yyyy-mm-dd hh:mm. ";
			}
		}
		if(!discontinued.equals("")){
			if (introduced.equals("")){
				errors += "Please choose an introduced date if you have a discontinued date. ";
			} else{
				try{
					LocalDateTime introducedDate = LocalDateTime.parse(introduced,formatter);
					LocalDateTime discontinuedDate = LocalDateTime.parse(discontinued,formatter);
					if (introducedDate.isAfter(discontinuedDate)){
						errors += "The discontinued date must be after the introduced date. ";
					}
				} catch(Exception e){
					errors += "The discontinued date doesn't have the right format: yyyy-mm-dd hh:mm. ";
				}

			}
		}
		if (!errors.isEmpty()){
			request.setAttribute("errors", errors);
			doGet(request,response);
		}else{
			ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder(name)
					.introduced(introduced)
					.discontinued(discontinued)
					.companyId(Integer.parseInt(companyId))
					.build();
			Computer computer = ComputerMapper.mapDTOToComputer(computerDTO);
			ComputerService computerService = new ComputerService();
			computerService.create(computer);
			LOGGER.info("creation of a new computer : {}",computerDTO);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard");
			dispatcher.forward(request, response);

		}
		
	}

}