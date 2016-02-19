
package com.excilys.formation.java.computerdb.service.implementation;

import java.util.List;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;
import com.excilys.formation.java.computerdb.dao.implementation.CompanyDAO;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.service.Service;
import com.excilys.formation.java.computerdb.service.Page;

/**
 * @author Cédric Cousseran
 *
 */
public class CompanyService implements Service<Company> {
	private static CompanyDAO companyDAO = null;

	public CompanyService(){
		companyDAO = new CompanyDAO();
	}

	/**
	 * Create a company.Not yet implemented, so return 0
	 */
	@Override
	public int create(Company obj) {
		return 0;
	}


	/**
	 * Delete a company. Not yet implemented, so return false
	 */
	@Override
	public boolean delete(Company obj) throws DatabaseConnectionException {
		try {
			companyDAO.delete(obj);
			return true;
		} catch ( CompanyNotFoundException | DAOSqlException e) {
			return false;
		}
	}

	/**
	 * Not yet implemented, so return false
	 */
	@Override
	public boolean update(Company obj) {
		return false;
	}

	@Override
	public Company find(int id) throws DatabaseConnectionException {
		try {
			return companyDAO.find(id);
		} catch (DAOSqlException e) {
			e.printStackTrace();
		} catch (CompanyNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<Company> list() throws DatabaseConnectionException {
		try {
			return companyDAO.list();
		} catch (DAOSqlException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Paging not implemented for Company, will return the same result as list()
	 * @throws DatabaseConnectionException 
	 */
	@Override
	public List<Company> listPage(Page page) throws DatabaseConnectionException {
		try {
			return companyDAO.list();
		} catch (DAOSqlException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Not implemented for Company
	 */
	@Override
	public List<Company> findByName(String name) throws DatabaseConnectionException {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.service.Service#listPageByName(int, int, java.lang.String)
	 */
	@Override
	public List<Company> listPageByName(Page page) throws DatabaseConnectionException {
		return companyDAO.listPageByName(page.getPage()*page.getPageSize(), page.getPageSize(),page.getSearch(), page.getOrderSearch());
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.service.Service#selectCount(java.lang.String)
	 */
	@Override
	public int selectCount(String name) {
		return companyDAO.selectCount(name);
	}

}
