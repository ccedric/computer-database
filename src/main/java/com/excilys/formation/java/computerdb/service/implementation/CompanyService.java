
package com.excilys.formation.java.computerdb.service.implementation;

import java.util.List;

import com.excilys.formation.java.computerdb.dao.implementation.CompanyDAO;
import com.excilys.formation.java.computerdb.db.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.service.Service;

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
	public boolean delete(Company obj) {
		return false;
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
		return companyDAO.find(id);
	}


	@Override
	public List<Company> list() throws DatabaseConnectionException {
		return companyDAO.list();
	}

	/**
	 * Paging not implemented for Company, will return the same result as list()
	 * @throws DatabaseConnectionException 
	 */
	@Override
	public List<Company> listPage(int indexBegin, int indexEnd) throws DatabaseConnectionException {
		return companyDAO.list();
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
	public List<Company> listPageByName(int indexBegin, int pageSize, String name) throws DatabaseConnectionException {
		return companyDAO.listPageByName(indexBegin,pageSize,name);
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.service.Service#selectCount(java.lang.String)
	 */
	@Override
	public int selectCount(String name) {
		return companyDAO.selectCount(name);
	}

}
