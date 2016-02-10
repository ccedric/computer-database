package com.excilys.formation.java.computerdb.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.computerdb.dao.DAO;
import com.excilys.formation.java.computerdb.db.ConnectionFactory;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.mapper.CompanyMapper;
import com.excilys.formation.java.computerdb.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Data Access Object for the class Company
 * @author Cédric Cousseran
 *
 */
public class CompanyDAO implements DAO<Company> {
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	public CompanyDAO() {
		super();
	}

	@Override
	public int create(Company obj) {
		return 0;
	}

	@Override
	public boolean delete(Company obj) {
		return false;
	}

	@Override
	public boolean update(Company obj) {
		return false;
	}

	@Override
	public Company find(int id) {

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
			if(result.first()){
				company = CompanyMapper.map(result);  
			}
		} catch (SQLException e) {
			logger.error("Error while finding the company, id searched: {}",id);
			e.printStackTrace();
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}		
		logger.info("Company found, id: {}, name: {}",company.getId(),company.getName());
		return company;		
	}

	@Override
	public List<Company> list() {
		Connection connect = ConnectionFactory.getConnection();
		List<Company> companies = new ArrayList<Company>();
		ResultSet result = null;
		try {
			result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery("SELECT * from company");  
			while (result.next()){
				companies.add(CompanyMapper.map(result));
			}
		} catch (SQLException e){
			logger.error("Error while retrieving the list of companies");

			e.printStackTrace();
		} finally{
			DbUtil.close(result);
			DbUtil.close(connect);
		}
		logger.info("List of companies found, size of the list: {}",companies.size());

		return companies;
	}

}
