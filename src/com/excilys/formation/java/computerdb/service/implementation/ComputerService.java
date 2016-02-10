/**
 * 
 */
package com.excilys.formation.java.computerdb.service.implementation;

import java.util.List;

import com.excilys.formation.java.computerdb.dao.implementation.ComputerDAO;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.Service;

/**
 * @author Cédric Cousseran
 *
 */
public class ComputerService implements Service<Computer> {
	private static ComputerDAO computerDAO = null;
	
	public ComputerService(){
		computerDAO = new ComputerDAO();
	}
	
	@Override
	public int create(Computer obj) {
		return computerDAO.create(obj);
	}


	@Override
	public boolean delete(Computer obj) {
		return computerDAO.delete(obj);
	}

	@Override
	public boolean update(Computer obj) {
		return computerDAO.update(obj);
	}

	@Override
	public Computer find(int id) {
		return computerDAO.find(id);
	}


	@Override
	public List<Computer> list() {
		return computerDAO.list();
	}


	@Override
	public List<Computer> listPage(int indexBegin, int pageSize) {
		return computerDAO.listPage(indexBegin, pageSize);
	}

}
