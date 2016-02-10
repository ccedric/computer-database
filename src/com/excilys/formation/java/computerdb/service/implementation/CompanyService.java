
package com.excilys.formation.java.computerdb.service.implementation;

import java.util.List;

import com.excilys.formation.java.computerdb.dao.implementation.CompanyDAO;
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
	public Company find(int id) {
		return companyDAO.find(id);
	}


	@Override
	public List<Company> list() {
		return companyDAO.list();
	}

	/**
	 * Paging not implemented for Company, will return the same result as list()
	 */
	@Override
	public List<Company> listPage(int indexBegin, int indexEnd) {
		return companyDAO.list();
	}

}
