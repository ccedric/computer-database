
package com.excilys.formation.java.computerdb.service.implementation;

import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.dao.implementation.CompanyDao;
import com.excilys.formation.java.computerdb.dao.implementation.ComputerDao;
import com.excilys.formation.java.computerdb.db.TransactionManager;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Company;
import com.excilys.formation.java.computerdb.service.Page;

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
public class CompanyService
    implements com.excilys.formation.java.computerdb.service.Service<Company> {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

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
  public int create(Company obj) {
    try {
      return companyDao.create(obj);
    } catch (NotImplementedException e) {
      return 0;
    } finally {
      TransactionManager.getInstance().remove();
    }
  }

  /**
   * Delete a company, and all his computers.
   */
  @Override
  @Transactional(readOnly = false)
  public void delete(Company obj) throws DatabaseConnectionException {
    TransactionManager transactionManager = TransactionManager.getInstance();
    try {
      transactionManager.setAutoCommit(false);
      computerDao.deleteByCompany(obj);
      companyDao.delete(obj);
      transactionManager.commit();
    } catch (Exception e) {
      LOGGER.error("Error while deleting the company and his computers");
      transactionManager.rollback();
    } finally {
      TransactionManager.getInstance().remove();
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
    } finally {
      TransactionManager.getInstance().remove();
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Company find(long id) throws DatabaseConnectionException {
    try {
      return companyDao.find(id);
    } catch (DaoSqlException | CompanyNotFoundException e) {
      LOGGER.info("Exception catched in the CompanyService find");
    } finally {
      TransactionManager.getInstance().remove();
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
    } finally {
      TransactionManager.getInstance().remove();
    }
    return null;
  }

  /**
   * Paging not implemented for Company, will return the same result as list().
   */
  @Override
  @Transactional(readOnly = true)
  public List<Company> listPage(Page page) throws DatabaseConnectionException {
    try {
      return companyDao.list();
    } catch (DaoSqlException | NotImplementedException e) {
      LOGGER.info("Exception catched in the CompanyService listPage");
    } finally {
      TransactionManager.getInstance().remove();
    }
    return null;
  }

  /**
   * Not implemented for Company.
   */
  @Override
  @Transactional(readOnly = true)
  public List<Company> findByName(String name) throws DatabaseConnectionException {
    try {
      return companyDao.findByName(name);
    } catch (NotImplementedException e) {
      return null;
    } finally {
      TransactionManager.getInstance().remove();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.excilys.formation.java.computerdb.service.Service#listPageByName(int, int,
   * java.lang.String)
   */
  @Override
  @Transactional(readOnly = true)
  public List<Company> listPageByName(Page page) throws DatabaseConnectionException {
    try {
      return companyDao.listPageByName(page.getPage() * page.getPageSize(), page.getPageSize(),
          page.getSearch(), page.getOrderSearch());
    } catch (NotImplementedException e) {
      return null;
    } finally {
      TransactionManager.getInstance().remove();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.excilys.formation.java.computerdb.service.Service#selectCount(java.lang.String)
   */
  @Override
  @Transactional(readOnly = true)
  public int selectCount(String name) {
    try {
      return companyDao.selectCount(name);
    } catch (NotImplementedException e) {
      return 0;
    } finally {
      TransactionManager.getInstance().remove();
    }
  }

}
