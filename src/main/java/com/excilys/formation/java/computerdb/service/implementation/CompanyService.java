
package com.excilys.formation.java.computerdb.service.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.dao.implementation.CompanyDAO;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDAO;
import com.excilys.formation.java.computerdb.db.ConnectionFactory;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.service.Service;
import com.excilys.formation.java.computerdb.service.Page;

/**
 * @author Cédric Cousseran
 * Service layer for the Company model, call the DAO Company
 */
public class CompanyService implements Service<Company> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
	private static CompanyDAO companyDAO = null;

	public CompanyService(){
		companyDAO = new CompanyDAO();
	}

	/**
	 * Create a company.Not yet implemented, so return 0
	 */
	@Override
	public int create(Company obj) {
		try{
			return companyDAO.create(obj);
		} catch (NotImplementedException e){
			return 0;
		}
	}


	/**
	 * Delete a company, and all his computers
	 */
	@Override
	public boolean delete(Company obj) throws DatabaseConnectionException {
		ComputerDAO computerDAO = new ComputerDAO();
		Connection connect = null;
		try {
			connect = ConnectionFactory.getConnection();
			connect.setAutoCommit(false);
			computerDAO.deleteByCompany(obj,connect);
			companyDAO.delete(obj,connect);
			return true;
		} catch ( Exception e) {
			LOGGER.error("Error while deleting the company and his computers");
			try {
				connect.rollback();
			} catch (SQLException e1) {
				LOGGER.error("Error while rolling back, you're doomed boy");
			}
			return false;
		} finally{
			DbUtil.close(connect);
		}
	}

	/**
	 * Not yet implemented, so return false
	 */
	@Override
	public boolean update(Company obj) {
		try{
			companyDAO.update(obj);
			return true;
		} catch (NotImplementedException e){
			return false;
		}

	}

	@Override
	public Company find(int id) throws DatabaseConnectionException {
		try {
			return companyDAO.find(id);
		} catch (DAOSqlException | CompanyNotFoundException e) {} 
		return null;
	}


	@Override
	public List<Company> list() throws DatabaseConnectionException {
		try {
			return companyDAO.list();
		} catch (DAOSqlException e) {}
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
		} catch (DAOSqlException | NotImplementedException e) {}
		return null;
	}


	/**
	 * Not implemented for Company
	 */
	@Override
	public List<Company> findByName(String name) throws DatabaseConnectionException {
		try{
			return companyDAO.findByName(name);
		} catch (NotImplementedException e){
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.service.Service#listPageByName(int, int, java.lang.String)
	 */
	@Override
	public List<Company> listPageByName(Page page) throws DatabaseConnectionException {
		try{
			return companyDAO.listPageByName(page.getPage()*page.getPageSize(), page.getPageSize(),page.getSearch(), page.getOrderSearch());
		} catch (NotImplementedException e){
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.service.Service#selectCount(java.lang.String)
	 */
	@Override
	public int selectCount(String name) {
		try{
			return companyDAO.selectCount(name);
		} catch (NotImplementedException e){
			return 0;
		}
	}

}
