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
import com.excilys.formation.java.computerdb.db.ConnectionFactory;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;
import com.excilys.formation.java.computerdb.order.OrderSearch;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Data Access Object for the class Company
 * @author Cédric Cousseran
 *
 */
public class CompanyDAO implements DAO<Company> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

	public CompanyDAO() {}

	@Override
	public int create(Company obj) throws DatabaseConnectionException {
		return 0;
	}

	@Override
	public void delete(Company obj) throws DatabaseConnectionException, CompanyNotFoundException, DAOSqlException {
		Connection connect = ConnectionFactory.getConnection();
		PreparedStatement statementComputer = null;
		PreparedStatement statementCompany = null;

		String sqlDelete = "DELETE FROM company WHERE id=?";
		String sqlDeleteComputer = "DELETE FROM computer where company_id = ?";

		try {
			connect.setAutoCommit(false);

			statementComputer = connect.prepareStatement(sqlDeleteComputer);
			statementComputer.setInt(1, obj.getId());
			statementComputer.executeUpdate();

			statementCompany = connect.prepareStatement(sqlDelete);
			statementCompany.setInt(1, obj.getId());
			int rows = statementCompany.executeUpdate();
			connect.commit();


			if (rows>0){
				LOGGER.info("Company deleted, id {}, name {}", obj.getId(), obj.getName());
			} else{
				LOGGER.info("Company couldn't be deleted, check if he exists in the database, id {}, name {}", obj.getId(), obj.getName());
				throw new CompanyNotFoundException("Error while deleting the company,company not found");			
			}
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Error while deleting the company, rolling back");
			try {
				connect.rollback();
			} catch (SQLException e1) {
				LOGGER.error("Error while rolling back, you're doomed boy");
			}
			
			throw new DAOSqlException("SQL error while deleting the company");
		} finally{
			DbUtil.close(statementComputer);
			DbUtil.close(statementCompany);
			DbUtil.close(connect);
		}

	}

	@Override
	public void update(Company obj) throws DatabaseConnectionException {
	}

	@Override
	public Company find(int id) throws DatabaseConnectionException, DAOSqlException, CompanyNotFoundException {

		Connection connect = ConnectionFactory.getConnection();
		ResultSet result = null;
		Company company =  null;
		PreparedStatement statement = null;
		String sql = "SELECT * from company  WHERE id=?";

		if (id==0){
			return null;
		}
		try {
			statement = connect.prepareStatement(sql);
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
			e.printStackTrace();
			throw new DAOSqlException("SQL error while finding the company");
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}		
		LOGGER.info("Company found, id: {}, name: {}",company.getId(),company.getName());
		return company;		
	}

	@Override
	public List<Company> list() throws DatabaseConnectionException, DAOSqlException {
		Connection connect = ConnectionFactory.getConnection();
		List<Company> companies = new ArrayList<Company>();
		ResultSet result = null;
		try {
			result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery("SELECT * from company");  
			while (result.next()){
				companies.add(CompanyMapper.fromResultSet(result));
			}
		} catch (SQLException e){
			LOGGER.error("Error while retrieving the list of companies");
			e.printStackTrace();
			throw new DAOSqlException("SQL error while finding the list of companies");
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		LOGGER.info("List of companies found, size of the list: {}",companies.size());

		return companies;
	}

	/**
	 * Not yet implemented, return null
	 */
	@Override
	public List<Company> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException {
		return null;
	}

	@Override
	public List<Company> findByName(String name) throws DatabaseConnectionException {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.dao.DAO#selectCount()
	 */
	@Override
	public int selectCount(String name) throws DatabaseConnectionException {
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.excilys.formation.java.computerdb.dao.DAO#listPageByName(int, int, java.lang.String)
	 */
	@Override
	public List<Company> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order) throws DatabaseConnectionException {
		return null;
	}

}
