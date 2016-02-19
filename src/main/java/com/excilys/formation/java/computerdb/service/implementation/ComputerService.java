/**
 * 
 */
package com.excilys.formation.java.computerdb.service.implementation;

import java.util.List;

import com.excilys.formation.java.computerdb.dao.exception.ComputerDAOInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDAO;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.service.Service;
import com.excilys.formation.java.computerdb.service.exception.TimestampDiscontinuedBeforeIntroducedException;

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
		if((obj.getIntroduced()!=null)&&(obj.getDiscontinued()!=null)&&(obj.getIntroduced().isAfter(obj.getDiscontinued()))){
			throw new TimestampDiscontinuedBeforeIntroducedException("The discontinued timestamp is before the introduced timestamp");
		}
		try {
			return computerDAO.create(obj);
		} catch (ComputerDAOInvalidException e) {
			e.printStackTrace();
		} catch (DAOSqlException e) {
			e.printStackTrace();
		}
		return 0;
	}


	@Override
	public boolean delete(Computer obj) throws DatabaseConnectionException {
		try {
			computerDAO.delete(obj);
			return true;
		} catch (ComputerNotFoundException e) {
			e.printStackTrace();
		} catch (DAOSqlException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Computer obj) throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException {
		if((obj.getIntroduced()!=null)&&(obj.getDiscontinued()!=null)&&(obj.getIntroduced().isAfter(obj.getDiscontinued()))){
			throw new TimestampDiscontinuedBeforeIntroducedException("The discontinued timestamp is before the introduced timestamp");
		}
		try {
			computerDAO.update(obj);
			return true;
		} catch (ComputerNotFoundException | DAOSqlException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Computer find(int id) throws DatabaseConnectionException {
		try {
			return computerDAO.find(id);
		} catch (DAOSqlException | ComputerNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<Computer> list() throws DatabaseConnectionException {
		try {
			return computerDAO.list();
		} catch (DAOSqlException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<Computer> listPage(Page page) throws DatabaseConnectionException {
		try {
			return computerDAO.listPage(page.getStartingIndex(), page.getPageSize());
		} catch (DAOSqlException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Computer> findByName(String name) throws DatabaseConnectionException {
		try {
			return computerDAO.findByName(name);
		} catch (DAOSqlException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Computer> listPageByName(Page page) throws DatabaseConnectionException {
		try {
			return computerDAO.listPageByName(page.getStartingIndex(), page.getPageSize(), page.getSearch(), page.getOrderSearch());
		} catch (DAOSqlException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.service.Service#selectCount(java.lang.String)
	 */
	@Override
	public int selectCount(String name) {
		try {
			return computerDAO.selectCount(name);
		} catch (DatabaseConnectionException | DAOSqlException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
