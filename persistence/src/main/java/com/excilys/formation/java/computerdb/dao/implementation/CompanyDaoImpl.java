package com.excilys.formation.java.computerdb.dao.implementation;

import com.excilys.formation.java.computerdb.dao.CompanyDao;
import com.excilys.formation.java.computerdb.dao.exception.CompanyDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.CompanyNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.NotImplementedException;
import com.excilys.formation.java.computerdb.model.Company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
  private SessionFactory sessionFactory;

  @Override
  public long create(Company obj) {
    LOGGER.warn("Call to the method create of CompanyDao, wich is not implemented");
    throw new NotImplementedException(
        "The create method for the dao company has not yet been implemented");
  }

  @Override
  public void delete(Company obj) {
    Session session = sessionFactory.getCurrentSession();
    if (null == obj) {
      throw new CompanyDaoInvalidException("Tried to delete a null company");
    }
    session.delete(obj);
    LOGGER.info("Company deleted, id {}, name {}", obj.getId(), obj.getName());
  }

  @Override
  public void update(Company obj) {
    LOGGER.warn("Call to the method update of CompanyDao, wich is not implemented");
    throw new NotImplementedException(
        "The update method for the dao company has not yet been implemented");
  }

  @Override
  public Company find(long id) {
    Session session = sessionFactory.getCurrentSession();
    Company company = (Company) session.get(Company.class, id);
    if (company == null ) {
      LOGGER.warn("Company not found with the id {}",id);
      throw new CompanyNotFoundException("Company not found with the id ");
    } else {
      LOGGER.info("Company found, id: {}, name: {}", company.getId(), company.getName());
      return company;
    }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Company> list() {
    Session session = sessionFactory.getCurrentSession();

    List<Company> companies = session.createCriteria(Company.class, "company")
        .list();
    LOGGER.info("List of Companies found, size of the list: {}", companies.size());
    return companies;
  }
}
