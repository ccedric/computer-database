package com.excilys.formation.java.computerdb.service.implementation;

import com.excilys.formation.java.computerdb.dao.CompanyDao;
import com.excilys.formation.java.computerdb.dao.ComputerDao;
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
  private CompanyDao companyDao;
  @Autowired
  private ComputerDao computerDao;

  /**
   * Create a company.Not yet implemented, so return 0
   */
  @Override
  public long create(Company obj) {
    return companyDao.create(obj);
  }

  /**
   * Delete a company, and all his computers.
   */
  @Override
  @Transactional(readOnly = false)
  public void delete(Company obj) {
    computerDao.deleteByCompany(obj);
    companyDao.delete(obj);
    LOGGER.info("Company and all his computers associated deleted");
  }

  /**
   * Not yet implemented, so return false.
   */
  @Override
  public void update(Company obj) {
    companyDao.update(obj);
  }

  @Override
  public Company find(long id) {
    return companyDao.find(id);
  }

  @Override
  public List<Company> list() {
    return companyDao.list();
  }
}
