package com.excilys.formation.java.computerdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.computerdb.db.ConnectionFactory;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.model.Company;

import java.sql.Connection;

public class CompanyDAO extends DAO<Company> {
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

		Company company = new Company();   
		if (id==0){
			return company;
		}
		try {
			ResultSet result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery(
							"SELECT * from company  WHERE id="+id
							);    
			if(result.first()){
				company = new Company(id, result.getString("name"));  
			}
			DbUtil.close(result);
		} catch (SQLException e) {
			logger.error("Error while finding the company, id searched: {}",id);
			e.printStackTrace();
		}
		DbUtil.close(connect);
		logger.info("Company found, id: {}, name: {}",company.getId(),company.getName());
		return company;		
	}

	@Override
	public List<Company> list() {
        Connection connect = ConnectionFactory.getConnection();
		List<Company> companies = new ArrayList<Company>();
		try {
			ResultSet result = connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY
					).executeQuery("SELECT * from company");  
			while (result.next()){
				companies.add(new Company(result.getInt("id"),result.getString("name")));
			}
			DbUtil.close(result);
		} catch (SQLException e){
			logger.error("Error while retrieving the list of companies");

			e.printStackTrace();
		}
		DbUtil.close(connect);
		logger.info("List of companies found, size of the list: {}",companies.size());

		return companies;
	}

}
