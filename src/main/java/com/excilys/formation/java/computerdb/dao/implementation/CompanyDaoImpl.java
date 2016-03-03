package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.dao.CompanyDao;
import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.model.mapper.CompanyMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
public class CompanyDaoImpl implements CompanyDao {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDaoImpl.class);

  @Autowired
  DataSource dataSource;

  @Autowired
  CompanyMapper companyMapper;

  private static final String deleteQuery = "DELETE FROM company WHERE id=?";
  private static final String findQuery = "SELECT * from company  WHERE id=?";
  private static final String listQuery = "SELECT * from company";

  @Override
  public long create(Company obj) {
    throw new NotImplementedException(
        "The create method for the dao company has not yet been implemented");
  }

  @Override
  public void delete(Company obj) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.update(deleteQuery, obj.getId());
    LOGGER.info("Company deleted, id {}, name {}", obj.getId(), obj.getName());
  }

  @Override
  public void update(Company obj) {
    throw new NotImplementedException(
        "The update method for the dao company has not yet been implemented");
  }

  @Override
  public Company find(long id) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    try {
      Company company = jdbcTemplate.queryForObject(findQuery, new Object[] { id },
          new RowMapper<Company>() {
            public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
              return companyMapper.fromResultSet(rs);
            }
          });

      LOGGER.info("Company found, id: {}, name: {}", company.getId(), company.getName());

      return company;
    } catch (EmptyResultDataAccessException e) {
      throw new CompanyNotFoundException("Company not found with the id " + id);
    }
  }

  @Override
  public List<Company> list() throws DatabaseConnectionException, DaoSqlException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    List<Company> companies = jdbcTemplate.queryForObject(listQuery,
        new RowMapper<List<Company>>() {
          public List<Company> mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<Company> companies = new ArrayList<Company>();
            while (rs.next()) {
              Company company = companyMapper.fromResultSet(rs);
              companies.add(company);

            }
            return companies;
          }
        });

    return companies;
  }
}
