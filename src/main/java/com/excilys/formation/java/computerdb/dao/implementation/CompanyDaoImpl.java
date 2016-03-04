package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.dao.CompanyDao;
import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
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
  private DataSource dataSource;

  @Autowired
  private CompanyMapper companyMapper;

  @Override
  public long create(Company obj) {
    LOGGER.warn("Call to the method create of CompanyDao, wich is not implemented");
    throw new NotImplementedException(
        "The create method for the dao company has not yet been implemented");
  }

  private static final String DELETEQUERY = "DELETE FROM company WHERE id=?";
  
  @Override
  public void delete(Company obj) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.update(DELETEQUERY, obj.getId());
    LOGGER.info("Company deleted, id {}, name {}", obj.getId(), obj.getName());
  }

  @Override
  public void update(Company obj) {
    LOGGER.warn("Call to the method update of CompanyDao, wich is not implemented");
    throw new NotImplementedException(
        "The update method for the dao company has not yet been implemented");
  }

  private static final String FINDQUERY = "SELECT * from company  WHERE id=?";

  @Override
  public Company find(long id) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    try {
      Company company = jdbcTemplate.queryForObject(FINDQUERY, new Object[] { id },
          new RowMapper<Company>() {
            public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
              return companyMapper.fromResultSet(rs);
            }
          });

      LOGGER.info("Company found, id: {}, name: {}", company.getId(), company.getName());

      return company;
    } catch (EmptyResultDataAccessException e) {
      LOGGER.warn("Company not found with the id {}",id);
      throw new CompanyNotFoundException("Company not found with the id " + id,e);
    }
  }

  private static final String LISTQUERY = "SELECT * from company";
  
  @Override
  public List<Company> list() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    try {
      List<Company> companies = jdbcTemplate.queryForObject(LISTQUERY,
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
      LOGGER.info("List of Companies found, size of the list: {}", companies.size());

      return companies;
    } catch (EmptyResultDataAccessException e) {
      LOGGER.error("No companies found at all");
      throw new CompanyNotFoundException("No company found in the list",e);
    }
  }
}
