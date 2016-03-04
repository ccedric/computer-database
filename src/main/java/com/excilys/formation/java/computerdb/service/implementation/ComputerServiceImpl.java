package com.excilys.formation.java.computerdb.service.implementation;

import com.excilys.formation.java.computerdb.dao.ComputerDao;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.model.exception.ComputerInvalidException;
import com.excilys.formation.java.computerdb.model.validation.ComputerValidator;
import com.excilys.formation.java.computerdb.service.ComputerService;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.service.exception.ComputerServiceInvalidException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for the model Computer, call the DAO Computer.
 * 
 * @author Cédric Cousseran
 */
@Service
@Transactional
public class ComputerServiceImpl implements ComputerService {
  @Autowired
  private ComputerDao computerDao;

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerServiceImpl.class);

  public ComputerDao getComputerDao() {
    return computerDao;
  }

  public void setComputerDao(ComputerDao computerDao) {
    this.computerDao = computerDao;
  }

  @Override
  @Transactional(readOnly = false)
  public long create(Computer obj) {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while creating a new Computer, computer invalid ");
      throw new ComputerServiceInvalidException(
          "Error while creating the coputer, computer invalid", e);
    }

    return computerDao.create(obj);
  }

  @Override
  @Transactional(readOnly = false)
  public void delete(Computer obj) {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while creating a new Computer, computer invalid ");
      throw new ComputerServiceInvalidException(
          "Error while creating the coputer, computer invalid", e);
    }
    computerDao.delete(obj);
  }

  @Override
  @Transactional(readOnly = false)
  public void update(Computer obj) {
    try {
      ComputerValidator.validate(obj);
    } catch (ComputerInvalidException e) {
      LOGGER.error("Error while creating a new Computer, computer invalid ");
      throw new ComputerServiceInvalidException(
          "Error while creating the coputer, computer invalid", e);
    }

    try {
      computerDao.update(obj);
    } catch (ComputerNotFoundException | DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService update");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Computer find(long id) {
    return computerDao.find(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> list() {
    return computerDao.list();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> findByName(String name) {
    return computerDao.findByName(name);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> listPageByName(Page page) {
    return computerDao.listPageByName(page.getStartingIndex(), page.getPageSize(), page.getSearch(),
        page.getOrderSearch());
  }

  @Override
  @Transactional(readOnly = true)
  public int selectCount(String name) {
    return computerDao.selectCount(name);
  }

}
