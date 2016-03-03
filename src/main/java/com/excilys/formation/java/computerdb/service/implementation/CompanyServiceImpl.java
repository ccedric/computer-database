
package com.excilys.formation.java.computerdb.service.implementation;

import com.excilys.formation.java.computerdb.dao.CompanyDao;
import com.excilys.formation.java.computerdb.dao.ComputerDao;
import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.service.CompanyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for the Company model, call the DAO Company.
 * 
 * @author Cédric Cousseran
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

  @Autowired
  CompanyDao companyDao;
  @Autowired
  ComputerDao computerDao;

  public CompanyDao getCompanyDao() {
    return companyDao;
  }

  public void setCompanyDao(CompanyDao companyDao) {
    this.companyDao = companyDao;
  }

  public ComputerDao getComputerDao() {
    return computerDao;
  }

  public void setComputerDao(ComputerDao computerDao) {
    this.computerDao = computerDao;
  }

  /**
   * Create a company.Not yet implemented, so return 0
   */
  @Override
  @Transactional(readOnly = false)
  public long create(Company obj) {
    try {
      return companyDao.create(obj);
    } catch (NotImplementedException e) {
      return 0;
    }
  }

  /**
   * Delete a company, and all his computers.
   */
  @Override
  @Transactional(readOnly = false)
  public void delete(Company obj) throws DatabaseConnectionException {
    try {
      computerDao.deleteByCompany(obj);
      companyDao.delete(obj);
    } catch (Exception e) {
      LOGGER.error("Error while deleting the company and his computers");
    }
  }

  /**
   * Not yet implemented, so return false.
   */
  @Override
  @Transactional(readOnly = false)
  public void update(Company obj) {
    try {
      companyDao.update(obj);
    } catch (NotImplementedException e) {
      LOGGER.error("Update company not implemented");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Company find(long id) throws DatabaseConnectionException {
    try {
      return companyDao.find(id);
    } catch (DaoSqlException | CompanyNotFoundException e) {
      LOGGER.info("Exception catched in the CompanyService find");
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Company> list() throws DatabaseConnectionException {
    try {
      return companyDao.list();
    } catch (DaoSqlException e) {
      LOGGER.info("Exception catched in the CompanyService list");
    }
    return null;
  }
}
