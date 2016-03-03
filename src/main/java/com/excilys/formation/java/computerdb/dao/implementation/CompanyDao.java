package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.dao.Dao;
import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.db.DbUtil;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;
import com.excilys.formation.java.computerdb.order.OrderSearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


/**
 * Data Access Object for the class Company.
 * 
 * @author Cédric Cousseran
 *
 */
@Repository
public class CompanyDao implements Dao<Company> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);
  
  @Autowired
  DataSource dataSource;
  
  @Autowired
  CompanyMapper companyMapper;
  
  private static final String deleteQuery = "DELETE FROM company WHERE id=?";
  private static final String findQuery = "SELECT * from company  WHERE id=?";
  private static final String listQuery = "SELECT * from company";



  
  @Override
  public int create(Company obj) throws DatabaseConnectionException {
    throw new NotImplementedException(
        "The create method for the dao company has not yet been implemented");
  }

  @Override
  public void delete(Company obj)
      throws DatabaseConnectionException, CompanyNotFoundException, DaoSqlException {
    PreparedStatement statementCompany = null;
    Connection conn = null;
    try {
      conn = dataSource.getConnection();
      statementCompany = conn.prepareStatement(deleteQuery);
      statementCompany.setLong(1, obj.getId());
      int rows = statementCompany.executeUpdate();
      conn.commit();

      if (rows > 0) {
        LOGGER.info("Company deleted, id {}, name {}", obj.getId(), obj.getName());
      } else {
        LOGGER.info(
            "Company couldn't be deleted, check if he exists in the database, id {}, name {}",
            obj.getId(), obj.getName());
        throw new CompanyNotFoundException("Error while deleting the company,company not found");
      }
    } catch (SQLException e) {
      LOGGER.error("Error while deleting the company, rolling back");
      throw new DaoSqlException("SQL error while deleting the company");
    } finally {
      DbUtil.close(statementCompany);
      DbUtil.close(conn);
    }
  }

  @Override
  public void update(Company obj) throws DatabaseConnectionException {
    throw new NotImplementedException(
        "The update method for the dao company has not yet been implemented");
  }

  @Override
  public Company find(long id)
      throws DatabaseConnectionException, DaoSqlException, CompanyNotFoundException {

    ResultSet result = null;
    Company company = null;
    PreparedStatement statement = null;
    Connection conn = null;

    if (id == 0) {
      return null;
    }
    try {
      conn = dataSource.getConnection();
      statement = conn.prepareStatement(findQuery);
      statement.setLong(1, id);
      result = statement.executeQuery();
      if (result.next()) {
        company = companyMapper.fromResultSet(result);
      } else {
        LOGGER.info("No company found with the id: {}", id);
        throw new CompanyNotFoundException("The company couln't be found");
      }
    } catch (SQLException e) {
      LOGGER.error("Error while finding the company, id searched: {}", id);
      throw new DaoSqlException("SQL error while finding the company");
    } finally {
      DbUtil.close(result);
      DbUtil.close(conn);
    }
    LOGGER.info("Company found, id: {}, name: {}", company.getId(), company.getName());
    return company;
  }

  @Override
  public List<Company> list() throws DatabaseConnectionException, DaoSqlException {
    List<Company> companies = new ArrayList<Company>();
    ResultSet result = null;
    try {
      result = dataSource.getConnection()
          .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
          .executeQuery(listQuery);
      while (result.next()) {
        companies.add(companyMapper.fromResultSet(result));
      }
    } catch (SQLException e) {
      LOGGER.error("Error while retrieving the list of companies");
      throw new DaoSqlException("SQL error while finding the list of companies");
    } finally {
      DbUtil.close(result);
    }
    LOGGER.info("List of companies found, size of the list: {}", companies.size());

    return companies;
  }

  @Override
  public List<Company> listPage(int indexBegin, int pageSize) throws DatabaseConnectionException {
    throw new NotImplementedException(
        "The listPage method for the dao company has not yet been implemented");
  }

  @Override
  public List<Company> findByName(String name) throws DatabaseConnectionException {
    throw new NotImplementedException(
        "The findByName method for the dao company has not yet been implemented");
  }

  @Override
  public int selectCount(String name) throws DatabaseConnectionException {
    throw new NotImplementedException(
        "The select method for the dao company has not yet been implemented");
  }

  @Override
  public List<Company> listPageByName(int indexBegin, int pageSize, String name, OrderSearch order)
      throws DatabaseConnectionException {
    throw new NotImplementedException(
        "The listPageByName method for the dao company has not yet been implemented");
  }

}
