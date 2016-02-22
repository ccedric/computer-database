package com.excilys.formation.java.computerdb.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.computerdb.dao.DAO;
import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DAOSqlException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.db.TransactionManager;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;
import com.excilys.formation.java.computerdb.order.OrderSearch;

import java.sql.PreparedStatement;

/**
 * Data Access Object for the class Company
 * @author Cédric Cousseran
 *
 */
public class CompanyDAO implements DAO<Company> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	private static final String deleteQuery = "DELETE FROM company WHERE id=?";
	private static final String findQuery = "SELECT * from company  WHERE id=?";
	private static final String listQuery = "SELECT * from company";
	
	public CompanyDAO() {}

	@Override
	public int create(Company obj) throws DatabaseConnectionException {
		throw new NotImplementedException("The create method for the dao company has not yet been implemented");
	}

	public void delete(Company obj) throws DatabaseConnectionException, CompanyNotFoundException, DAOSqlException {
		PreparedStatement statementCompany = null;
		try {
			statementCompany = TransactionManager.getInstance().get().prepareStatement(deleteQuery);
			statementCompany.setInt(1, obj.getId());
			int rows = statementCompany.executeUpdate();
			TransactionManager.getInstance().commit();


			if (rows>0){
				LOGGER.info("Company deleted, id {}, name {}", obj.getId(), obj.getName());
			} else{
				LOGGER.info("Company couldn't be deleted, check if he exists in the database, id {}, name {}", obj.getId(), obj.getName());
				throw new CompanyNotFoundException("Error while deleting the company,company not found");			
			}
		} catch (SQLException e) {
			LOGGER.error("Error while deleting the company, rolling back");
			throw new DAOSqlException("SQL error while deleting the company");
		} finally{
			DbUtil.close(statementCompany);
		}
	}

	@Override
	public void update(Company obj) throws DatabaseConnectionException {
		throw new NotImplementedException("The update method for the dao company has not yet been implemented");
	}

	@Override
	public Company find(int id) throws DatabaseConnectionException, DAOSqlException, CompanyNotFoundException {

		ResultSet result = null;
		Company company =  null;
		PreparedStatement statement = null;

		if (id==0){
			return null;
		}
		try {
			statement = TransactionManager.getInstance().get().prepareStatement(findQuery);
			statement.setInt(1, id);
			result = statement.executeQuery();    
			if(result.next()){
				company = CompanyMapper.fromResultSet(result);  
			} else{
				LOGGER.info("No company found with the id: {}",id);
				throw new CompanyNotFoundException("The company couln't be found");
			}
		} catch (SQLException e) {
			LOGGER.error("Error while finding the company, id searched: {}",id);
			throw new DAOSqlException("SQL error while finding the company");
		} finally{
			DbUtil.close(result);
		}		
		LOGGER.info("Company found, id: {}, name: {}",company.getId(),company.getName());
		return company;		
	}

	@Override
	public List<Company> list() throws DatabaseConnectionException, DAOSqlException {
		List<Company> companies = new ArrayList<Company>();
		ResultSet result = null;
		try {
			result = TransactionManager.getInstance().get().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery(listQuery);  
			while (result.next()){
				companies.add(CompanyMapper.fromResultSet(result));
			}
		} catch (SQLException e){
			LOGGER.error("Error while retrieving the list of companies");
			throw new DAOSqlException("SQL error while finding the list of companies");
		} finally{
			DbUtil.close(result);
		}
		LOGGER.info("List of companies found, size of the list: {}",companies.size());

		return companies;
	}


	@Override
	public List<Company> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException {
		throw new NotImplementedException("The listPage method for the dao company has not yet been implemented");
	}

	@Override
	public List<Company> findByName(String name) throws DatabaseConnectionException {
		throw new NotImplementedException("The findByName method for the dao company has not yet been implemented");
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.dao.DAO#selectCount()
	 */
	@Override
	public int selectCount(String name) throws DatabaseConnectionException {
		throw new NotImplementedException("The select method for the dao company has not yet been implemented");
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.dao.DAO#listPageByName(int, int, java.lang.String)
	 */
	@Override
	public List<Company> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order) throws DatabaseConnectionException {
		throw new NotImplementedException("The listPageByName method for the dao company has not yet been implemented");
	}

}
