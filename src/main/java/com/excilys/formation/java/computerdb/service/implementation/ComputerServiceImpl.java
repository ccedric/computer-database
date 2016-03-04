package com.excilys.formation.java.computerdb.service.implementation;

import com.excilys.formation.java.computerdb.dao.ComputerDao;
import com.excilys.formation.java.computerdb.dao.exception.ComputerDaoInvalidException;
import com.excilys.formation.java.computerdb.dao.exception.ComputerNotFoundException;
import com.excilys.formation.java.computerdb.dao.exception.DaoSqlException;
import com.excilys.formation.java.computerdb.db.exception.DatabaseConnectionException;
import com.excilys.formation.java.computerdb.model.Computer;
import com.excilys.formation.java.computerdb.service.ComputerService;
import com.excilys.formation.java.computerdb.service.Page;
import com.excilys.formation.java.computerdb.service.exception.TimestampDiscontinuedBeforeIntroducedException;

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
    if ((obj.getIntroduced() != null) && (obj.getDiscontinued() != null)
        && (obj.getIntroduced().isAfter(obj.getDiscontinued()))) {
      throw new TimestampDiscontinuedBeforeIntroducedException(
          "The discontinued timestamp is before the introduced timestamp");
    }
    try {
      return computerDao.create(obj);
    } catch (ComputerDaoInvalidException | DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService creation");
    }
    return 0;
  }

  @Override
  @Transactional(readOnly = false)
  public void delete(Computer obj) {
    try {
      computerDao.delete(obj);
    } catch (ComputerNotFoundException | DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService delete");
    }
  }

  @Override
  @Transactional(readOnly = false)
  public void update(Computer obj) {
    if ((obj.getIntroduced() != null) && (obj.getDiscontinued() != null)
        && (obj.getIntroduced().isAfter(obj.getDiscontinued()))) {
      throw new TimestampDiscontinuedBeforeIntroducedException(
          "The discontinued timestamp is before the introduced timestamp");
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
    try {
      return computerDao.find(id);
    } catch (DaoSqlException | ComputerNotFoundException e) {
      LOGGER.info("Exception catched in the ComputerService find");
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> list() {
    try {
      return computerDao.list();
    } catch (DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService list");
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> findByName(String name) {
    try {
      return computerDao.findByName(name);
    } catch (DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService findByName");
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Computer> listPageByName(Page page) {
    try {
      return computerDao.listPageByName(page.getStartingIndex(), page.getPageSize(),
          page.getSearch(), page.getOrderSearch());
    } catch (DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService listPageByName");
    }
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public int selectCount(String name) {
    try {
      return computerDao.selectCount(name);
    } catch (DatabaseConnectionException | DaoSqlException e) {
      LOGGER.info("Exception catched in the ComputerService selectCount");
    }
    return 0;
  }

}
