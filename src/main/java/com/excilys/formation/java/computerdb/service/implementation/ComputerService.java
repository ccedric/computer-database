/**
 * 
 */
package com.excilys.formation.java.computerdb.service.implementation;

import java.util.List;

import com.excilys.formation.java.computerdb.dao.implementation.ComputerDAO;
import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.Service;
import com.excilys.formation.java.computerdb.service.TimestampDiscontinuedBeforeIntroducedException;

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
		return computerDAO.create(obj);
	}


	@Override
	public boolean delete(Computer obj) throws DatabaseConnectionException {
		return computerDAO.delete(obj);
	}

	@Override
	public boolean update(Computer obj) throws DatabaseConnectionException, TimestampDiscontinuedBeforeIntroducedException {
		if((obj.getIntroduced()!=null)&&(obj.getDiscontinued()!=null)&&(obj.getIntroduced().isAfter(obj.getDiscontinued()))){
			throw new TimestampDiscontinuedBeforeIntroducedException("The discontinued timestamp is before the introduced timestamp");
		}
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

	@Override
	public List<Computer> findByName(String name) throws DatabaseConnectionException {
		computerDAO.findByName(name);
		return null;
	}

	@Override
	public List<Computer> listPageByName(int indexBegin, int pageSize, String name) throws DatabaseConnectionException {
		return computerDAO.listPageByName(indexBegin, pageSize, name);
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.service.Service#selectCount(java.lang.String)
	 */
	@Override
	public int selectCount(String name) {
		return computerDAO.selectCount(name);
	}

}
