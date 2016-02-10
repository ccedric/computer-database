/**
 * 
 */
package com.excilys.formation.java.computerdb.service.implementation;

import java.util.List;

import com.excilys.formation.java.computerdb.dao.implementation.ComputerDAO;
import com.excilys.formation.java.computerdb.dao.implementation.TimestampDiscontinuedBeforeIntroducedException;
import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;
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
	public int create(Computer obj) throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException {
		return computerDAO.create(obj);
	}


	@Override
	public boolean delete(Computer obj) throws DatabaseConnectionException {
		return computerDAO.delete(obj);
	}

	@Override
	public boolean update(Computer obj) throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException {
		return computerDAO.update(obj);
	}

	@Override
	public Computer find(int id) throws DatabaseConnectionException {
		return computerDAO.find(id);
	}


	@Override
	public List<Computer> list() throws DatabaseConnectionException {
		return computerDAO.list();
	}


	@Override
	public List<Computer> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException {
		return computerDAO.listPage(indexBegin, pageSize);
	}

}
